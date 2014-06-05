package com.boundary.camel.component.port;

import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriParams;

@UriParams
public class PortConfiguration implements Cloneable {
	
    @UriParam
    private String host;
    
    @UriParam
    private int port;
    
    @UriParam
    private int timeOut;

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

