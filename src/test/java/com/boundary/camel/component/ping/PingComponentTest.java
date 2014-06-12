package com.boundary.camel.component.ping;

import java.util.List;

import java.util.concurrent.TimeUnit;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import com.boundary.camel.component.common.ServiceStatus;
import com.boundary.camel.component.ping.PingInfo;

public class PingComponentTest extends CamelTestSupport {

    @Test
    public void testPing() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMessageCount(1);
        mock.await(5, TimeUnit.SECONDS);
        
        mock.assertIsSatisfied();
        List <Exchange> receivedExchanges = mock.getReceivedExchanges();
        for(Exchange e: receivedExchanges) {
        	PingInfo status = e.getIn().getBody(PingInfo.class);
        	
         	assertTrue("check transmitted",status.getTransmitted() > 0);
        	assertEquals("check transmitted/received", status.getTransmitted(),status.getReceived());
           	assertTrue("check ping status",status.getStatus() == ServiceStatus.SUCCESS);
        }
    }
    
    @Test
    public void testMultiplePing() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMessageCount(3);
        mock.await(10, TimeUnit.SECONDS);
        
        mock.assertIsSatisfied();
        List <Exchange> receivedExchanges = mock.getReceivedExchanges();
        for(Exchange e: receivedExchanges) {
        	PingInfo status = e.getIn().getBody(PingInfo.class);
        	
        	assertTrue("check transmitted",status.getTransmitted() > 0);
        	assertEquals("check transmitted/received", status.getTransmitted(),status.getReceived());
        	assertTrue("check ping status",status.getStatus() == ServiceStatus.SUCCESS);
        }

    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("ping://foo?host=localhost&delay=5")
                  .to("mock:result");
            }
        };
    }
}
