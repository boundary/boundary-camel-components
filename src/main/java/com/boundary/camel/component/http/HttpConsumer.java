package com.boundary.camel.component.http;

import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.impl.ScheduledPollConsumer;

/**
 * The Ping consumer.
 */
public class HttpConsumer extends ScheduledPollConsumer {
    private final HttpEndpoint endpoint;

    public HttpConsumer(HttpEndpoint endpoint, Processor processor) {
        super(endpoint, processor);
        this.endpoint = endpoint;
    }
    
    protected HttpInfo executePortCheck() {
    	HttpCheck httpCheck = new HttpCheck();
    	
    	// TBD: Set this during initialization
    	httpCheck.setHost(endpoint.getHost());
    	
    	return httpCheck.performCheck();
    }
    
	@Override
	protected int poll() throws Exception {

		Exchange exchange = endpoint.createExchange();
		Message message = exchange.getIn();
		
		HttpInfo status = executePortCheck();

		message.setBody(status, HttpInfo.class);

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
