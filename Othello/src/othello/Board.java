package othello;

public class Board {
	// REFERENCES

	Disc[][] myDiscs;

	// BOARD VARIABLES
	private int bWidth = OthelloConstants.ARRAY_W;
	private int bHeight = OthelloConstants.ARRAY_H;
	Boolean boardFull = false;
	Boolean movesLeft = true;
	int whitePieces = 2;
	int blackPieces = 2;

	public Board() {
		myDiscs = new Disc[bWidth][bHeight];
	}

	/**
	 * Creates a Disc for each space in the grid Places the first 4 discs in the
	 * middle
	 * 
	 * @author sirkevinicus
	 * @since 9/1/2017
	 */
	public void initGrid() {
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
	 * Updates the Grid by changing the Disc at [r][c] to color and then prints the
	 * grid
	 * 
	 * @param r
	 * @param c
	 * @param color
	 * @author Kevin Gerstner
	 * @since 9/1/2017
	 */
	public void updateGrid(int r, int c, int color) {
		myDiscs[r][c].changeColor(color);
		printGrid();
	}

	/**
	 * Prints the Othello Grid
	 * 
	 * @author Kevin Gerstner
	 * @since 9/1/2017
	 */
	public void printGrid() {
		printNums();
		printLineDivider();
		// PRINT ALL THE ROWS
		for (int row = 0; row < bWidth; row++) {
			printRow(row);
			printLineDivider();
		}
	}

	/**
	 * Formats and prints one row of the Othello Grid
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

	public void updateDiscNum(int color) {
		if (color == 1) {
			whitePieces++;
			blackPieces--;
		}
		if (color == 2) {
			whitePieces--;
			blackPieces++;
		}
	}

	/**
	 * Checks if the Grid has no more empty spaces.
	 * 
	 * @return true or false
	 */
	public Boolean checkIfBoardFull() {
		for (int r = 0; r < bHeight; r++) {
			for (int c = 0; c < bWidth; c++) {
				if (!myDiscs[r][c].isActive)
					return false;
			}
		}
		return true;
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
	 * Checks if there are only white discs left
	 * 
	 * @return true or false
	 */
	public Boolean checkIfWhiteOnly() {
		for (int r = 0; r < bHeight; r++) {
			for (int c = 0; c < bWidth; c++) {
				if (myDiscs[r][c].discColor == 2) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks if there are only black discs left
	 * 
	 * @return true or false
	 */
	public Boolean checkIfBlackOnly() {
		for (int r = 0; r < bHeight; r++) {
			for (int c = 0; c < bWidth; c++) {
				if (myDiscs[r][c].discColor == 1) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Prints out horizontal line dividers
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
			System.out.printf("  %d   ", i+1);
		}
	}

}
