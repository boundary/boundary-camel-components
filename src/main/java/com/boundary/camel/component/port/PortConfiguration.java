package com.boundary.camel.component.port;

import java.net.URI;

import org.apache.camel.RuntimeCamelException;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriParams;

@UriParams
public class PortConfiguration implements Cloneable {

	private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 7;
	private static final int DEFAULT_TIMEOUT = 5000;
	private static final String DEFAULT_PATH = "/tcp";
	
    @UriParam
    private String host = DEFAULT_HOST;
    
    @UriParam
    private int port = DEFAULT_PORT;
    
    @UriParam
    private int timeOut = DEFAULT_TIMEOUT;
    
	private String path = DEFAULT_PATH;
    
    public PortConfiguration() {
    }
    
    public PortConfiguration(URI uri) {
        configure(uri);
    }

    public void configure(URI uri) {
        // UserInfo can contain both username and password as: user:pwd@sshserver
        // see: http://en.wikipedia.org/wiki/URI_scheme

        setHost(uri.getHost());

        // URI.getPort returns -1 if port not defined, else use default port
        int uriPort = uri.getPort();
        if (uriPort != -1) {
            setPort(uriPort);
        }
        setPath(uri.getPath());
    }

    public PortConfiguration copy() {
        try {
            return (PortConfiguration) clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeCamelException(e);
        }
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
	
	static public PortConfiguration getConfiguration(String host,int port,String path,int timeOut) {
		PortConfiguration configuration = new PortConfiguration();
		configuration.setHost(host);
		configuration.setPort(port);
		configuration.setPath(path);
		configuration.setTimeout(timeOut);
		return configuration;
	}
	
	static public PortConfiguration getConfiguration(String host,int port) {
		PortConfiguration configuration = new PortConfiguration();
		configuration.setHost(host);
		configuration.setPort(port);
		return configuration;
	}
	
	static public PortConfiguration getConfiguration(String host,int port,int timeOut) {
		PortConfiguration configuration = new PortConfiguration();
		configuration.setHost(host);
		configuration.setPort(port);
		configuration.setTimeout(timeOut);
		return configuration;
	}
	
	public String toString() {
		StringBuffer s = new StringBuffer();
		
		s.append("host=" + host);
		s.append(",port=" + port);
		s.append(",timeout=" + timeOut);
		
		return s.toString();
	}
}

