package net.programmingpandas.firehug.inside;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

import net.programmingpandas.firehug.ByteSearch;

import static net.programmingpandas.firehug.Main.*;

public class InboundTunnel implements Runnable {

	InputStream in;
	OutputStream out;
	Thread thread;

	public InboundTunnel(Socket back, Socket forward) {
		try {
			this.out = back.getOutputStream();
			this.in = forward.getInputStream();
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
		byte[] buffer = new byte[4096]; // buffer of bytes from server
		byte[] output = new byte[4096]; // string of bytes that will be sent to
										// the client
		@SuppressWarnings("unused")
		int len = 0;
		while (running) {
			try {
				do {
					buffer[buffer.length] = (byte) in.read();
				} while (!(new ByteSearch(buffer).contains(downlinkPrefix) && new ByteSearch(
						buffer).contains(downlinkSuffix)));
				ByteSearch fr = new ByteSearch(buffer);
				fr.removePattern(downlinkPrefix);
				fr.removePattern(downlinkSuffix, "");
				output = fr.get();
				out.write(output);
				buffer = new byte[4096];
			} catch (SocketException e) {
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
