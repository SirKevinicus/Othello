package othello;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Player {

	// REFERENCES
	OthelloOutput text = new OthelloOutput();
	String color;
	Scanner sc = new Scanner(System.in);

	// VARS
	ArrayList<int[]> moves = new ArrayList<int[]>(); // List of valid moves

	/**
	 * Adds a new valid move for the current player
	 * 
	 * @param i
	 * @author sirkevinicus
	 * @since 10/11/17
	 */
	public void addValidMove(int r, int c) {
		int[] vc = new int[2]; // Creates a new int[] valid coordinate, storing row and col
		// If the coordinate is already valid, doesn't add it again
		for (int[] a : moves) {
			if (a[0] == r && a[1] == c) {
				return;
			}
		}
		vc[0] = r;
		vc[1] = c;
		moves.add(vc);
	}

	/**
	 * Resets the Valid Moves
	 * 
	 * @author sirkevinicus
	 * @since 10/13/17
	 */
	public void resetValidMoves() {
		moves.clear();
	}

	/**
	 * If the player has a valid move, returns true
	 * 
	 * @author sirkevinicus
	 * @return Boolean
	 */
	public Boolean hasValidMove() {
		if (moves.size() != 0)
			return true;
		else
			return false;
	}

	/**
	 * If the coordinate is valid, returns true
	 * 
	 * @param r
	 * @param c
	 * @return true or false
	 * @author sirkevinicus
	 * @since 10/13/17
	 */
	public Boolean isValidMove(int r, int c) {
		// Checks to see if the given [r,c] is in the validCoordinates list
		for (int[] a : moves) {
			if (a[0] == r && a[1] == c) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Prints out all of the current player's valid moves
	 * 
	 * @author sirkevinicus
	 * @since 10/11/17
	 */
	public void printValidMoves() {
		System.out.println("Valid Moves: ");
		for (int[] b : moves) {
			System.out.print("(" + (b[0] + 1) + "," + (b[1] + 1) + ")\n");
		}
	}

	/**
	 * Asks the player for coordinates to place their piece
	 * 
	 * @return String coords
	 * @author sirkevinicus
	 * @since 10/13/17
	 */
	public String getUserCoords() {
		String coords = sc.next();

		// If the input is a special character
		if (coords.equals("pass")) {
			// return for pass function
		} else if (coords.equals("score")) {
			// return for score function
		} else if (coords.equals("help")) {
			// return for help function
		} else if (coords.equals("quit")) {
			// return for quit function
		} else {
			// Makes sure input length is 3, fits pattern [1-8],[1-8], and the move is valid
			while ((coords.length() != 3) || !fitsCoordsPattern(coords)
					|| !isValidMove(getRCoor(coords), getCCoor(coords))) {
				System.out.println("Please enter a valid coordinate.");
				coords = sc.next();
				if (coords.equals("pass")) {
					break;
				} else if (coords.equals("score")) {
					break;
				} else if (coords.equals("help")) {
					break;
				} else if (coords.equals("quit")) {
					break;
				}
			}
		}
		return coords;
	}

	/**
	 * Checks if the input fits the pattern [1-8],[1-8]
	 * 
	 * @param s
	 * @return true or false
	 * @author sirkevinicus
	 * @since 10/15/17
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
	 * @param string
	 * @return row coordinate
	 * @author sirkevinicus
	 * @since 10/15/17
	 */
	public int getRCoor(String s) {
		return Integer.parseInt(s.substring(0, 1)) - 1;
	}

	/**
	 * Returns the Col Coordinate from the input string
	 * 
	 * @param string
	 * @return col coordinate
	 * @author sirkevinicus
	 * @since 10/15/17
	 */
	public int getCCoor(String s) {
		return Integer.parseInt(s.substring(2)) - 1;
	}

	/**
	 * Return a random move out of the valid moves
	 * 
	 * @return random move
	 * @author sirkevinicus
	 * @since 10/15/17
	 */
	public String getRandomMove() {
		int[] randomCoord = moves.get((int) (Math.random() * moves.size()));
		return (randomCoord[0] + 1) + "," + (randomCoord[1] + 1);
	}
}
