package com.boundary.camel.component.ping;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.boundary.camel.component.ping.PingArguments;

public class PingArgumentsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	private PingArguments args;

	@Before
	public void setUp() throws Exception {
		args = new PingArguments();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPingArguments() {
		PingArguments args = new PingArguments();
		
		assertEquals("Check default WaitTime",args.DEFAULT_WAIT_TIME,args.getWaitTime());
	}

	@Test
	public void testSetWaitTime() {
		long expectedWaitTime = 10000;
		args.setWaitTime(expectedWaitTime);
		assertEquals("Check SetWaitTime()",expectedWaitTime, args.getWaitTime());
	}
	
	@Test
	public void testPacketSize() {
		long expectedPacketSize = 100000;
		args.setPacketSize(expectedPacketSize);
		
		assertEquals("Check setPacketSize",expectedPacketSize,args.getPacketSize());

	}

}
