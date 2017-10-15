package othello;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Game {
	// REFERENCES
	OthelloOutput text = new OthelloOutput();
	Player player = new Player();
	Board board = new Board(this, player);

	// VARIABLES
	private Boolean blackTurn = true;
	private Boolean isFirstGame = true;
	Scanner scan = new Scanner(System.in);
	private Boolean p1MovesLeft = true;
	private Boolean p2MovesLeft = true;

	/**
	 * Runs the game, depending on which game mode was selected.
	 * 
	 * @param gametype
	 * @author sirkevinicus
	 * @since 10/13/17
	 */
	public void run(String gametype) {
		// Print the game and start taking turns until the game is over
		text.printDiv();
		blackTurn = true;

		// Print the instructions if this is the first game
		if (isFirstGame) {
			text.printInstructions();
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

	/**
	 * Runs the 1P vs. CPU game
	 * 
	 * @author sirkevinicus
	 * @since 10/13/17
	 */
	public void run1PGame() {
		board.initBoard();
		board.printBoard();
		board.generateValidMoves();
		String coords;

		while (p1MovesLeft || p2MovesLeft) {
			player.resetValidMoves();
			board.generateValidMoves();
			if (player.hasValidMove()) {
				p1MovesLeft = true;
				p2MovesLeft = true;
				printWhosTurn();
				// If it's the player's turn, ask for coords. If it's CPU's turn, random coords
				if (blackTurn) {
					coords = player.getUserCoords();
				} else {
					coords = player.getRandomMove();
				}
				// If not a special command
				if (player.fitsCoordsPattern(coords)) {
					// Extracts Coordinates from the coords string
					int rCoor = player.getRCoor(coords);
					int cCoor = player.getCCoor(coords);

					board.addDisc(rCoor, cCoor, getMyColor());
					board.flipPieces(rCoor, cCoor);

					board.printBoard();
					changeTurn();
				}
				if (coords.equals("pass")) {
					pass();
				} else if (coords.equals("score")) {
					printScore();
				} else if (coords.equals("help")) {
					player.printValidMoves();
				} else if (coords.equals("quit")) {
					return;
				}
			}
			// If the player doesn't have any valid moves
			else {
				// If this is the first time, set p1 moves to false and pass
				if (p1MovesLeft && p2MovesLeft) {
					p1MovesLeft = false;
					pass();
				}
				// If the other player also doesn't have moves, set p2 moves to false
				// And the game will end
				else if (!p1MovesLeft && p2MovesLeft)
					p2MovesLeft = false;
			}
		}
		printResults();
	}

	/**
	 * Runs the P vs. P game mode
	 * 
	 * @author sirkevinicus
	 * @since 10/13/17
	 */
	public void run2PGame() {
		board.initBoard();
		board.printBoard();
		board.generateValidMoves();
		String coords;

		while (p1MovesLeft || p2MovesLeft) {
			player.resetValidMoves();
			board.generateValidMoves();
			if (player.hasValidMove()) {
				p1MovesLeft = true;
				p2MovesLeft = true;
				printWhosTurn();
				coords = player.getUserCoords();
				// If not a special command
				if (player.fitsCoordsPattern(coords)) {
					// Extracts Coordinates from the coords string
					int rCoor = player.getRCoor(coords);
					int cCoor = player.getCCoor(coords);

					// Flips all discs
					board.addDisc(rCoor, cCoor, getMyColor());
					board.flipPieces(rCoor, cCoor);

					// Updates the board, changes turns
					board.printBoard();
					changeTurn();
				}
				if (coords.equals("pass")) {
					pass();
				} else if (coords.equals("score")) {
					printScore();
				} else if (coords.equals("help")) {
					player.printValidMoves();
				} else if (coords.equals("quit")) {
					return;
				}
			} else {
				// If this is the first time, p1 has no moves left, and passes
				if (p1MovesLeft && p2MovesLeft) {
					p1MovesLeft = false;
					pass();
				}
				// If the second player also has no moves left, ends the game
				else if (!p1MovesLeft && p2MovesLeft)
					p2MovesLeft = false;
			}
		}
		printResults();
	}

	/**
	 * Runs the simulation game mode
	 * 
	 * @author sirkevinicus
	 * @since 10/13/17
	 */
	public void runSim() {
		// Determine the number of times to run the simulation
		System.out.println("How many times would you like to run the simulation?");
		int numTimes = 1;
		while (!scan.hasNextInt()) {
			System.out.println("Please input a valid number");
			scan.next();
		}
		numTimes = scan.nextInt();
		int[] spreadArray = new int[numTimes];

		// Run the simulation numTimes times
		for (int i = 0; i < numTimes; i++) {
			System.out.println("Running test #" + (i + 1) + ".");
			board.initBoard();
			p1MovesLeft = true;
			p2MovesLeft = true;
			board.generateValidMoves();
			String coords;

			while (p1MovesLeft || p2MovesLeft) {
				player.resetValidMoves();
				board.generateValidMoves();
				if (player.hasValidMove()) {
					p1MovesLeft = true;
					p2MovesLeft = true;

					coords = player.getRandomMove();
					// Extracts Coordinates from the coords string
					int rCoor = player.getRCoor(coords);
					int cCoor = player.getCCoor(coords);

					// Flips all discs
					board.flipPieces(rCoor, cCoor);

					// Adds the new piece and updates the board
					board.addDisc(rCoor, cCoor, getMyColor());
					changeTurn();
				} else {
					if (p1MovesLeft && p2MovesLeft) {
						p1MovesLeft = false;
						pass();
					} else if (!p1MovesLeft && p2MovesLeft)
						p2MovesLeft = false;
				}
			}
			spreadArray[i] = board.getBlackPieces() - board.getWhitePieces();
		}
		printSpread(spreadArray);
	}

	/**
	 * Advances the game to the next player's turn
	 * 
	 * @author sirkevinicus
	 * @since 10/13/17
	 */
	public void pass() {
		if (isBlackTurn())
			setBlackTurn(false);
		else
			setBlackTurn(true);
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
		System.out.printf("SCORE: \n Black: %d \n White: %d \n", board.getBlackPieces(), board.getWhitePieces());
	}

	/**
	 * Asks the player what kind of game they want to play
	 * 
	 * @return returns 1P, 2P, or SIM
	 * @author sirkevinicus
	 * @since 10/13/17
	 */
	public String askForGametype() {
		text.printGamemodeSelection();
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
	 * @author sirkevinicus
	 * @since 10/15/17
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
	 * @author sirkevinicus
	 * @since 10/15/17
	 */
	public int getMyColor() {
		if (blackTurn)
			return 2;
		else
			return 1;
	}

	/**
	 * Advances the game to the next player's turn
	 * 
	 * @author sirkevinicus
	 * @since 10/15/17
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
	 * 
	 * @author sirkevinicus
	 * @since 10/15/17
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
		int wp = board.getWhitePieces();
		int bp = board.getBlackPieces();
		// Check if anyone won the game
		if (wp > bp) {
			text.printWhiteWins();
			System.out.printf("\nScore: \n White: %3d \n Black: %3d", wp, bp);
		} else if (wp < bp) {
			text.printBlackWins();
			System.out.printf("\nScore: \n Black: %3d \n White: %3d", bp, wp);
		} else {
			text.printTie();
			System.out.printf("\nScore: \n Black: %3d \n White: %3d", bp, wp);
		}
	}

	/**
	 * Prints the spread values to a file
	 * 
	 * @param array
	 *            of spreads
	 * @author sirkevinicus
	 * @since 10/13/17
	 */
	public void printSpread(int[] a) {
		try {
			PrintWriter writer = new PrintWriter("simresults.txt", "UTF-8");
			for (int i : a)
				writer.println(i + ";");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Quits the game
	 * 
	 * @author sirkevinicus
	 * @since 10/13/17
	 */
	public void quit() {
		System.err.print("Exiting game.");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.err.print(".");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.err.print(".\n");
		text.printGoodbye();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 100; i++) {
			System.out.println("\b");
		}
		System.out.flush();

		System.exit(0);

	}

	/**
	 * Asks the user if they want to play again
	 * 
	 * @return true or false
	 * @author sirkevinicus
	 * @since 10/13/17
	 */
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
		} else {
			return false;
		}
	}

	/**
	 * Returns whether or not it's black's turn
	 * 
	 * @return true or false
	 * @author sirkevinicus
	 * @since 10/13/17
	 */
	public Boolean isBlackTurn() {
		if (blackTurn)
			return true;
		else
			return false;
	}

	/**
	 * Sets the game to black's turn
	 * 
	 * @param b
	 * @author sirkevinicus
	 * @since 10/13/17
	 */
	public void setBlackTurn(Boolean b) {
		blackTurn = b;
	}
}
