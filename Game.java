package projet;

import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

public class Game implements Runnable {

	// Fields
	private Socket clientSocket;
	private HangmanClient bonhommePendu = new HangmanClient();
	private boolean isPlayerTurn;
	private boolean isGameStarted;

	DataOutputStream dataOutputStream;
	DataInputStream dataInputStream;
	InputStream inputStream;
	PrintWriter printWriter;

	public Game(Socket clientSocket) throws IOException {
		// Declaring fields
		this.clientSocket = clientSocket;
		//
		dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
		dataInputStream = new DataInputStream(clientSocket.getInputStream());
		//
		inputStream = clientSocket.getInputStream();
		printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
	}

	public void run() {
		// 2 joueurs doivent se connecter pour commencer une partie
		while (clientSocket.isConnected()) {
			try {
				
				// Check if the players are connected and ready
				if (!arePlayersReady()) {
					sendMsgToClient("En attente de joueurs");
					continue;
				}
				
				//Start the game
				if(arePlayersReady() && !isGameStarted) {
					//Préparer la partie
					setup();
					isGameStarted = true;
					continue;
				}
				
				// Receive and handle packet
				if(arePlayersReady() && isGameStarted) {
				handleGameState();
				}
				

			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}

		}

	}
	
	private void setup() {
		//Envoie un message pour dire que la partie est commencer
		printWriter.println(true);
		//Ask the main to generate a random word
		
	}

	public void handleClientSocket() throws IOException, InterruptedException {
		InputStream inputStream = clientSocket.getInputStream();
		PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line;
		while ((line = reader.readLine()) != null) {
			if ("quit".equalsIgnoreCase(line)) {
				break;
			}
			String msg = "You typed: " + line;
			output.println(msg);
		}

		inputStream.close();
		output.close();
		clientSocket.close();
	}

	/**
	 * 
	 * @return true si les joueurs sont prêts à jouer
	 */
	public boolean arePlayersReady() {
		return (Main.getPlayerCount() > 0) ? true : false;
	}

	/**
	 * Permet d'envoyer un message msg à un client
	 * 
	 * @param msg Message à envoyer
	 * @throws IOException
	 */
	public void sendMsgToClient(String msg) throws IOException, InterruptedException {
		printWriter.println(msg);
		clientSocket.getOutputStream().flush();
	}

	/**
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void sendPlayerStatus() throws IOException, InterruptedException {
		printWriter.println(isPlayerTurn());
		clientSocket.getOutputStream().flush();
	}
	
	/**
	 * 
	 * @param vie
	 * @throws IOException
	 */
	public void sendPacket(int vie) throws IOException {
		printWriter.print(vie);
		clientSocket.getOutputStream().flush();
	}
	
	/**
	 * 
	 * @param list
	 * @throws IOException
	 */
	public void sendPacket(char[] list) throws IOException {
		printWriter.print(list);
		clientSocket.getOutputStream().flush();
	}
	/**
	 * 
	 * @param solutionPartiel
	 * @throws IOException
	 */
	public void sendPacket(String solutionPartiel) throws IOException {
		printWriter.print(solutionPartiel);
		clientSocket.getOutputStream().flush();
	}

	public void handleGameState() throws IOException, InterruptedException {
		//Send the client wether or not its his turn to player that ufcking dwdqe qweqweqw
		sendPlayerStatus();
		//
	}
	/**
	 * 
	 * @return
	 */
	private boolean isPlayerTurn() {
		return (this.hashCode() == Main.getClientList().get(Main.getPlayerRotation()).hashCode());
	}

}
