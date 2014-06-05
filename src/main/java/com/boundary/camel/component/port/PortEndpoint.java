package com.boundary.camel.component.port;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.spi.UriParam;

import com.boundary.camel.component.common.ServiceEndpoint;
import com.boundary.camel.component.common.ServiceStatus;
import com.boundary.camel.component.port.PortConfiguration;

/**
 * Implements a Camel {@link Endpoint} to check a port
 */
public class PortEndpoint extends ServiceEndpoint {
	
	private TCPClient client;
	
    @UriParam
    private PortConfiguration portConfiguration;

	
    public PortEndpoint() {
    	client = new TCPClient();
    }

    public PortEndpoint(String uri, PortComponent component) {
        super(uri, component);
     }

	public PortEndpoint(String endpointUri) {
        super(endpointUri);
    }
    
    /**
     * Creates the producer for the component.
     */
    public Producer createProducer() throws Exception {
        return new PortProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
    	PortConsumer consumer = new PortConsumer(this, processor);
        return consumer;
    }

    public boolean isSingleton() {
        return true;
    }
    
    public void setConfiguration(PortConfiguration portConfiguration) {
    	this.portConfiguration = portConfiguration;
    }
    
    public PortConfiguration getConfiguration() {
    	return this.portConfiguration;
    }
    
    public void setClientConfiguration(TCPClient client, PortConfiguration portConfiguration) {
    	client.setHost(portConfiguration.getHost());
    	client.setPort(portConfiguration.getPort());
    	client.setTimeout(portConfiguration.getTimeout());
    }
    
    public void setPortInfo(PortInfo info,TCPClient client) {
    	
    	info.setHost(client.getHost());
    	info.setPort(client.getPort());
    	info.setMessage(client.getMessage());
    	
    	switch(client.getStatus()) {
    	case CONNECTED:
    		info.setStatus(ServiceStatus.SUCCESS);
    	case CONNECTION_REFUSED:
    	case SOCKET_TIMEOUT:
    	case UNKNOWN_HOST:
    	case ERROR:
    		info.setStatus(ServiceStatus.FAIL);
    		break;
    	default:
    		assert false: "PortInfo not set for status: " + client.getStatus();
    	}
    }
    
    public PortInfo performCheck() {
    	PortInfo info = new PortInfo();
    	
    	setClientConfiguration(client,getConfiguration());
    	client.connect();
    	setPortInfo(info,client);
    	
    	return info;
    }
}
