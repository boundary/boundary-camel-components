package com.boundary.camel.component.url;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import java.net.UnknownHostException;
import java.nio.channels.IllegalBlockingModeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.camel.component.common.ServiceCheck;
import com.boundary.camel.component.ping.PingClient;

/**
 * Implements a client socket connection to host and port.
 * 
 * The client can be configured and invoked in several different ways:
 * <pre>
 * 
 *    TCPClient client = new UrlClient();
 *    client.connect(); // Use defaults of host, port, and time out.
 * </pre>
 * 
 * @author davidg
 * 
 */
public class URLClient extends ServiceCheck {

	private static final Logger LOG = LoggerFactory.getLogger(URLClient.class);
	
	private URLStatus urlStatus;
	private String message;

	private int connectTimeout;

	public URLClient() {
	}

	public String getMessage() {
		return this.message;
	}

	/**
	 * Get the results of an attempt to connect to a URL
	 * 
	 * @return {@link URLStatus}
	 */
	public URLStatus getURLStatus() {
		return urlStatus;
	}

	/**
	 * Attempt a connection to the host and port.
	 * 
	 * @param s {@link String}
	 */
	public void connect(String s) {

		try {
			URI uri = new URI(s);
			URL url = uri.toURL();
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			
			urlConnection.setConnectTimeout(connectTimeout);

			BufferedReader bin = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			String content = url.getContent().getClass().toString();
			System.out.println(content);
			while ((line = bin.readLine()) != null) {
				System.out.println(line);
			}

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

	public static void main(String[] args) {
		URLClient client = new URLClient();
		String uri = "http://localhost";
		client.connect(uri);
		System.out.println("status: " + client.getURLStatus());
	}

	public static URLClient setUpDefaultClient() {
		return new URLClient();
	}
}
