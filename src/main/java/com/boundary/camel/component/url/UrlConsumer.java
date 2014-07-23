package com.boundary.camel.component.url;

import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.impl.ScheduledPollConsumer;


/**
 * The Ping consumer.
 */
public class UrlConsumer extends ScheduledPollConsumer {
    private final UrlEndpoint endpoint;

    public UrlConsumer(UrlEndpoint endpoint, Processor processor) {
        super(endpoint, processor);
        this.endpoint = endpoint;
    }
    
    protected UrlResult executePortCheck() {

    	return new UrlResult();
    }
    
	@Override
	protected int poll() throws Exception {

		Exchange exchange = endpoint.createExchange();
		Message message = exchange.getIn();
		
		UrlResult status = executePortCheck();

		message.setBody(status, UrlResult.class);

		try {
			// send message to next processor in the route
			getProcessor().process(exchange); 
			return 1; // number of messages polled
		} finally {
			// log exception if an exception occurred and was not handled
			if (exchange.getException() != null) {
				getExceptionHandler().handleException(
						"Error processing exchange", exchange,
						exchange.getException());
			}
		}
	}
}
