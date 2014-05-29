package boundary.com.camel.component.ping;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PingCheck implements ServiceCheck {

	private long waitTime;
	private long packetSize;
	private String host;

	public PingCheck() {
		// Set defaults
		host = "localhost";
	}

	public PingStatus performCheck() {
		PingStatus status = new PingStatus();
		
		// Set up bogus information to get started
		status.setHost(host);
		status.setRTTMax(3456.78);

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
