package com.boundary.camel.component.ping;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import com.boundary.camel.component.common.ServiceStatus;
import com.boundary.camel.component.ping.PingInfo;

public class PingHostNotReachableTest extends CamelTestSupport {

    @Test
    public void testPing() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(1);
        mock.await(20, TimeUnit.SECONDS);
        
        mock.assertIsSatisfied();
        List <Exchange> receivedExchanges = mock.getReceivedExchanges();
        for(Exchange e: receivedExchanges) {
        	PingInfo status = e.getIn().getBody(PingInfo.class);
        	
        	assertTrue("check transmitted",status.getTransmitted() > 0);
        	assertEquals("check received packets",0,status.getReceived());
        	assertTrue("check ping status",status.getStatus() == ServiceStatus.FAIL);
        }
    }
    
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("ping://192.168.1.27:?delay=5")
                .to("mock:result");
            }
        };
    }
}
