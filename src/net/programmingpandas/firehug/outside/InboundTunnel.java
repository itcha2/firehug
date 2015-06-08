package net.programmingpandas.firehug.outside;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class InboundTunnel implements Runnable {

	InputStream in;
	OutputStream out;
	Thread thread;
	
	public InboundTunnel(Socket back, Socket forward){
		try {
			this.out = forward.getOutputStream();
			this.in = back.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		thread = new Thread(this);
		thread.start();
	}
	
	public InboundTunnel(InputStream in, OutputStream out){
		this.in = in;
		this.out = out;
		thread = new Thread(this);
		thread.start();
	}
	
	
//"GET "+buffer+" HTTP/1.1\n\n"
/**
 * 
 */
	public void run(){
		byte[] buffer = new byte[4096]; //buffer of bytes from client
		byte[] output = new byte[4096]; //string of bytes that will be sent out
		int len = 0;
		while(true){
			try{
			len = in.read(buffer);
			output = new String("HTTP/1.1 200 OK\n" + buffer).getBytes();
			out.write(output);
			buffer = new byte[4096];
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}
