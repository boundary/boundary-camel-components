package com.boundary.camel.component.ping;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.Endpoint;

import com.boundary.camel.component.common.ServiceEndpoint;


/**
 * Implements a Camel {@link Endpoint} to ping a host
 */
@UriEndpoint(scheme = "ping", consumerClass = PingConsumer.class)
public class PingEndpoint extends ServiceEndpoint {
	
	PingClient client;
	
    @UriParam
	PingConfiguration configuration;
	
    public PingEndpoint() {
    }

    public PingEndpoint(String uri, PingComponent component) {
        super(uri, component);
     }

	public PingEndpoint(String endpointUri) {
        super(endpointUri);
    }
	
    public PingEndpoint(String uri, PingComponent component, PingConfiguration configuration) {
        super(uri,component);
        this.configuration = configuration;
    }
    
    /**
     * Creates the producer for the component.
     */
    public Producer createProducer() throws Exception {
        return new PingProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
    	PingConsumer consumer = new PingConsumer(this, processor);
        return consumer;
    }

    public boolean isSingleton() {
        return true;
    }
    
    public void setConfiguration(PingConfiguration configuration) {
    	this.configuration = configuration;
    }
    
    public PingConfiguration getConfiguration() {
    	return this.configuration;
    }
 
    @Override
    protected void doStart() throws Exception {
        super.doStart();

        client = PingClient.setUpDefaultClient();
    }
    
    @Override
    protected void doStop() throws Exception {   
        if (client != null) {
            client = null;
        }

        super.doStop();
    }
    
    public void setPingInfo(PingResult info,PingConfiguration configuration,PingClient client) {
    	info.setHost(configuration.getHost());
    	info.setPort(configuration.getPort());
    }
    
    /**
     * 
     * @param configuration {@link PingConfiguration}
     * @return {@link PingResult}
     */
    public PingResult performCheck(PingConfiguration configuration) {
    	PingResult info = client.ping(configuration);
    	
    	setPingInfo(info,configuration,client);
    	
    	return info;
    }
    
    public PingResult performCheck() {
    	return this.performCheck(getConfiguration());
    }

}
