package com.boundary.camel.component.port;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.Endpoint;

import com.boundary.camel.component.common.ServiceEndpoint;
import com.boundary.camel.component.common.ServiceStatus;
import com.boundary.camel.component.port.PortConfiguration;

/**
 * Implements a Camel {@link Endpoint} to check a port
 */
@UriEndpoint(scheme = "port", consumerClass = PortConsumer.class)
public class PortEndpoint extends ServiceEndpoint {
	
	private TCPClient client;
	
    @UriParam
    private PortConfiguration portConfiguration;

    public PortEndpoint() {
    	client = new TCPClient();
    }

	public PortEndpoint(String endpointUri) {
        super(endpointUri);
    }

    public PortEndpoint(String uri, PortComponent component) {
        super(uri, component);
     }

    public PortEndpoint(String uri, PortComponent component, PortConfiguration configuration) {
        super(uri, component);
        this.portConfiguration = configuration;
    }

    
    /**
     * Creates the producer for the component.
     */
    public Producer createProducer() throws Exception {
        return new PortProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
    	PortConsumer consumer = new PortConsumer(this, processor);
        configureConsumer(consumer);
        return consumer;
    }

    public boolean isSingleton() {
        return false;
    }
    
    public void setConfiguration(PortConfiguration configuration) {
    	this.portConfiguration = configuration;
    }
    
    public PortConfiguration getConfiguration() {
    	return this.portConfiguration;
    }
    
    @Override
    protected void doStart() throws Exception {
        super.doStart();

        client = TCPClient.setUpDefaultClient();
    }
    
    @Override
    protected void doStop() throws Exception {
        if (client != null) {
            client = null;
        }

        super.doStop();
    }

    public void setPortInfo(PortResult info,PortConfiguration configuration,TCPClient client) {
    	
    	info.setHost(configuration.getHost());
    	info.setPort(configuration.getPort());
    	info.setMessage(client.getMessage());
    	info.setPortStatus(client.getPortStatus());
    	
    	switch(client.getPortStatus()) {
    	case CONNECTED:
    		info.setStatus(ServiceStatus.SUCCESS);
    		break;
    	case CONNECTION_REFUSED:
    	case SOCKET_TIMEOUT:
    	case UNKNOWN_HOST:
    	case ERROR:
    		info.setStatus(ServiceStatus.FAIL);
    		break;
    	default:
    		assert false: "PortInfo not set for status: " + client.getPortStatus();
    	}
    }
    
    public PortResult performCheck(PortConfiguration configuration) {
    	PortResult info = new PortResult();
   
    	client.connect(configuration.getHost(),configuration.getPort(),configuration.getTimeout());
    	
    	setPortInfo(info,configuration,client);
    	
    	return info;

    }
    
    public PortResult performCheck() {
    	return this.performCheck(getConfiguration());
    }
}
