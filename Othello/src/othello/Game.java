package othello;

public class Game {
	// Create references
	OthelloConstants con = new OthelloConstants();
	Board board = new Board();

	public void run() {
		// Print the game and start taking turns until the game is over
		System.out.println(con.asciiName);
		board.initBoard();
		board.printBoard();
		printOthelloInstructions();
		board.startTurn();
		checkIfGameOver();
	}

	public void printOthelloInstructions() {
		System.out.println(
				"Welcome to Othello! \n\nINSTRUCTIONS:\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\nPlease enter the Coordinates, Row First, then Column.\nEX: 1,2 = Row 1, Col 2\nYou may pass by typing 'pass'.\nYou can view the score by typing 'score'.\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
	}

	public void checkIfGameOver() {
		// Check if anyone won the game
		if (board.whitePieces > board.blackPieces) {
			System.out.println("WHITE WINS!");
			System.out.printf("Score: \n White: %3d \n Black: %3d", board.whitePieces, board.blackPieces);
		} else if (board.whitePieces < board.blackPieces) {
			System.out.println("BLACK WINS!");
			System.out.printf("Score: \n Black: %3d \n White: %3d", board.blackPieces, board.whitePieces);
		} else {
			System.out.println("IT'S A TIE!");
			System.out.printf("Score: \n Black: %3d \n White: %3d", board.blackPieces, board.whitePieces);
		}
	}
}
