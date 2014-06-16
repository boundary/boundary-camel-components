package com.boundary.camel.component.port;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
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
    	Message in = exchange.getIn();
    	PortConfiguration configuration = in.getBody(PortConfiguration.class);
    	LOG.debug("PortConfiguration: " + configuration);
    	PortInfo portInfo = endpoint.performCheck(configuration);
    	in.setBody(portInfo);
    }
}
