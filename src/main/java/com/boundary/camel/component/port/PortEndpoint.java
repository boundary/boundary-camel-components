package com.boundary.camel.component.port;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;

import com.boundary.camel.component.common.ServiceEndpoint;

/**
 * Implements a Camel {@link Endpoint} to check a port
 */
public class PortEndpoint extends ServiceEndpoint {
	
    public PortEndpoint() {
    }

    public PortEndpoint(String uri, PortComponent component) {
        super(uri, component);
     }

	public PortEndpoint(String endpointUri) {
        super(endpointUri);
    }
    
    /**
     * Creates the producer for the component.
     */
    public Producer createProducer() throws Exception {
        return new PortProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
    	PortConsumer consumer = new PortConsumer(this, processor);
        return consumer;
    }

    public boolean isSingleton() {
        return true;
    }
}
