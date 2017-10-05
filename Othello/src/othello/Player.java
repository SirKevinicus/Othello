package othello;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Player {

	// REFERENCES
	Scanner sc = new Scanner(System.in);
	OthelloConstants con;
	String color;
	Board board;

	// VARIABLES
	Boolean blackTurn = true;
	private ArrayList<int[]> vms = new ArrayList<int[]>();
	
	// CONSTRUCTOR
	public Player(Board bo) {
		board = bo;
	}

	/**
	 * Tells the user who's turn it is, asks for coordinates for their next piece
	 * Checks to make sure the input is valid Updates the Grid and prints it out
	 * 
	 * @author sirkevinicus
	 * @since 9/1/2017
	 */
	public void takeTurn() {
		resetValidMoves();
		board.generateValidMoves();
		if (!hasValidMove())
			return;
		printValidMoves();
		printWhosTurn();
		String coords = getUserCoords();
		
		//If not a special command
		if (fitsCoordsPattern(coords)) {
			// Extracts Coordinates from the coords string
			int rCoor = getRCoor(coords);
			int cCoor = getCCoor(coords);

			// Flips all discs
			board.flipPieces(rCoor, cCoor);
			//Adds the new piece and updates the board
			board.updateBoard(rCoor, cCoor, getMyColor());
			changeTurn();
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
			board.printScore();
		} else {
			// Makes sure input length is 3, fits pattern [1-8],[1-8], and the space is
			// empty
			while ((coords.length() != 3) || !fitsCoordsPattern(coords)
					|| checkIfActive(getRCoor(coords), getCCoor(coords))
					|| !isValidMove(getRCoor(coords), getCCoor(coords))) {
				System.out.println("Please enter a valid coordinate.");
				coords = sc.next();
			}
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
		Boolean thingIsActive = board.myDiscs[r][c].isActive;
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
			System.out.print("(" + (b[0]+1) + "," + (b[1]+1) + ")\n");
		}
	}
	
	/**
	 * Adds a new valid move
	 * @param i
	 */
	public void addValidMove(int[] i) {
		int r = i[0];
		int c = i[1];
		for(int[] a: vms) {
			if(a[0] == r && a[1] == c) {
				return;
			}
		}
		vms.add(i);
	}
	
	/**
	 * Resets the Valid Moves
	 * @author sirkevinicus
	 */
	public void resetValidMoves() {
		vms.clear();
	}
	
	/**
	 * If the player has a valid move, returns true
	 * @author sirkevinicus
	 * @return
	 */
	public Boolean hasValidMove() {
		if (vms.size() != 0)
			return true;		
		else
			return false;
	}
	
	/**
	 * If the coordinate is valid, returns true
	 * @param r
	 * @param c
	 * @return true or false
	 */
	public Boolean isValidMove(int r, int c) {
		for (int[] a : vms) {
			if (a[0] == r && a[1] == c) {
				return true;
			}
		}
		return false;
	}
}
