package com.boundary.camel.component.port;

import com.boundary.camel.component.common.ServiceCheck;

public class PortCheck extends ServiceCheck {
	
	TCPClient client;
	private int timeOut;
	
	
	public PortCheck() {
		client = new TCPClient();
	}
	
	protected PortInfo executeCheck() {
		PortInfo status = new PortInfo();
		return status;
	}

	public PortInfo performCheck() {
		PortInfo status = new PortInfo();
		return status;
	}
	
	public void setPort(int port) {
		client.setPort(port);
	}
	
	public int getPort() {
		return client.getPort();
	}
	
	public void setTimeout(int timeOut) {
		this.timeOut = timeOut;
	}
	
	public int getTimeout() {
		return this.timeOut;
	}
}
