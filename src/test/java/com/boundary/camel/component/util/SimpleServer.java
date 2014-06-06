package com.boundary.camel.component.util;

//This example is from the book _Java in a Nutshell_ by David Flanagan.
//Written by David Flanagan.  Copyright (c) 1996 O'Reilly & Associates.
//You may study, use, modify, and distribute this example for any purpose.
//This example is provided WITHOUT WARRANTY either expressed or implied.

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleServer extends Thread {
	public final static int DEFAULT_PORT = 6789;
	protected int port;
	protected ServerSocket listen_socket;
	List<Connection> connectionList;

	// Exit with an error message, when an exception occurs.
	public static void fail(Exception e, String msg) {
		System.err.println(msg + ": " + e);
		System.exit(1);
	}

	// Create a ServerSocket to listen for connections on; start the thread.
	public SimpleServer(int port) {
		connectionList = new ArrayList<Connection>();
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

	// The body of the server thread. Loop forever, listening for and
	// accepting connections from clients. For each connection,
	// create a Connection object to handle communication through the
	// new Socket.
	public void run() {
		try {
			Socket clientSocket = listen_socket.accept();
		} catch (IOException e) {
			fail(e, "Exception while listening for connections");
		}
	}

	// Start the server up, listening on an optionally specified port
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

	public void stopServer(){
		try {
		listen_socket.close();
		} catch(Exception e) {
			System.out.println("Stopping Server");
		}
	}
}
