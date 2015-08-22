package ui;

import game.ActingOutOfTurnException;
import game.Board;
import game.Card;
import game.Coordinate;
import game.Game;
import game.GameStateModificationException;
import game.Player;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;


/**
 *
 * @author Badi James
 *
 */
public class TextUI {	
	private static final String DIVIDE = "=======================================================";
	public static final String NEWLINE = "\n > ";
	public static final String PROMPT = "Select an option: ";
	public static final String OPT_MOVE = "Roll to move";
	public static final String OPT_SUGGEST = "Make a suggestion";
	public static final String OPT_ACCUSE = "Make a final accusation";
	public static final String OPT_HAND = "Check hand";
	public static final String OPT_NOTES = "View detective notebook";
	public static final String OPT_END = "End turn";

	public static final String HELP_PREMISE = "What's the premise?";
	public static final String HELP_START = "How does the game start?";
	public static final String HELP_TURN = "How do you play a turn?";
	public static final String HELP_HYPOTH = "What's the difference between suggestions and accusations?";
	public static final String HELP_WINNING = "How do you win?";
	public static final String HELP_BODY = "Who is Dr Body?";
	public static final String HELP_EXIT = "Exit help.";
	
	private PrintStream OUT = System.out;
	private Scanner scan;

	static final String[] PLAYER_OPTIONS = {
		OPT_MOVE,
		OPT_SUGGEST,
		OPT_ACCUSE,
		OPT_HAND,
		OPT_NOTES,
		OPT_END
	};
	static final String[] HELP_OPTIONS = new String[] {
		HELP_PREMISE,
		HELP_START,
		HELP_TURN,
		HELP_HYPOTH,
		HELP_WINNING,
		HELP_BODY,
		HELP_EXIT
	};
	
	public TextUI(){
		scan = new Scanner(System.in);
	}

	/** Change the output stream (where text is printed) */	
	public void swapStream(PrintStream ps) {
		OUT = ps;
	}
	
	/**Prints the inputed text to System.out
	  *@param text Text to print out
	  */
	public void printText(String text){
		OUT.println();
		OUT.print(text);
	}

	/**Prints the given message to System.out then
	  *asks user for an integer. Can be used to get
	  *user to select an option etc
	  *@param question Message to print out
	  *@return User inputed integer
	  */
	public int askInt(String question){
		OUT.println();
		OUT.print(question + PROMPT + NEWLINE);
		return receiveInteger();
	}

	public void printArray(String[] textArray){
		OUT.println();
		for(int i = 0; i < textArray.length; i++) {
			OUT.printf("%d : %s\n", i+1, textArray[i]);
		}
	}

	public void printList(List<String> list) {
		OUT.println();
		int i = 0;
		for(String s : list) {
			OUT.printf("%d : %s\n", i+1, s);
			i++;
		}
	}

	
	public String toStringFromCollection(Collection<?> coll) {
		String string = "";
		int i = 0;
		for(Object o : coll) {
			if (i == coll.size() - 1) {
				string += o.toString();
			} else if (i == coll.size() - 2) {
				string += o.toString() +" and ";
			} else {
				string += o.toString() +", ";
			}
			i++;
		}
		return string;
	}

	/** Returns the given list as a grammatically-sound String, eg:
	 * "Miss Scarlet, pipe and conservatory".
	 */
	public String toStringFromCards(List<Card> list) {
		OUT.println();
		String string = "";
		int i = 0;
		for(Card s : list) {
			if (i == list.size() - 1) {
				string += s.getValue();
			} else if (i == list.size() - 2) {
				string += s.getValue() +" and ";
			} else {
				string += s.getValue() +", ";
			}
		}
		return string;
	}

	public void printDivide() {
		OUT.println();
		OUT.print(DIVIDE);
	}

