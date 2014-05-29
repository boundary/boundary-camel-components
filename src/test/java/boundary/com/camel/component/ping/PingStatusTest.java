/**
 * 
 */
package boundary.com.camel.component.ping;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author davidg
 * 
 */
public class PingStatusTest {
	
	private PingStatus status;

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
		status = new PingStatus();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTransmitted() {
		int expectedTransmitted = 100;
		status.setTransmitted(expectedTransmitted);
		assertEquals("Check transmitted", expectedTransmitted, status.getTransmitted());;
	}
	@Test
	public void testReceived() {
		int expectedReceived = 100;
		status.setReceived(expectedReceived);
		assertEquals("Check transmitted", expectedReceived, status.getReceived());
	}
	@Test
	public void testTTL() {
		int expectedTTL = 100;
		status.setTTL(expectedTTL);
	}
	
	@Test
	public void testTime() {
		double expectedTime = 123.45;
		status.setTime(expectedTime);
		assertEquals("Check Time", expectedTime,status.getTime(),0.0);
	}
	
	@Ignore("Not implemented")
	@Test
	public void testPayload() {
		fail("Not yet implemented");
	}
	@Test
	public void testRTTMin() {
		double expectedRTTMin = 123.45;
		status.setRTTMin(expectedRTTMin);
		assertEquals("Check RTTMin", expectedRTTMin,status.getRTTMin(),0.0);
	}
	@Test
	public void testRTTAvg() {
		double expectedRTTAvg = 123.45;
		status.setRTTAvg(expectedRTTAvg);
		assertEquals("Check Time", expectedRTTAvg,status.getRTTAvg(),0.0);
	}
	@Test
	public void testRTTMax() {
		double expectedRTTMax = 123.45;
		status.setRTTMax(expectedRTTMax);
		assertEquals("Check Time", expectedRTTMax,status.getRTTMax(),0.0);
	}
	@Test
	public void testRTTMDev() {
		double expectedRTTMDev = 123.45;
		status.setRTTMDev(expectedRTTMDev);
		assertEquals("Check Time", expectedRTTMDev,status.getRTTMDev(),0.0);
	}
}
