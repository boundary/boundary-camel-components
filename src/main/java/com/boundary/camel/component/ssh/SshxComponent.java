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

import java.net.URI;
import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.UriEndpointComponent;
import org.apache.sshd.common.KeyPairProvider;

/**
 * Represents the component that manages {@link SshEndpoint}.
 */
public class SshxComponent extends UriEndpointComponent {
    private SshxConfiguration configuration;

    public SshxComponent() {
        super(SshxEndpoint.class);
    }

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        SshxConfiguration newConfig;

        if (configuration == null) {
            newConfig = new SshxConfiguration(new URI(uri));
        } else {
            newConfig = configuration.copy();
        }

        SshxEndpoint endpoint = new SshxEndpoint(uri, this, newConfig);
        setProperties(endpoint.getConfiguration(), parameters);
        return endpoint;
    }

    public SshxConfiguration getConfiguration() {
        if (configuration == null) {
            configuration = new SshxConfiguration();
        }
        return configuration;
    }

    public void setConfiguration(SshxConfiguration configuration) {
        this.configuration = configuration;
    }

    public String getHost() {
        return getConfiguration().getHost();
    }

    public void setHost(String host) {
        getConfiguration().setHost(host);
    }

    public int getPort() {
        return getConfiguration().getPort();
    }

    public void setPort(int port) {
        getConfiguration().setPort(port);
    }

    public String getUsername() {
        return getConfiguration().getUsername();
    }

    public void setUsername(String username) {
        getConfiguration().setUsername(username);
    }

    public String getPassword() {
        return getConfiguration().getPassword();
    }

    public void setPassword(String password) {
        getConfiguration().setPassword(password);
    }

    public String getPollCommand() {
        return getConfiguration().getPollCommand();
    }

    public void setPollCommand(String pollCommand) {
        getConfiguration().setPollCommand(pollCommand);
    }

    public KeyPairProvider getKeyPairProvider() {
        return getConfiguration().getKeyPairProvider();
    }

    public void setKeyPairProvider(KeyPairProvider keyPairProvider) {
        getConfiguration().setKeyPairProvider(keyPairProvider);
    }

    public String getKeyType() {
        return getConfiguration().getKeyType();
    }

    public void setKeyType(String keyType) {
        getConfiguration().setKeyType(keyType);
    }

    public long getTimeout() {
        return getConfiguration().getTimeout();
    }

    public void setTimeout(long timeout) {
        getConfiguration().setTimeout(timeout);
    }

    /**
     * @deprecated As of version 2.11, replaced by {@link #getCertResource()}
     */
    public String getCertFilename() {
        return getConfiguration().getCertFilename();
    }

    /**
     * @deprecated As of version 2.11, replaced by {@link #setCertResource(String)}
     */
    public void setCertFilename(String certFilename) {
        getConfiguration().setCertFilename(certFilename);
    }

    public String getCertResource() {
        return getConfiguration().getCertResource();
    }

    public void setCertResource(String certResource) {
        getConfiguration().setCertResource(certResource);
    }
}