	/**
	 * Prints the given question and then scans for an integer with a value between the
	 * given integers (inclusive). Repeats until user has entered a valid integer to System.in
	 *
	 * @param question String to print before scanning
	 * @param min Minimum bound of requested integer
	 * @param max Maximum bound of requested integer
	 * @return User inputed integer
	 */
	public int askIntBetween(String question, int min, int max){
		if (min > max) { throw new IllegalArgumentException(); }
		OUT.println();
		OUT.print(question + (min!=max? PROMPT:"") + NEWLINE);
		int answer = receiveInteger();

		while(answer < min || answer > max){
			OUT.printf("Answer has to be between %d and %d inclusive. Please re-enter: ", min, max);
			answer = receiveInteger();
		}
		return answer;
	}

	/**
	 * Scans System.in for an integer. If scan.next() can not be parsed to an integer, prints a
	 * message then scans again, repeating until a valid integer input has been enter
	 * @return Inputed integer
	 */
	private int receiveInteger(){
		int answer = Integer.MIN_VALUE;
		String input = scan.next();
		while(answer == Integer.MIN_VALUE){
			try{
				answer = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				OUT.println("Input not recognised as an integer. Please re-enter: ");
				input = scan.next();
			}
		}
		return answer;
	}

	public void clearScreen() {
		for (int i = 0; i < 100; i++) {
			OUT.println();
		}
	}

	/** Simple method to manage the 'do you want to print rules' option. */
	public void printWelcomeAndRules() {
		// welcome text
		printDivide();
		printText("\t\t      Cluedo \n       the classic detective game, digitised.".toUpperCase());
		printDivide();
		// then ask if the player wants to read rules or start game
		printArray(new String[] { "Rules","Start game"});
		int select = askIntBetween(NEWLINE, 1, 2)-1;
		if (select == 1) { return; } // if the player doesn't want to play, avoid the loop
		while (select < HELP_OPTIONS.length) {
			select = printRules(select);
		}
	}

	/** Prompts the user to select the number of players and then
	 * prompts them to select the character names that they will
	 * be using throughout the game.
	 */
	List<Player> selectCharacters() throws GameStateModificationException {
		int numPlayers = askIntBetween("How many players? (3 - 6 people can play.)",3,6);
		List<Player> players = new ArrayList<Player>();
		List<String> options = new ArrayList<String>();
		for (int i = 0; i < Card.CHARACTERS.length; i++) {
			options.add(Card.CHARACTERS[i]);
		}
		if (numPlayers == Card.CHARACTERS.length) {
			printText("Starting a game with all 6 characters.");
			for (int i = 0; i < Card.CHARACTERS.length;i++){
				players.add(new Player(Card.CHARACTERS[i],Card.CHARACTERS[i]));
			}
			return players;
		}
		for (int i = 0; i < numPlayers; i++) {
			printDivide();
			printList(options);
			/* if Miss Scarlet hasn't been selected yet, remind the user she always plays first. */
			int select = askIntBetween("Please add a character. "+
					(options.get(0)==Card.SCARLET?("("+ Card.SCARLET +" always goes first)."):""),1,options.size())-1;
			int realIndex = Card.indexOf(Card.Type.CHARACTER, options.get(select));
			players.add(new Player(Card.CHARACTERS[i],Card.CHARACTERS[realIndex]));
			options.remove(select);
		}
		return players;
	}

	/** Given a map of possible moves, prints these and returns the list of
	 * descriptions.
	 * @param possibleMoves
	 * @return
	 */
	public List<String> printPossibleMoves(Player p, Map<Coordinate, String> possibleMoves) {
		List<String> moveDescs = new ArrayList<String>();
		for (Entry<Coordinate,String> s : possibleMoves.entrySet()) {
			moveDescs.add("Move "+ relativeMovementString(p.position(),s.getKey())
					+".\n\t"+ s.getValue());
		}
		/* if the player was forcibly moved, they may not want to leave this room. */
		if (p.wasForced()) {
			moveDescs.add("Stay here.");
		}
		return moveDescs;
	}

	/** Prints the options available to the player.
	 * @param options List to cycle through and print.
	 * @param p*/
	public String getSelectedOption(List<String> options, Player p) {
		printList(options);
		OUT.println("\n\ncalledgetselectedoptions");
		return options.get(askIntBetween("",1,options.size())-1);
	}

