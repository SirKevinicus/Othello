package othello;

public class OthelloConstants {
	public static final int ARRAY_W = 4;
	public static final int ARRAY_H = 4;

	final static String asciiName = "_________________________________________________________________\n"
			+ " _______ _________          _______  _        _        _______ \n"
			+ "(  ___  )\\__   __/|\\     /|(  ____ \\( \\      ( \\      (  ___  )\n"
			+ "| (   ) |   ) (   | )   ( || (    \\/| (      | (      | (   ) |\n"
			+ "| |   | |   | |   | (___) || (__    | |      | |      | |   | |\n"
			+ "| |   | |   | |   |  ___  ||  __)   | |      | |      | |   | |\n"
			+ "| |   | |   | |   | (   ) || (      | |      | |      | |   | |\n"
			+ "| (___) |   | |   | )   ( || (____/\\| (____/\\| (____/\\| (___) |\n"
			+ "(_______)   )_(   |/     \\|(_______/(_______/(_______/(_______)\n"
			+ "_________________________________________________________________\n";
	final static String instructions = "Welcome to Othello! \n\nINSTRUCTIONS:"
			+ "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
			+ "Please enter the Coordinates, Row First, then Column.\n"
			+ "EX: 1,2 = Row 1, Col 2\nYou may pass by typing 'pass'.\n" + "You can view the score by typing 'score'.\n"
			+ "See your possible moves by typing 'help'.\n" + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";

	final static String gamemodeSelection = "SELECT YOUR GAME TYPE: \n" + "-----------------------\n"
			+ "1P: 1 Player vs. CP\n" + "2P: Player vs. Player\n" + "SIM: Run Simulation\n" + "-----------------------";

	final String whiteWins = "           ______________________________________\n"
			+ "  ________|                                      |_______\n"
			+ "  \\       |   W  H  I  T  E      W  I  N  S  !   |      /\n"
			+ "   \\      |   _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _    |     /\n"
			+ "   /      |______________________________________|     \\\n"
			+ "  /__________)                                (_________\\";

	final String blackWins = "           ______________________________________\n"
			+ "  ________|                                      |_______\n"
			+ "  \\       |   B  L  A  C  K      W  I  N  S  !   |      /\n"
			+ "   \\      |   _______________________________    |     /\n"
			+ "   /      |______________________________________|     \\\n"
			+ "  /__________)                                (_________\\";
	
	final String itsATie = "           ______________________________________\n"
			+ "  ________|                                      |_______\n"
			+ "  \\       |   I  T  S       A      T  I  E  !    |      /\n"
			+ "   \\      |   ...............................    |     /\n"
			+ "   /      |______________________________________|     \\\n"
			+ "  /__________)                                (_________\\";
	
	final String divider = " _  _  _  _  _  _  _  _  _  _  _  _  _  _  _  _  _  _  _  _  _  _  _  _\n" + 
			"( )( )( )( )( )( )( )( )( )( )( )( )( )( )( )( )( )( )( )( )( )( )( )( )\n" + 
			"-O--T--H--E--L--L--O--X--O--T--H--E--L--L--O--X--O--T--H--E--L--L--O--X-\n" + 
			"(_)(_)(_)(_)(_)(_)(_)(_)(_)(_)(_)(_)(_)(_)(_)(_)(_)(_)(_)(_)(_)(_)(_)(_)\n";

	public void printInstructions() {
		System.out.println(instructions);
	}

	public static void printAsciiName() {
		System.out.println(asciiName);
	}

	public void printGamemodeSelection() {
		System.out.println(gamemodeSelection);
	}

	public void printWhiteWins() {
		System.out.println(whiteWins);
	}

	public void printBlackWins() {
		System.out.println(blackWins);
	}
	
	public void printTie() {
		System.out.println(itsATie);
	}
	public void printDiv() {
		System.out.println(divider);
	}
}
