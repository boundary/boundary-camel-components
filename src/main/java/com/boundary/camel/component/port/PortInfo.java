package com.boundary.camel.component.port;

import com.boundary.camel.component.common.ServiceInfo;

public class PortInfo extends ServiceInfo {
	
	private PortStatus portStatus;

	PortInfo() {
		portStatus = PortStatus.CONNECTED;
	}

	public void setPortStatus(PortStatus portStatus) {
		this.portStatus = portStatus;
	}
	
	public PortStatus getPortStatus() {
		return this.portStatus;
	}
	
	public String toString() {
		StringBuffer s = new StringBuffer();
		
		s.append("host=" + getHost());
		s.append(",port=" + getPort());
		s.append(",timeout=" + getTimeout());
		s.append(",portStatus=" + getPortStatus().toString());
		
		return s.toString();

	}
}
