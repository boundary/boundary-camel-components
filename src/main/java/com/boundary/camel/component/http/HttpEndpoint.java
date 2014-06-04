package com.boundary.camel.component.http;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;

import com.boundary.camel.component.common.ServiceEndpoint;

/**
 * Implements a Camel {@link Endpoint} to check a port
 */
public class HttpEndpoint extends ServiceEndpoint {
	
    public HttpEndpoint() {
    }

    public HttpEndpoint(String uri, HttpComponent component) {
        super(uri, component);
     }

	public HttpEndpoint(String endpointUri) {
        super(endpointUri);
    }
    
    /**
     * Creates the producer for the component.
     */
    public Producer createProducer() throws Exception {
        return new HttpProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
    	HttpConsumer consumer = new HttpConsumer(this, processor);
        return consumer;
    }

    public boolean isSingleton() {
        return true;
    }
}
