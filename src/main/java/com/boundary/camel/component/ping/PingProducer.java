package com.boundary.camel.component.ping;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.camel.component.port.PortConfiguration;
import com.boundary.camel.component.port.PortResult;

/**
 * The Ping producer.
 */
public class PingProducer extends DefaultProducer {
    private static final Logger LOG = LoggerFactory.getLogger(PingProducer.class);
    private PingEndpoint endpoint;

    public PingProducer(PingEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
    }

    public void process(Exchange exchange) throws Exception {
    	Message in = exchange.getIn();
    	PingConfiguration configuration = in.getBody(PingConfiguration.class);
    	PingResult pingInfo = endpoint.performCheck(configuration);
    	in.setBody(pingInfo);
    }
}
