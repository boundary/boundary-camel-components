package com.boundary.camel.component.port;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.boundary.camel.component.common.ServiceStatus;
import com.boundary.camel.component.ping.PingInfo;
import com.boundary.camel.component.port.PortInfo;
import com.boundary.camel.component.util.MultiThreadedServer;

public class PortComponentTest extends CamelTestSupport {
	
	private final static String HOST = "localhost";
	private final static int LISTENING_PORT = 1234;
	private final static int TIMEOUT = 5000;
	
	private MultiThreadedServer server;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}


    @Test
    public void testPort() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMessageCount(1);
        mock.await(5, TimeUnit.SECONDS);
        
        mock.assertIsSatisfied();
    }
    
	@Test
	public void testConnect() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMessageCount(1);
        mock.await(5, TimeUnit.SECONDS);
        
        // Start TCP server
//		server = new MultiThreadedServer(22, 1);
//		server.start();
		
        mock.assertIsSatisfied();
        
        List <Exchange> receivedExchanges = mock.getReceivedExchanges();
        for(Exchange e: receivedExchanges) {
        	PortInfo info = e.getIn().getBody(PortInfo.class);
 
        	assertEquals("check message","OK",info.getMessage());
        	assertEquals("check host",HOST,info.getHost());
        	assertTrue(info.getStatus() == ServiceStatus.SUCCESS);
        }

			
		// Shutdown server
//		server.stop();
	}
    

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("port://" + HOST + ":" + LISTENING_PORT + "/tcp?timeout=" + TIMEOUT)
                .to("mock:result");
            }
        };
    }
}
