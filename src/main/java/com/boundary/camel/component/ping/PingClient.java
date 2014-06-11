package com.boundary.camel.component.ping;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.camel.component.common.ServiceCheck;
import com.boundary.camel.component.common.ServiceInfo;
import com.boundary.camel.component.common.ServiceStatus;

/**
 * Implements a service check by using the ping command found in most *inix environments
 * 
 * Output from the {@link PingClient} is a instance of {@link PingInfo}
 * 
 * @author davidg
 *
 */
public class PingClient extends ServiceCheck {
	
    private static final Logger LOG = LoggerFactory.getLogger(PingClient.class);

	private long waitTime;
	private long packetSize;

	
	private String pingCommand = null;
	
	private File [] unixPingPaths = {new File("/bin/ping"), new File("/usr/bin/ping"), new File("/sbin/ping")};
	
	private final static String TRANSMITTED_RECEIVED_REG_EX = "^(\\d+)\\s\\w+\\s\\w+\\W\\s(\\d+)\\.*";
	private final static String RTT_REG_EX = "(\\d+\\.\\d+)\\/(\\d+\\.\\d+)\\/(\\d+\\.\\d+)\\/(\\d+\\.\\d+)";
	private final static String NO_ROUTE_TO_HOST_REG_EX = "[Uu]nknown\\shost";
	private final static String UNKNOWN_HOST_REG_EX = "^ping:\\s([\\w\\W\\s]*)";


	/**
	 * Default constructor
	 */
	public PingClient() {

	}

	/**
	 * Get the absolute path to the ping command.
	 * Defaults to the bare ping command that uses
	 * the PATH variable to find the executable.
	 * 
	 * @return
	 */
	public String findPingCommand() {
		// Default to letting
		String s = "ping";

		// Loop through our defined well-known paths
		// to find the path to the ping command
		for (File path: unixPingPaths) {
			if (path.canExecute()) {
				s = path.getAbsolutePath();
				LOG.debug("Path to ping: {}",s);
			}
		}

		return s;
	}
	
	/**
	 * Determines if the ping command exists and
	 * where it is located.
	 * 
	 * return String
	 */
	protected String getPingCommand() {
		String s;
		
		// Use the location of the ping command that was
		// explicitly set by configuration.
		if (pingCommand != null) {
			s = pingCommand;
		}
		else {
			s = findPingCommand();
		}

		return s;
	}

	/**
	 * Create the {@link CommandLine} instance needed to execute
	 * ping on the platform we are running on.
	 * 
	 * TBD: Currently only handles *nix platforms.
	 * 
	 * @return CommandLine
	 */
	protected CommandLine configureCommandline(PingConfiguration configuration) {

		// TBD: Override command used
		Map<String, String> map = new HashMap<String, String>();
		map.put("host", configuration.getHost());
		CommandLine commandLine = new CommandLine(getPingCommand());
		commandLine.addArgument("-c");
		commandLine.addArgument("2");
		commandLine.addArgument("-q");
		commandLine.addArgument("${host}");
		commandLine.setSubstitutionMap(map);
		
		return commandLine;
	}

	/**
	 * Create a {@link List} of the output from a {@link ByteArrayOutputStream}
	 * @param out
	 * @param err
	 * @return List<String>
	 */
	protected List<String> getStringOutput(ByteArrayOutputStream stream) {
		List<String> lines = new ArrayList<String>();
	
		byte[] content = stream.toByteArray();
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;
		try {
			inputStream = new ByteArrayInputStream(content);
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return lines;
	}
	
	/**
	 * Parses the output of ping and populates a instance of {@link PingStatus}
	 * @param lines
	 * @return PingSTatus
	 */
	protected PingInfo parse(int exitValue, List<String> outLines,List<String> errLines) {
		PingInfo info = new PingInfo();
		Pattern roundTripTimePat = Pattern.compile(RTT_REG_EX);
		Pattern transmitReceivePat = Pattern.compile(TRANSMITTED_RECEIVED_REG_EX);
		Pattern noRouteToHostPat = Pattern.compile(NO_ROUTE_TO_HOST_REG_EX);
		Pattern unknownHostPat = Pattern.compile(UNKNOWN_HOST_REG_EX);

		info.setHost(getHost());

		// Parse the output based on exit value of ping
		switch (exitValue) {

		// Clean exit
		case 0:
			// Extract the RTT times
			for (String line : outLines) {
				Matcher matcher = roundTripTimePat.matcher(line);
				if (matcher.find()) {
					info.setRTTMin(Double.parseDouble(matcher.group(1)));
					info.setRTTAvg(Double.parseDouble(matcher.group(2)));
					info.setRTTMax(Double.parseDouble(matcher.group(3)));
					info.setRTTMDev(Double.parseDouble(matcher.group(4)));
				}
			}
			// Extract the transmit and received counts
			for (String line : outLines) {
				Matcher matcher = transmitReceivePat.matcher(line);
				if (matcher.find()) {
					info.setTransmitted(Integer.parseInt(matcher.group(1)));
					info.setReceived(Integer.parseInt(matcher.group(2)));
				}
			}
			
			// If no ICMP packages are returned then consider the test failed
			if (info.getReceived() == 0) {
				info.setStatus(ServiceStatus.FAIL);
			}
			else {
				info.setStatus(ServiceStatus.SUCCESS);
			}
			break;
		// Error case: 1) Unable to resolve host ; 2) Host unreachable
		case 1:
		case 2:
		case 68:
			for (String line : outLines) {
				Matcher matcher = transmitReceivePat.matcher(line);
				if (matcher.find()) {
					info.setTransmitted(Integer.parseInt(matcher.group(1)));
					info.setReceived(Integer.parseInt(matcher.group(2)));
				}
			}
			
			for (String line: errLines) {
				Matcher matcher = unknownHostPat.matcher(line);
				if (matcher.find()) {
					info.setMessage(matcher.group(1));
				}
			}
			
			info.setStatus(ServiceStatus.FAIL);
			break;
		default:
			assert false: "Unknown exit code";
		}

		return info;
	}

	protected PingInfo ping(PingConfiguration configuration) {
		PingInfo info = null;

		// Configure our command line based on the OS
		// we are running on.
		CommandLine commandLine = configureCommandline(configuration);

		// Create the executor and set the expected exit
		// value of 0,2
		DefaultExecutor executor = new DefaultExecutor();
				
		// 1) Execute the ping command
		// 2) Get the output from the command
		// 3) Parse the output
		// 4) Assign to the PingStatus
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteArrayOutputStream err = new ByteArrayOutputStream();

			PumpStreamHandler handler = new PumpStreamHandler(out, err);
			int exitValues[] = { 0, 1, 2, 68 };
			executor.setExitValues(exitValues);
			executor.setStreamHandler(handler);
			int exitValue = executor.execute(commandLine);
			List<String> outLines = getStringOutput(out);
			List<String> errLines = getStringOutput(err);
 			info = parse(exitValue,outLines,errLines);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return info;
	}

	public static PingClient setUpDefaultClient() {
		return new PingClient();
	}
}
