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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UrlConfigurationTest {
	
	private UrlConfiguration urlConfiguration;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		urlConfiguration = new UrlConfiguration();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetScheme() {
		urlConfiguration.setScheme("ftp");
		assertEquals("scheme not equal","ftp",urlConfiguration.getScheme());
	}

	@Test
	public void testGetQuery() {
		String query = "a=foo&b=foo";
		urlConfiguration.setQuery(query);
		assertEquals("query not equal",query,urlConfiguration.getQuery());
	}

	@Test
	public void testSetHost() {
		urlConfiguration.setHost("foobar");
		assertEquals("host not equal","foobar",urlConfiguration.getHost());
	}

	@Test
	public void testSetPort() {
		urlConfiguration.setPort(8080);
		assertEquals("port not equal",8080,urlConfiguration.getPort());
	}

	@Test
	public void testSetTimeout() {
		urlConfiguration.setTimeout(6666);
		assertEquals("timeout not equal",6666,urlConfiguration.getTimeout());
	}

	@Test
	public void testSetPath() {
		String path = "red/green/blue/";
		urlConfiguration.setPath(path);
		assertEquals("paths not equal",path,urlConfiguration.getPath());
	}

	@Test
	public void testSetUser() {
		urlConfiguration.setUser("lerma");
		assertEquals("user not equal","lerma",urlConfiguration.getUser());
	}

	@Test
	public void testSetPassword() {
		String password="q85JnK>hm>uwF6W";
		urlConfiguration.setPassword(password);
		assertEquals("password not equal",password,urlConfiguration.getPassword());
	}
}
