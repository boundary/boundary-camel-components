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
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.channels.IllegalBlockingModeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.camel.component.common.ServiceCheck;


/**
 * Implements a URL client to communicate using HTTP(S),FTP,etc
 * 
 */
public class UrlClient extends ServiceCheck {

	private static final Logger LOG = LoggerFactory.getLogger(UrlClient.class);
	
	private URLStatus urlStatus;
	private InputStream content;
	private HttpURLConnection connection;

	private int connectTimeout;
	private String output;
	private int responseCode;

	private UrlConfiguration configuration;

	private double NANO_SECONDS_TO_MILLI_SECONDS = 1E-6;

	private long elapsedTime;

	public UrlClient() {
	}

	/**
	 * Get the results of an attempt to connect to a URL
	 * 
	 * @return {@link URLStatus}
	 */
	public URLStatus getURLStatus() {
		return urlStatus;
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
		setElapsedTime((long)((stop - start)*NANO_SECONDS_TO_MILLI_SECONDS));
	}
	
	private void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public long getElapsedTime() {
		return elapsedTime;
	}

	protected void connect() {
		try {
			URL url = configuration.toURL();
			LOG.info("attempting connection with url: {}",url);
			
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(connectTimeout);
			connection.connect();
			responseCode = connection.getResponseCode();
			
			BufferedReader is = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			String line;
			StringBuffer sb = new StringBuffer();
			while ((line = is.readLine()) != null) {
				sb.append(line);
			}
			
			output = sb.toString();

		} catch (FileNotFoundException f) {
			System.out.println("FileNotFoundException");
			f.printStackTrace();
		}
		catch (UnknownHostException u) {
			u.printStackTrace();
		}
		catch (IOException i) {
			i.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public String getOutput() {
		return output;
	}

	public int getResponseCode() {
		return responseCode;
	}


	public static void main(String[] args) throws InterruptedException {
		UrlClient client = new UrlClient();
		UrlConfiguration config = new UrlConfiguration();
		config.setScheme("http");
		config.setHost("localhost");
		String uri = "http://localhost";
		client.connect(config);
		System.out.println("status: " + client.getURLStatus());
		System.out.println("output: " + client.getOutput());
		System.out.println("elapsedTime: " + client.getElapsedTime());
	}
}
