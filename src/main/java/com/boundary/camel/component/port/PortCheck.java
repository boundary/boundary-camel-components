package com.boundary.camel.component.port;

import com.boundary.camel.component.ping.ServiceCheck;

public class PortCheck extends ServiceCheck {
	
	public PortCheck() {
		
	}
	
	protected PortInfo executeCheck() {
		PortInfo status = new PortInfo();
		return status;
	}

	public PortInfo performCheck() {
		PortInfo status = new PortInfo();
		return status;
	}
}
