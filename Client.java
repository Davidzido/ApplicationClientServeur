package projet;

import java.net.*;
import java.util.List;
import java.util.Scanner;

import projet.HangmanClient.BonhommePenduData;

import java.io.*;

//http://cs.lmu.edu/~ray/notes/javanetexamples/

public class Client {

	private final static String ADDRESS = "127.0.0.1";
	private final static int PORT = 5000;
	private static Socket socket;

	private static boolean gameStarted;
	private static boolean gameOver;

	private static boolean isGameStarted() throws IOException {
		Scanner input = new Scanner(socket.getInputStream());
		String isGameStarted = input.nextLine();
		return (isGameStarted.equalsIgnoreCase("true")) ? true : false;
	}

	public static boolean isPlayerTurn() throws IOException {
		Scanner input = new Scanner(socket.getInputStream());
		String playerTurn = input.nextLine();
		System.out.println("IsPlayerTurn = " + playerTurn);
		return (playerTurn.equalsIgnoreCase("true")) ? true : false;
	}

	// Retourne le nombre de vies restantes
	public static int nbVie() throws IOException {
		Scanner input = new Scanner(socket.getInputStream());
		int vie = input.nextInt();
		return vie;
	}

	// Retourne les caractères déjà essayés
	public static String valeursEssayer() throws IOException {
		Scanner input = new Scanner(socket.getInputStream());
		String charUsed = input.nextLine();
		return charUsed;
	}

	public static void display() throws IOException {
		// On peut également faire en sorte que la méthode renvoie un String
		// qui sera composé du bonhomme, des valeurs essayées, la solution partielle et
		// le nb de vies restantes

		// Afficher le nombre de vies restantes
		System.out.println("Vie(s): " + nbVie());

		// input les valeurs essayer
		System.out.println(valeursEssayer());

		// Affichage de la solution partielle
		// System.out.println(getSolutionPartielle());

		// Montrer le bonhomme
		//System.out.println(getBonhomme(nbVie()));

	}

	public static void main(String args[]) {
		int c = 0;
		try {
			socket = new Socket(ADDRESS, PORT);
			// System.out.println("Enter lines of text :");
			Scanner sc = new Scanner(System.in);
			Scanner input = new Scanner(socket.getInputStream());
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
			while (c < 10) {

				// En attende du début de la partie.
				if (!gameStarted) {
					System.out.println("En attente de début de partie.");
					gameStarted = isGameStarted();
					continue;
				}

				// Game Loop
				while (gameStarted || !gameOver) {

					// Player turn loop
					while (isPlayerTurn()) {
						// Montrer le GUI
						display();

						// Demander une lettre
						System.out.println("Veuillez choisir un caractère : ");
						output.println(sc.nextLine().charAt(0));

						// Montrer le GUI
						display();
					}
				}

				System.out.println("Partie commencé");

				// output.println(sc.nextLine());
				// if (input.hasNextLine()) {
				// System.out.println(input.nextLine());
				// } else {
				// break;
				// }
				c++;
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
