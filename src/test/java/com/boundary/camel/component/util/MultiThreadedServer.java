package com.boundary.camel.component.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedServer implements Runnable {

	private int port;
	private int maxConnections;
	private ThreadGroup threadGroup;
	private Thread thread;
	ServerSocket listener;

	public MultiThreadedServer(int port,int maxConnections) {
		this.port = port;
		this.maxConnections = maxConnections;
		this.threadGroup = new ThreadGroup(this.getClass().getName());
		this.thread = new Thread(threadGroup, this, "listening");
	}
	
	// Listen for incoming connections and handle them
	public void start() throws InterruptedException {
		thread.start();
	}
	
	public void stop() throws IOException {
		
		listener.close();
		
//	    int nAlloc = threadGroup.activeCount();
//	    System.out.println("Attemping to stop " + nAlloc + " threads");
//	    int n = 0;
//
//	    Thread[] threads;
//	    do {
//	        nAlloc *= 2;
//	        threads = new Thread[ nAlloc ];
//	        n = threadGroup.enumerate( threads );
//	    } while ( n == nAlloc );
//	    
//	    for (Thread t : threads) {
//	    	System.out.println("interuppting thread: " + t.getName());
//	    	t.interrupt();
//	    }
	}
	

	@Override
	public void run() {
		int i = 0;

		try {
			listener = new ServerSocket(port);
			Socket server;

			while ((i++ < maxConnections) || (maxConnections == 0)) {
				server = listener.accept();
				Connection connection = new Connection(server);
				Thread t = new Thread(threadGroup,connection,"connection #" + i);
				t.start();
			}
		} catch (Exception ioe) {
			System.out.println("IOException on socket listen: " + ioe);
			ioe.printStackTrace();
		}
	}
	
	public static void main(String [] args) throws InterruptedException {
		MultiThreadedServer server = new MultiThreadedServer(1234,5);
		server.start();
		Thread.currentThread().join();
	}
}

class Connection implements Runnable {
	
	private Socket server;
	private String line;
	private String input;

	Connection(Socket server) {
		this.server = server;
	}

	public void run() {

		input = "";

		try {
			// Get input from the client
			DataInputStream ip = new DataInputStream(server.getInputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(ip));
			PrintStream out = new PrintStream(server.getOutputStream());

			while ((line = in.readLine()) != null && !line.equals(".")) {
				input = input + line;
			}

			// Write to the client if we have data
			if (input.length() > 0) {
				out.println("Overall message is:" + input);
			}

			server.close();
		} catch(InterruptedIOException i) {
			System.err.println("Interrupting Thread");
		}
		catch (IOException ioe) {
			System.err.println("IOException on socket listen: " + ioe);
			ioe.printStackTrace();
		}
		finally {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
