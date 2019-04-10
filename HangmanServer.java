package projet;

public class HangmanServer {

	private static char[] solutionPartielle = new char[Main.getMot().length()]; // tableau qui va servir à l'affichage
															// de la solution
	// masqué
	private static char[] charUsed = new char[Main.getMot().length() + Main.getVie()]; // tableau qui va servir à
																						// conserver les valeurs
	// essayées
	private static int pos = 0;
	
	/**
	 * 
	 * @return true si les joueurs sont prêts à jouer
	 */
	public static boolean arePlayersReady() {
		return (Main.getPlayerCount() > 1) ? true : false;
	}

	public static char[] getSolutionPartielle() {
		return solutionPartielle;
	}

	public static char[] getCharUsed() {
		return charUsed;
	}
	
	public static boolean isInputDuplicate(char essai) {
		boolean isInputDuplicate = false;

		// Déclaration permettant de s'assuer que le client ne rentre pas la même valeur
		for (int i = 0; i < getCharUsed().length; i++) {
			if (essai == getCharUsed()[i]) {
				isInputDuplicate = true;
			}
		}
		return isInputDuplicate;
	}

	/**
	 * 
	 * @param essai
	 * @return
	 */
	public static String getValeursEssayer(char essai) {
		charUsed[pos] = essai; // placer la valeur essayer dans le tableau créé précédement
		for (int i = 0; i < pos; i++) { // Déclaration permettant de trier les valeurs essayées en ordre croissant
			for (int j = i; j < pos; j++) {
				if ((charUsed[i] > charUsed[j + 1])) {
					char temp = charUsed[j + 1];
					charUsed[j + 1] = charUsed[i];
					charUsed[i] = temp;
				}
			}
		}
		pos++;
		return String.copyValueOf(charUsed);
	}

	public static void setSolutionPartielleInitiale() {
		String mot = Main.getMot();
		// Boucle for qui va permettre l'affichage de la solution masqué par // des
		// tirets
		for (int i = 0; i < mot.length(); i++) {
			solutionPartielle[i] = '-';
		}
	} 

	/**
	 * Déclaration qui permet de remplacer un tiret par le caractère qui lui ai
	 * associé quand un essai est bon
	 * 
	 * @param essai
	 * @return
	 */
	public static String updateSolutionPartielle(char essai) {
			for (int i = 0; i < Main.getMot().length(); i++) {
				if (Main.getMot().charAt(i) == essai) {
					solutionPartielle[i] = essai;
				}
			}
		return String.copyValueOf(solutionPartielle);
	}

	/**
	 * 
	 * @return
	 */
	public static boolean hasWon() {
		return Main.getMot().equals(String.valueOf(solutionPartielle));
	}
}
