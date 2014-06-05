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
 * @author davidg
 * 
 */
public class TCPClient {

	private static final Logger LOG = LoggerFactory.getLogger(TCPClient.class);

	private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 7;
	private static final int DEFAULT_TIME_OUT = 5000;

	private String host;
	private int port;
	private Socket sock;
	private int timeOut;
	private PortStatus status;
	private String message;

	public TCPClient() {
		this(DEFAULT_HOST, DEFAULT_PORT, DEFAULT_TIME_OUT);
	}

	public TCPClient(String host, int port) {
		this(host, port, DEFAULT_TIME_OUT);
	}

	/**
	 * Constructor the {@link TCPClient} instance
	 */
	public TCPClient(String host, int port, int timeOut) {
		this.sock = new Socket();
		this.timeOut = timeOut;
		this.host = host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getHost() {
		return this.host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getPort() {
		return this.port;
	}

	public void setTimeout(int timeOut) {
		this.timeOut = timeOut;
	}

	public int getTimeout() {
		return this.timeOut;
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
	public void connect() {
		InetSocketAddress address = new InetSocketAddress(host, port);

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
		TCPClient client = new TCPClient("localhost", 1234, 5000);
		client.connect();
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
}
