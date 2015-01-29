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

import static java.net.HttpURLConnection.*;
import static com.boundary.camel.component.url.UrlStatus.*;

import java.util.UUID;
import java.util.List;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlProducerTest extends CamelTestSupport {
	
    private static final Logger LOG = LoggerFactory.getLogger(UrlComponentTest.class);
    
    private final String WEB_HOSTNAME = "httpbin.org";
    private final String UKNOWN_HOST = "www." + UUID.randomUUID().toString() + ".org";
    
    @Produce(uri = "direct:url-in")
    private ProducerTemplate in;

    @EndpointInject(uri = "mock:url-out")
    private MockEndpoint out;

	private UrlConfiguration urlConfig;
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		urlConfig = new UrlConfiguration();
	}

	@After
	public void tearDown() throws Exception {
		urlConfig = null;
		super.tearDown();
	}
	
	private void sendRequestCheckResponse(int expectedResponseCode,UrlStatus status) throws InterruptedException {
        out.expectedMessageCount(1);
        
        in.sendBody(urlConfig);
        
        out.assertIsSatisfied();
        
        List <Exchange> exchanges = out.getExchanges();
        assertEquals("Exchange count is incorrect",1,exchanges.size());
        
        for(Exchange e: exchanges) {
        	UrlResult result = e.getIn().getBody(UrlResult.class);
        	assertEquals("HTTP response code is incorrect",expectedResponseCode,result.getResponseCode());
        	assertEquals("UrlStats is incorrect",status,result.getURLStatus());
        }
	}


    @Test
    public void testUrl() throws Exception {
        sendRequestCheckResponse(HTTP_OK,OK);
    }
    
    @Test
    public void testUnknownHost() throws InterruptedException {
    	urlConfig.setHost(UKNOWN_HOST);
    	sendRequestCheckResponse(HTTP_BAD_GATEWAY,OK);
    }
    
    @Test
    public void testBasicAuth() throws InterruptedException {
    	urlConfig.setHost(WEB_HOSTNAME);
    	urlConfig.setPath("basic-auth/foo/bar");
    	urlConfig.setUser("foo");
    	urlConfig.setPassword("bar");
    	sendRequestCheckResponse(HTTP_OK,OK);
    }
    
    @Test
    public void testRoot() throws InterruptedException {
    	urlConfig.setHost(WEB_HOSTNAME);

        sendRequestCheckResponse(HTTP_OK,OK);
    }
    
    @Test
    public void testDoNotFollowRedirects() throws InterruptedException {
    	urlConfig.setHost(WEB_HOSTNAME);
    	urlConfig.setPath("redirect-to?url=http://example.com/");
    	urlConfig.setFollowRedirects(false);
        sendRequestCheckResponse(HTTP_MOVED_TEMP,OK);
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("direct:url-in")
                .log("${body.getClass.toString}")
                .to("url:http://localhost")
                .log("${body.getResponseCode}")
                .to("mock:url-out");
            }
        };
    }
}
