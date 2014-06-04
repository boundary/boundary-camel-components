package com.boundary.camel.component.http;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

/**
 * Represents the component that manages {@link PingEndpoint}.
 */
public class HttpComponent extends DefaultComponent {

    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        Endpoint endpoint = new HttpEndpoint(uri, this);
        setProperties(endpoint, parameters);
        return endpoint;
    }
}
