package com.boundary.camel.component.port;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.boundary.camel.component.util.MultiThreadedServer;

public class TCPClientTest {
	
	private TCPClient client;
	private final static int LISTENING_PORT = 5555;
	private final static int TIMEOUT = 5000;
	
	MultiThreadedServer server;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		client = new TCPClient();
	}

	@After
	public void tearDown() throws Exception {
		client = null;
	}
	
	@Test
	public void testConnect() throws Exception {
		server = new MultiThreadedServer(LISTENING_PORT,1);
		server.start();
		
		TCPClient client = new TCPClient();
		client.connect("localhost",LISTENING_PORT,TIMEOUT);
		
		assertTrue(client.getStatus() == PortStatus.CONNECTED);
		
		server.stop();
	}

	@Test
	public void testConnectionRefused() {
		TCPClient client = new TCPClient();
		client.connect("localhost",555,TIMEOUT);
		System.out.println(client.getStatus());
		assertTrue(client.getStatus() == PortStatus.CONNECTION_REFUSED);
	}
	
	@Test
	public void testUnknownHost() {
		TCPClient client = new TCPClient();
		client.connect("foobar",1234,TIMEOUT);
		assertTrue(client.getStatus() == PortStatus.UNKNOWN_HOST);
	}
	
	@Test
	public void testSocketTimeout() {
		TCPClient client = new TCPClient();
		client.connect("10.0.0.0",1234,TIMEOUT);
		assertTrue(client.getStatus() == PortStatus.SOCKET_TIMEOUT);
	}
}
