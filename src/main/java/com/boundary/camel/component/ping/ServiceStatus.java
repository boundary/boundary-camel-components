package com.boundary.camel.component.ping;

import java.util.Date;

public abstract class ServiceStatus {
	
	private Status status;
	private String message;
	private Date timeStamp;
	
	public ServiceStatus() {
		status = Status.SUCCESS;
		message = "OK";
		timeStamp = new Date();
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Status getStatus() {
		return this.status;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setTimestamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public Date getTimestamp() {
		return this.timeStamp;
	}
}
