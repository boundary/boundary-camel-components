package com.boundary.camel.component.port;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
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
import com.boundary.camel.component.util.SimpleServer;

public class PortComponentTest extends CamelTestSupport {

	private final static String HOST = "localhost";
	private final static String UKNOWN_HOST = "abc.def.com";
	private final static int PORT = 5555;
	private final static int UNKNOWN_PORT = 8888;
	private final static int TIMEOUT = 5000;

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
		MockEndpoint mock = getMockEndpoint("mock:consumer-connect");
		mock.expectedMessageCount(1);
		mock.await(5, TimeUnit.SECONDS);

		mock.assertIsSatisfied();
	}
	
	private void validateConnect(MockEndpoint mock) throws InterruptedException {
		mock.assertIsSatisfied();

		List<Exchange> receivedExchanges = mock.getReceivedExchanges();
		for (Exchange e : receivedExchanges) {
			PortInfo info = e.getIn().getBody(PortInfo.class);

			assertEquals("check message", "OK", info.getMessage());
			assertEquals("check host", HOST, info.getHost());
			assertEquals("check port", PORT, info.getPort());
			assertTrue(info.getPortStatus() == PortStatus.CONNECTED);
			assertEquals("check timeout", TIMEOUT, info.getTimeout());
			assertTrue(info.getStatus() == ServiceStatus.SUCCESS);
		}

	}
	
	private void validateConnectionRefused(MockEndpoint mock) throws InterruptedException {
		mock.assertIsSatisfied();

		List<Exchange> receivedExchanges = mock.getReceivedExchanges();
		for (Exchange e : receivedExchanges) {

			PortInfo info = e.getIn().getBody(PortInfo.class);

			assertEquals("check host", HOST, info.getHost());
			assertEquals("check port", UNKNOWN_PORT, info.getPort());
			assertEquals("check port status",
					PortStatus.CONNECTION_REFUSED.toString(), info
							.getPortStatus().toString());
			assertEquals("check timeout", TIMEOUT, info.getTimeout());
			assertTrue(info.getStatus() == ServiceStatus.FAIL);
		}

	}
	
	private void validateUnknownHost(MockEndpoint mock) throws InterruptedException {
		mock.assertIsSatisfied();

		List<Exchange> receivedExchanges = mock.getReceivedExchanges();
		for (Exchange e : receivedExchanges) {

			PortInfo info = e.getIn().getBody(PortInfo.class);

			assertEquals("check host", UKNOWN_HOST, info.getHost());
			assertEquals("check port", PORT, info.getPort());
			assertEquals("check port status",
					PortStatus.UNKNOWN_HOST.toString(), info.getPortStatus().toString());
			assertEquals("check timeout", TIMEOUT, info.getTimeout());
			assertTrue(info.getStatus() == ServiceStatus.FAIL);
		}

	}

	@Test
	public void testConsumerConnect() {
		// Start TCP server
		SimpleServer server = new SimpleServer(PORT);

		try {
			MockEndpoint mock = getMockEndpoint("mock:consumer-connect");
			mock.expectedMessageCount(1);
			mock.await(5, TimeUnit.SECONDS);

			validateConnect(mock);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			server.stopServer();
		}
	}

	@Test
	public void testConsumerConnectionRefused() throws InterruptedException {
		MockEndpoint mock = getMockEndpoint("mock:consumer-connection-refused");
		mock.expectedMessageCount(1);
		mock.await(5, TimeUnit.SECONDS);
		
		validateConnectionRefused(mock);
	}
	
	@Test
	public void testConsumerUnknownHost() throws InterruptedException {
		MockEndpoint mock = getMockEndpoint("mock:consumer-unknown-host");
		mock.expectedMessageCount(1);
		mock.await(5, TimeUnit.SECONDS);

		validateUnknownHost(mock);
	}
	
	@Test
	public void testProducerConnect() {
		// Start TCP server
		SimpleServer server = new SimpleServer(PORT);

		try {
			MockEndpoint mock = getMockEndpoint("mock:producer-connect");
			mock.expectedMessageCount(1);
			mock.await(5, TimeUnit.SECONDS);
			
			template.sendBody("direct:config-connect",PortConfiguration.getConfiguration(HOST,PORT,TIMEOUT));
			
			validateConnect(mock);

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			server.stopServer();
		}
	}
	
	@Test
	public void testProducerConnectionRefused() throws InterruptedException {
		MockEndpoint mock = getMockEndpoint("mock:producer-connection-refused");
		mock.expectedMessageCount(1);
		mock.await(5, TimeUnit.SECONDS);
		
		template.sendBody("direct:config-connection-refused",PortConfiguration.getConfiguration(HOST,UNKNOWN_PORT,TIMEOUT));
		
		validateConnectionRefused(mock);
	}
	
	@Test
	public void testProducerUnknownHost() throws InterruptedException {
		MockEndpoint mock = getMockEndpoint("mock:producer-unknown-host");
		mock.expectedMessageCount(1);
		mock.await(5, TimeUnit.SECONDS);
		
		template.sendBody("direct:config-unknown-host",PortConfiguration.getConfiguration(UKNOWN_HOST,PORT,TIMEOUT));

		validateUnknownHost(mock);
	}


	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		return new RouteBuilder() {
			public void configure() {
				from("port://" + HOST + ":" + PORT + "/tcp?timeout=" + TIMEOUT + "&delay=10000")
				.to("mock:consumer-connect");
				
				from("port://" + HOST + ":" + UNKNOWN_PORT + "/tcp?timeout=" + TIMEOUT + "&delay=1000")
				.to("mock:consumer-connection-refused");
				
				from("port://" + "abc.def.com" + ":" + PORT + "/tcp?timeout=" + TIMEOUT + "&delay=10000")
				.to("mock:consumer-unknown-host");
				
				from("port://" + "10.0.0.0" + ":" + PORT + "/tcp?timeout=" + TIMEOUT + "&delay=10000")
				.to("mock:consumer-timeout");
				
				from("direct:config-connect")
				.to("port://tcp")
				.to("mock:producer-connect")
				.to("stream:out");
				
				from("direct:config-connection-refused")
				.to("port://tcp")
				.to("mock:producer-connection-refused")
				.to("stream:out");
				
				from("direct:config-unknown-host")
				.to("port://tcp")
				.to("mock:producer-unknown-host")
				.to("mock:out");
			}
		};
	}
}
