package com.boundary.camel.component.port;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.channels.IllegalBlockingModeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.camel.component.ping.PingClient;

/**
 * Implements a client socket connection to host and port.
 * 
 * The client can be configured and invoked as in the following code snippet
 * <pre>
 *    
 *    TCPClient client = new TCPClient();
 *    client.connect("myhost",1234,5000);
 *    
 * </pre>
 * 
 * @author davidg
 * 
 */
public class TCPClient {

	private static final Logger LOG = LoggerFactory.getLogger(TCPClient.class);
	
	private PortStatus portStatus;
	private String message;

	public TCPClient() {
	}

	public String getMessage() {
		return this.message;
	}

	/**
	 * Get the results of an attempt to connect to a host and port
	 * 
	 * @return {@link PortStatus}
	 */
	public PortStatus getPortStatus() {
		return portStatus;
	}

	/**
	 * Attempt a connection to the host and port.
	 * 
	 */
	public void connect(String host, int port, int timeOut) {
		InetSocketAddress address = new InetSocketAddress(host, port);
		Socket sock = new Socket();

		try {
			sock.connect(address, timeOut);
			sock.close();
			message = "OK";
			portStatus = PortStatus.CONNECTED;
		} catch (SocketTimeoutException t) {
			printStackTrace(t);
			message = t.getMessage();
			portStatus = PortStatus.SOCKET_TIMEOUT;
		} catch (ConnectException c) {
			printStackTrace(c);
			message = c.getMessage();
			portStatus = PortStatus.CONNECTION_REFUSED;
		} catch (UnknownHostException u) {
			printStackTrace(u);
			message = u.getMessage();
			portStatus = PortStatus.UNKNOWN_HOST;
		} catch (IllegalArgumentException a) {
			printStackTrace(a);
			message = a.getMessage();
			portStatus = PortStatus.ERROR;
		} catch (IllegalBlockingModeException b) {
			printStackTrace(b);
			message = b.getMessage();
			portStatus = PortStatus.ERROR;
		} catch (IOException e) {
			printStackTrace(e);
			message = e.getMessage();
			portStatus = PortStatus.ERROR;
		} finally {
			try {
				sock.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		TCPClient client = new TCPClient();
		client.connect("localhost", 1234, 5000);
		System.out.println("status: " + client.getPortStatus());
	}

	private void printStackTrace(Exception e) {
		StringBuffer s = new StringBuffer();
		StackTraceElement[] trace = e.getStackTrace();
		s.append(e.getClass().getName() + ": " + e.getMessage() + "\n");
		for (StackTraceElement element : trace) {
			s.append("at " + element.toString() + "\n");
		}
		LOG.debug(s.toString());
	}

	public static TCPClient setUpDefaultClient() {
		return new TCPClient();
	}
}
