package com.boundary.camel.component.util;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements a simple TCP listener for port testing
 * 
 * @author davidg
 *
 */
public class SimpleServer extends Thread {
	public final static int DEFAULT_PORT = 6789;
	protected int port;
	protected ServerSocket listen_socket;

	/**
	 * Helper function to output an error
	 * 
	 * @param e {@link Exception} that ocurred
	 * @param msg {@link String} containing an informative message
	 */
	public static void fail(Exception e, String msg) {
		System.err.println(msg + ": " + e);
		System.exit(1);
	}

	/**
	 * Starts a simple TCP server on the provided report.
	 * 
	 * @param port Number of the listening port
	 */
	public SimpleServer(int port) {
		if (port == 0)
			port = DEFAULT_PORT;
		this.port = port;
		try {
			listen_socket = new ServerSocket(port);
		} catch (IOException e) {
			fail(e, "Exception creating server socket");
		}
		System.out.println("Server: listening on port " + port);
		this.start();
	}

	/**
	 * Runs waiting for a connection on the socket.
	 * 
	 */
	public void run() {
		try {
			Socket clientSocket = listen_socket.accept();
		} catch (IOException e) {
			fail(e, "Exception while listening for connections");
		}
	}

	/**
	 * Application that runs a socket on port passed to the program
	 * 
	 * @param args Port to use, defaults to 5555
	 */
	public static void main(String[] args) {
		int port = 5555;
		if (args.length == 1) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				port = 0;
			}
		}
		new SimpleServer(port);
	}

	/**
	 * Stop listening on the socket by closing {@link Socket} instance.
	 */
	public void stopServer() {
		new Thread() {
			public void run() {
				try {
					listen_socket.close();
					System.out.println("Stopping Server");
				} catch(Exception e) {
					System.out.println("Exception Stopping Server");
					e.printStackTrace();
				}
			}
		}.start();
	}
}
