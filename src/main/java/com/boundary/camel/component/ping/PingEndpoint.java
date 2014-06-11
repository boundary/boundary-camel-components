package com.boundary.camel.component.ping;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;

import com.boundary.camel.component.common.ServiceEndpoint;
import com.boundary.camel.component.common.ServiceStatus;
import com.boundary.camel.component.port.PortComponent;
import com.boundary.camel.component.port.PortConfiguration;
import com.boundary.camel.component.port.PortConsumer;
import com.boundary.camel.component.port.PortInfo;
import com.boundary.camel.component.port.TCPClient;

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
    
    public void setPingInfo(PingInfo info,PingConfiguration configuration,PingClient client) {
    	
    	info.setHost(configuration.getHost());
    	info.setPort(configuration.getPort());
    	
    	// TBD: Setting the ServiceStatus
    	
//    	switch(client.getPortStatus()) {
//    	case CONNECTED:
//    		info.setStatus(ServiceStatus.SUCCESS);
//    		break;
//    	case CONNECTION_REFUSED:
//    	case SOCKET_TIMEOUT:
//    	case UNKNOWN_HOST:
//    	case ERROR:
//    		info.setStatus(ServiceStatus.FAIL);
//    		break;
//    	default:
//    		assert false: "PortInfo not set for status: " + client.getPortStatus();
//    	}
    }
    
    /**
     * 
     * @param configuration {@link PingConfiguration}
     * @return
     */
    public PingInfo performCheck(PingConfiguration configuration) {
    	PingInfo info = client.ping(configuration);
    	
    	setPingInfo(info,configuration,client);
    	
    	return info;
    }
    
    public PingInfo performCheck() {
    	return this.performCheck(getConfiguration());
    }

}
