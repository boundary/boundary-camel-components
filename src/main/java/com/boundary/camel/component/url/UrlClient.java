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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;

import static java.net.HttpURLConnection.*;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import static com.boundary.camel.component.url.UrlStatus.*;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.camel.component.common.ServiceCheck;


/**
 * Implements a URL client to communicate using HTTP(S),FTP,etc
 * 
 */
public class UrlClient extends ServiceCheck {
	
	private final static int UNDEFINED_HTTP_RESPONSE=0;
	
	private final static double NANO_SECONDS_TO_MILLI_SECONDS = 1E-6;
	private final static Logger LOG = LoggerFactory.getLogger(UrlClient.class);
	
	private UrlStatus urlStatus;
	private InputStream inputStream;
	private HttpURLConnection connection;
	private String responseBody;
	private long responseTime;
	private int responseCode =  UNDEFINED_HTTP_RESPONSE;
	private String errorMessage;

	private UrlConfiguration configuration;

	public UrlClient() {
	}

	/**
	 * Get the results of an attempt to connect to a URL
	 * 
	 * @return {@link URLStatus}
	 */
	public UrlStatus getURLStatus() {
		return urlStatus;
	}
	
	public void setURLStatus(UrlStatus urlStatus) {
		this.urlStatus = urlStatus;
	}

	public static UrlClient setUpDefaultClient() {
		return new UrlClient();
	}
	
	public void connect(UrlConfiguration configuration) {
		// Set the configuration
		this.configuration = configuration;
		
		// Start the timer
    	long start = System.nanoTime();
    	
    	// Connect to the URL and retrieve the contents
    	connect();
    	
    	// Stop the timer
    	long stop = System.nanoTime();

    	// compute the elapsed time
		setResponseTime((long)((stop - start)*NANO_SECONDS_TO_MILLI_SECONDS));
	}
	
	private void configureConnection(HttpURLConnection connection) throws ProtocolException {
		
		connection.setConnectTimeout(configuration.getTimeout());
		connection.setRequestMethod(configuration.getRequestMethod());
		connection.setInstanceFollowRedirects(configuration.getFollowRedirects());
		
		if (configuration.getUser() != null ||
				configuration.getPassword() != null) {
				StringBuffer sb = new StringBuffer();
				if (configuration.getUser() != null) {
					sb.append(configuration.getUser());
				}
				sb.append(":");
				if (configuration.getPassword() != null) {
					sb.append(configuration.getPassword());
				}
				connection.setRequestProperty("Authorization", "Basic "  + Base64.encodeBase64String(sb.toString().getBytes()));
			}
	}

	protected void connect() {
		try {
			URL url = configuration.toURL();
			LOG.info("attempting connection with url: {}",url);
			
			connection = (HttpURLConnection) url.openConnection();
			configureConnection(connection);
		
			connection.connect();
			setResponseCode(connection.getResponseCode());
			setInputStream(connection.getInputStream());
			
			BufferedReader is = new BufferedReader(new InputStreamReader(getInputStream()));
			
			String line;
			StringBuffer sb = new StringBuffer();
			while ((line = is.readLine()) != null) {
				sb.append(line);
			}
			
			setResponseBody(sb.toString());
			setURLStatus(OK);
		} 
		catch (UnknownHostException u) {
			setURLStatus(UNKNOWN_HOST);
			setResponseCode(HTTP_BAD_GATEWAY);
			if (LOG.isDebugEnabled()) {
				u.printStackTrace();
			}
		}
		catch (SocketTimeoutException s) {
			setURLStatus(TIME_OUT);
			setResponseCode(HTTP_INTERNAL_ERROR);
			if (LOG.isDebugEnabled()) {
				s.printStackTrace();
			}
		}
		catch (IOException i) {
			setURLStatus(ERROR);
			setResponseCode(HTTP_INTERNAL_ERROR);
			if (LOG.isDebugEnabled()) {
				i.printStackTrace();
			}
		}
		catch (Exception e) {
			setURLStatus(ERROR);
			setResponseCode(HTTP_INTERNAL_ERROR);
			if (LOG.isDebugEnabled()) {
				e.printStackTrace();
			}
		}
	}
	
	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
	
	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public HttpURLConnection getConnection() {
		return connection;
	}

	public void setConnection(HttpURLConnection connection) {
		this.connection = connection;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public static void main(String[] args) throws InterruptedException {
		UrlClient client = new UrlClient();
		UrlConfiguration config = new UrlConfiguration();
		config.setScheme("http");
		config.setHost("localhost");
		client.connect(config);
		System.out.println("status: " + client.getURLStatus());
		System.out.println("output: " + client.getResponseBody());
		System.out.println("elapsedTime: " + client.getResponseTime());
	}
}
