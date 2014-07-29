package com.boundary.camel.component.url;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.camel.component.port.PortConfiguration;
import com.boundary.camel.component.port.PortResult;

/**
 * The Port producer.
 */
public class UrlProducer extends DefaultProducer {
    private static final Logger LOG = LoggerFactory.getLogger(UrlProducer.class);
    private UrlEndpoint endpoint;

    public UrlProducer(UrlEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
    }

    public void process(Exchange exchange) throws Exception {
    	Message in = exchange.getIn();
    	UrlConfiguration configuration = in.getBody(UrlConfiguration.class);
    	LOG.debug("UrlConfiguration: " + configuration);
    	UrlResult urlResult = endpoint.performCheck(configuration);
    	in.setBody(urlResult);
    }
}
