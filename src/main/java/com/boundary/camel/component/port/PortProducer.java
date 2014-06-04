package com.boundary.camel.component.port;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Port producer.
 */
public class PortProducer extends DefaultProducer {
    private static final Logger LOG = LoggerFactory.getLogger(PortProducer.class);
    private PortEndpoint endpoint;

    public PortProducer(PortEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
    }

    public void process(Exchange exchange) throws Exception {
        LOG.debug(exchange.getIn().getBody().toString());
        System.out.println(exchange.getIn().getBody().toString());
    }
}
