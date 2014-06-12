package com.boundary.camel.component.ping;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.boundary.camel.component.ping.PingConfiguration;

public class PingConfigurationTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	private PingConfiguration config;

	@Before
	public void setUp() throws Exception {
		config = new PingConfiguration();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetWaitTime() {
		long expectedWaitTime = 10000;
		config.setWaitTime(expectedWaitTime);
		assertEquals("Check SetWaitTime()",expectedWaitTime, config.getWaitTime());
	}
	
	@Test
	public void testPacketSize() {
		long expectedPacketSize = 100000;
		config.setPacketSize(expectedPacketSize);
		
		assertEquals("Check setPacketSize",expectedPacketSize,config.getPacketSize());
	}
	
	@Test
	public void testHost() {
		String expectedHost = "that's a spicey meat ta' ball";
		config.setHost(expectedHost);
		
		assertEquals("Check setPacketSize",expectedHost,config.getHost());
	}
	
	
	@Test
	public void testPortConfigurationURI() throws URISyntaxException {
		PingConfiguration configuration = new PingConfiguration(new URI("ping://barney:rubble@myhost:23/icmp?timeout=5"));
		assertEquals("check host","myhost",configuration.getHost());
		assertEquals("check port",23,configuration.getPort());
		assertEquals("check path","/icmp",configuration.getPath());
		assertEquals("check user","barney",configuration.getUser());
		assertEquals("check password","rubble",configuration.getPassword());
	}
	
	@Test
	public void testURIConfigurationNoPort() throws URISyntaxException {
		PingConfiguration configuration = new PingConfiguration(new URI("ping://myhost:/icmp?timeout=5"));
		assertEquals("check host","myhost",configuration.getHost());
		assertEquals("check port",7,configuration.getPort());
		assertEquals("check path","/icmp",configuration.getPath());
		assertNull("check user",configuration.getUser());
		assertNull("check password",configuration.getPassword());
	}
	
	@Test
	public void testURIConfigurationkHostOnly() throws URISyntaxException {
		PingConfiguration configuration = new PingConfiguration(new URI("ping://myhost/icmp"));
		assertEquals("check host","myhost",configuration.getHost());
		assertEquals("check port",7,configuration.getPort());
		assertEquals("check path","/icmp",configuration.getPath());
		assertNull("check user",configuration.getUser());
		assertNull("check password",configuration.getPassword());
	}

	
	@Test
	public void testURIConfigurationNoUserInfo() throws URISyntaxException {
		PingConfiguration configuration = new PingConfiguration(new URI("ping://myhost:/icmp?timeout=5"));
		assertEquals("check host","myhost",configuration.getHost());
		assertEquals("check port",7,configuration.getPort());
		assertEquals("check path","/icmp",configuration.getPath());
		assertNull("check user",configuration.getUser());
		assertNull("check password",configuration.getPassword());
	}
	
	@Test
	public void testURIConfigurationNoPassword() throws URISyntaxException {
		PingConfiguration configuration = new PingConfiguration(new URI("ping://fred:@myhost/icmp?timeout=5"));
		assertEquals("check host","myhost",configuration.getHost());
		assertEquals("check port",7,configuration.getPort());
		assertEquals("check path","/icmp",configuration.getPath());
		assertEquals("check user","fred",configuration.getUser());
		assertNull("check password",configuration.getPassword());
	}

	@Test
	public void testCopy() {
		PingConfiguration configuration = new PingConfiguration();
		PingConfiguration copy = configuration.copy();
		
		assertEquals("check host",configuration.getHost(),copy.getHost());
		assertEquals("check port",configuration.getPort(),copy.getPort());
		assertEquals("check path",configuration.getPath(),copy.getPath());
		assertEquals("check user",configuration.getUser(),copy.getUser());
		assertEquals("check password",configuration.getPassword(),copy.getPassword());
		assertEquals("check timeout",configuration.getTimeout(),copy.getTimeout());
	}

	@Test
	public void testPort() {
		int port = 1000;
		PingConfiguration configuration = new PingConfiguration();
		configuration.setPort(port);
		assertEquals("check timeout",port,configuration.getPort());
	}

	@Test
	public void testTimeout() {
		int timeOut = 1000;
		PingConfiguration configuration = new PingConfiguration();
		configuration.setTimeout(timeOut);
		assertEquals("check timeout",timeOut,configuration.getTimeout());
	}

	@Test
	public void testToString() throws URISyntaxException {
		PingConfiguration configuration = new PingConfiguration(new URI("ping://ted:nugent@cat.scratch.fever.com:666/icmp?timeout=5"));
		
		// TBD: the timeout query parameter defaults to a value of 5000, it is not parsed.
		assertEquals("check toString()","host=cat.scratch.fever.com,port=666,path=/icmp,user=ted,password=nugent,timeout=5000",configuration.toString());
	}
}
