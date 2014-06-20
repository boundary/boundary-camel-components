package com.boundary.camel.component.ssh;

import java.net.URI;

import org.apache.camel.RuntimeCamelException;

import com.boundary.camel.component.common.ServiceCheckBaseConfiguration;

public class SshxConfiguration extends ServiceCheckBaseConfiguration implements Cloneable {
	
	
	private String command;


	public SshxConfiguration() {
		
	}
	
	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	
	/**
	 * Initialize a {@link SshxConfiguration} instance from a url
	 * 
	 * @param uri {@link URI} use to configure the {@link SshxConfiguration}
	 */
	public SshxConfiguration(URI uri) {
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
    
    public SshxConfiguration copy() {
        try {
            return (SshxConfiguration) clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeCamelException(e);
        }
    }

	
	/**
	 * Returns a {@link String} with the internal representation
	 * of the instance.
	 * 
	 * @return {@link String} String contents of {@link SshxConfiguration}
	 */
	public String toString() {
		StringBuffer s = new StringBuffer();
		
		s.append(getHost() == null ?  "" : "host=" + getHost());
		s.append(getPort() == 0 ?  "" : ",port=" + getPort());
		s.append(getPath() == null ?  "" : ",path=" +  getPath());
		s.append(getUser() == null ?  "" : ",user=" + getUser());
		s.append(getPassword() == null ? "" : ",password=" + getPassword());
		s.append(getTimeout() == 0 ? "" : ",timeout=" + getTimeout());
		s.append(getCommand() == null ? "" : ",command=" + getCommand());

		return s.toString();
	}
	
	public static SshxConfiguration getConfiguration(String host,int timeOut) {
		SshxConfiguration configuration = new SshxConfiguration();
		
		configuration.setHost(host);
		configuration.setTimeout(timeOut);
		
		return configuration;
	}
	
	public static SshxConfiguration getConfiguration(String host) {
		return getConfiguration(host,DEFAULT_TIMEOUT);
	}
	
	public static SshxConfiguration getConfiguration() {
		return getConfiguration(DEFAULT_HOST,DEFAULT_TIMEOUT);
	}
}
