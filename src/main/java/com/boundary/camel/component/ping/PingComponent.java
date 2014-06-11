package com.boundary.camel.component.ping;

import java.net.URI;
import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

import com.boundary.camel.component.port.PortConfiguration;
import com.boundary.camel.component.port.PortEndpoint;

/**
 * Represents the component that manages {@link PingEndpoint}.
 */
public class PingComponent extends DefaultComponent {
	
    private PingConfiguration configuration;

    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        PingConfiguration newConfig;

        if (configuration == null) {
            newConfig = new PingConfiguration(new URI(uri));
        } else {
            newConfig = configuration.copy();
        }

        PingEndpoint endpoint = new PingEndpoint(uri, this, newConfig);
        setProperties(endpoint.getConfiguration(), parameters);
        
        return endpoint;
    }
}
