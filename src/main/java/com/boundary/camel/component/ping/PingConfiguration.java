package com.boundary.camel.component.ping;

import java.net.URI;

import org.apache.camel.RuntimeCamelException;
import org.apache.camel.spi.UriParam;

import com.boundary.camel.component.common.ServiceCheckBaseConfiguration;

public class PingConfiguration extends ServiceCheckBaseConfiguration implements Cloneable {

	private final long DEFAULT_PACKET_SIZE = 56;
	private final long DEFAULT_WAIT_TIME = 5000;
	private final int DEFAULT_COUNT = 2;
	
    @UriParam
	private long waitTime = DEFAULT_WAIT_TIME;
    
    @UriParam
	private long packetSize = DEFAULT_WAIT_TIME;
    
    @UriParam
    private int count = DEFAULT_COUNT;

	public PingConfiguration() {
		
	}
	
	/**
	 * Initialize a {@link PingConfiguration} instance from a url
	 * 
	 * @param uri {@link URI} use to configure the {@link PingConfiguration}
	 */
	public PingConfiguration(URI uri) {
		configure(uri);
	}
	
	/**
	 * Splits out the parts of the user info string from the {@link URL} class
	 * 
	 * @param userInfo {@link String} of the form "user:password"
	 */
	private void setUserInfo(String userInfo) {
		if (userInfo != null) {
			// Split the user info base using a :
			String[] s = userInfo.split(":");

			// User is the first string if there are one or more
			// parts to the user info.
			if (s.length >= 1) {
				setUser(s[0]);
			}
			
			// If we have two parts in the user info the second is the password
			if (s.length == 2) {
				setPassword(s[1]);
			}
		}
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
        setUserInfo(uri.getUserInfo());
		setPath(uri.getPath());
		
		// TODO: Add support for the query parameters
    }
    
    public PingConfiguration copy() {
        try {
            return (PingConfiguration) clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeCamelException(e);
        }
    }

	/**
	 * 
	 * @param waitTime
	 */
	void setWaitTime(long waitTime) {
		this.waitTime = waitTime;
	}

	public long getWaitTime() {
		return waitTime;
	}

	public void setPacketSize(long packetSize) {
		this.packetSize = packetSize;
	}

	public long getPacketSize() {
		return this.packetSize;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public int getCount() {
		return this.count;
	}
	
	/**
	 * Returns a {@link String} with the internal representation
	 * of the instance.
	 * 
	 * @return {@link String} String contents of {@link PingConfiguration}
	 */
	public String toString() {
		StringBuffer s = new StringBuffer();
		
		s.append(getHost() == null ?  "" : "host=" + getHost());
		s.append(getPort() == 0 ?  "" : ",port=" + getPort());
		s.append(getPath() == null ?  "" : ",path=" +  getPath());
		s.append(getUser() == null ?  "" : ",user=" + getUser());
		s.append(getPassword() == null ? "" : ",password=" + getPassword());
		s.append(getTimeout() == 0 ? "" : ",timeout=" + getTimeout());

		return s.toString();
	}
	
	public static PingConfiguration getConfiguration(String host,int timeOut) {
		PingConfiguration configuration = new PingConfiguration();
		
		configuration.setHost(host);
		configuration.setTimeout(timeOut);
		
		return configuration;
	}
	
	public static PingConfiguration getConfiguration(String host) {
		return getConfiguration(host,DEFAULT_TIMEOUT);
	}
	
	public static PingConfiguration getConfiguration() {
		return getConfiguration(DEFAULT_HOST,DEFAULT_TIMEOUT);
	}
}