	// --------------------------------------------------------------
	/* String manipulation methods */
	// --------------------------------------------------------------

	/** Prints the pages of rules. */
	private int printRules(int page) {
		if (page < 0 || page >= HELP_OPTIONS.length) { return page; }
		if (page == 0) { // Premise
			printText("-- "+ HELP_OPTIONS[0].toUpperCase());
			printText("You and your fellow detectives have been invited to stay at the home\n"
							+"of known recluse Dr. Body. However, one morning, you all wake only to be\n"
							+"informed that Dr. Body is nowhere to be found. Then word arrives that he\n"
							+"has been discovered some distance from his estate--murdered!\n"
							+"The body has clearly been moved and you are prevented from seeing it, \n"
							+"but the house has been left otherwise untouched so you may pursue your\n"
							+"investigations, and now it is up to you to solve this grisly crime.");
		} else if (page == 1) { // starting a game
			printText("-- "+ HELP_OPTIONS[1].toUpperCase());
			printText( "First, exit this help menu. How else will you start?\n"
							+ "Play begins once a number of players has been entered, between 3 and 6,\n"
							+ "and you have selected the character you want to play. \n"
							+ "All players then take turns moving, making suggestions and using the\n"
							+ "information they gather to deduce who killed Dr. Body. \n"
							+ "You're all competing against each other, so be sure to end your turn\n"
							+ "fully before you hand the controls to the next player!");
		} else if (page == 2) { // taking a turn
			printText("-- "+ HELP_OPTIONS[2].toUpperCase());
			printText("When your turn starts, you will be able to move your piece or check your\n"
						   + "notes. The game rolls between 1 and 6 spaces for you to move and works\n"
						   + "out the best squares for you to move to. Select one to move!\n"
						   + "If you can, move into a new room and make a suggestion to learn\n"
						   + "information about the murder from the other players.");
		} else if (page == 3) { // suggestions and accusations
			printText("-- "+ HELP_OPTIONS[3].toUpperCase());
			printText("Suggestions can be made when you are in any room.\n"
						   + "This room will be part of your suggestion. Choose the character and weapon\n"
						   + "to complete the suggestion, and then (following turn order) if a player\n"
						   + "holds a card that can refute any part of your hypothesis, they'll show it\n "
						   + "to you. (If you accuse a player, they'll move into the room too.)\n"
						   + "Accusations are how you win (or lose) the game. Once you are certain who,\n"
						   + "what and where, choose 'Make final accusation' and enter your results.\n"
						   + "If you are correct, you win! If you are wrong, you are out of the game (but\n"
						   + "your cards can still be used to refute other players' suggestions).");
		} else if (page == 4) { // winning
			printText("-- "+ HELP_OPTIONS[4].toUpperCase());
			printText("Whoever makes the first correct final accusation wins the game! If only\n"
						   + "one player is left, they win by default.");
		} else if (page == 5) { // dr body
			printText("-- "+ HELP_OPTIONS[5].toUpperCase());
			printText("Shame on you! Your dear friend? Slightly socially-awkward recluse?\n"
					       + "Invited you to stay here out of the kindness of his heart?"
					       + "\nYes, *that* Dr. Body!");
		}
		int select = HELP_OPTIONS.length;
		if (page < HELP_OPTIONS.length - 1) {
			printText("");
			printArray(HELP_OPTIONS);
			select = askIntBetween("",1,HELP_OPTIONS.length)-1;
		}
		return select;
	}

