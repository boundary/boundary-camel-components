package boundary.com.camel.component.ping;

import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.impl.ScheduledPollConsumer;

/**
 * The Ping consumer.
 */
public class PingConsumer extends ScheduledPollConsumer {
    private final PingEndpoint endpoint;

    public PingConsumer(PingEndpoint endpoint, Processor processor) {
        super(endpoint, processor);
        this.endpoint = endpoint;
    }
    
    protected PingStatus executePingCheck() {
    	PingCheck pingCheck = new PingCheck();
    	
    	// TBD: Set this during initialization
    	pingCheck.setHost(endpoint.getHost());
    	
    	return pingCheck.performCheck();
    }
    
    protected SocketStatus executeSocketCheck() {
    	SocketCheck socketCheck = new SocketCheck();
    	
    	return socketCheck.performCheck();
    }
    
    protected HttpStatus executeHttpCheck() {
    	HttpCheck httpCheck = new HttpCheck();
    	
    	return httpCheck.performCheck();
    }

    @Override
    protected int poll() throws Exception {

        Exchange exchange = endpoint.createExchange();
        Message message = exchange.getIn();
        
        // Perform the health check on the host/service by
        // 1) Running the Ping command
        // 2) Attempting to connect to a socket on a port in the host
        // 3) Making an HTTP(s) call to an endpoint
        
        //TBD: Ugly way to dispatch this. Will need to be refactored at somepoint
        // to use generics??
        switch (endpoint.getServiceCheckType()) {
        case PING:
        	message.setBody(executePingCheck());
        	break;
        case SOCKET:
        	message.setBody(executeSocketCheck());
        	break;
        case HTTP:
        	message.setBody(executeHttpCheck());
        	break;
        default:
        	assert(true);
        }        


        try {
            // send message to next processor in the route
            getProcessor().process(exchange);
            return 1; // number of messages polled
        } finally {
            // log exception if an exception occurred and was not handled
            if (exchange.getException() != null) {
                getExceptionHandler().handleException("Error processing exchange", exchange, exchange.getException());
            }
        }
    }
}
