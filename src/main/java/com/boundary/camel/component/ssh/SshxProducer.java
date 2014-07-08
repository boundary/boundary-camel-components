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

import org.apache.camel.CamelExchangeException;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.component.ssh.SshResult;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SshxProducer extends DefaultProducer {
	
    protected final Logger LOG = LoggerFactory.getLogger(getClass());
    
    private SshxEndpoint endpoint;

    public SshxProducer(SshxEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        final Message in = exchange.getIn();
        SshxConfiguration config = in.getMandatoryBody(SshxConfiguration.class);
        endpoint.setConfiguration(config);
        
        final String command = config.getCommand();

        try {
            SshxResult result = endpoint.sendExecCommand(config);
            exchange.getOut().setBody(result);
            exchange.getOut().setHeader(SshxResult.EXIT_VALUE, result.getExitValue());
            exchange.getOut().setHeader(SshxResult.STDERR, result.getStderr());
        } catch (Exception e) {
            throw new CamelExchangeException("Cannot execute command: " + command, exchange, e);
        }

        // propagate headers and attachments
        exchange.getOut().getHeaders().putAll(in.getHeaders());
        exchange.getOut().setAttachments(in.getAttachments());
    }
}