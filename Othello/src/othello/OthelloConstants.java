package othello;

public class OthelloConstants {
	public static final int ARRAY_W = 8;
	public static final int ARRAY_H = 8;
	
	final String asciiName = "-----------------------------------------------------------------\n"
			+ " _______ _________          _______  _        _        _______ \n"
			+ "(  ___  )\\__   __/|\\     /|(  ____ \\( \\      ( \\      (  ___  )\n"
			+ "| (   ) |   ) (   | )   ( || (    \\/| (      | (      | (   ) |\n"
			+ "| |   | |   | |   | (___) || (__    | |      | |      | |   | |\n"
			+ "| |   | |   | |   |  ___  ||  __)   | |      | |      | |   | |\n"
			+ "| |   | |   | |   | (   ) || (      | |      | |      | |   | |\n"
			+ "| (___) |   | |   | )   ( || (____/\\| (____/\\| (____/\\| (___) |\n"
			+ "(_______)   )_(   |/     \\|(_______/(_______/(_______/(_______)\n"
			+ "_________________________________________________________________\n";
	final String instructions = "Welcome to Othello! \n\nINSTRUCTIONS:"
			+ "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
			+ "Please enter the Coordinates, Row First, then Column.\n"
			+ "EX: 1,2 = Row 1, Col 2\nYou may pass by typing 'pass'.\n"
			+ "You can view the score by typing 'score'.\n"
			+ "See your possible moves by typing 'help'.\n"
			+ "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
	public void printInstructions() {
		System.out.println(instructions);
	}
	public void printAsciiName() {
		System.out.println(asciiName);
	}
}
