package com.boundary.camel.component.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class TestSocketServer implements Runnable {
	
	private int port;
	private String host;
	
	public TestSocketServer(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void start() {
		
	}
	
	public void stop() {
		
	}

	public static void main(String[] args) {
		try {
			ServerSocket listener = new ServerSocket(1234);
			Boolean finished = false;
			
			while(!finished) {
				Thread.sleep(10000);
				Socket client = listener.accept(); // wait for connection
				
				InputStream in = client.getInputStream();
				OutputStream out = client.getOutputStream();
				// read a byte
				byte someByte = (byte) in.read();
				// read a newline or carriage-return-delimited string
				BufferedReader bin = new BufferedReader( new InputStreamReader( in ) );
				String someString = bin.readLine();
				// write a byte
				out.write( 43);
				// say goodbye
				PrintWriter pout = new PrintWriter(out,true);
				pout.println("Goodbye!");
				// read a serialized Java object
				ObjectInputStream oin = new ObjectInputStream( in );
				Date date = (Date) oin.readObject();
				client.close();
			}
		} catch (Exception e) {

		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}

