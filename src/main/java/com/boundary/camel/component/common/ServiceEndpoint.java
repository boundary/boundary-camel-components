package com.boundary.camel.component.common;

import org.apache.camel.Component;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.ScheduledPollEndpoint;
import org.apache.camel.Endpoint;

public class ServiceEndpoint extends ScheduledPollEndpoint {
	
	private String host;
	private int port;

	public ServiceEndpoint() {
	}

	public ServiceEndpoint(String endpointUri, Component component) {
		super(endpointUri, component);
	}

    @SuppressWarnings("deprecation")
	public ServiceEndpoint(String endpointUri) {
		super(endpointUri);
	}

	@Override
	public Consumer createConsumer(Processor arg0) throws Exception {
		return null;
	}

	@Override
	public Producer createProducer() throws Exception {
		return null;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}
	
	/**
	 * Sets the host associated with this services {@link Endpoint}
	 * 
	 * @param host Host to be set on this {@link Endpoint}
	 */
    public void setHost(String host) {
    	this.host = host;
    }
    
    /**
     * Get the host associated with this service's {@link Endpoint}
     * 
     * @return {@link String} Host associated with this {@link Endpoint}
     */
    public String getHost() {
    	return this.host;
    }
    
    /**
     * Sets the port associated with this {@link Endpoint}
     * @param port Port associated with this {@link Endpoint}
     */
    public void setPort(int port) {
    	this.port = port;
    }
    
    /**
     * Returns the port of this {@link Endpoint}
     * @return int Port associated with the {@link Endpoint}
     */
    public int getPort() {
    	return this.port;
    }
}
