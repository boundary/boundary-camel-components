package boundary.com.camel.component.ping;

import java.util.concurrent.TimeUnit;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;

/**
 * Implements a Camel endpoint of checking the health of network services
 */
public class PingEndpoint extends DefaultEndpoint {
	
	public final long DEFAULT_DELAY = 60;
	public final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
	public final ServiceCheckType DEFAULT_SERVICE_CHECK = ServiceCheckType.PING;
	
	private long delay;
	private ServiceCheckType serviceCheckType;
	private TimeUnit timeUnit;

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
     * Creates the producer for the component. TBD if this needed.
     */
    public Producer createProducer() throws Exception {
        return new PingProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
    	PingConsumer consumer = new PingConsumer(this, processor);
    	consumer.setDelay(delay);
    	consumer.setTimeUnit(timeUnit);
        return consumer;
    }

    public boolean isSingleton() {
        return true;
    }
    
    public void setDelay(long delay) {
    	this.delay = delay;
    }
    
    public void setTimeUnit(TimeUnit timeUnit) {
    	this.timeUnit = timeUnit;
    }
    
    public void setServiceCheckType(ServiceCheckType serviceCheckType) {
    	this.serviceCheckType = serviceCheckType;
    }
    
    public ServiceCheckType getServiceCheckType() {
    	return this.serviceCheckType;
    }
}
