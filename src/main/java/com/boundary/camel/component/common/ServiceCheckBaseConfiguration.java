package com.boundary.camel.component.common;

import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriParams;

/**
 * Rolls up and common data and operations need for {@link ServiceCheck} configuration.
 * 
 * @author davidg
 *
 */

@UriParams
public class ServiceCheckBaseConfiguration implements ServiceCheckConfiguration {
	
	public static final String DEFAULT_HOST="localhost";
	public static final int DEFAULT_PORT = 7;
	public static final int DEFAULT_TIMEOUT = 5000;
	
	private String host = DEFAULT_HOST;
	private int port = DEFAULT_PORT;
    
    @UriParam
    private int timeOut = DEFAULT_TIMEOUT;
    
	private String path;
	private String user;
	private String password;

	public ServiceCheckBaseConfiguration() {
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
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getUser() {
		return this.user;
	}
	
	public void setPassword(String user) {
		this.password = user;
	}
	
	public String getPassword() {
		return this.password;
	}
}
