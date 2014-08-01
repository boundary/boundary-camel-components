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

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.camel.component.common.ServiceStatus;
import com.boundary.camel.component.ping.PingProducer;


public class UrlComponentTest extends CamelTestSupport {
	
    private static final Logger LOG = LoggerFactory.getLogger(UrlComponentTest.class);

    @Produce(uri = "direct:url-in")
    private ProducerTemplate in;
    
    @EndpointInject(uri = "mock:url-out")
    private MockEndpoint out;

    @Ignore
    @Test
    public void testUrl() throws Exception {
    	UrlConfiguration urlConfiguration = new UrlConfiguration();
        out.expectedMessageCount(1);
        out.assertIsSatisfied();
        
        List <Exchange> exchanges = out.getExchanges();
    	LOG.info("EXCHANGE COUNT: " + exchanges.size());

        for(Exchange e: exchanges) {
        	UrlResult result = e.getIn().getBody(UrlResult.class);
        	assertEquals("response code unexpected",200,result.getResponseCode());
        }
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("url://localhost")
                  .to("mock:url-out");
            }
        };
    }
}
