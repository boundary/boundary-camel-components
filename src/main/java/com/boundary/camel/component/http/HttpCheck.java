package com.boundary.camel.component.http;

import com.boundary.camel.component.common.ServiceCheck;

public class HttpCheck extends ServiceCheck {
	
	public HttpCheck() {
		
	}
	
	public HttpInfo performCheck() {
		HttpInfo info = new HttpInfo();
		return info;
	}	
}
