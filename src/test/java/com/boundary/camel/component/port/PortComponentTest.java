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
		MockEndpoint mock = getMockEndpoint("mock:connect");
		mock.expectedMessageCount(1);
		mock.await(5, TimeUnit.SECONDS);

		mock.assertIsSatisfied();
	}

	@Test
	public void testConnect() {
		// Start TCP server
		SimpleServer server = new SimpleServer(PORT);

		try {
			MockEndpoint mock = getMockEndpoint("mock:connect");
			mock.expectedMessageCount(1);
			mock.await(5, TimeUnit.SECONDS);

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
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			server.stopServer();
		}
	}

	@Test
	public void testConnectionRefused() throws InterruptedException {
		MockEndpoint mock = getMockEndpoint("mock:connection-refused");
		mock.expectedMessageCount(1);
		mock.await(5, TimeUnit.SECONDS);

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
	
	@Test
	public void testUnknownHost() throws InterruptedException {
		MockEndpoint mock = getMockEndpoint("mock:unknown-host");
		mock.expectedMessageCount(1);
		mock.await(5, TimeUnit.SECONDS);

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

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		return new RouteBuilder() {
			public void configure() {
				from("port://" + HOST + ":" + PORT + "/tcp?timeout=" + TIMEOUT + "&delay=10000")
				.to("mock:connect");
				
				from("port://" + HOST + ":" + UNKNOWN_PORT + "/tcp?timeout=" + TIMEOUT + "&delay=1000")
				.to("mock:connection-refused");
				
				from("port://" + "abc.def.com" + ":" + PORT + "/tcp?timeout=" + TIMEOUT + "&delay=10000")
				.to("mock:unknown-host");
				
				from("port://" + "10.0.0.0" + ":" + PORT + "/tcp?timeout=" + TIMEOUT + "&delay=10000")
				.to("mock:timeout");

			}
		};
	}
}
