package projet;

import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

public class Game implements Runnable {

	// Fields
	private Socket clientSocket;
	
	
	private boolean isGameStarted;
	private boolean isDisplaySent;
	private boolean hasReceivePlayerInput;

	DataOutputStream dataOutputStream;
	DataInputStream dataInputStream;
	InputStream inputStream;
	PrintWriter output;
	private static Scanner input;

	public Game(Socket clientSocket) throws IOException {
		// Declaring fields
		this.clientSocket = clientSocket;
		
		inputStream = clientSocket.getInputStream();
		output = new PrintWriter(clientSocket.getOutputStream(), true);
		input = new Scanner(clientSocket.getInputStream());
	}

	public void run() {
		// 2 joueurs doivent se connecter pour commencer une partie
		while (Main.getVie() > 0) {
			try {
				

				//Start the game
				if(HangmanServer.arePlayersReady() && !isGameStarted) {
					//Préparer la partie
					isGameStarted = true;
					setup();
				}
				
				// Receive and handle packet
				if(HangmanServer.arePlayersReady() && isGameStarted) {
				handleGameState();
				}
				

			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}

		}

	}
	
	private void setup() {
		//Envoie un message pour dire que la partie est commencer
		System.out.println("isGameStarted = "+ isGameStarted);
		//output.println(isGameStarted);
		output.print(true);
		output.print(true);
		output.print(true);
		output.print(true);
		output.print(true);
		//Crée la solution partielle a partir du mot.
		HangmanServer.setSolutionPartielleInitiale();
		
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
	
	public static Scanner getInput() throws IOException {
		return input;
	}

	/**
	 * Permet d'envoyer un message msg à un client
	 * 
	 * @param msg Message à envoyer
	 * @throws IOException
	 */
	public void sendMsgToClient(String msg) throws IOException, InterruptedException {
		output.println(msg);
		clientSocket.getOutputStream().flush();
	}

	/**
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void sendPlayerStatus() throws IOException, InterruptedException {
		//output.println(isPlayerTurn());
		output.println(isPlayerTurn());
		clientSocket.getOutputStream().flush();
	}
	
	public void sendPacket(int vie, char[] charUsed, char[] solutionPartiel) throws IOException {
		output.println(String.valueOf(vie)+":"+String.copyValueOf(charUsed)+":"+String.copyValueOf(solutionPartiel));
		clientSocket.getOutputStream().flush();
	}

	public void handleGameState() throws IOException, InterruptedException {
		//Send the client wether or not its his turn to player that ufcking dwdqe qweqweqw
		sendPlayerStatus();
		//System.out.println("isPlayerTurn() = "+isPlayerTurn());
		
		if(isPlayerTurn()) {
			
			if(!isDisplaySent) {
				
				sendPacket(Main.getVie(), HangmanServer.getCharUsed(), HangmanServer.getSolutionPartielle());
				isDisplaySent = false;
			}
			
			char playerInput = getInput().nextLine().charAt(0);

			//-Request player input()[]
			if(playerInput != ' ') {
			//CHECK PLAYER INPUT
			checkEssaiInput(playerInput);
			//ENDTURN
			Main.setPlayerRotation(Main.getPlayerRotation() + 1);
			//Send the display one last time
			sendPacket(Main.getVie(), HangmanServer.getCharUsed(), HangmanServer.getSolutionPartielle());
			}
			else {
			//SEND FEEDBACK
			sendMsgToClient("ATTENTION:");
			sendMsgToClient("Veuillez entrer une lettre!");
			}
			
		}
	}
	
	public void checkEssaiInput(char essai) throws IOException, InterruptedException {
		// mettre les inputs en minuscule
		essai = Character.toLowerCase(essai);
		
		if(HangmanServer.isInputDuplicate(essai)) {
			sendMsgToClient("ATTENTION: La lettre a déja été essayé!");
			//Enleve une vie
			Main.setVie(Main.getVie() - 1);
		}
		else if(!HangmanServer.isInputDuplicate(essai)) {
			//Compare avec mot
			if (Main.getMot().contains(String.valueOf(essai))) {
				sendMsgToClient("Bravo la lettre "+essai+" se trouve dans le mot caché.");
				HangmanServer.updateSolutionPartielle(essai);
			}
			else if (!Main.getMot().contains(String.valueOf(essai))) {
				sendMsgToClient("Ouch la lettre "+essai+" ne se trouve pas dans le mot caché.");
				//Enleve une vie
				Main.setVie(Main.getVie() - 1);
			}
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean isPlayerTurn() {
		return (this.hashCode() == Main.getClientList().get(Main.getPlayerRotation()).hashCode());
	}

}
