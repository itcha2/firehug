package net.programmingpandas.firehug.outside;

import static net.programmingpandas.firehug.Main.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

import net.programmingpandas.firehug.ByteSearch;

public class InboundTunnel implements Runnable {

	InputStream in;
	OutputStream out;
	Thread thread;

	public InboundTunnel(Socket back, Socket forward) {
		try {
			this.out = forward.getOutputStream();
			this.in = back.getInputStream();
			thread = new Thread(this);
			thread.start();
		} catch (SocketException e) {

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public InboundTunnel(InputStream in, OutputStream out) {
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
		byte[] buffer = new byte[4096]; // buffer of bytes from client
		ByteSearch output = new ByteSearch(); // string of bytes that will be sent out
		int len = 0;
		while (running) {
			try {
				len = in.read(buffer);
				output.set(downlinkPrefix.getBytes());
				output.appendPattern(buffer);
				output.appendPattern(downlinkSuffix.getBytes());
				out.write(output.get());
				buffer = new byte[4096];
			} catch (SocketException e) {
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
