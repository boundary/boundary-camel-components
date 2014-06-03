package com.boundary.camel.component.ping;

import java.util.concurrent.TimeUnit;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.ScheduledPollEndpoint;

/**
 * Implements a Camel endpoint of checking the health of network services
 */
public class PingEndpoint extends ScheduledPollEndpoint {
	
	public final ServiceCheckType DEFAULT_SERVICE_CHECK = ServiceCheckType.PING;
	
	private ServiceCheckType serviceCheckType;
	private String host;

	/**
	 * Configure the default values for the end point
	 */
    public PingEndpoint() {
        setDefaults();
    }

    public PingEndpoint(String uri, PingComponent component) {
        super(uri, component);
        setDefaults();
     }

    @SuppressWarnings("deprecation")
	public PingEndpoint(String endpointUri) {
        super(endpointUri);
        setDefaults();
    }
    
    private void setDefaults() {
    	serviceCheckType = DEFAULT_SERVICE_CHECK;
    }

    /**
     * Creates the producer for the component. TBD if this needed for the service check component
     */
    public Producer createProducer() throws Exception {
        return new PingProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
    	PingConsumer consumer = new PingConsumer(this, processor);
        return consumer;
    }

    public boolean isSingleton() {
        return true;
    }
 
    /**
     * Internal method to get the type of service check used on this end point.
     * @return ServiceCheckType
     */
    protected ServiceCheckType getServiceCheckType() {
    	return this.serviceCheckType;
    }

    
    /**
     * These member functions handle the URL parameters
     * to be configured on this EndPoint.
     */
        
    public void setType(String type) {
    	this.serviceCheckType = ServiceCheckType.valueOf(type.toUpperCase());
    }
    
    public String getType() {
    	return this.serviceCheckType.toString().toUpperCase();
    }
    
    public void setHost(String host) {
    	this.host = host;
    }
    
    public String getHost() {
    	return this.host;
    }
}
