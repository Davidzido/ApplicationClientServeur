package projet;

import java.net.*;
import java.util.Scanner;
import java.io.*;

//http://cs.lmu.edu/~ray/notes/javanetexamples/

public class Client {

	private final static String ADDRESS = "127.0.0.1";
	private final static int PORT = 5000;
	private static Socket socket;

	public static void main(String args[]) {
		try {
			socket = new Socket(ADDRESS, PORT);
			System.out.println("Enter lines of text :");
			Scanner sc = new Scanner(System.in);
			Scanner input = new Scanner(socket.getInputStream());
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
			while (true) {
				output.println(sc.nextLine());
				if (input.hasNextLine()) {
					System.out.println(input.nextLine());
				} else {
					break;
				}
			}
			sc.close();
			input.close();
			output.close();
			socket.close();
			System.exit(-1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
