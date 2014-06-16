package com.boundary.camel.component.port;

import java.net.URI;
import java.util.Map;

import org.apache.camel.Endpoint;	
import org.apache.camel.impl.UriEndpointComponent;


/**
 * Represents the component that manages {@link PortEndpoint}.
 */
public class PortComponent extends UriEndpointComponent {
    private PortConfiguration configuration;
	
    public PortComponent() {
        super(PortEndpoint.class);
    }

    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        PortConfiguration newConfig;

        if (configuration == null) {
            newConfig = new PortConfiguration(new URI(uri));
        } else {
            newConfig = configuration.copy();
        }

        PortEndpoint endpoint = new PortEndpoint(uri, this, newConfig);
        setProperties(endpoint.getConfiguration(), parameters);
 
        
        return endpoint;
    }
}
