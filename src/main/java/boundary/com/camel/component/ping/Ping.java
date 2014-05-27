package boundary.com.camel.component.ping;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Ping {

	public Ping() {
		// TODO Auto-generated constructor stub
	}

	public static PingResults ping(PingArguments args) {
		Ping ping = new Ping();
		PingResults results = ping.executePing(args);

		return results;
	}

	public PingResults executePing(PingArguments args) {

		return null;
	}

	public List<String> executeProcess(String command) {
		try {

			Process p;

			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			// int exitValue = p.exitValue();
			// System.err.println("exitValue=" + exitValue);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			String s;

			List<String> lines = new ArrayList<String>();
			while ((s = stdInput.readLine()) != null) {
				lines.add(s);
				// System.err.println(s);
			}

			p.destroy();
			return lines;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
