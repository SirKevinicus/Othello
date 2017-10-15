package othello;

public class Board {
	// REFERENCES
	Disc[][] myDiscs;
	OthelloOutput text = new OthelloOutput();
	Game game;
	Player p1;

	// VARIABLES
	private int bWidth = OthelloOutput.ARRAY_W;
	private int bHeight = OthelloOutput.ARRAY_H;
	private int whitePieces = 2;
	private int blackPieces = 2;

	// CONSTRUCTOR
	public Board(Game g, Player one) {
		p1 = one;
		game = g;
		myDiscs = new Disc[bWidth][bHeight];
	}

	/**
	 * Creates a Disc for each space on the board and places the first 4 discs in
	 * the middle
	 * 
	 * @author sirkevinicus
	 * @since 9/1/2017
	 */
	public void initBoard() {
		int leftMid = (int) (bWidth / 2) - 1; // Finds the left middle
		int rightMid = leftMid + 1; // Finds the right middle

		// Initialize all discs
		for (int row = 0; row < bWidth; row++) {
			for (int col = 0; col < bHeight; col++) {
				myDiscs[row][col] = new Disc(row, col);
			}
		}

		// Place the starting discs
		myDiscs[leftMid][leftMid] = new Disc(leftMid, leftMid, "white"); // Top Left White
		myDiscs[leftMid][rightMid] = new Disc(leftMid, rightMid, "black"); // Top Right Black
		myDiscs[rightMid][leftMid] = new Disc(rightMid, leftMid, "black"); // Bot Left Black
		myDiscs[rightMid][rightMid] = new Disc(rightMid, rightMid, "white"); // Bot Right White
		setBlackPieces(2);
		setWhitePieces(2);
	}

	/**
	 * Prints the Othello Board
	 * 
	 * @author Kevin Gerstner
	 * @since 9/1/2017
	 */
	public void printBoard() {
		text.printDiv();
		text.printNums();
		text.printLineDivider();
		// PRINT ALL THE ROWS
		for (int row = 0; row < bWidth; row++) {
			printRow(row);
			text.printLineDivider();
		}
		text.printDiv();
	}

	/**
	 * Places a new disc on the board
	 * 
	 * @param r
	 * @param c
	 * @param color
	 * @author Kevin Gerstner
	 * @since 9/1/2017
	 */
	public void addDisc(int r, int c, int color) {
		myDiscs[r][c].changeColor(color);
		if (game.isBlackTurn())
			blackPieces++;
		else
			whitePieces++;
	}

	/**
	 * Finds all of the possible moves for the current player
	 * 
	 * @author sirkevinicus
	 * @since 10/13/17
	 */
	public void generateValidMoves() {
		for (int r = 0; r < myDiscs[0].length; r++) {
			for (int c = 0; c < myDiscs[1].length; c++) {
				// If the disc is not active
				if (!myDiscs[r][c].isActive) {
					isDirectionValid(r, c, "up");
					isDirectionValid(r, c, "down");
					isDirectionValid(r, c, "left");
					isDirectionValid(r, c, "right");
					isDirectionValid(r, c, "upL");
					isDirectionValid(r, c, "upR");
					isDirectionValid(r, c, "downL");
					isDirectionValid(r, c, "downR");
				}
			}
		}
	}

	/**
	 * Flips all pieces that are sandwiched
	 * 
	 * @param r
	 * @param c
	 * @author sirkevinicus
	 * @since 10/13/17
	 */
	public void flipPieces(int r, int c) {
		if (isDirectionValid(r, c, "up"))
			flip(r, c, "up");
		if (isDirectionValid(r, c, "down"))
			flip(r, c, "down");
		if (isDirectionValid(r, c, "left"))
			flip(r, c, "left");
		if (isDirectionValid(r, c, "right"))
			flip(r, c, "right");
		if (isDirectionValid(r, c, "upL"))
			flip(r, c, "upL");
		if (isDirectionValid(r, c, "upR"))
			flip(r, c, "upR");
		if (isDirectionValid(r, c, "downL"))
			flip(r, c, "downL");
		if (isDirectionValid(r, c, "downR"))
			flip(r, c, "downR");
	}

