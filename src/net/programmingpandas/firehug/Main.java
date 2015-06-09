package net.programmingpandas.firehug;

import java.io.*;

public class Main {

	public static String uplinkPrefix;
	public static String uplinkSuffix;
	public static String downlinkPrefix;
	public static String downlinkSuffix;
	public static int lowerPort;
	public static int middlePort;
	public static int upperPort;
	public static boolean running = true;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length >= 4 && args[0].equals("inside"))
			new net.programmingpandas.firehug.inside.Server(args[1],
					Integer.parseInt(args[2]), Integer.parseInt(args[3]));
		else if (args.length >= 4 && args[0].equals("outside"))
			new net.programmingpandas.firehug.outside.Server(args[1],
					Integer.parseInt(args[2]), Integer.parseInt(args[3]));
		else if(args.length == 3)
			readConfig(args[2], args[0], args[1]);
		else
			System.out.println("usage:\n"
					+ "java -jar firehug.jar inside|outside <target> <config>\n"
					+ "OR\n"
					+ "java -jar firehug.jar inside|outside <target> <downlink port> <uplink port>");
	}

	public static void readConfig(String config, String mode, String target) {
		FileReader fr;
		try {
			fr = new FileReader(config);
			char[] contents;
			contents = new char[5000];
			fr.read(contents);
			String[] lines = new String(contents).split("\n");
			for(int i = 0; i < lines.length; i++){
			lines[i].replaceAll("\\n", "\n");
			}
			uplinkPrefix = lines[0];
			uplinkSuffix = lines[1];
			downlinkPrefix = lines[2];
			downlinkSuffix = lines[3];
			lowerPort = Integer.parseInt(lines[4]);
			middlePort = Integer.parseInt(lines[5]);
			upperPort = Integer.parseInt(lines[6]);
			
			if (mode.equals("inside"))
				new net.programmingpandas.firehug.inside.Server(target, lowerPort, middlePort);
			else if (mode.equals("outside"))
				new net.programmingpandas.firehug.outside.Server(target, middlePort, upperPort);
			else
				System.out.println("usage:\n"
						+ "java -jar firehug.jar inside|outside <target> <config>\n"
						+ "OR\n"
						+ "java -jar firehug.jar inside|outside <target> <downlink port> <uplink port>");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
