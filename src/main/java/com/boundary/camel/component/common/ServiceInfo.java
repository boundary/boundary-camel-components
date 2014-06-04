package com.boundary.camel.component.common;

/**
 * 
 * @author davidg
 *
 */
public class ServiceInfo {
	
	private ServiceStatus status;
	private String message;
	
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

}
