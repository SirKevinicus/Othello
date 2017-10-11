package othello;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Game {
	// Create references
	OthelloConstants con = new OthelloConstants();
	Board board;

	// Variables
	private Boolean blackTurn = true;
	private Boolean isFirstGame = true;
	Scanner scan = new Scanner(System.in);

	public void run(String gametype) {
		// Print the game and start taking turns until the game is over
		con.printDiv();
		if (isFirstGame) {
			con.printInstructions();
			pressEnterToContinue();
		}
		switch (gametype) {
		case "1P":
			run1PGame();
			break;
		case "2P":
			run2PGame();
			break;
		case "SIM":
			runSim();
			break;
		default:
			System.err.println("Something went wrong...");
		}
	}

	public void run1PGame() {
		Player p1 = new Player(this);
		board = new Board(this, p1);
		board.initBoard();
		board.printBoard();
		board.generateValidMoves();
		String coords;

		while (p1.hasValidMove()) {
			p1.resetValidMoves();
			board.generateValidMoves();
			if (p1.hasValidMove()) {
				printWhosTurn();
				if (blackTurn) {
					coords = p1.getUserCoords();
				} else {
					coords = p1.getRandomMove();
				}
				// If not a special command
				if (p1.fitsCoordsPattern(coords)) {
					// Extracts Coordinates from the coords string
					int rCoor = p1.getRCoor(coords);
					int cCoor = p1.getCCoor(coords);

					// Flips all discs
					board.flipPieces(rCoor, cCoor);

					// Adds the new piece and updates the board
					board.updateBoard(rCoor, cCoor, getMyColor());
					board.printBoard();
					changeTurn();
				}
			}
		}
		printResults();
	}

	public void runSim() {
		System.out.println("How many times would you like to run the simulation?");
		int numTimes = scan.nextInt();
		int[] spreadArray = new int[numTimes];
		for (int i = 1; i < numTimes; i++) {
			System.out.println("Running test #" + (i+1) + ".") ;
			Player p1 = new Player(this);
			board = new Board(this, p1);
			board.initBoard();
			board.generateValidMoves();
			String coords;

			while (p1.hasValidMove()) {
				p1.resetValidMoves();
				board.generateValidMoves();
				if (p1.hasValidMove()) {
					coords = p1.getRandomMove();
					// Extracts Coordinates from the coords string
					int rCoor = p1.getRCoor(coords);
					int cCoor = p1.getCCoor(coords);

					// Flips all discs
					board.flipPieces(rCoor, cCoor);

					// Adds the new piece and updates the board
					board.updateBoard(rCoor, cCoor, getMyColor());
					changeTurn();
				}
			}
			spreadArray[i] = board.blackPieces - board.whitePieces;
		}
		printSpread(spreadArray);
	}

	public void run2PGame() {
		Player p1 = new Player(this);
		board = new Board(this, p1);
		board.initBoard();
		board.printBoard();
		board.generateValidMoves();
		String coords;

		while (p1.hasValidMove()) {
			p1.resetValidMoves();
			board.generateValidMoves();
			if (p1.hasValidMove()) {
				printWhosTurn();
				coords = p1.getUserCoords();

				// If not a special command
				if (p1.fitsCoordsPattern(coords)) {
					// Extracts Coordinates from the coords string
					int rCoor = p1.getRCoor(coords);
					int cCoor = p1.getCCoor(coords);

					// Flips all discs
					board.flipPieces(rCoor, cCoor);

					// Adds the new piece and updates the board
					board.updateBoard(rCoor, cCoor, getMyColor());
					board.printBoard();
					changeTurn();
				}
			}
		}
		printResults();
	}

	/**
	 * Waits for an enter press to continue
	 * 
	 * @author Code found at:
	 *         https://stackoverflow.com/questions/19870467/how-do-i-get-press-any-key-to-continue-to-work-in-my-java-code
	 * @since 10/11/2017
	 */
	private void pressEnterToContinue() {
		System.out.println("Press Enter key to continue...");
		try {
			System.in.read();
		} catch (Exception e) {
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
		con.printGamemodeSelection();
		String input = scan.next();
		while (!input.equals("1P") && !input.equals("2P") && !input.equals("SIM")) {
			System.out.println("Please input a valid gamemode.");
			input = scan.next();
		}
		return input;
	}

	/**
	 * Returns the inactive player's color
	 * 
	 * @return 1(white) or 2(black)
	 */
	public int getEnemyColor() {
		if (blackTurn)
			return 1;
		else
			return 2;
	}

	/**
	 * Returns the active player's color
	 * 
	 * @return 1(white) or 2(black)
	 */
	public int getMyColor() {
		if (blackTurn)
			return 2;
		else
			return 1;
	}

	/**
	 * Advances the game to the next player's turn
	 */
	public void changeTurn() {
		if (blackTurn) {
			blackTurn = false;
		} else {
			blackTurn = true;
		}
	}

	/**
	 * Prints who's turn it is
	 */
	public void printWhosTurn() {// Print Statement
		if (getMyColor() == 1) {
			System.out.println("It's WHITE'S turn! Place your next disc.");
		} else {
			System.out.println("It's BLACK'S turn! Place your next disc.");
		}
	}

	/**
	 * Prints out the end game results
	 * 
	 * @author sirkevinicus
	 * @since 10/11/2017
	 */
	public void printResults() {
		// Check if anyone won the game
		if (board.whitePieces > board.blackPieces) {
			con.printWhiteWins();
			System.out.printf("\nScore: \n White: %3d \n Black: %3d", board.whitePieces, board.blackPieces);
		} else if (board.whitePieces < board.blackPieces) {
			con.printBlackWins();
			System.out.printf("\nScore: \n Black: %3d \n White: %3d", board.blackPieces, board.whitePieces);
		} else {
			con.printTie();
			System.out.printf("\nScore: \n Black: %3d \n White: %3d", board.blackPieces, board.whitePieces);
		}
	}
	
	public void printSpread(int[] a) {
		try {
			PrintWriter writer = new PrintWriter("simresults.txt", "UTF-8");
			for(int i: a)
				writer.print(i + ",");
			writer.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
	}

	public Boolean playAgain() {
		System.out.println("\nWould you like to play again? Type 'y' for yes and 'n' for no.");
		String pa = scan.next();
		while (!pa.equals("y") && !pa.equals("n")) {
			System.out.println("Please input 'y' for yes and 'n' for no.");
			pa = scan.next();
		}
		if (pa.equals("y")) {
			isFirstGame = false;
			return true;
		} else
			return false;
	}

	public Boolean isBlackTurn() {
		if (blackTurn)
			return true;
		else
			return false;
	}

	public void setBlackTurn(Boolean b) {
		blackTurn = b;
	}
}
