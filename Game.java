package projet;

import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

public class Game implements Runnable {

	private Socket clientSocket;
	private int playerId;

	static Scanner sc = new Scanner(System.in);
	static int vie;
	static String mot;
	static int nbrdessaies;
	static String hangman;
	static String rejouer;
	static boolean erreur;
	static char essaie;
	static char temp;

	public Game(Socket clientSocket, int playerId) {
		this.clientSocket = clientSocket;
		this.playerId = playerId;
	}

	public void run() {
		System.out.println("Connected " + clientSocket);
		try {
			handleClientSocket();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
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

	public void demarrage() {
		do {
			System.out.println("Veuillez entrer un mot :");
			mot = sc.nextLine(); // Choisir le mot � deviner
			mot = mot.toLowerCase();
			System.out.println("Le mot choisi est: " + mot);
			do {

				try {
					System.out.println("Veuillez choisir un nombre de vies entre 1 et 8:");
					vie = sc.nextInt(); // Choisir un nombre de vie entre 1 et 8
					erreur = false;
				} catch (InputMismatchException e) { // intercepter les erreurs caus�es par un input diff�rent d'un
														// integer
					System.out.println("Erreur");
					sc.nextLine();
					erreur = true;
				}
			} while (erreur == true || vie < 1 || vie > 8); // S'assurer que le client entre un nombre de vie entre 1
															// et 8
			System.out.println("Le nombre de vies choisi est :" + vie);
			jeu(vie, mot);// faire les commandes de la m�thode jeu
			do {
				System.out.println("Voulez-vous rejouez ? Y/N");
				rejouer = sc.next(); // input la commande pour d�cider de rejouer ou pas
				sc.nextLine();

				// Informer le client que l'input est invalide
				if (!rejouer.equalsIgnoreCase("n") && !rejouer.equalsIgnoreCase("no") && !rejouer.equalsIgnoreCase("y")
						&& !rejouer.equalsIgnoreCase("yes")) {

					System.out.println("Erreur");
				}
				// S'assuer que le client entre un input valide
			} while (!rejouer.equalsIgnoreCase("n") && !rejouer.equalsIgnoreCase("no") && !rejouer.equalsIgnoreCase("y")
					&& !rejouer.equalsIgnoreCase("yes"));

		} while (rejouer.equalsIgnoreCase("y") || rejouer.equalsIgnoreCase("yes")); // Loop do/while pour jouer une
																					// autre game

		System.out.println("Thanks for playing");
	}

	public void jeu(int vie, String mot) {
		char[] inconnu = new char[mot.length()]; // tableau qui va servir � l'affichage de la solution masqu�e
		char[] list = new char[mot.length() + vie]; // tableau qui va servir � conserver les valeurs essay�es
		int y = 0;

		for (int i = 0; i < mot.length(); i++) { // Boucle for qui va permettre l'affichage de la solution masqu�e par
													// des tirets
			inconnu[i] = '-';
			if (mot.charAt(i) == ' ') {
				inconnu[i] = ' ';
			}
		}
		System.out.print("Solution: ");
		System.out.println(inconnu); // afficher la solution masqu�

		// D�clarations while et switch qui va permettre d'achiffer le dessin du
		// bonhomme pendu d�pendamment du nombre de vie restant
		while (vie > 0) {

			switch (vie) {
			case 1:
				hangman = "\n  _________" + "\n  |       |" + "\n  |       O" + "\n  |     --|--" + "\n  |      /"
						+ "\n__|__\n";
				break;
			case 2:
				hangman = "\n  _________" + "\n  |       |" + "\n  |       O" + "\n  |     --|--" + "\n  |" + "\n  |"
						+ "\n__|__\n";
				break;
			case 3:
				hangman = "\n  _________" + "\n  |       |" + "\n  |       O" + "\n  |     --|" + "\n  |" + "\n  |"
						+ "\n__|__\n";
				break;
			case 4:
				hangman = "\n  _________" + "\n  |       |" + "\n  |       O" + "\n  |       |" + "\n  |" + "\n  |"
						+ "\n__|__\n";
				break;
			case 5:
				hangman = "\n  _________" + "\n  |       |" + "\n  |       O" + "\n  |" + "\n  |" + "\n  |"
						+ "\n__|__\n";
				break;
			case 6:
				hangman = "\n  _________" + "\n  |       |" + "\n  |" + "\n  |" + "\n  |" + "\n  |" + "\n__|__\n";
				break;
			case 7:
				hangman = "\n  _________" + "\n  |" + "\n  |" + "\n  |" + "\n  |" + "\n  |" + "\n__|__\n";
				break;
			case 8:
				hangman = "\n" + "\n  |" + "\n  |" + "\n  |" + "\n  |" + "\n  |" + "\n__|__\n";
				break;
			}
			System.out.println(hangman); // Affichage du bonhomme pendu

			do {
				erreur = false;
				System.out.println("Veuillez choisir un caract�re");
				essaie = sc.next().charAt(0); // input les valeurs essayer
				if (essaie >= 'A' && essaie <= 'Z' || essaie >= 'a' && essaie <= 'z') {
					essaie = Character.toLowerCase(essaie); // mettre les inputs en minuscule
				}

				for (int i = 0; i < list.length; i++) { // D�claration permettant de s'assuer que le client ne rentre
														// pas la m�me valeur
					temp = list[i];
					if (essaie == temp) {
						System.out.println("La valeur a d�ja �t� essay�");
						erreur = true;
					}
				}
			} while (erreur == true || (essaie < '0' || (essaie > '9' && essaie < 'A') || (essaie > 'Z' && essaie < 'a')
					|| essaie > 'z')); // conditions
			// permettant
			// de
			// s'assurer
			// que
			list[y] = essaie; // placer les valeurs essayer dans le tableau cr�� pr�c�dement
			System.out.print("Valeurs essayer: ");
			for (int i = 0; i < y; i++) { // D�claration permettant de trier les valeurs essay�es en ordre croissant
				for (int j = i; j < y; j++) {
					if ((list[i] > list[j + 1])) {
						char temp = list[j + 1];
						list[j + 1] = list[i];
						list[i] = temp;

					}

				}
			}

			String str = String.copyValueOf(list); // mettre les valeurs du tableau sous forme d'un String
			System.out.println(str); // Imprimer les valeurs essayer en ordre croissant

			if (mot.contains(essaie + "")) { // D�claration qui permet de remplacer un tiret par le caract�re qui lui ai
												// associ� quand un essaie est bon
				for (int i = 0; i < mot.length(); i++) {
					if (mot.charAt(i) == essaie) {
						inconnu[i] = essaie;
						System.out.println("Vie : " + vie);
					}

				}
				y++; // passer au prochain enplacement dans le tableau d'essaie pour ne pas �craser
						// les valeurs pr�c�dentes
				System.out.print("Solution: ");
				System.out.println(inconnu); // Imprimer � nouveau le mot masqu� avec les valeurs trouv�es
				if (mot.equals(String.valueOf(inconnu))) { // D�claration permettant de d�terminer si on le client a
															// gagn�
					System.out.println("Victoire");
					break;
				}
			} else { // Sinon, on enl�ve une vie et on continue

				vie--;
				y++;
				System.out.println("vie :" + vie);
				System.out.print("Solution: ");
				System.out.println(inconnu);
			}

			if (vie == 0) { // D�claration permettant de d�terminer si on a perdu
				System.out.println("\n  _________" + "\n  |       |" + "\n  |       O" + "\n  |     --|--"
						+ "\n  |      / \\" + "\n__|__" + "\n Vous avez perdu");
			}

		}

	}

}
