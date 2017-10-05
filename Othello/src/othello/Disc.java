package othello;

public class Disc {

	public Boolean isActive;		//Is there a Disc here?
	public Integer discColor = 0;	//Colors: 0=null, 1=white, 2=black
	public int rCoor;	//Row coord of this disc
	public int cCoor;	//Col coord of this disc

	/**
	 * CONSTRUCTOR
	 * @param r
	 * @param c
	 * @param dt
	 */
	public Disc(int r, int c, String dt) {
		//dt: color of this disc, as a string
		rCoor = r;
		cCoor = c;
		isActive = true;
		switch (dt) {
		case "white":
			discColor = 1;
			break;
		case "black":
			discColor = 2;
			break;
		default:
			System.err.println("!!!The disc type is INVALID!!!");
		}
	}

	/**
	 * CONSTRUCTOR
	 * @param r
	 * @param c
	 */
	public Disc(int r, int c) {
		rCoor = r;
		cCoor = c;
		isActive = false;
		discColor = 0;
	}

	/**
	 * Either adds a disc if there isn't one or changes the color of the disc to the opposite.
	 * @param int color
	 * @author Kevin Gerstner
	 * @since 9/1/2017
	 */
	public void changeColor(int color) {
		if (isActive == false) { // If there is no disc in this location
			this.isActive = true; // Put a disc in this location
			if (color == 2) // If it's black's turn,
				this.discColor = 2; // Add a black disc
			else
				this.discColor = 1; // If white turn, add white disc
		} else { // If there is a disc in this location
			if (this.discColor == 1) // Change White to Black
				this.discColor = 2;
			else // Change Black to White
				this.discColor = 1;
		}
	}
	
	/**
	 * Returns the disc color
	 * @return discColor
	 * @author Kevin Gerstner
	 * @since 9/1/2017
	 */
	public int getDiscColor() {
		return discColor;
	}
	
	/**
	 * Returns the Row Coordinate of this disc
	 * @return rCoor
	 * @author Kevin Gerstner
	 * @since 9/1/2017
	 */
	public int getRCoor() {
		return rCoor;
	}
	
	/**
	 * Returns the Col Coordinate of this disc
	 * @return cCoor
	 * @author Kevin Gerstner
	 * since 9/1/2017
	 */
	public int getCCoor() {
		return cCoor;
	}
}
