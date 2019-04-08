package projet;

public class HangmanClient {

	public enum BonhommePenduData {
		ZERO("\n  _________" + "\n  |       |" + "\n  |       O" + "\n  |     --|--" + "\n  |      / \\" + "\n__|__"
				+ "\n Vous avez perdu"),
		UNE("\n  _________" + "\n  |       |" + "\n  |       O" + "\n  |     --|--" + "\n  |      /" + "\n__|__\n"),
		DEUX("\n  _________" + "\n  |       |" + "\n  |       O" + "\n  |     --|--" + "\n  |" + "\n  |" + "\n__|__\n"),
		TROIS("\n  _________" + "\n  |       |" + "\n  |       O" + "\n  |     --|" + "\n  |" + "\n  |" + "\n__|__\n"),
		QUATRE("\n  _________" + "\n  |       |" + "\n  |       O" + "\n  |       |" + "\n  |" + "\n  |" + "\n__|__\n"),
		CINQ("\n  _________" + "\n  |       |" + "\n  |       O" + "\n  |" + "\n  |" + "\n  |" + "\n__|__\n"),
		SIX("\n  _________" + "\n  |       |" + "\n  |" + "\n  |" + "\n  |" + "\n  |" + "\n__|__\n"),
		SEPT("\n  _________" + "\n  |" + "\n  |" + "\n  |" + "\n  |" + "\n  |" + "\n__|__\n"),
		HUIT("\n" + "\n  |" + "\n  |" + "\n  |" + "\n  |" + "\n  |" + "\n__|__\n");

		// Fields
		private String bonhomme;

		private BonhommePenduData(String bonhomme) {
			this.bonhomme = bonhomme;
		}

		public String getBonhomme() {
			return bonhomme;
		}

	}

	/**
	 * 
	 * 
	 * @param vie
	 * @return
	 */
	public String getBonhomme(int vie) {
		String hangman = new String();
		switch (vie) {
		case 0:
			hangman = BonhommePenduData.ZERO.getBonhomme();
			break;
		case 1:
			hangman = BonhommePenduData.UNE.getBonhomme();
			break;
		case 2:
			hangman = BonhommePenduData.DEUX.getBonhomme();
			break;
		case 3:
			hangman = BonhommePenduData.TROIS.getBonhomme();
			break;
		case 4:
			hangman = BonhommePenduData.QUATRE.getBonhomme();
			break;
		case 5:
			hangman = BonhommePenduData.CINQ.getBonhomme();
			break;
		case 6:
			hangman = BonhommePenduData.SIX.getBonhomme();
			break;
		case 7:
			hangman = BonhommePenduData.SEPT.getBonhomme();
			break;
		case 8:
			hangman = BonhommePenduData.HUIT.getBonhomme();
			break;
		}
		return hangman;
	}

}
