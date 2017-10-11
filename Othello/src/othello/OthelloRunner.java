package othello;

public class OthelloRunner {

	public static void main(String[] args) {
		OthelloConstants.printAsciiName();
		Game myGame = new Game();
		do {
		myGame.run(myGame.askForGametype());
		} while(myGame.playAgain());
	}
}
