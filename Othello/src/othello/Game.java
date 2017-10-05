package othello;

public class Game {
	// Create references
		OthelloConstants con = new OthelloConstants();
		Board da = new Board();
		Player player = new Player(da);

		
	public void run() {
		// Print the game and start taking turns until the game is over
		System.out.println(con.asciiName);
		da.initGrid();
		da.printGrid();
		System.out.println(
				"Welcome to Othello! \n\nINSTRUCTIONS:\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\nPlease enter the Coordinates, Row First, then Column.\nEX: 1,2 = Row 1, Col 2\nYou may pass by typing 'pass'.\nYou can view the score by typing 'score'.\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		while (!da.checkIfBoardFull() && !da.checkIfBlackOnly() && !da.checkIfWhiteOnly()) {
			player.takeTurn();
		};

		// Check if anyone won the game
		if (da.checkIfBlackOnly()) {
			System.out.println("BLACK WINS!");
			System.out.printf("Score: \n Black: %3d \n White: %3d", da.blackPieces, da.whitePieces);
		} else if (da.checkIfWhiteOnly()) {
			System.out.println("WHITE WINS!");
			System.out.printf("Score: \n White: %3d \n Black: %3d", da.whitePieces, da.blackPieces);
		} else {
			if (da.whitePieces > da.blackPieces) {
				System.out.println("WHITE WINS!");
				System.out.printf("Score: \n White: %3d \n Black: %3d", da.whitePieces, da.blackPieces);
			} else if (da.whitePieces < da.blackPieces) {
				System.out.println("BLACK WINS!");
				System.out.printf("Score: \n Black: %3d \n White: %3d", da.blackPieces, da.whitePieces);
			} else {
				System.out.println("IT'S A TIE!");
				System.out.printf("Score: \n Black: %3d \n White: %3d", da.blackPieces, da.whitePieces);
			}
		}
	}
}

