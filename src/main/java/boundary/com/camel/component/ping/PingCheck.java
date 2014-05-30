package boundary.com.camel.component.ping;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
/**
 * Implements a service check by using the ping command found in most *inix environments
 * 
 * Output from the {@link PingCheck} is a instance of {@link PingStatus}
 * 
 * @author davidg
 *
 */
public class PingCheck implements ServiceCheck {

	private long waitTime;
	private long packetSize;
	private String host;
	
	private String pingCommand = null;
	
	private final static String TRANSMITTED_RECEIVED_REG_EX = "^(\\d+)\\s\\w+\\s\\w+\\W\\s(\\d+)\\.*";
	private final static String RTT_REG_EX = "(\\d+\\.\\d+)\\/(\\d+\\.\\d+)\\/(\\d+\\.\\d+)\\/(\\d+\\.\\d+)";


	public PingCheck() {
		// Set defaults
		host = "localhost";
	}

	public PingStatus performCheck() {
		PingStatus status;

		status = executeCheck();

		return status;
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
		// explictly set by configuration.
		if (pingCommand != null) {
			s = pingCommand;
		}
		else {
			// TODO: File tests to see where the ping command exists
			s = "ping";
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
	protected CommandLine configureCommandline() {

		// TBD: Override command used
		Map<String, String> map = new HashMap<String, String>();
		map.put("host", getHost());
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
	protected PingStatus parse(List<String> lines) {
		PingStatus status = new PingStatus();
		Pattern rttPat = Pattern.compile(RTT_REG_EX);
		
		// Extract the RTT times
		for(String line: lines) {
			Matcher matcher = rttPat.matcher(line);
			if (matcher.find()) {
				status.setRTTMin(Double.parseDouble(matcher.group(1)));
				status.setRTTAvg(Double.parseDouble(matcher.group(2)));
				status.setRTTMax(Double.parseDouble(matcher.group(3)));
				status.setRTTMDev(Double.parseDouble(matcher.group(4)));
			}
		}
		
		// Extract the transmit and received counts
		Pattern trPat = Pattern.compile(TRANSMITTED_RECEIVED_REG_EX);
		for(String line: lines) {
			System.out.println(line);
			Matcher matcher = trPat.matcher(line);
			if (matcher.find()) {
				status.setTransmitted(Integer.parseInt(matcher.group(1)));
				status.setReceived(Integer.parseInt(matcher.group(2)));
			}
		}
		
		return status;
	}

	protected PingStatus executeCheck() {
		PingStatus status = null;

		// Configure our command line based on the OS
		// we are running on.
		CommandLine commandLine = configureCommandline();

		// Create the executor and set the expected exit
		// value of 0.
		DefaultExecutor executor = new DefaultExecutor();
		executor.setExitValue(0);
		
		// Add a watchdog. TBD: Maybe dead code
		ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
		executor.setWatchdog(watchdog);
		
		// 1) Execute the ping command
		// 2) Get the output from the command
		// 3) Parse the output
		// 4) Assign to the PingStatus
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteArrayOutputStream err = new ByteArrayOutputStream();

			PumpStreamHandler handler = new PumpStreamHandler(out, err);
			executor.setExitValue(0);
			executor.setStreamHandler(handler);
			int exitValue = executor.execute(commandLine);
			List<String> lines = getStringOutput(out);
			
			status = parse(lines);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	/**
	 * 
	 * @param waitTime
	 */
	void setWaitTime(long waitTime) {
		this.waitTime = waitTime;
	}

	public long getWaitTime() {
		return waitTime;
	}

	public void setPacketSize(long packetSize) {
		this.packetSize = packetSize;
	}

	public long getPacketSize() {
		return this.packetSize;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getHost() {
		return this.host;
	}
}
