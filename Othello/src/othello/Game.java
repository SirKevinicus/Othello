package othello;

import java.util.Scanner;

public class Game {
	// Create references
	OthelloConstants con = new OthelloConstants();
	Board board;
	Player p1;
	Player p2;
	Scanner s = new Scanner(System.in);

	public void run(String gametype) {
		// Print the game and start taking turns until the game is over
		con.printAsciiName();
		con.printInstructions();
		switch (gametype) {
		case "1P":
			p1 = new Player(this, true);
			p2 = new Player(this, false);
			break;
		case "2P":
			p1 = new Player(this, true);
			p2 = new Player(this, true);
			break;
		case "SIM":
			p1 = new Player(this, false);
			p2 = new Player(this, false);
			break;
		default:
			System.err.println("Something went wrong...");
		}
		board = new Board(this, p1, p2);
		board.initBoard();
		board.printBoard();
		do {
			p1.resetValidMoves();
			board.generateValidMoves();
			if (!p1.hasValidMove())
				return;
			p1.printWhosTurn();
			String coords = p1.getUserCoords();

			// If not a special command
			if (p1.fitsCoordsPattern(coords)) {
				// Extracts Coordinates from the coords string
				int rCoor = p1.getRCoor(coords);
				int cCoor = p1.getCCoor(coords);

				// Flips all discs
				board.flipPieces(rCoor, cCoor);
				// Adds the new piece and updates the board
				board.updateBoard(rCoor, cCoor, p1.getMyColor());
				p1.changeTurn();
			}
		} while (p1.hasValidMove());
		checkIfGameOver();
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

	/**
	 * Prints the score to the console
	 * 
	 * @author sirkevinicus
	 * @since 9/13/2017
	 */
	public void printScore() {
		System.out.printf("SCORE: \n Black: %d \n White: %d \n", board.blackPieces, board.whitePieces);
	}

	public String askForGametype() {
		System.out.println("SELECT YOUR GAME TYPE: \n1P: 1 Player vs. CP\n2P: Player vs. Player\nSIM: Run Simulation");
		String input = s.next();
		while (!input.equals("1P") && !input.equals("2P") && !input.equals("SIM")) {
			System.out.println("Please input a valid gamemode.");
			input = s.next();
		}
		return input;
	}
}
