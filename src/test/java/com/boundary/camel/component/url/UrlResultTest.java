// Copyright 2014 Boundary, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.boundary.camel.component.url;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.boundary.camel.component.common.ServiceStatus;

public class UrlResultTest {
	
	private UrlResult result;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		result = new UrlResult();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUrlInfo() {
		assertNotNull("check uri",result.getHost());
		assertNull("check url",result.getURL());
	}

	@Test
	public void testToString() {
		String url = "https://www.google.com/some/path";
		result.setStatus(ServiceStatus.SUCCESS);
		result.setURLStatus(URLStatus.UNKNOWN_HOST);
		assertEquals("check toString()",",url=https://www.google.com/some/path,timeout=5000,urlStatus=UNKNOWN_HOST",result.toString());
	}


	@Test
	public void testStatus() {
		result.setURLStatus(URLStatus.UNKNOWN_HOST);
		assertEquals("check status",URLStatus.UNKNOWN_HOST,result.getURLStatus());
	}

	@Test
	public void testGetStatus() {
		ServiceStatus status = ServiceStatus.FAIL;
		result.setStatus(status);
		assertEquals("check service status",status,result.getStatus());
	}

	@Test
	public void testMessage() {
		String message = "foobar";
		result.setMessage(message);
		assertEquals("check message",message,result.getMessage());
	}

	@Test
	public void tesHost() {
		String host = "myHost";
		result.setHost(host);
		assertEquals("check host",host,result.getHost());
	}

	@Test
	public void testTimestamp() {
		Date now = new Date();
		result.setTimestamp(now);
		assertEquals("check timestamp",now,result.getTimestamp());
	}
}
