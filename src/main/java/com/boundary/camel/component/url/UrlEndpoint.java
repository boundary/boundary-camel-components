package com.boundary.camel.component.url;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.Endpoint;

import com.boundary.camel.component.common.ServiceEndpoint;

/**
 * Implements a Camel {@link Endpoint} to check a port
 */
public class UrlEndpoint extends ServiceEndpoint {
	
    public UrlEndpoint() {
    }

    public UrlEndpoint(String uri, UrlComponent component) {
        super(uri, component);
     }

	public UrlEndpoint(String endpointUri) {
        super(endpointUri);
    }
    
    /**
     * Creates the producer for the component.
     */
    public Producer createProducer() throws Exception {
        return new UrlProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
    	UrlConsumer consumer = new UrlConsumer(this, processor);
        return consumer;
    }

    public boolean isSingleton() {
        return true;
    }
}
