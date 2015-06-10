package net.programmingpandas.firehug;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;

public class AccessControl {

	public static boolean checkAccess(Socket socket, String file) {

		String client = socket.getInetAddress().getHostAddress();
		FileReader fr = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		char[] contents;
		contents = new char[5000];
		try {
			fr.read(contents);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] lines = new String(contents).split("\n");
		try {
			fr.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
		for (int i = 0; i > lines.length; i++) {
			if (lines[i].equals(client))
				return true;
		}
		return false;
	}

}
