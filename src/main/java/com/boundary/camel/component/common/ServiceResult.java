package com.boundary.camel.component.common;

import java.util.Date;

/**
 * 
 * @author davidg
 *
 */
public class ServiceResult {
	
	private ServiceStatus status = ServiceStatus.SUCCESS;
	private String message = "OK";
	private String host = "localhost";
	private int port = 7;
	private int timeOut = 5000;
	private Date timeStamp = new Date();
	
	public ServiceResult() {
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
	
	public void setTimestamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public Date getTimestamp() {
		return this.timeStamp;
	}
}
