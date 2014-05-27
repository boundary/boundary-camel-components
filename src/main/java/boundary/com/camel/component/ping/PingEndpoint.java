package boundary.com.camel.component.ping;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;

/**
 * Represents a Ping endpoint.
 */
public class PingEndpoint extends DefaultEndpoint {

    public PingEndpoint() {
    }

    public PingEndpoint(String uri, PingComponent component) {
        super(uri, component);
    }

    public PingEndpoint(String endpointUri) {
        super(endpointUri);
    }

    public Producer createProducer() throws Exception {
        return new PingProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
        return new PingConsumer(this, processor);
    }

    public boolean isSingleton() {
        return true;
    }
}
