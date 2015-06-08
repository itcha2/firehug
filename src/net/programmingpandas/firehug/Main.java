package net.programmingpandas.firehug;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length >= 4 && args[0].equals("inside"))
			new net.programmingpandas.firehug.inside.Server(args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]));
		else if(args.length >= 4 && args[0].equals("outside"))
			new net.programmingpandas.firehug.outside.Server(args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]));
		else
			System.out.println("usage: java -jar firehug.jar inside|outside <target> <downlink port> <uplink port>");
	}

}
