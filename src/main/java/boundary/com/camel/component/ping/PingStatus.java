package boundary.com.camel.component.ping;

public class PingStatus implements ServiceStatus {
	
	private int ttl;
	private int transmitted;
	private int received;
	private double time;
	private double rttMin;
	private double rttAvg;
	private double rttMax;
	private double rttMDev;

	public PingStatus() {
		
	}
	
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
}
