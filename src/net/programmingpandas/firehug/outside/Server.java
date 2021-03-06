package net.programmingpandas.firehug.outside;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import net.programmingpandas.firehug.inside.InboundTunnel;
import net.programmingpandas.firehug.inside.OutboundTunnel;

public class Server implements Runnable {

	String target = new String();
	private int uplinkPort;
	private int downlinkPort;

	public Server(String target, int downlinkPort, int uplinkPort) {
		this.target = target;
		new Thread(this).start();
		this.downlinkPort = downlinkPort;
		this.uplinkPort = uplinkPort;
	}

	@SuppressWarnings("resource")
	public void run() {
		while (true) {
			ServerSocket ss;
			Socket down = new Socket();
			Socket up = new Socket();
			try {
				ss = new ServerSocket(downlinkPort);
				down = ss.accept();
				up = new Socket(target, uplinkPort);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new OutboundTunnel(down, up);
			new InboundTunnel(down, up);
		}
	}

}
