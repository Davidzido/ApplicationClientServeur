package projet;

public class HangmanServer {

	// Check le caract�re input par le client (Dans le Game.java)
	public boolean checkEssaieInput(char essaie, Game game) {
		boolean isInputCorrect = true;
		if (essaie >= 'A' && essaie <= 'Z' || essaie >= 'a' && essaie <= 'z') {
			// mettre les inputs en minuscule
			essaie = Character.toLowerCase(essaie);

			// D�claration permettant de s'assuer que le client ne rentre pas la m�me valeur
			for (int i = 0; i < Main.getCharUsed().length; i++) {

				if (essaie == Main.getCharUsed()[i]) {
					System.out.println("La valeur a d�ja �t� essay�");
					isInputCorrect = false;
				}
			}
		}
		return isInputCorrect;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
