package com.boundary.camel.component.ping;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

/**
 * Represents the component that manages {@link PingEndpoint}.
 */
public class PingComponent extends DefaultComponent {

    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        Endpoint endpoint = new PingEndpoint(uri, this);
        setProperties(endpoint, parameters);
        return endpoint;
    }
}
