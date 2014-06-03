package com.boundary.camel.component.ping;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import com.boundary.camel.component.ping.PingStatus;
import com.boundary.camel.component.ping.Status;

public class PingHostNotReachableTest extends CamelTestSupport {

    @Test
    public void testPing() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(1);
        mock.await(10, TimeUnit.SECONDS);
        
        mock.assertIsSatisfied();
        List <Exchange> receivedExchanges = mock.getReceivedExchanges();
        for(Exchange e: receivedExchanges) {
        	PingStatus status = e.getIn().getBody(PingStatus.class);
        	
        	assertTrue("check transmitted",status.getTransmitted() > 0);
        	assertEquals("check received packets",0,status.getReceived());
        	assertTrue("check ping status",status.getStatus() == Status.FAIL);
        }
    }
    
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("ping://?type=ping&delay=5&host=192.168.1.27")
                  .to("ping://bar")
                  .to("mock:result");
            }
        };
    }
}
