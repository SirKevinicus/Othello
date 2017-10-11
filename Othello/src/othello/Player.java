package othello;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Player {

	// REFERENCES
	OthelloConstants con = new OthelloConstants();
	String color;
	Game game;
	Scanner sc = new Scanner(System.in);

	// VARS
	ArrayList<int[]> vms = new ArrayList<int[]>(); // List of valid moves

	// CONSTRUCTOR
	public Player(Game g) {
		game = g;
	}

	public void printValidMoves() {
		System.out.println("Valid Moves: ");
		for (int[] b : vms) {
			System.out.print("(" + (b[0] + 1) + "," + (b[1] + 1) + ")\n");
		}
	}

	/**
	 * Adds a new valid move
	 * 
	 * @param i
	 */
	public void addValidMove(int r, int c) {
		int[] vc = new int[2];
		for (int[] a : vms) {
			if (a[0] == r && a[1] == c) {
				return;
			}
		}
		vc[0] = r;
		vc[1] = c;
		vms.add(vc);
	}

	/**
	 * Resets the Valid Moves
	 * 
	 * @author sirkevinicus
	 */
	public void resetValidMoves() {
		vms.clear();
	}

	/**
	 * If the player has a valid move, returns true
	 * 
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
	 * 
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

	/**
	 * Asks the player for coordinates to place their piece
	 * 
	 * @return String coords
	 */
	public String getUserCoords() {
		String coords = sc.next();
		if (coords.equals("pass")) {
			if (game.isBlackTurn())
				game.setBlackTurn(false);
			else
				game.setBlackTurn(true);
		} else if (coords.equals("score")) {
			game.printScore();
		} else if (coords.equals("help")) {
			printValidMoves();
		} else {
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
	 * @param strin
	 * @return row coor
	 */
	public int getRCoor(String s) {
		return Integer.parseInt(s.substring(0, 1)) - 1;
	}

	/**
	 * Returns the Col Coordinate from the input string
	 * 
	 * @param string
	 * @return col coor
	 */
	public int getCCoor(String s) {
		return Integer.parseInt(s.substring(2)) - 1;
	}

	/**
	 * Return a random move out of the valid moves
	 * 
	 * @author sirkevinicus
	 * @return random move
	 */
	public String getRandomMove() {
		int[] randomCoord = vms.get((int) (Math.random() * vms.size()));
		return (randomCoord[0]+1) + "," + (randomCoord[1]+1);
	}
}
