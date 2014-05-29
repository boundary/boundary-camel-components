package boundary.com.camel.component.ping;

import java.util.concurrent.TimeUnit;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.impl.ScheduledPollEndpoint;

/**
 * Implements a Camel endpoint of checking the health of network services
 */
public class PingEndpoint extends ScheduledPollEndpoint {
	
	public final long DEFAULT_DELAY = 500;
	public final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
	public final ServiceCheckType DEFAULT_SERVICE_CHECK = ServiceCheckType.PING;
	
	private long delay;
	private ServiceCheckType serviceCheckType;
	private TimeUnit timeUnit;
	private String host;

	/**
	 * Configure the default values for the end point
	 */
    public PingEndpoint() {
        setDefaults();
    }

    public PingEndpoint(String uri, PingComponent component) {
        super(uri, component);
        setDefaults();
     }

    @SuppressWarnings("deprecation")
	public PingEndpoint(String endpointUri) {
        super(endpointUri);
        setDefaults();
    }
    
    private void setDefaults() {
       	delay = DEFAULT_DELAY;
    	timeUnit = DEFAULT_TIME_UNIT;
    	serviceCheckType = DEFAULT_SERVICE_CHECK;
    }

    /**
     * Creates the producer for the component. TBD if this needed for the service check component
     */
    public Producer createProducer() throws Exception {
        return new PingProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
    	PingConsumer consumer = new PingConsumer(this, processor);
//    	consumer.setUseFixedDelay(true);
//    	consumer.setDelay(delay);
//    	consumer.setTimeUnit(timeUnit);
        return consumer;
    }

    public boolean isSingleton() {
        return true;
    }
 
    /**
     * Internal method to get the type of service check used on this end point.
     * @return ServiceCheckType
     */
    protected ServiceCheckType getServiceCheckType() {
    	return this.serviceCheckType;
    }

    
    /**
     * These member functions handle the URL parameters
     * to be configured on this EndPoint.
     */
    
    /**
     * Combined with the TimeUnit it determines how long to wait
     * between each service check.
     * 
     * @param delay
     */
    public void setDelay(long delay) {
    	this.delay = delay;
    }
    
    /*
     * Sets the time units by the delay parameter
     */
    public void setTimeUnit(String timeUnit) {
    	this.timeUnit = TimeUnit.valueOf(timeUnit.toUpperCase());
    }
    
    public void setType(String type) {
    	this.serviceCheckType = ServiceCheckType.valueOf(type.toUpperCase());
    }
    
    public String getType() {
    	return this.serviceCheckType.toString().toUpperCase();
    }
    
    public void setHost(String host) {
    	this.host = host;
    }
    
    public String getHost() {
    	return this.host;
    }
}
