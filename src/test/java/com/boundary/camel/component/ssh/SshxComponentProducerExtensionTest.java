package com.boundary.camel.component.ssh;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.AvailablePortFinder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.boundary.camel.component.ping.PingResult;

public class SshxComponentProducerExtensionTest extends SshComponentTestSupport {
	
	
	@Before
	public void setUp() throws Exception {
		super.setUp();

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {

		super.tearDown();
	}

    @Test
    public void testInputConfiguration() throws Exception {
    	SshxConfiguration config = new SshxConfiguration();
        final String msg = "test 1\n";
        config.setCommand(msg);
        config.setHost("localhost");
        config.setPort(port);

        MockEndpoint mock = getMockEndpoint("mock:config-ssh-out");
        mock.expectedMinimumMessageCount(1);

        template.sendBody("direct:config-ssh", config);

        assertMockEndpointsSatisfied();
    }
    
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() {
            	from("direct:config-ssh")
                .to("sshx://smx:smx@localhost:" + port + "?timeout=3000")
     			.to("mock:config-ssh-out");
            }
        };
    }
}
