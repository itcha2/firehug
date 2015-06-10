package net.programmingpandas.firehug.inside;

import static net.programmingpandas.firehug.Main.*;
import static net.programmingpandas.firehug.AccessControl.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

	String target = new String();
	int downlinkPort;
	int uplinkPort;

	public Server(String target, int downlinkPort, int uplinkPort) {
		this.target = target;
		new Thread(this).start();
		this.downlinkPort = downlinkPort;
		this.uplinkPort = uplinkPort;
	}

	public void run() {
		Socket down = null;
		Socket up = null;
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(downlinkPort);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (running) {
			try {
				down = ss.accept();
				if (!checkAccess(down, "ACCESS")){
					down.close();
					continue;
				}
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
