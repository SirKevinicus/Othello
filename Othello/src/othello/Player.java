package othello;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Player {

	// REFERENCES
	Scanner sc = new Scanner(System.in);
	Board b;
	OthelloConstants con;

	// VARIABLES
	Boolean blackTurn = true;
	private Boolean moveIsValid = true;
	private int gWidth = OthelloConstants.ARRAY_W;
	private int gHeight = OthelloConstants.ARRAY_H;
	private ArrayList<int[]> vms = new ArrayList<int[]>();

	// CONSTRUCTOR
	public Player(Board d) {
		b = d;
	}

	/**
	 * Tells the user who's turn it is, asks for coordinates for their next piece
	 * Checks to make sure the input is valid Updates the Grid and prints it out
	 * 
	 * @author sirkevinicus
	 * @since 9/1/2017
	 */
	public void takeTurn() {
		getValidMoves();
		printValidMoves();
		printWhosTurn();
		String coords = getUserCoords();

		// Extracts Coordinates from the coords string
		int rCoor = getRCoor(coords);
		int cCoor = getCCoor(coords);

		// Set Disc color to new color
		flipPieces(rCoor,cCoor);
		b.updateGrid(rCoor, cCoor, getMyColor());
		changeTurn();
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
	 * Advances the game to the next player's turn
	 */
	public void changeTurn() {
		if (blackTurn)
			blackTurn = false;
		else
			blackTurn = true;
	}

	/**
	 * Asks the player for coordinates to place their piece
	 * 
	 * @return String coords
	 */
	public String getUserCoords() {
		String coords = sc.next();
		if (coords.equals("pass")) {
			if (blackTurn)
				blackTurn = false;
			else
				blackTurn = true;
		} else if (coords.equals("score")) {
			printScore();
		} else {
			// Makes sure input length is 3, fits pattern [1-8],[1-8], and the space is
			// empty
			while ((coords.length() != 3) || !fitsCoordsPattern(coords)
					|| checkIfActive(getRCoor(coords), getCCoor(coords))
					|| !isValidMove2(getRCoor(coords), getCCoor(coords))) {
				System.out.println("Please enter a valid coordinate.");
				coords = sc.next();
			}
			;
		}
		return coords;
	}

	/**
	 * Checks to see if the Disc at [r][c] is active
	 * 
	 * @param s
	 * @return true or false
	 */
	public Boolean checkIfActive(int r, int c) {
		Boolean thingIsActive = b.myDiscs[r][c].isActive;
		if (thingIsActive) {
			System.err.println("There's already a disc there!");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if the input fits the pattern [1-8],[1,8]
	 * 
	 * @param s
	 * @return true or false
	 */
	public Boolean fitsCoordsPattern(String s) {
		String coords = s;
		if (Pattern.matches("[1-8],[1-8]", (CharSequence) coords))
			return true;
		else {
			return false;
		}
	}

	public void getValidMoves() {
		for (int r = 0; r < b.myDiscs[0].length; r++) {
			for (int c = 0; c < b.myDiscs[1].length; c++) {
				if (r != 0)
					isDirectionValid(r, c, "up");
				if (r != gHeight - 1)
					isDirectionValid(r, c, "down");
				if (c != 0)
					isDirectionValid(r, c, "left");
				if (c != gWidth - 1)
					isDirectionValid(r, c, "right");
				if (r != 0 && c != 0)
					isDirectionValid(r, c, "upL");
				if (r != 0 && c != gWidth - 1)
					isDirectionValid(r, c, "upR");
				if (r != gHeight - 1 && c != 0)
					isDirectionValid(r, c, "downL");
				if (r != gHeight - 1 && c != gWidth - 1)
					isDirectionValid(r, c, "downR");
			}
		}
	}

	public void flipPieces(int r, int c) {
		if (r != 0)
			if (isDirectionValid(r, c, "up"))
				flip(r, c, "up");
		if (r != gHeight - 1)
			if (isDirectionValid(r, c, "down"))
				flip(r,c,"down");
		if (c != 0)
			if (isDirectionValid(r, c, "left"))
				flip(r,c,"left");
		if (c != gWidth - 1) {
			if (isDirectionValid(r, c, "right")) {
				flip(r,c,"right");}}
		if (r != 0 && c != 0)
			if (isDirectionValid(r, c, "upL"))
				flip(r,c,"upL");
		if (r != 0 && c != gWidth - 1)
			if (isDirectionValid(r, c, "upR"))
				flip(r,c,"upR");
		if (r != gHeight - 1 && c != 0)
			if (isDirectionValid(r, c, "downL"))
				flip(r,c,"downL");
		if (r != gHeight - 1 && c != gWidth - 1)
			if (isDirectionValid(r, c, "downR"))
				flip(r,c,"downR");
	}

	public void flip(int r, int c, String d) {
		int modR = 0; // The Row Modifier, such as -1 for up
		int modC = 0; // The col modifier, such as 1 for right
		//int[] vc = new int[2]; // The coordinates of the valid move
		// Depending on the inputted direction, change the row and col modifiers
		switch (d) {
		case "up":
			modR = -1;
			break;
		case "down":
			modR = 1;
			break;
		case "left":
			modC = -1;
			break;
		case "right":
			modC = 1;
			break;
		case "upL":
			modR = -1;
			modC = -1;
			break;
		case "upR":
			modR = -1;
			modC = 1;
			break;
		case "downL":
			modR = 1;
			modC = -1;
			break;
		case "downR":
			modR = 1;
			modC = 1;
			break;
		default:
			System.err.println("Invalid direction!");
		}

		// This is the actual check
		// If the immediate disc is an enemy, perform the check
		if (b.myDiscs[r + modR][c + modC].discColor == getEnemyColor()) {
			b.flipPiece(r + modR, c + modC, getMyColor());
			// Continue in the direction until a non-enemy disc is found
			while (b.myDiscs[r + modR][c + modC].discColor == getEnemyColor()) {
				if (modR < 0) // go left if checking left
					modR--;
				else if (modR > 0) // go right if checking right
					modR++;
				if (modC < 0) // go up if checking up
					modC--;
				else if (modC > 0) // go down if checking down
					modC++;
				b.flipPiece(r + modR, c + modC, getMyColor());
			}
		}
	}

	public Boolean isDirectionValid(int r, int c, String d) {
		int modR = 0; // The Row Modifier, such as -1 for up
		int modC = 0; // The col modifier, such as 1 for right
		int[] vc = new int[2]; // The coordinates of the valid move
		// Depending on the inputted direction, change the row and col modifiers
		switch (d) {
		case "up":
			modR = -1;
			break;
		case "down":
			modR = 1;
			break;
		case "left":
			modC = -1;
			break;
		case "right":
			modC = 1;
			break;
		case "upL":
			modR = -1;
			modC = -1;
			break;
		case "upR":
			modR = -1;
			modC = 1;
			break;
		case "downL":
			modR = 1;
			modC = -1;
			break;
		case "downR":
			modR = 1;
			modC = 1;
			break;
		default:
			System.err.println("Invalid direction!");
		}

		// This is the actual check
		// If the immediate disc is an enemy, perform the check
		if (b.myDiscs[r + modR][c + modC].discColor == getEnemyColor()) {
			// Continue in the direction until a non-enemy disc is found
			while (b.myDiscs[r + modR][c + modC].discColor == getEnemyColor()) {
				if (modR < 0) // go left if checking left
					modR--;
				else if (modR > 0) // go right if checking right
					modR++;
				if (modC < 0) // go up if checking up
					modC--;
				else if (modC > 0) // go down if checking down
					modC++;
			}
			// If the non-enemy disc exists, that means it's yours and the move is valid
			if (b.myDiscs[r + modR][c + modC].isActive) {
				vc[0] = (r);
				vc[1] = (c);
				vms.add(vc); // add the coordinate to valid moves
				return true;
			}
			// If not, move is invalid
			else
				return false;
		} else
			return false;
	}

	public Boolean isValidMove2(int r, int c) {
		for (int[] a : vms) {
			if (a[0] == r && a[1] == c) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Prints the score to the console
	 * 
	 * @author sirkevinicus
	 * @since 9/13/2017
	 */
	public void printScore() {
		System.out.printf("SCORE: \n Black: %d \n White: %d \n", b.blackPieces, b.whitePieces);
	}

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
	 * GETTERS
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	/**
	 * Returns the Row Coordinate from the input string
	 * 
	 * @param s
	 * @return row coor
	 */
	public int getRCoor(String s) {
		return Integer.parseInt(s.substring(0, 1)) - 1;
	}

	/**
	 * Returns the Col Coordinate from the input string
	 * 
	 * @param s
	 * @return col coor
	 */
	public int getCCoor(String s) {
		return Integer.parseInt(s.substring(2)) - 1;
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

	public void printValidMoves() {
		for (int[] b : vms) {
			System.out.print("(" + b[0] + "," + b[1] + ")\n");
		}
	}
}
