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

import com.boundary.camel.component.ping.PingCheck;

/**
 * Implements a client socket connection to host and port.
 * 
 * The client can be configured and invoked in several different ways:
 * <pre>
 * 
 *    TCPClient client = new TCPClient();
 *    client.connect(); // Use defaults of host, port, and time out.
 *    
 *    ....
 *    
 *    TCPClient client = new TCPClient("myhost",1234,5000);
 *    client.connect();
 *    
 *    ...
 *    
 *    TCPClient client = new TCPClient();
 *    client.setHost("myhost");
 *    client.setPort(1234);
 *    client.setTimeout(5000);
 *    client.connect();
 *    
 *    ...
 *    
 *    TCPClient client = new TCPClient();
 *    client.connect("myhost",1234,5000);
 *    
 *    ...
 *    
 *    // NOTE: Any parameter specificed in the connect method will set its corresponding member variable.
 * </pre>
 * 
 * @author davidg
 * 
 */
public class TCPClient {

	private static final Logger LOG = LoggerFactory.getLogger(TCPClient.class);
	
	private PortStatus status;
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
	public PortStatus getStatus() {
		return status;
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
			status = PortStatus.CONNECTED;
		} catch (SocketTimeoutException t) {
			printStackTrace(t);
			message = t.getMessage();
			status = PortStatus.SOCKET_TIMEOUT;
		} catch (ConnectException c) {
			printStackTrace(c);
			message = c.getMessage();
			status = PortStatus.CONNECTION_REFUSED;
		} catch (UnknownHostException u) {
			printStackTrace(u);
			message = u.getMessage();
			status = PortStatus.UNKNOWN_HOST;
		} catch (IllegalArgumentException a) {
			printStackTrace(a);
			message = a.getMessage();
			status = PortStatus.ERROR;
		} catch (IllegalBlockingModeException b) {
			printStackTrace(b);
			message = b.getMessage();
			status = PortStatus.ERROR;
		} catch (IOException e) {
			printStackTrace(e);
			message = e.getMessage();
			status = PortStatus.ERROR;
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
		System.out.println("status: " + client.getStatus());
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
