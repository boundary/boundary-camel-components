package boundary.com.camel.component.ping;

/**
 * Contains information regarding the results of sending an ICMP ping to a host.
 * @author davidg
 *
 */
public class PingStatus extends ServiceStatus {
	
	private String host;
	private int ttl;
	private int transmitted;
	private int received;
	private double time;
	private double rttMin;
	private double rttAvg;
	private double rttMax;
	private double rttMDev;

	public PingStatus() {
		host = "localhost";
	}
	
	public void setHost(String host) {
		this.host = host;
	}

	public String getHost() {
		return this.host;
	}
	
	/**
	 * Sets the TTL (Time To Live) on the ICMP packet sent to the host.
	 * 
	 * @param ttl
	 */
	public void setTTL(int ttl) {
		this.ttl = ttl;
	}
	
	public int getTTL() {
		return this.ttl;
	}

	public void setTransmitted(int transmitted) {
		this.transmitted = transmitted;
	}

	public int getTransmitted() {
		return transmitted;
	}

	public void setReceived(int received) {
		this.received = received;
	}

	public int getReceived() {

		return received;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public double getTime() {
		return this.time;
	}

	public void setRTTMin(double rttMin) {
		this.rttMin = rttMin;
	}
	
	public double getRTTMin() {
		return this.rttMin;
	}

	public void setRTTAvg(double rttAvg) {
		this.rttAvg = rttAvg;
	}

	public double getRTTAvg() {
		return this.rttAvg;
	}

	public void setRTTMax(double rttMax) {
		this.rttMax = rttMax;
	}

	public double getRTTMax() {
		return this.rttMax;
	}

	public void setRTTMDev(double rttMDev) {
		this.rttMDev = rttMDev;
	}

	public double getRTTMDev() {
		return this.rttMDev;
	}
	
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("host: " + host);
		s.append(",transmitted: " + transmitted);
		s.append(",received: " + received);
		s.append(",rttMin: " + rttMin);
		s.append(",rttAvg: " + rttAvg);
		s.append(",rttMax: " + rttMax);
		s.append(",rttMDev: " + rttMDev);
		
		return s.toString();
	}
}
