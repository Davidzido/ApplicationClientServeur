package projet;

import java.net.*;
import java.util.List;
import java.util.Scanner;

import projet.HangmanClient.BonhommePenduData;

import java.io.*;

//http://cs.lmu.edu/~ray/notes/javanetexamples/

public class Client {

	HangmanClient hangmanClient = new HangmanClient();

	private final static String ADDRESS = "127.0.0.1";
	private final static int PORT = 5000;
	private static Socket socket;
	private static Scanner input;

	private static boolean gameStarted;
	private static boolean gameOver;
	private static boolean checkGameStartedMessage;
	private static boolean isDisplayShown;
	private static boolean isPlayerTurn;

	// THE BRAIN
	public static Scanner getInput() throws IOException {
		return input;
	}

	private static boolean isGameStarted() throws IOException {
		//return getInput().hasNextBoolean() ? getInput().nextBoolean() : false;
		System.out.println("Charles : ");
		return Boolean.parseBoolean(getInput().nextLine());
		//return getInput().nextBoolean();
	}

	public static boolean isPlayerTurn() throws IOException {
		return getInput().hasNextBoolean() ? getInput().nextBoolean() : false;
	}

	
	public static String getDisplayValue() throws IOException {
		return getInput().nextLine();
	}

	public static void display() throws IOException {
		// On peut également faire en sorte que la méthode renvoie un String
		// qui sera composé du bonhomme, des valeurs essayées, la solution partielle et
		// le nb de vies restantes

		String[] displaySplit = getDisplayValue().split(":");
		int vie = Integer.parseInt(displaySplit[0]);
		String charUsed = displaySplit[1];
		String solutionPartielle = displaySplit[2];
		
		
		// UPPERBORDER
		System.out.println("*******************************************");
		// Montrer le bonhomme
		System.out.println(HangmanClient.getBonhomme(vie));
		// Montrer nbre de vie restante
		System.out.println("Vie(s): " + vie);
		// Montrer les valeurs essayées
		System.out.println("C: "+charUsed);
		// Montrer la solution partielle
		System.out.println("R: " + solutionPartielle);
		// LOWERBORDER
		System.out.println("*******************************************");
	}

	public static void reponseHandler() throws IOException {
		String reponse = getInput().nextLine();
		System.out.println("Reponse = "+reponse);
		if(reponse.equalsIgnoreCase("ATTENTION:")) {
			System.out.println(reponse);
			System.out.println(getInput().nextLine());
		}
		else if(reponse.equalsIgnoreCase("ATTENTION: La lettre a déja été essayé!") 
				|| reponse.contains("Bravo") 
				|| reponse.contains("Ouch")) {
			System.out.println(reponse);
			display();
			isPlayerTurn = false;
		}
	}

	public static void main(String args[]) {
		try {
			socket = new Socket(ADDRESS, PORT);
			// System.out.println("Enter lines of text :");
			Scanner sc = new Scanner(System.in);
			input = new Scanner(socket.getInputStream());
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

			// WELCOME MESSAGE

			while (socket.isConnected()) {

				// En attende du début de la partie.
				if (!gameStarted) {
					if (!checkGameStartedMessage) {
						System.out.println("En attente de début de partie.");
						checkGameStartedMessage = true;
					}
					gameStarted = isGameStarted();
					//System.out.println("gameStarted = "+gameStarted);
					continue;
				}

				// Game Loop
				while (gameStarted || !gameOver) {
					// Player turn loop
					while (isPlayerTurn) {
						if (!isDisplayShown) {
							// Montrer le UI
							getInput().nextLine();
							display();
							isDisplayShown = true;
						}
						// Demander une lettre
						System.out.println("Veuillez choisir un caractère : ");
						char userInput = sc.nextLine().charAt(0);
						if (HangmanClient.isInputAcceptable(userInput)) {
							output.println(userInput);
						} else {
							System.out.println("U dumb, plz tri agane");
							continue;
						}

						reponseHandler();
					}
					//System.out.println("Player turn " + isPlayerTurn);
					
					isPlayerTurn = isPlayerTurn();
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
