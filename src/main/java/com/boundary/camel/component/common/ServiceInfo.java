package com.boundary.camel.component.common;

/**
 * 
 * @author davidg
 *
 */
public class ServiceInfo {
	
	private ServiceStatus status;
	private String message;
	private String host;
	private int port;
	
	public ServiceInfo() {
		status = ServiceStatus.SUCCESS;
	}
	
	public void setStatus(ServiceStatus status) {}
	
	public ServiceStatus getStatus() {
		return this.status;
	}
	
	public void setMessage(String message) {
		this.message = message;
	} 
	
	public String getMessage() {
		return this.message;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public String getHost() {
		return this.host;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public int getPort() {
		return this.port;
	}


}
