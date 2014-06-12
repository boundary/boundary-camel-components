package com.boundary.camel.component.ping;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import com.boundary.camel.component.common.ServiceStatus;
import com.boundary.camel.component.ping.PingInfo;
import com.boundary.camel.component.port.PortConfiguration;

public class PingComponentTest extends CamelTestSupport {
	
	private final String HOST = "localhost";
	private final String UNKNOWN_HOST = "abc.def.com";
	private final String UNREACHABLE_HOST = "192.168.1.27";
	private final String TIMEOUT_HOST = "192.168.1.26";
	
	private void validatePing(MockEndpoint mock) throws InterruptedException {
        mock.assertIsSatisfied();
        List <Exchange> receivedExchanges = mock.getReceivedExchanges();
        for(Exchange e: receivedExchanges) {
        	PingInfo status = e.getIn().getBody(PingInfo.class);
        	
         	assertTrue("check transmitted",status.getTransmitted() > 0);
        	assertEquals("check transmitted/received", status.getTransmitted(),status.getReceived());
           	assertTrue("check ping status",status.getStatus() == ServiceStatus.SUCCESS);
        }
	}
	
	private void validateUnresolveableHost(MockEndpoint mock) throws InterruptedException {
        mock.assertIsSatisfied();
        
        List <Exchange> receivedExchanges = mock.getReceivedExchanges();
        for(Exchange e: receivedExchanges) {
        	PingInfo status = e.getIn().getBody(PingInfo.class);
        	String message = status.getMessage().toUpperCase();
        	
    		Pattern messagePat = Pattern.compile("UNKNOWN HOST");
    		Matcher match = messagePat.matcher(message);
    		
    		assertTrue(match.find());
        	assertTrue("check transmitted",status.getTransmitted() == 0);
        	assertTrue(status.getStatus() == ServiceStatus.FAIL);
        }
	}
	
	private void validateHostNotReachable(MockEndpoint mock) throws InterruptedException {
        mock.assertIsSatisfied();
        List <Exchange> receivedExchanges = mock.getReceivedExchanges();
        for(Exchange e: receivedExchanges) {
        	PingInfo status = e.getIn().getBody(PingInfo.class);
        	
        	assertTrue("check transmitted",status.getTransmitted() > 0);
        	assertEquals("check received packets",0,status.getReceived());
        	assertTrue("check ping status",status.getStatus() == ServiceStatus.FAIL);
        }

	}
	
	private void validateHostTimeout(MockEndpoint mock) throws InterruptedException {
        mock.assertIsSatisfied();
        
        List <Exchange> receivedExchanges = mock.getReceivedExchanges();
        for(Exchange e: receivedExchanges) {
        	PingInfo status = e.getIn().getBody(PingInfo.class);
        	
        	assertTrue("check transmitted",status.getTransmitted() > 0);
        	assertTrue("check received",status.getReceived() == 0);
        	assertTrue(status.getStatus() == ServiceStatus.FAIL);
        }
	}

    @Test
    public void testConsumerPing() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:consumer-ping-out");
        mock.expectedMessageCount(1);
        mock.await(5, TimeUnit.SECONDS);
        
        validatePing(mock);
    }
    
	@Test
	public void testMultiplePing() throws Exception {
		MockEndpoint mock = getMockEndpoint("mock:consumer-ping-out");
		mock.expectedMessageCount(3);
		mock.await(10, TimeUnit.SECONDS);

		validatePing(mock);
	}
    
    @Test
    public void testHostUnresolveable() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:consumer-unknown-host-out");
        mock.expectedMinimumMessageCount(1);
        mock.await(10, TimeUnit.SECONDS);
        
        validateUnresolveableHost(mock);
    }
    
    @Test
    public void testHostNotReachable() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:consumer-unreachable-host-out");
        mock.expectedMinimumMessageCount(1);
        mock.await(20, TimeUnit.SECONDS);
        
        validateHostNotReachable(mock);
    }
    
    @Test
    public void testTimeOut() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:consumer-timeout-host-out");
        mock.expectedMinimumMessageCount(1);
        mock.await(15, TimeUnit.SECONDS);
        
        validateHostTimeout(mock);
    }
    
    @Test
    public void testProducerPing() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:producer-ping-out");
        mock.expectedMessageCount(1);
        mock.await(5, TimeUnit.SECONDS);
        
		template.sendBody("direct:producer-ping-in",PingConfiguration.getConfiguration(HOST));
        
		validatePing(mock);
    }
    
    @Test
    public void testProducerUnknownHost() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:producer-ping-out");
        mock.expectedMessageCount(1);
        mock.await(5, TimeUnit.SECONDS);
        
		template.sendBody("direct:producer-ping-in",PingConfiguration.getConfiguration(UNKNOWN_HOST));
        
		validateUnresolveableHost(mock);
    }
    
    public void testProducerHostNotReachable() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:producer-ping-out");
        mock.expectedMessageCount(1);
        mock.await(5, TimeUnit.SECONDS);
        
		template.sendBody("direct:producer-ping-in",PingConfiguration.getConfiguration(UNREACHABLE_HOST));
        
		validateHostNotReachable(mock);
    }
    
    public void testProducerHostTimeOut() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:producer-ping-out");
        mock.expectedMessageCount(1);
        mock.await(5, TimeUnit.SECONDS);
        
		template.sendBody("direct:producer-ping-in",PingConfiguration.getConfiguration(TIMEOUT_HOST));
        
		validateHostTimeout(mock);
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
            	
                from("ping://" + HOST + ":" + "/icmp?delay=5")
                .to("mock:consumer-ping-out");
                
                from("ping://" + UNKNOWN_HOST + ":" + "/icmp?delay=5")
                .to("mock:consumer-unknown-host-out");
                
                from("ping://" + UNREACHABLE_HOST + ":" + "/icmp?delay=5")
                .to("mock:consumer-unreachable-host-out");
                
                from("ping://" + TIMEOUT_HOST + ":" + "/icmp?delay=5")
                .to("mock:consumer-timeout-host-out");
                
                from("direct:producer-ping-in")
                .to("ping://icmp")
                .to("mock:producer-ping-out");
            }
        };
    }
}
