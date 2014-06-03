package com.boundary.camel.component.ping;

public class SocketCheck implements ServiceCheck {
	
	private String host;

	public SocketCheck() {
		
	}
	
	protected SocketStatus executeCheck() {
		SocketStatus status = new SocketStatus();
		return status;
	}

	public SocketStatus performCheck() {
		SocketStatus status = new SocketStatus();
		return status;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public String getHost() {
		return this.host;
	}
}
