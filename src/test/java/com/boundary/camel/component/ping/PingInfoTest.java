/**
 * 
 */
package com.boundary.camel.component.ping;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.boundary.camel.component.ping.PingInfo;

/**
 * @author davidg
 * 
 */
public class PingInfoTest {
	
	private PingInfo info;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}


	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		info = new PingInfo();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testHost() {
		String expectedHost = "localhost";
		info.setHost(expectedHost);
		assertEquals("Check host",expectedHost,info.getHost());
	}

	@Test
	public void testTransmitted() {
		int expectedTransmitted = 100;
		info.setTransmitted(expectedTransmitted);
		assertEquals("Check transmitted", expectedTransmitted, info.getTransmitted());;
	}
	@Test
	public void testReceived() {
		int expectedReceived = 100;
		info.setReceived(expectedReceived);
		assertEquals("Check transmitted", expectedReceived, info.getReceived());
	}
	@Test
	public void testTTL() {
		int expectedTTL = 100;
		info.setTTL(expectedTTL);
		assertEquals("Check TTL", expectedTTL, info.getTTL());
	}
	
	@Test
	public void testTime() {
		double expectedTime = 123.45;
		info.setTime(expectedTime);
		assertEquals("Check Time", expectedTime,info.getTime(),0.0);
	}
	
	@Ignore("Not implemented")
	@Test
	public void testPayload() {
		fail("Not yet implemented");
	}
	@Test
	public void testRTTMin() {
		double expectedRTTMin = 123.45;
		info.setRTTMin(expectedRTTMin);
		assertEquals("Check RTTMin", expectedRTTMin,info.getRTTMin(),0.0);
	}
	@Test
	public void testRTTAvg() {
		double expectedRTTAvg = 123.45;
		info.setRTTAvg(expectedRTTAvg);
		assertEquals("Check Time", expectedRTTAvg,info.getRTTAvg(),0.0);
	}
	@Test
	public void testRTTMax() {
		double expectedRTTMax = 123.45;
		info.setRTTMax(expectedRTTMax);
		assertEquals("Check Time", expectedRTTMax,info.getRTTMax(),0.0);
	}
	@Test
	public void testRTTMDev() {
		double expectedRTTMDev = 123.45;
		info.setRTTMDev(expectedRTTMDev);
		assertEquals("Check Time", expectedRTTMDev,info.getRTTMDev(),0.0);
	}
}
