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

	public static final int DEFAULT_TIME_OUT=5000;
	
	private InetSocketAddress address;
	private Socket sock;
	private int timeOut;
	private TCPClientStatus status;

	/**
	 * Constructor the {@link TCPClient} instance
	 */
	public TCPClient(String host, int port,int timeOut) {
		this.sock = new Socket();
		this.timeOut = timeOut;
		this.address = new InetSocketAddress(host,port);
	}
	
	public TCPClient(String host,int port) {
		this(host,port,DEFAULT_TIME_OUT);
	}
	
	/**
	 * Attempt a connection to the host and port.
	 * 
	 */
	public void connect() {

		try {
			sock.connect(address, timeOut);
			sock.close();
			status = TCPClientStatus.CONNECTED;
		}
		catch (SocketTimeoutException t) {
			printStackTrace(t);
			status = TCPClientStatus.SOCKET_TIMEOUT;
		}
		catch (ConnectException c) {
			printStackTrace(c);
			status = TCPClientStatus.CONNECTION_REFUSED;
		}
		catch (UnknownHostException u) {
			printStackTrace(u);
			status = TCPClientStatus.UNKNOWN_HOST;
		}
		catch (IllegalArgumentException a) {
			printStackTrace(a);
			status = TCPClientStatus.ERROR;
		}
		catch (IllegalBlockingModeException b) {
			printStackTrace(b);
			status = TCPClientStatus.ERROR;
		}
		catch (IOException e) {
			printStackTrace(e);
			status = TCPClientStatus.ERROR;
		}
		finally {
			try {
				sock.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Get the results of an attempt to connect to a host and port
	 * 
	 * @return {@link TCPClientStatus}
	 */
	public TCPClientStatus getStatus() {
		return status;
	}

	public static void main(String[] args) {
		TCPClient client = new TCPClient("localhost",1234,5000);
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