	public void printRules(){
		printText("-- "+ HELP_OPTIONS[0].toUpperCase());
		printText("You and your fellow detectives have been invited to stay at the home\n"
						+"of known recluse Dr. Body. However, one morning, you all wake only to be\n"
						+"informed that Dr. Body is nowhere to be found. Then word arrives that he\n"
						+"has been discovered some distance from his estate--murdered!\n"
						+"The body has clearly been moved and you are prevented from seeing it, \n"
						+"but the house has been left otherwise untouched so you may pursue your\n"
						+"investigations, and now it is up to you to solve this grisly crime.");
		printText("-- "+ HELP_OPTIONS[1].toUpperCase());
		printText( "First, exit this help menu. How else will you start?\n"
						+ "Play begins once a number of players has been entered, between 3 and 6,\n"
						+ "and you have selected the character you want to play. \n"
						+ "All players then take turns moving, making suggestions and using the\n"
						+ "information they gather to deduce who killed Dr. Body. \n"
						+ "You're all competing against each other, so be sure to end your turn\n"
						+ "fully before you hand the controls to the next player!");
		printText("-- "+ HELP_OPTIONS[2].toUpperCase());
		printText("When your turn starts, you will be able to move your piece or check your\n"
					   + "notes. The game rolls between 1 and 6 spaces for you to move and works\n"
					   + "out the best squares for you to move to. Select one to move!\n"
					   + "If you can, move into a new room and make a suggestion to learn\n"
					   + "information about the murder from the other players.");
		printText("-- "+ HELP_OPTIONS[3].toUpperCase());
		printText("Suggestions can be made when you are in any room.\n"
					   + "This room will be part of your suggestion. Choose the character and weapon\n"
					   + "to complete the suggestion, and then (following turn order) if a player\n"
					   + "holds a card that can refute any part of your hypothesis, they'll show it\n "
					   + "to you. (If you accuse a player, they'll move into the room too.)\n"
					   + "Accusations are how you win (or lose) the game. Once you are certain who,\n"
					   + "what and where, choose 'Make final accusation' and enter your results.\n"
					   + "If you are correct, you win! If you are wrong, you are out of the game (but\n"
					   + "your cards can still be used to refute other players' suggestions).");
		printText("-- "+ HELP_OPTIONS[4].toUpperCase());
		printText("Whoever makes the first correct final accusation wins the game! If only\n"
					   + "one player is left, they win by default.");
		printText("-- "+ HELP_OPTIONS[5].toUpperCase());
		printText("Shame on you! Your dear friend? Slightly socially-awkward recluse?\n"
				       + "Invited you to stay here out of the kindness of his heart?"
				       + "\nYes, *that* Dr. Body!");

	}

	/** Returns a random message, such that it completes the phrase:
	 * CHARACTER_NAME accuses ACCUSED_NAME of _____________ in the ROOM with the WEAPON */
	public String randomMurderDescription() {
		String[] m = { "bloody treachery",
					"violent actions",
					"unwanted attention",
					"killing Dr Body",
					"ending the life of Dr Body",
					"commiting murder",
					"homicide",
					"murder"
					};
		return m[Game.RNG.nextInt(m.length-1)+1];
	}

	/** Returns a random message, to be printed instead of a
	 * 'dead' player taking their turn. */
	public String randomDeathMessage() {
		String[] m = { "files some paperwork.",
					"is looking lost.",
					"is returning their Detective of the Month plaque.",
					"is wondering what went wrong.",
					"is making excuses to the police chief.",
					"is trying to regain some respect from the detectives.",
					"seems superfluous, really.",
					"is wishing the rest of you would hurry up and solve this murder.",
					"regrets no longer being allowed to say 'Elementary!'."
					};
		return m[Game.RNG.nextInt(m.length-1)+1];
	}

	/** Given a player and the entry, return a relative statement about the
	 * player's movement.
	 *
	 * @param from
	 * @param to
	 * @return
	 */
	public String relativeMovementString(Coordinate from, Coordinate to) {
		String s = "Moving";
		int pX = from.getX();
		int pY = from.getY();
		int cX = to.getX();
		int cY = to.getY();

		/** a complex series of if statements to determine the direction
		 * of movement.
		 */
		if (pY == cY) {
			s = "due ";
		} else if (Math.max(pY, cY) == cY) { // when the destination has the largest Y
			s = "south-";
		} else { // when the destination has the smallest Y
			s = "north-";
		}

		if (pX == cX) {
			/* if there's no change in X, we're going straight up or down,
			 * so replace whatever we've just done, given that we know the player is going
			 * north or south. */
			if (s.charAt(0) == 'N') {
				s = "due north";
			} else if (s.charAt(0) == 'S') {
				s = "due south";
			}
		}
		if (Math.max(pX, cX) == cX) {
			s += "east";
		} else if (Math.max(pX,cX) == pX) {
			s += "west";
		}
		return s;
	}

