package com.boundary.camel.component.ping;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import com.boundary.camel.component.ping.SocketStatus;
import com.boundary.camel.component.ping.Status;

public class SocketComponentTest extends CamelTestSupport {

    @Test
    public void testSocket() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMessageCount(1);
        mock.await(5, TimeUnit.SECONDS);
        
        mock.assertIsSatisfied();
        List <Exchange> receivedExchanges = mock.getReceivedExchanges();
        for(Exchange e: receivedExchanges) {
        	SocketStatus status = e.getIn().getBody(SocketStatus.class);
        	
        	assertTrue("check ping status",status.getStatus() == Status.SUCCESS);
        }
    }
    

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("ping://foo?type=socket&host=localhost&delay=5")
                  .to("ping://bar")
                  .to("mock:result");
            }
        };
    }
}
