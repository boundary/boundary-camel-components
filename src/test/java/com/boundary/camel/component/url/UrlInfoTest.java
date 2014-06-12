package com.boundary.camel.component.url;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.boundary.camel.component.common.ServiceStatus;

public class UrlInfoTest {
	
	private UrlInfo info;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		info = new UrlInfo();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUrlInfo() {
		assertNotNull("check uri",info.getHost());
		assertNull("check url",info.getURL());
		assertNull("check uri",info.getURI());
	}

	@Test
	public void testToString() {
		String url = "https://www.google.com/some/path";
		info.setStatus(ServiceStatus.SUCCESS);
		info.setURLStatus(URLStatus.UNKNOWN_HOST);
		info.setURL(url);
		assertEquals("check toString()",",url=https://www.google.com/some/path,timeout=5000,urlStatus=UNKNOWN_HOST",info.toString());
	}


	@Test
	public void testStatus() {
		info.setURLStatus(URLStatus.UNKNOWN_HOST);
		assertEquals("check status",URLStatus.UNKNOWN_HOST,info.getURLStatus());
	}

	@Test
	public void testGetStatus() {
		ServiceStatus status = ServiceStatus.FAIL;
		info.setStatus(status);
		assertEquals("check service status",status,info.getStatus());
	}

	@Test
	public void testMessage() {
		String message = "foobar";
		info.setMessage(message);
		assertEquals("check message",message,info.getMessage());
	}

	@Test
	public void tesHost() {
		String host = "myHost";
		info.setHost(host);
		assertEquals("check host",host,info.getHost());
	}

	@Test
	public void testTimestamp() {
		Date now = new Date();
		info.setTimestamp(now);
		assertEquals("check timestamp",now,info.getTimestamp());
	}
}
