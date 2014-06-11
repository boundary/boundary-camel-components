package com.boundary.camel.component.ping;

import java.net.URI;

import org.apache.camel.RuntimeCamelException;
import org.apache.camel.spi.UriParam;

import com.boundary.camel.component.common.ServiceCheckBaseConfiguration;
import com.boundary.camel.component.port.PortConfiguration;

public class PingConfiguration extends ServiceCheckBaseConfiguration implements Cloneable {

	public final long DEFAULT_PACKET_SIZE = 0;
	public final long DEFAULT_WAIT_TIME = 5000;
	
    @UriParam
	private long waitTime = DEFAULT_WAIT_TIME;
    
    @UriParam
	private long packetSize = DEFAULT_WAIT_TIME;

	public PingConfiguration() {
		
	}
	
	public PingConfiguration(URI uri) {
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
        String [] userInfo = uri.getUserInfo().split(":");
        setUser(userInfo[0]);
        setPassword(userInfo[1]);
        setPath(uri.getPath());
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
	
	public String toString() {
		StringBuffer s = new StringBuffer();
		
		s.append("host=" + getHost());
		s.append(",port=" + getPort());
		s.append(",path=" + getPath());
		s.append(",user=" + getUser());
		s.append(",password=" + getPassword());
		s.append(",timeout=" + getTimeout());

		return s.toString();
	}
}
