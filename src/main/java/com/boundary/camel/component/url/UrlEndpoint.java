// Copyright 2014 Boundary, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.boundary.camel.component.url;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.Endpoint;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;

import com.boundary.camel.component.common.ServiceEndpoint;
import com.boundary.camel.component.common.ServiceStatus;


/**
 * Implements a Camel {@link Endpoint} to check a port
 */
@UriEndpoint(scheme = "url", consumerClass = UrlConsumer.class)
public class UrlEndpoint extends ServiceEndpoint {
	
	private double NANO_SECONDS_TO_MILLI_SECONDS=1E-6;
	
	private UrlClient client;
	
    @UriParam
    private UrlConfiguration urlConfiguration;

	
    public UrlEndpoint() {
    }

    public UrlEndpoint(String uri, UrlComponent component) {
        super(uri, component);
     }

	public UrlEndpoint(String endpointUri) {
        super(endpointUri);
    }
	
    public UrlEndpoint(String uri, UrlComponent component, UrlConfiguration configuration) {
        super(uri, component);
        this.urlConfiguration = configuration;
    }

    
    public Consumer createConsumer(Processor processor) throws Exception {
    	UrlConsumer consumer = new UrlConsumer(this, processor);
        return consumer;
    }
    
    /**
     * Creates the producer for the component.
     */
    public Producer createProducer() throws Exception {
        return new UrlProducer(this);
    }
    
    @Override
    protected void doStart() throws Exception {
        super.doStart();

        client = UrlClient.setUpDefaultClient();
    }
    
    @Override
    protected void doStop() throws Exception {
        if (client != null) {
            client = null;
        }

        super.doStop();
    }
    
    public UrlConfiguration getConfiguration() {
    	return this.urlConfiguration;
    }
    
    public void setConfiguration(UrlConfiguration configuration) {
    	this.urlConfiguration = configuration;
    }

    public boolean isSingleton() {
        return true;
    }
    
    public UrlResult performCheck(UrlConfiguration configuration) {
    	UrlResult result = new UrlResult();   
    	client.connect(configuration);
    	setUrlResult(result,configuration,client);
    	return result;
    }
    
	public UrlResult performCheck() {
    	return this.performCheck(getConfiguration());
    }
    
    private void setUrlResult(UrlResult result,UrlConfiguration configuration, UrlClient client) {
    	result.setResponseTime(client.getResponseTime());
    	result.setResponseCode(client.getResponseCode());
    	result.setResponseBody(client.getResponseBody());
    	result.setHost(configuration.getHost());
    	result.setPort(configuration.getPort());
    	result.setMessage("OK");
    	result.setRequestMethod(configuration.getRequestMethod());
    	result.setURL(configuration.getUrl());

    	result.setStatus(client.getURLStatus() == UrlStatus.OK ? ServiceStatus.SUCCESS : ServiceStatus.FAIL);
    		
	}
}