	/**
	 * Checks to see if a direction is valid
	 * 
	 * @param r
	 * @param c
	 * @param d
	 * @return true or false
	 * @author sirkevinicus
	 * @since 10/13/17
	 */
	public Boolean isDirectionValid(int r, int c, String d) {
		int[] mods = getModifiers(d);
		int modR = mods[0]; // The Row Modifier, such as -1 for up
		int modC = mods[1]; // The col modifier, such as 1 for right

		try {
			// If the immediate disc is an enemy, perform the check
			if (myDiscs[r + modR][c + modC].discColor == game.getEnemyColor()) {
				// Continue in the direction until a non-enemy disc is found
				while (myDiscs[r + modR][c + modC].discColor == game.getEnemyColor()) {
					if (modR < 0) // go left if checking left
						modR--;
					else if (modR > 0) // go right if checking right
						modR++;
					if (modC < 0) // go up if checking up
						modC--;
					else if (modC > 0) // go down if checking down
						modC++;
				}
				// If the non-enemy disc exists, that means the move is valid
				if (myDiscs[r + modR][c + modC].isActive) {
					p1.addValidMove(r, c);
					return true;
				}
				// If not, move is invalid
				else
					return false;
			} else
				return false;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	/**
	 * Flips all pieces in a direction
	 * 
	 * @param r
	 * @param c
	 * @param d
	 * @author sirkevinicus
	 * @since 10/13/17
	 */
	public void flip(int r, int c, String d) {
		int[] mods = getModifiers(d);
		int modR = mods[0]; // The Row Modifier, such as -1 for up
		int modC = mods[1]; // The col modifier, such as 1 for right

		// Continue in the direction until a non-enemy disc is found
		while (myDiscs[r + modR][c + modC].discColor == game.getEnemyColor()) {
			myDiscs[r + modR][c + modC].changeColor(game.getMyColor());
			// Update Disc Numbers
			if (game.getMyColor() == 1) {
				addWhitePiece();
				subBlackPiece();
			}
			if (game.getMyColor() == 2) {
				subWhitePiece();
				addBlackPiece();
			}
			if (modR < 0) // go left if checking left
				modR--;
			else if (modR > 0) // go right if checking right
				modR++;
			if (modC < 0) // go up if checking up
				modC--;
			else if (modC > 0) // go down if checking down
				modC++;
		}

	}

	/**
	 * Prints one row of the Othello Board
	 * 
	 * @param r
	 * @author sirkevinicus
	 * @since 9/1/2017
	 */
	public void printRow(int r) {
		System.out.print(r + 1); // Prints the row's number
		text.printBar();
		for (int col = 0; col < bWidth; col++) {
			// If there's a disc
			if (myDiscs[r][col].isActive) {
				// If the disc is white
				if (myDiscs[r][col].discColor == 1) {
					System.out.print("  O"); // Print 'O'
					System.out.print("  ");
				}

				// If the disc is black
				if (myDiscs[r][col].discColor == 2) {
					System.out.print("  X"); // Print 'X'
					System.out.print("  ");
				}
			} else { // If there's no disc
				System.out.print("     "); // Print a blank space
			}
			text.printBar();
		}
	}

	/**
	 * Returns a row and col modifier for each direction Ex: returns -1, 0 for "up"
	 * because it is a row up
	 * 
	 * @param d
	 * @return row and col modifiers
	 * @author sirkevinicus
	 * @since 10/13/17
	 */
	public int[] getModifiers(String d) {
		int[] mods = { 0, 0 };
		switch (d) {
		case "up":
			mods[0] = -1;
			break;
		case "down":
			mods[0] = 1;
			break;
		case "left":
			mods[1] = -1;
			break;
		case "right":
			mods[1] = 1;
			break;
		case "upL":
			mods[0] = -1;
			mods[1] = -1;
			break;
		case "upR":
			mods[0] = -1;
			mods[1] = 1;
			break;
		case "downL":
			mods[0] = 1;
			mods[1] = -1;
			break;
		case "downR":
			mods[0] = 1;
			mods[1] = 1;
			break;
		default:
			System.err.println("Invalid direction!");
		}
		return mods;
	}

	/**
	 * Returns num of black pieces on the board
	 * 
	 * @return blackPieces
	 * @author sirkevinicus
	 * @since 10/13/17
	 */
	public int getBlackPieces() {
		return blackPieces;
	}

	/**
	 * Sets black pieces to b
	 * 
	 * @param b
	 * @author sirkevinicus
	 * @since 10/13/17
	 */
	public void setBlackPieces(int b) {
		blackPieces = b;
	}

	/**
	 * Adds a black disc to the total
	 * 
	 * @author sirkevinicus
	 * @since 10/14/17
	 */
	public void addBlackPiece() {
		blackPieces++;
	}

	/**
	 * Subtracts a black piece from the total
	 * 
	 * @author sirkevinicus
	 * @since 10/14/17
	 */
	public void subBlackPiece() {
		blackPieces--;
	}

	/**
	 * Returns num of white pieces on the board
	 * 
	 * @return whitePieces
	 * @author sirkevinicus
	 * @since 10/13/17
	 */
	public int getWhitePieces() {
		return whitePieces;
	}

	/**
	 * Adds a white disc to the total
	 * 
	 * @author sirkevinicus
	 * @since 10/14/17
	 */
	public void addWhitePiece() {
		whitePieces++;
	}

	/**
	 * Subtracts a white piece from the total
	 * 
	 * @author sirkevinicus
	 * @since 10/14/17
	 */
	public void subWhitePiece() {
		whitePieces--;
	}

	/**
	 * Sets white pieces to w
	 * 
	 * @param w
	 * @author sirkevinicus
	 * @since 10/13/17
	 */
	public void setWhitePieces(int w) {
		whitePieces = w;
	}
}
