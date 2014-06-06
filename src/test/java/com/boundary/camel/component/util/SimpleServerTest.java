package com.boundary.camel.component.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SimpleServerTest {

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

	private void connect(int port, int timeOut) {
		Socket sock = new Socket();
		InetSocketAddress address = new InetSocketAddress("localhost", port);
		try {
			sock.connect(address, timeOut);
			System.out.println("Connected");
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (sock != null) {
				try {
					sock.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Test
	public void testSimpleServerFirst() {
		SimpleServer server = new SimpleServer(6666);
		connect(6666, 5000);
		server.stopServer();
	}

	@Test
	public void testSimpleServerSecond() {
		SimpleServer server = new SimpleServer(6666);
		connect(8888, 5000);
		server.stopServer();
	}

	@Test
	public void testStopServer() {
	}

}