	/** Given a coordinate, return a string describing its relative
	 * position on the board.
	 *
	 * @param game TODO
	 * @param c
	 * @return
	 */
	public String relativeBoardPosString(int boardwidth, Coordinate c) {
		String s = "Moving";
		int pX = c.getX();
		int pY = c.getY();

		int division = (int)(boardwidth / 3 + 0.5);

		/** a complex series of if statements to determine the player's location. */
		if (pY < division) {
			s = "north";
		} else if (pY < division*2) {
			s = "central";
		} else {
			s = "south";
		}

		if (pX < division) {
			s += "-west";
		} else if (pX < division*2) {
			if (! (s.charAt(0) == 'c')) {
				s += "-and-central";
			}
		} else {
			s += "-east";
		}
		return s;
	}

	/** Prints some pretty text about the player's location when their
	 * turn begins.
	 * @param game TODO
	 * @param p
	 * @throws ActingOutOfTurnException
	 */
	public void printPlayerStatus(int boardwidth, Player p, String roomName) throws ActingOutOfTurnException {
		if (p.isPlaying()) {
			printText(p.getCharacter() +"'s turn begins in the "+
					relativeBoardPosString(boardwidth, p.position()) + " "+ roomName);
		} else { // if they're not playing, just print something interesting.
			printText(p.getCharacter() + " " + randomDeathMessage());
		}
	}

	public List<String> printPlayerOptions(String room, Player p) {
		List<String> options = new ArrayList<String>();
		/* if the player is in a room, give them the option to make a suggestion.
		 * Otherwise limit their options to making an accusation, viewing their
		 * cards or ending the turn. */
		options.add(OPT_HAND);
		options.add(OPT_NOTES);
		if (p.hasMoved()) {
			if (!room.equals(Board.HALLWAYSTRING)) {
				options.add(OPT_SUGGEST);
			}
			options.add(OPT_ACCUSE);
		} else {
			options.add(OPT_MOVE);
		}
		if (p.hasMoved()) {
			options.add(OPT_END);
		}
		printList(options);
		return options;
	}

	/** This repeatedly calls textUI.printArray and createNotesToPrint
	 * to print the player's detective notebook.
	 * @param p
	 * @throws ActingOutOfTurnException
	 */
	public void viewNotebook(Player p) throws ActingOutOfTurnException {
		printDivide();
		printArray(createNotesToPrint(p, Card.Type.CHARACTER));
		printArray(createNotesToPrint(p, Card.Type.WEAPON));
		printArray(createNotesToPrint(p, Card.Type.ROOM));
		printDivide();
	}

	/** Creates an array of the cards of a given type and, for each card,
	 * adds this to an array of strings, with empty open brackets if
	 * unproven and an X if proven innocent.
	 * @param p
	 * @param t TODO
	 */
	public String[] createNotesToPrint(Player p, Card.Type t) {
		String[] printing;
		String[] from;
		if (t == Card.Type.CHARACTER) {
			printing = new String[Card.CHARACTERS.length];
			from = Card.CHARACTERS;
		} else if (t == Card.Type.WEAPON) {
			printing = new String[Card.WEAPONS.length];
			from = Card.WEAPONS;
		} else if (t == Card.Type.ROOM) {
			printing = new String[Card.ROOMS.length];
			from = Card.ROOMS;
		} else {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < printing.length; i++) {
			// if the player is proven innocent, prepend (X), else ( )
			printing[i] = (p.isInnocent(t,from[i]) ? "(X)":"( )") + " "
				+ capitalise(from[i]);
		}
		return printing;
	}

	/** Capitalises the first word in the given string. Assumes the string
	 * is valid and the first character is alphabetic.
	 * @param string
	 * @return
	 */
	public String capitalise(String string) {
		return string.substring(0, 1).toUpperCase() + string.substring(1, string.length());
	}
}
