package com.boundary.camel.component.ping;

/**
 * Interface to that defines service checks.
 * 
 * @author davidg
 *
 */
public abstract class ServiceCheck {
		
	private String host;
	
	public ServiceCheck() {
		// Set default host
		host = "localhost";
	}
	
	public void setHost(String host) {
		this.host = host;
	}

	public String getHost() {
		return this.host;
	}
	
}
