package com.boundary.camel.component.common;

/**
 * 
 * @author davidg
 *
 */
public class ServiceInfo {
	
	private ServiceStatus status = ServiceStatus.SUCCESS;
	private String message = "OK";
	private String host = "localhost";
	private int port = 7;
	private int timeOut = 5000;
	
	public ServiceInfo() {
	}
	
	public void setStatus(ServiceStatus status) {
		this.status = status;
	}
	
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

	public void setTimeout(int timeOut) {
		this.timeOut = timeOut;
	}
	
	public int getTimeout() {
		return this.timeOut;
	}


}
