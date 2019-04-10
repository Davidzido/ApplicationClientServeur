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

	private static ServerSocket serverSocket;
	private static ArrayList<Game> clientList = new ArrayList<Game>();
	
	//Fields
	private static int vie;
	private static String mot;
	private static char[] charUsed;
	private static int playerRotation;

	//Getters
	public static int getPlayerRotation() {
		return playerRotation;
	}
	
	public static void setPlayerRotation(int playerRotation) {
		if(playerRotation > (clientList.size() - 1)) {
			playerRotation = 0;
		}
		Main.playerRotation = playerRotation;
	}

	public static ArrayList<Game> getClientList(){
		return clientList;
	}
	
	public static int getPlayerCount() {
		return clientList.size();
	}

	public static int getVie() {
		return vie;
	}

	public static void setVie(int vie) {
		Main.vie = vie;
	}

	public static String getMot() {
		return mot;
	}
	
	public static void setMot(String mot) {
		Main.mot = mot;
	}

	public static void main(String[] args) {
		int port = 5000;
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			ExecutorService pool = Executors.newFixedThreadPool(10);
			
			setMot("tortue");
			setVie(2);
			setPlayerRotation(0);
			//Crée la solution partielle a partir du mot
			HangmanServer.setSolutionPartielleInitiale();
			while (true) {
				System.out.println("About to accept client connection...");

				Socket clientSocket = serverSocket.accept();

				System.out.println("Accepted the connection from " + clientSocket.toString());

				// Game gère la communication avec le clientSocket
				Game game = new Game(clientSocket);
				clientList.add(game);
				pool.execute(game);
				System.out.println("PlayerCount = "+getPlayerCount());
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
