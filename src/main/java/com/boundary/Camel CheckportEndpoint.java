package com.boundary;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;

/**
 * Represents a Camel Checkport endpoint.
 */
public class Camel CheckportEndpoint extends DefaultEndpoint {

    public Camel CheckportEndpoint() {
    }

    public Camel CheckportEndpoint(String uri, Camel CheckportComponent component) {
        super(uri, component);
    }

    public Camel CheckportEndpoint(String endpointUri) {
        super(endpointUri);
    }

    public Producer createProducer() throws Exception {
        return new Camel CheckportProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
        return new Camel CheckportConsumer(this, processor);
    }

    public boolean isSingleton() {
        return true;
    }
}
