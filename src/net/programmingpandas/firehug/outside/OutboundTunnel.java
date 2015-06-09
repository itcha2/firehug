package net.programmingpandas.firehug.outside;

import static net.programmingpandas.firehug.Main.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class OutboundTunnel implements Runnable{
	
	InputStream in;
	OutputStream out;
	Thread thread;

	public OutboundTunnel(Socket back, Socket forward) {
		try {
			this.out = back.getOutputStream();
			this.in = forward.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		thread = new Thread(this);
		thread.start();
	}

	public OutboundTunnel(InputStream in, OutputStream out) {
		this.in = in;
		this.out = out;
		thread = new Thread(this);
		thread.start();
	}

	// "GET "+buffer+" HTTP/1.1\n\n"
	/**
 * 
 */
	public void run() {
		byte[] buffer = new byte[4096]; // buffer of bytes from server
		byte[] output = new byte[4096]; // string of bytes that will be sent to the client
		int len = 0;
		while (running) {
			try {
				do{
					buffer[buffer.length] = (byte)in.read();
				}
				while(!(new String(buffer).contains(uplinkPrefix) && new String(buffer).contains(uplinkSuffix)));
				String fr = new String(buffer);
				fr.replaceAll(uplinkPrefix, "");
				fr.replaceAll(uplinkSuffix, "");
				output = new String("GET " + buffer + " HTTP/1.1\n\n").getBytes();
				out.write(output);
				buffer = new byte[4096];
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}