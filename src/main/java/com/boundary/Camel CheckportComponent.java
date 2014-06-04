package com.boundary;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

/**
 * Represents the component that manages {@link Camel CheckportEndpoint}.
 */
public class Camel CheckportComponent extends DefaultComponent {

    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        Endpoint endpoint = new Camel CheckportEndpoint(uri, this);
        setProperties(endpoint, parameters);
        return endpoint;
    }
}
