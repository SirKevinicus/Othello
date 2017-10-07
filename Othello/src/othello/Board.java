package othello;

public class Board {
	// REFERENCES
	Disc[][] myDiscs;
	Game game;
	Player p1;
	Player p2;

	// BOARD VARIABLES
	private int bWidth = OthelloConstants.ARRAY_W;
	private int bHeight = OthelloConstants.ARRAY_H;
	Boolean boardFull = false;
	Boolean movesLeft = true;
	int whitePieces = 2;
	int blackPieces = 2;

	public Board(Game g, Player one, Player two) {
		game = g;
		p1 = one;
		p2 = two;
		myDiscs = new Disc[bWidth][bHeight];
	}

	/**
	 * Creates a Disc for each space in the grid Places the first 4 discs in the
	 * middle
	 * 
	 * @author sirkevinicus
	 * @since 9/1/2017
	 */
	public void initBoard() {
		int leftMid = (int) (bWidth / 2) - 1; // 3
		int rightMid = leftMid + 1; // 4

		for (int row = 0; row < bWidth; row++) { // Initialize all discs
			for (int col = 0; col < bHeight; col++) {
				myDiscs[row][col] = new Disc(row, col);
			}
		}
		// Place the starting discs
		myDiscs[leftMid][leftMid] = new Disc(leftMid, leftMid, "white"); // Top Left White
		myDiscs[leftMid][rightMid] = new Disc(leftMid, rightMid, "black"); // Top Right Black
		myDiscs[rightMid][leftMid] = new Disc(rightMid, leftMid, "black"); // Bot Left Black
		myDiscs[rightMid][rightMid] = new Disc(rightMid, rightMid, "white"); // Bot Right White
	}

	/**
	 * Prints the Othello Board
	 * 
	 * @author Kevin Gerstner
	 * @since 9/1/2017
	 */
	public void printBoard() {
		printNums();
		printLineDivider();
		// PRINT ALL THE ROWS
		for (int row = 0; row < bWidth; row++) {
			printRow(row);
			printLineDivider();
		}
	}

	/**
	 * Places a new disc and reprints the Board
	 * 
	 * @param r
	 * @param c
	 * @param color
	 * @author Kevin Gerstner
	 * @since 9/1/2017
	 */
	public void updateBoard(int r, int c, int color) {
		myDiscs[r][c].changeColor(color);
		if(p1.getMyColor() == 2)
			blackPieces++;
		else
			whitePieces++;
		printBoard();
	}

	public void generateValidMoves() {
		for (int r = 0; r < myDiscs[0].length; r++) {
			for (int c = 0; c < myDiscs[1].length; c++) {
				if (!myDiscs[r][c].isActive) {
					if (r != 0)
						isDirectionValid(r, c, "up");
					if (r != bHeight - 1)
						isDirectionValid(r, c, "down");
					if (c != 0)
						isDirectionValid(r, c, "left");
					if (c != bWidth - 1)
						isDirectionValid(r, c, "right");
					if (r != 0 && c != 0)
						isDirectionValid(r, c, "upL");
					if (r != 0 && c != bWidth - 1)
						isDirectionValid(r, c, "upR");
					if (r != bHeight - 1 && c != 0)
						isDirectionValid(r, c, "downL");
					if (r != bHeight - 1 && c != bWidth - 1)
						isDirectionValid(r, c, "downR");
				}
			}
		}
	}

	public void flipPieces(int r, int c) {
		if (r != 0)
			if (isDirectionValid(r, c, "up"))
				flip(r, c, "up");
		if (r != bHeight - 1)
			if (isDirectionValid(r, c, "down"))
				flip(r, c, "down");
		if (c != 0)
			if (isDirectionValid(r, c, "left"))
				flip(r, c, "left");
		if (c != bWidth - 1)
			if (isDirectionValid(r, c, "right"))
				flip(r, c, "right");
		if (r != 0 && c != 0)
			if (isDirectionValid(r, c, "upL"))
				flip(r, c, "upL");
		if (r != 0 && c != bWidth - 1)
			if (isDirectionValid(r, c, "upR"))
				flip(r, c, "upR");
		if (r != bHeight - 1 && c != 0)
			if (isDirectionValid(r, c, "downL"))
				flip(r, c, "downL");
		if (r != bHeight - 1 && c != bWidth - 1)
			if (isDirectionValid(r, c, "downR"))
				flip(r, c, "downR");
	}

	public Boolean isDirectionValid(int r, int c, String d) {
		int[] mods = getModifiers(d);
		int modR = mods[0]; // The Row Modifier, such as -1 for up
		int modC = mods[1]; // The col modifier, such as 1 for right
		int[] vc = new int[2]; // The coordinates of the valid move

		try {
			// If the immediate disc is an enemy, perform the check
			if (myDiscs[r + modR][c + modC].discColor == p1.getEnemyColor()) {
				// Continue in the direction until a non-enemy disc is found
				while (myDiscs[r + modR][c + modC].discColor == p1.getEnemyColor()) {
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
					vc[0] = r;
					vc[1] = c;
					p1.addValidMove(vc); // add the coordinate to valid moves
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

	public void flip(int r, int c, String d) {
		int[] mods = getModifiers(d);
		int modR = mods[0]; // The Row Modifier, such as -1 for up
		int modC = mods[1]; // The col modifier, such as 1 for right

		// If the immediate disc is an enemy, perform the check
		if (myDiscs[r + modR][c + modC].discColor == p1.getEnemyColor()) {
			// Continue in the direction until a non-enemy disc is found
			while (myDiscs[r + modR][c + modC].discColor == p1.getEnemyColor()) {
				flipPiece(r + modR, c + modC, p1.getMyColor());
				if (p1.getMyColor() == 1) {
					whitePieces++;
					blackPieces--;
				}
				if (p1.getMyColor() == 2) {
					whitePieces--;
					blackPieces++;
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
	}

	/**
	 * Flips the Disc at [r][c] to the opposite color
	 * 
	 * @param r
	 * @param c
	 */
	public void flipPiece(int r, int c, int myColor) {
		myDiscs[r][c].changeColor(myColor);
	}

	/**
	 * Formats and prints one row of the Othello Board
	 * 
	 * @param r
	 * @author Kevin Gerstner
	 * @since 9/1/2017
	 */
	public void printRow(int r) {
		System.out.print(r + 1);
		printBar(bWidth); // Print a Bar to start
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
			printBar(bWidth);
		}
	}

	/**
	 * Prints out horizontal line dividers
	 * 
	 * @author Kevin Gerstner
	 */
	public void printLineDivider() {
		System.out.print("\n  ");
		for (int i = 0; i < bWidth; i++) {
			System.out.print("------");
		}
		System.out.print("\n");
	}

	/**
	 * Prints a vertical bar inbetween columns
	 * 
	 * @param boardWidth
	 */
	public void printBar(int boardWidth) {
		System.out.print('|');
	}

	/**
	 * Prints out the numbers at the top of the board
	 */
	public void printNums() {
		System.out.print("  ");
		for (int i = 0; i < bWidth; i++) {
			System.out.printf("  %d   ", i + 1);
		}
	}

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

}
