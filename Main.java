package projet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//http://cs.lmu.edu/~ray/notes/javanetexamples/

public class Main {

	static ServerSocket serverSocket;
	private static int playerId = 1;
	static ArrayList<Game> clientList = new ArrayList<Game>();

	public static void main(String[] args) {
		int port = 5000;
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			ExecutorService pool = Executors.newFixedThreadPool(10);
			while (true) {
				System.out.println("About to accept client connection...");

				Socket clientSocket = serverSocket.accept();

				System.out.println("Accepted the connection from " + clientSocket);

				// Game gère la communication avec le clientSocket
				Game game = new Game(clientSocket, playerId);
				clientList.add(game);
				System.out.println(game);
				pool.execute(game);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
