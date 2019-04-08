package projet;

public class Test {

	private HangmanClient bonhommePendu = new HangmanClient();

	public static void main(String[] args) {
		Test t = new Test();
		int vie = 3;

		// Upper Border
		System.out.println("*****************************************************");
		System.out.println(t.displayBonhomme(vie));
		// Les lettres essay�es
		System.out.println();
		// La solution partielle
		//Vie
		// Lower border
		System.out.println("*****************************************************");
	}

	private String displayBonhomme(int vie) {
		// Le bonhomme
		String hangman = bonhommePendu.getBonhomme(vie);
		// Les lettres essay�es

		// La solution partielle
		// Les lettres essay�es

		return hangman;
	}

}
