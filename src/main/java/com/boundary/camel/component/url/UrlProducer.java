package com.boundary.camel.component.http;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Port producer.
 */
public class HttpProducer extends DefaultProducer {
    private static final Logger LOG = LoggerFactory.getLogger(HttpProducer.class);
    private HttpEndpoint endpoint;

    public HttpProducer(HttpEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
    }

    public void process(Exchange exchange) throws Exception {
        LOG.debug(exchange.getIn().getBody().toString());
        System.out.println(exchange.getIn().getBody().toString());
    }
}
