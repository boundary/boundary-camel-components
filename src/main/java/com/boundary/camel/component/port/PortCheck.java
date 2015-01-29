package com.boundary.camel.component.port;

import com.boundary.camel.component.common.ServiceCheck;

public class PortCheck extends ServiceCheck {
	
	TCPClient client;
	private int timeOut;
	
	
	public PortCheck() {
		client = new TCPClient();
	}
	
	protected PortResult executeCheck() {
		PortResult status = new PortResult();
		return status;
	}

	public PortResult performCheck() {
		PortResult status = new PortResult();
		return status;
	}
}
