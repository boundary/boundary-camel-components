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
	
	public final static int LISTENING_PORT = 5555;
	
	MultiThreadedServer server;

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
	public void testConnect() throws Exception {
		server = new MultiThreadedServer(LISTENING_PORT,1);
		server.start();
		TCPClient client = new TCPClient("localhost",LISTENING_PORT);
		client.connect();
		assertTrue(client.getStatus() == TCPClientStatus.CONNECTED);
		server.stop();
	}

	@Test
	public void testConnectionRefused() {
		TCPClient client = new TCPClient("localhost",555);
		client.connect();
		System.out.println(client.getStatus());
		assertTrue(client.getStatus() == TCPClientStatus.CONNECTION_REFUSED);
	}
	
	@Test
	public void testUnknownHost() {
		TCPClient client = new TCPClient("foobar",1234);
		client.connect();
		assertTrue(client.getStatus() == TCPClientStatus.UNKNOWN_HOST);
	}
	
	@Test
	public void testSocketTimeout() {
		TCPClient client = new TCPClient("10.0.0.0",1234);
		client.connect();
		assertTrue(client.getStatus() == TCPClientStatus.SOCKET_TIMEOUT);
	}
}
