package com.boundary.camel.component.url;

import java.net.URL;
import java.util.List;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import static java.net.HttpURLConnection.*;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UrlProducerTest extends CamelTestSupport {
	
    private static final Logger LOG = LoggerFactory.getLogger(UrlComponentTest.class);
    
    @Produce(uri = "direct:url-in")
    private ProducerTemplate in;

    @EndpointInject(uri = "mock:url-out")
    private MockEndpoint out;

    @Test
    public void testUrl() throws Exception {
        out.expectedMessageCount(1);
        UrlConfiguration url = new UrlConfiguration();
        
        in.sendBody(url);
        
        out.assertIsSatisfied();
        List <Exchange> exchanges = out.getExchanges();
    	LOG.info("EXCHANGE COUNT: " + exchanges.size());

        for(Exchange e: exchanges) {
        	UrlResult result = e.getIn().getBody(UrlResult.class);
        	assertEquals("check url status",HTTP_OK,result.getResponseCode());
        }
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("direct:url-in")
                .to("url:http://localhost")
                .to("mock:url-out");
            }
        };
    }
}
