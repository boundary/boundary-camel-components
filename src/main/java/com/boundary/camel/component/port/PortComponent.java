package com.boundary.camel.component.port;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

/**
 * Represents the component that manages {@link PingEndpoint}.
 */
public class PortComponent extends DefaultComponent {

    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        Endpoint endpoint = new PortEndpoint(uri, this);
        setProperties(endpoint, parameters);
        return endpoint;
    }
}
