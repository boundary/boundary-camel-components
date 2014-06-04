package com.boundary.camel.component.ping;

public class HttpCheck extends ServiceCheck {
	
	public HttpCheck() {
		
	}
	
	public HttpInfo performCheck() {
		HttpInfo info = new HttpInfo();
		return info;
	}
}
