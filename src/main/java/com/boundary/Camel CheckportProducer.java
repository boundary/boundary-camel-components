package com.boundary;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Camel Checkport producer.
 */
public class Camel CheckportProducer extends DefaultProducer {
    private static final Logger LOG = LoggerFactory.getLogger(Camel CheckportProducer.class);
    private Camel CheckportEndpoint endpoint;

    public Camel CheckportProducer(Camel CheckportEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
    }

    public void process(Exchange exchange) throws Exception {
        System.out.println(exchange.getIn().getBody());    
    }

}
