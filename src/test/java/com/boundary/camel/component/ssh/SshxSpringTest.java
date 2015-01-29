package com.boundary.camel.component.ssh;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.Ignore;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;


/**
 * @version $Revision$
 */
public class SshxSpringTest extends CamelSpringTestSupport {

	@Ignore("Stubbed out test")
	@Test
	public void testPort() throws Exception {
		MockEndpoint out = getMockEndpoint("mock:out");
		out.setExpectedMessageCount(1);

		assertMockEndpointsSatisfied();
	}
	
	@Ignore("Stubbed out test")
	@Test
	public void testConnectionRefused() throws InterruptedException {
		MockEndpoint mock = getMockEndpoint("mock:out");
		mock.setExpectedMessageCount(1);

		mock.assertIsSatisfied();

	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/ssh-test.xml");
	}
}

