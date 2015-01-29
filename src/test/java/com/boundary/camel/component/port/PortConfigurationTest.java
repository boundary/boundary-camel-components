package com.boundary.camel.component.port;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PortConfigurationTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPortConfiguration() {
		PortConfiguration configuration = new PortConfiguration();
		
		assertEquals("check host","localhost",configuration.getHost());
		assertEquals("check port",7,configuration.getPort());
		assertEquals("check timeout",5000,configuration.getTimeout());
	}

	@Test
	public void testPortConfigurationURI() throws URISyntaxException {
		PortConfiguration configuration = new PortConfiguration(new URI("port://barney:rubble@myhost:23/tcp?timeout=5"));
		assertEquals("check host","myhost",configuration.getHost());
		assertEquals("check port",23,configuration.getPort());
	}

	@Test
	public void testCopy() {
		PortConfiguration configuration = new PortConfiguration();
		PortConfiguration copy = configuration.copy();
		
		assertEquals("check host",configuration.getHost(),copy.getHost());
		assertEquals("check port",configuration.getPort(),copy.getPort());
		assertEquals("check timeout",configuration.getTimeout(),copy.getTimeout());
	}

	@Test
	public void testHost() {
		String host = "funky";
		PortConfiguration configuration = new PortConfiguration();
		configuration.setHost(host);
		assertEquals("check host",host,configuration.getHost());
	}

	@Test
	public void testPort() {
		int port = 1000;
		PortConfiguration configuration = new PortConfiguration();
		configuration.setPort(port);
		assertEquals("check timeout",port,configuration.getPort());
	}

	@Test
	public void testTimeout() {
		int timeOut = 1000;
		PortConfiguration configuration = new PortConfiguration();
		configuration.setTimeout(timeOut);
		assertEquals("check timeout",timeOut,configuration.getTimeout());
	}

	@Test
	public void testToString() {
		PortConfiguration configuration = new PortConfiguration();
		
		assertEquals("check toString()","host=localhost,port=7,timeout=5000",configuration.toString());
	}
}
