package othello;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Player{

	// REFERENCES
	Scanner sc = new Scanner(System.in);
	OthelloConstants con;
	String color;
	Game game;
	Boolean isHuman;
	
	// VARIABLES
	Boolean blackTurn = true;
	private ArrayList<int[]> vms = new ArrayList<int[]>();
	
	// CONSTRUCTOR
	public Player(Game g, Boolean h) {
		game = g;
		isHuman = h;
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
			game.printScore();
		} else if (coords.equals("help")) {
			printValidMoves();
		}
		else {
			// Makes sure input length is 3, fits pattern [1-8],[1-8], and the space is
			// empty
			while ((coords.length() != 3) || !fitsCoordsPattern(coords)
					|| !isValidMove(getRCoor(coords), getCCoor(coords))) {
				System.out.println("Please enter a valid coordinate.");
				coords = sc.next();
			}
		}
		return coords;
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
		System.out.println("Valid Moves: ");
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
