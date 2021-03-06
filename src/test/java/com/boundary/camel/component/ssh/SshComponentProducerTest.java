/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.boundary.camel.component.ssh;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.component.ssh.SshResult;
import org.apache.camel.test.AvailablePortFinder;
import org.junit.Test;

public class SshComponentProducerTest extends SshComponentTestSupport {
	
	
    @Override
    public void setUp() throws Exception {
    	super.setUp();
    }
    
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }


    @Test
    public void testProducer() throws Exception {
        final String msg = "test\n";
    	SshxConfiguration config = new SshxConfiguration();
    	config.setCommand(msg);
    	config.setHost("localhost");
    	config.setPort(port);


        MockEndpoint mock = getMockEndpoint("mock:password");
        mock.expectedMinimumMessageCount(1);

        template.sendBody("direct:ssh", config);

        assertMockEndpointsSatisfied();
        
        List<Exchange> exchanges = mock.getExchanges();
        for (Exchange e : exchanges) {
        	System.out.println(e.toString());
        }
    }

    @Test
    public void testReconnect() throws Exception {
        final String msg = "test\n";
    	SshxConfiguration config = new SshxConfiguration();
        config.setCommand(msg);
    	config.setHost("localhost");
    	config.setPort(port);

        MockEndpoint mock = getMockEndpoint("mock:password");
        mock.expectedMinimumMessageCount(1);

        template.sendBody("direct:ssh", config);

        assertMockEndpointsSatisfied();

        sshd.stop();
        sshd.start();

        mock.reset();
        mock.expectedMinimumMessageCount(1);

        template.sendBody("direct:ssh", config);

        assertMockEndpointsSatisfied();
    }

    @Test
    public void testConnectionTimeout() throws Exception {
        final String msg = "test\n";
    	SshxConfiguration config = new SshxConfiguration();
        config.setCommand(msg);

        MockEndpoint mock = getMockEndpoint("mock:password");
        mock.expectedMinimumMessageCount(0);

        MockEndpoint mockError = getMockEndpoint("mock:error");
        mockError.expectedMinimumMessageCount(1);

        sshd.stop();
        sshd = null;

        template.sendBody("direct:ssh", config);

        assertMockEndpointsSatisfied();
    }
    


    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() {
                onException(Exception.class)
                        .handled(true)
                        .to("mock:error");

                from("direct:ssh")
                        .to("sshx://smx:smx@localhost:" + port + "?timeout=3000")
                        .to("mock:password");

            }
        };
    }
}
