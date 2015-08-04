package game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.management.InvalidAttributeValueException;

/** The Game class is used to model the Cluedo game, including the
 * list of players, turn progression and ending the game.
 *
 * It relies on the TextUI class to print messages to the console
 * and return user input.
 *
 * If this were a better design: 	
 * 	- most of Game's print methods would be in printUI instead.
 *  - whatever printed the messages would read these from a file instead. 
 *
 * @author mckayvick
 *
 */
public class Game {
	private static final Random R = new Random(System.currentTimeMillis());
	private static final String NEWLINE = "\n > ";
	private static final String PROMPT = "Select an option: ";
	private static final String[] PLAYER_OPTIONS = {
		"Make a suggestion",
		"Make a final accusation",
		"Check hand",
		"View detective notebook",
		"End turn"
	};
	private static final String[] HELP_OPTIONS = new String[] {
		"What's the premise?",
		"How does the game start?",
		"How do you play a turn?",
		"What's the difference between suggestions and accusations?",
		"How do you win?",
		"Who is Dr Body?",
		"Exit help."
	};

	public final Board BOARD = new Board();
	public final TextUI textUI = new TextUI();

	private List<Player> players;
	private Hypothesis guilty;
	private int activePlayer = -1; /* used to check when play has begun also, instead
										of creating and setting an additional field. 
										if activePlayer < 0, game has not really started
										and we can keep adding players. */


	/** Constructs a new game, prints the rules.
	 *
	 * @param players
	 */
	public Game() {
		this.players = new ArrayList<Player>();
	}

	/** This is the normal method that should be called to start a game.
	 * It asks the user how many players will be in this game, then
	 * creates the player list, sorts it, and tries to play.
	 * @throws GameStateModificationException 
	 * @throws ActingOutOfTurnException 
	 */
	public void startGame() throws GameStateModificationException, ActingOutOfTurnException {
		printWelcomeAndRules();
		int numPlayers = textUI.askIntBetween("How many players? (3 - 6 people can play.)"+NEWLINE,3,6);
		selectCharacters(numPlayers);
		Collections.sort(players);
		try {
			playGame();
		} catch (InvalidAttributeValueException e) {
			textUI.printText("Uh-oh! Invalid number of players.");
			e.printStackTrace();
			System.exit(1);
		}
	}

	/** Simple method to manage the 'do you want to print rules' option. */
	private void printWelcomeAndRules() {
		// welcome text
		textUI.printDivide();
		textUI.printText("\t\t      Cluedo \n       the classic detective game, digitised.".toUpperCase());
		textUI.printDivide();
		// then ask if the player wants to read rules or start game 
		textUI.printArray(new String[] { "Rules","Start game"});
		int select = textUI.askIntBetween(NEWLINE, 1, 2)-1;
		if (select == 1) { return; } // if the player doesn't want to play, avoid the loop
		while (select < HELP_OPTIONS.length) {
			select = printRules(select);
		}
	}

	/** Given the number of users playing, prints the remaining
	 * character options and repeatedly asks them to select which characters
	 * they would like to play.
	 *
	 * @param numPlayers
	 * @throws GameStateModificationException If this is called after play begins. 
	 */
	private void selectCharacters(int numPlayers) throws GameStateModificationException {
		if (players.size() != 0 && activePlayer >= 0) { throw new GameStateModificationException(); }
		List<String> options = new ArrayList<String>();
		for (int i = 0; i < Card.CHARACTERS.length; i++) {
			options.add(Card.CHARACTERS[i]);
		}
		if (numPlayers == Card.CHARACTERS.length) {
			textUI.printText("Starting a game with all 6 characters.");
			for (int i = 0; i < Card.CHARACTERS.length;i++){
				addPlayer(i);
			}
			return;
		}
		for (int i = 0; i < numPlayers; i++) {
			textUI.printDivide();
			textUI.printList(options);
			/* if Miss Scarlet hasn't been selected yet, remind the user she always plays first. */
			int select = textUI.askIntBetween("Please add a character. "+
					(options.get(0)==Card.SCARLET?("("+ Card.SCARLET +" always goes first)."):"") + NEWLINE,1,options.size())-1;
			int realIndex = indexOf(Card.Type.CHARACTER, options.get(select));
			addPlayer(realIndex);
			options.remove(select);
		}
	}

	/** Add a player to the game. 
	 * @param select
	 * @throws GameStateModificationException If this method is called after play begins.
	 */
	public void addPlayer(int select) throws GameStateModificationException {
		if (activePlayer < 0) {
			players.add(new Player(Card.CHARACTERS[select]));
		} else {
			throw new GameStateModificationException();
		}
	}


	/** This is how we play a game of Cluedo!
	 * @throws InvalidAttributeValueException
	 * @throws ActingOutOfTurnException 
	 */
	public void playGame() throws InvalidAttributeValueException, ActingOutOfTurnException {
		if (players == null) {
			throw new InvalidAttributeValueException("Can't start a game without players!");
		}
		if (guilty == null) {
			initialiseDeck();
		}
		activePlayer = 0;
		int roundCounter = 1;
		
		/** the game plays at least one turn. */
		do {
			textUI.printText("\t\tRound "+roundCounter+" begins.");
			textUI.printDivide();
			for (activePlayer = 0; activePlayer < players.size(); activePlayer++) {
				/** whenever the turn begins, get the player taking a turn and print some nice-looking stuff.*/
				Player p = players.get(activePlayer);
				/* we can throw away the confirm value-- we just want the UI to wait until the next player's ready.*/
				textUI.askIntBetween("It's "+ p.getName() +"'s turn now! "+p.getName()+", are you there? (Enter 1 to confirm.)",1,1);
				/* once the player has confirmed */
				if (p.isPlaying()) {
					textUI.printText(p.getName() +"'s turn begins in the "+ 
							relativeBoardPosString(p.position()) + " "+ BOARD.getRoom(p.position()));
					playerMoves(p);
					showPlayerOptions(p);
				} else { // if they're not playing, just print something interesting.
					textUI.printText(p.getName() + " " + randomDeathMessage());
				}
				if (!isPlaying()) {
					break;
				}
				textUI.askIntBetween("(Enter 1 to end your turn.) ",1,1);
				textUI.clearScreen();
				textUI.printDivide();
			}
			roundCounter++;
		} while (this.isPlaying());
		
		/* once the game is over, work out who won and print the winning message.*/
		Player winner = null;
		for (Player p:players) {
			if (p.isPlaying() && winner == null) {
				winner = p;
			} else if (p.isPlaying() && winner != null) {
				throw new IllegalStateException();
			}
		}
		if (winner == null) { throw new IllegalStateException(); }
		textUI.printText("Good game, all. "+ winner.getName()+" is the winner.");
	}

	/** Method for resolving a player's movement phase.
	 * The number of spaces that can be moved is calculated and options
	 * for movement printed; based on the player's selection,
	 * the player's position is updated accordingly.
	 *
	 * @param p
	 */
	public void playerMoves(Player p) {
		/* first, roll the dice */
		int roll = R.nextInt(6) + 1;
		textUI.printText(p.NAME +" rolls a "+roll+".");
		/** if the player was forced, print some acknowledging message */
		if (players.get(activePlayer).wasForced()) {
			textUI.printText("The questioning of "+ p.getName() +" is complete. \n (You are able to move or stay in the "+ BOARD.getRoom(p.position()) +".)");
		}
		textUI.printText("---------------------------------");
		/* get the map of options a player has and put them into an array */
		Map<Coordinate,String> moves = BOARD.possibleMoves(p.position(), roll);
		List<String> moveDescs = new ArrayList<String>();
		for (Entry<Coordinate,String> s : moves.entrySet()) {
			moveDescs.add("Move "+ relativeMovementString(p.position(),s.getKey()) 
					+".\n\t"+ s.getValue());
		}
		/* if the player was forcibly moved, they may not want to leave this room. */
		if (players.get(activePlayer).wasForced()) {
			moveDescs.add("Stay here.");
		}
		/* then we can print these options and ask the user to select an option*/
		textUI.printList(moveDescs);

		int userChoice = textUI.askIntBetween(PROMPT,1,moveDescs.size())-1;
		for (Entry<Coordinate,String> s : moves.entrySet()) {
			/* when the user-selected move is found, move the player */
			if (s.getValue().equalsIgnoreCase(moveDescs.get(userChoice))) {
				toggleOccupied(p.position(),s.getKey());
				p.move(s.getKey());
			}
		}
	}

	/** Given a player and the entry, return a relative statement about the 
	 * player's movement. 
	 * @param 
	 * @param coord
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
			s = "south-";
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
		} else if (Math.max(pX, cX) == cX) {
			s += "east";
		} else {
			s += "west";
		}
		return s;
	}

	/** Once a player has moved, set their current coordinate to occupied if it is
	 * in a hallway. If their destination coordinate is in the hallway, set this
	 * as occupied.
	 *
	 * This implementation only works as long as the Coordinate goTo was
	 * given as a result of calling BOARD.possibleMoves() and the coordinate
	 * comeFrom was given by a player's position() method. If this is not the case,
	 * this method is not safe to use.
	 *
	 * The only exception is when a player loses and their token is removed from
	 * the board: then the method is called with goTo == null.
	 *
	 * @param comeFrom The coordinate the player is moving from, as given by
	 * their position() method.
	 * @param goTo The coordinate they are moving to, as selected from the result
	 * of BOARD.possibleMoves().
	 */
	private void toggleOccupied(Coordinate comeFrom,Coordinate goTo) {
		if (comeFrom != null && BOARD.getRoom(comeFrom).equals(Board.HALLWAYSTRING)) {
			BOARD.toggleOccupied(comeFrom);
		}
		if (goTo != null && BOARD.getRoom(goTo).equals(Board.HALLWAYSTRING)) {
			BOARD.toggleOccupied(goTo);
		}
	}

	/** Prints the control options available to a player once their movement phase
	 * is complete.
	 *
	 * If they are in a room, the first option will be to make a suggestion.
	 * Otherwise, the first option is to make an accusation.
	 *
	 * @param p Player whose options are to be displayed.
	 * @throws ActingOutOfTurnException 
	 */
	public void showPlayerOptions(Player p) throws ActingOutOfTurnException {
		String[] options;
		/* if the player is in a room, give them the option to make a suggestion.
		 * Otherwise limit their options to making an accusation, viewing their
		 * cards or ending the turn. */
		if (BOARD.getRoom(p.position()).equals(Board.HALLWAYSTRING)) {
			options = new String[]{ PLAYER_OPTIONS[1], PLAYER_OPTIONS[2], PLAYER_OPTIONS[3], PLAYER_OPTIONS[4] };
		} else {
			options = PLAYER_OPTIONS;
		}
		textUI.printArray(options);
		int option = textUI.askIntBetween(PROMPT,1,options.length) - 1; // subtract one because we've asked for a number between 1 and length, and we want 0-to-length-minus-1
		/* do the selected option! */
		// if they want to make a guess...
		if (option == 0 && options[option].equals(PLAYER_OPTIONS[0])) {
			textUI.printText("MAKE GUESS");
			testHypothesis(p,null);
		}
		// if they want to make a final accusation...
		else if ((option == 0 || option == 1) && options[option].equals(PLAYER_OPTIONS[1])) {
			textUI.printText("MAKE FINAL ACCUSATION");
			textUI.printText("Are you sure you want to make your final accusation?");
			textUI.printArray(new String[] {"Yes, I would like to make my final choice.","No! Stop! I misclicked! Help!"});
			int select = textUI.askIntBetween(PROMPT+NEWLINE, 1, 2)-1;
			if (select == 0) {
				testAccusation(p);
			} else {
				showPlayerOptions(p);
			}
		} 
		// if they want to view their hand...
		else if ((option == 1 || option == 2) && options[option].equals(PLAYER_OPTIONS[2])) {
			viewHand(p, false);
			showPlayerOptions(p);
		}
		// if they want to open their notebook...
		else if ((option == 2 || option == 3) && options[option].equals(PLAYER_OPTIONS[3])) {
			textUI.printText("VIEW NOTEBOOK");
			viewNotebook(p);
			showPlayerOptions(p);
		}
	}

	/** This method is called when a player wants to make an accusation or
	 * a suggestion: each block prints part of their detective notebook and
	 * waits for them to select which character, weapon and room they would
	 * like to test with this hypothesis.
	 *
	 * @param p The player making the hypothesis
	 * @return A hypothesis to be tested against the hands of the other players
	 * or the guilty cards.
	 */
	private Hypothesis makeHypothesis(Player p, boolean isFinalAccusation) {
		Hypothesis h = new Hypothesis();
		textUI.printText(p.getName() +" is considering the evidence...");

		textUI.printText("These are the possible guilty characters:");
		textUI.printArray(createNotesToPrint(p, Card.Type.CHARACTER));
		int select = textUI.askIntBetween(PROMPT+NEWLINE,1,Card.CHARACTERS.length)-1;
		try {
			h.setCharacter(new Card(Card.Type.CHARACTER,Card.CHARACTERS[select]));
		} catch (IllegalAccessException e) {
			// this isn't actually possible
		}

		textUI.printText("These are the possible murder weapons:");
		textUI.printArray(createNotesToPrint(p, Card.Type.WEAPON));
		select = textUI.askIntBetween(PROMPT+NEWLINE,1,Card.WEAPONS.length)-1;
		try {
			h.setWeapon(new Card(Card.Type.WEAPON,Card.WEAPONS[select]));
		} catch (IllegalAccessException e) {
			// this isn't possible
		}

		Card roomCard;
		if (isFinalAccusation) {
			textUI.printText("These are the possible murder locations:");
			textUI.printArray(createNotesToPrint(p, Card.Type.ROOM));
			select = textUI.askIntBetween(PROMPT+NEWLINE,1,Card.ROOMS.length)-1;
			roomCard = new Card(Card.Type.ROOM,Card.ROOMS[select]);
		} else {
			String room = BOARD.getRoom(p.position());
			roomCard = new Card(Card.Type.ROOM,room);
			textUI.printText("In the "+ room +".");
			for (int i=0; i < players.size(); i++) {
				if (players.get(i).equalsName(h.getCharacter().getValue())) {
					players.get(i).forciblyMove(p.position());
				}
			}
		}
		try {
			h.setRoom(roomCard);
		} catch (IllegalAccessException e) {
			// this isn't possible
		}
		/* this statement prints the accusation text, according to the format:
		 * NAME (accuses ACCUSED of murder)|(theorises it was [themself|ACCUSED]) with the WEAPON in the ROOM.
		 */
		String accused = null;
		if (h.getCharacter().getValue().equals(p.getName())) {
			accused = "themself";
		} else { accused = h.getCharacter().getValue(); }
		textUI.printText(p.getName() + ( isFinalAccusation?
						" accuses "+ accused +" of "+ randomMurderDescription()
						:" theorises that it was "+	accused )
					+" with the "+ h.getWeapon() +" in the "+ h.getRoom() +".");
		return h;
	}

	/** This method is used when a player p wants to make a suggestion about
	 * who they suspect the murderer is.
	 *
	 * Game uses the player to the active player's left as the start of the round,
	 * then goes around the players asking if they are able to refute the hypothesis.
	 *
	 * This method does not allow a player to select which of their cards is used
	 * to refute the hypothesis, which means the asking player will be shown the same
	 * card every time they make the same hypothesis.
	 *
	 * @param p
	 */
	private boolean testHypothesis(Player p, Hypothesis h) {
		if (h == null) {
			h = makeHypothesis(p,false);
		}

		/* if the hypothesis requires a player, find and move them here. */
		Player accused = getPlayer(h.getCharacter());
		if (accused != null && !(accused.equals(p))) {
			accused.forciblyMove(p.position());
			textUI.printText(accused.getName() +" is brought to the "+ BOARD.getRoom(p.position()) +" to be questioned.");
		}

		/* now, go through all the players and check their hands for cards to refute
		 * the player's hypothesis.
		 */
		int i = (activePlayer+1) % players.size();;
		do {
			List<Card> l = players.get(i).refuteHypothesis(h);
			if (l.size() > 0) {
				// the ternary check just says: if this is new information to the asking player, mention that fact in the printout.
				textUI.printText(players.get(i).getName() +" shares some "+ (p.isInnocent(l.get(0))? "":"new ") +"evidence with "+ p.getName() +".");
				p.vindicate(l.get(0));
				return false;
			}
			i = (i+1) % players.size();
		} while (i!=activePlayer);
		textUI.printText("The evidence was not forthcoming.");
		return true;
	}

	/** If the given Card matches a player in the game, return them. */
	public Player getPlayer(Card character) {
		for (Player p : players) {
			if (p.getName().equals(character.getValue())) {
				return p;
			}
		}
		return null;
	}

	/** Iff the given Card matches a player in the game, return true. */
	public boolean isPlayer(Card character) {
		for (Player p : players) {
			if (p.getName().equals(character.getValue())) {
				return true;
			}
		}
		return false;
	}



	/** The player has chosen to make an accusation! Assemble their
	 * hypothesis, see if it equals the guilty one. If not, end them.
	 * Otherwise, they win. The player list is cleared and the game
	 * ends.
	 *
	 * @param p
	 */
	private boolean testAccusation(Player p) {
		Hypothesis h = makeHypothesis(p,true);
		if (!h.equals(guilty)) {
			textUI.printText(p.getName()+"'s guess was incorrect.");
			p.kill();
			toggleOccupied(p.position(),null);
			return false;
		} else {
			textUI.printText("Success! "+ p.getName() +" has made a correct accusation and the guilty party will be brought to justice.");
			if (p.getName().equals(h.getCharacter().getValue())) {
				textUI.printText("('Accusation' sounds kinder than 'loud, weeping confession into "+ players.get( (activePlayer + 1) % players.size()).getName() +"'s arms.)");
			}
			for (Player ps : players) {
				if (p != ps) {
					ps.kill();
				}
			}
			return true;
		}
	}


	/** This repeatedly calls textUI.printArray and createNotesToPrint
	 * to print the player's detective notebook.
	 * @param p
	 * @throws ActingOutOfTurnException 
	 */
	private void viewNotebook(Player p) throws ActingOutOfTurnException {
		if (activePlayer < 0 || players.indexOf(p) != activePlayer) {
			throw new ActingOutOfTurnException();
		}
		textUI.printDivide();
		textUI.printArray(createNotesToPrint(p,Card.Type.CHARACTER));
		textUI.printArray(createNotesToPrint(p,Card.Type.WEAPON));
		textUI.printArray(createNotesToPrint(p,Card.Type.ROOM));
		textUI.printDivide();
	}


	/** Creates an array of the cards of a given type and, for each card,
	 * adds this to an array of strings, with empty open brackets if
	 * unproven and an X if proven innocent.
	 * @param p
	 */
	private String[] createNotesToPrint(Player p, Card.Type t) {
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
				+ capitaliseString(from[i]);
		}
		return printing;
	}

	/** Capitalises the first word in the given string. Assumes the string
	 * is valid and the first character is alphabetic. 
	 * @param string
	 * @return
	 */
	private String capitaliseString(String string) {
		return string.substring(0, 1).toUpperCase() + string.substring(1, string.length());
	}

	/** This method selects the guilty character, weapon and room and deals
	 * the remaining cards to the players.
	 */
	public void initialiseDeck() {
		initialiseDeck(null,null,null);
	}

	/** This method takes the guilty character, weapon and room and uses
	 * this information to create the guilty Hypothesis, then deals
	 * the remaining cards to the players.
	 * Leave any parameter(s) as null to select randomly.
	 *
	 * @param c Guilty character
	 * @param w Guilty weapon
	 * @param r Guilty room
	 */
	public void initialiseDeck(String c, String w, String r) {
		// for each null value, pick a random card to be guilty
		if (c == null) {
			int i = (int) (Math.random()*Card.CHARACTERS.length);
			c = Card.CHARACTERS[i];
		}
		if (w == null) {
			int i = (int) (Math.random()*Card.WEAPONS.length);
			w = Card.WEAPONS[i];
		}
		if (r == null) {
			int i = (int) (Math.random()*Card.ROOMS.length);
			r = Card.ROOMS[i];
		}

		// put guilty cards into fields
		guilty = new Hypothesis(new Card(Card.Type.CHARACTER, c),
								new Card(Card.Type.WEAPON, w),
								new Card(Card.Type.ROOM, r));

		ArrayList<Card> deck = createNewDeck(c, w, r); // creates, shuffles a new deck of cards
		int remainder = (Card.DECKSIZE-3) % players.size();
		int handSize = (Card.DECKSIZE-3-remainder) / players.size();

		// add cards to each player's hand
		int cardIdx = 0;
		//textUI.printText((double)(Card.DECKSIZE-3)/players.size() +" cards between "+ players.size() +" and "+deck.size()+" cards, "+remainder+" remaining, to deal between "+ handSize +"-sized hands.");

		for (Player p : players) {
			//textUI.printText(p.getName() +" starts at "+ cardIdx);
			for (int i = 0; i < handSize; i++) {
			//	textUI.printText("\t"+ cardIdx+" "+ deck.get(cardIdx).toString());
				p.giveCard(deck.get(cardIdx));
				cardIdx ++;
			}
		}


		// if there are any cards remaining, print a message
		if (remainder != 0) {
			//textUI.printText(handSize +" * "+ players.size() +"("+ handSize * players.size() +") != "+ Card.DECKSIZE);
			showSpareCards(deck.subList(cardIdx, deck.size()));
			// then ensure every player has these cards marked off...
			for (Player p : players) {
				for (Card spareCard : deck.subList(cardIdx,deck.size())) {
					p.vindicate(spareCard);
				}
			}
		}
	}

	/** If there are spare cards, prints a message informing the user. */
	private void showSpareCards(List<Card> subList) {
		if (subList.size() < 1) {return;}
		String list = "";
		for (int i = 0; i < subList.size(); i++) {
			if (i < subList.size()-2) {
				list += subList.get(i) +", ";
			} else if (i < subList.size()-1){
				list += subList.get(i) +" ";
			} else {
				list += subList.get(i);
			}
		}
		textUI.printText("Everyone knows that "+ subList.toString() +" had nothing to do with the murder.");
	}

	/** Given the values of the guilty cards, this method assembles a shuffled list
	 * containing the remaining innocent cards and returns it.
	 *
	 * @param c One of the CHARACTER strings defined in Cards
	 * @param w One of the WEAPON strings
	 * @param r One of the ROOM strings.
	 * @return A shuffled list of the remaining cards, ready to be dealt to players.
	 */
	public static ArrayList<Card> createNewDeck(String c, String w, String r) {
		ArrayList<Card> deck = new ArrayList<Card>();
		if (c == null || w == null || r == null) {
			throw new IllegalArgumentException("Parameters cannot be null when creating a deck.");
		}

		// add some cards to the deck
		for (int i = 0; i < Card.CHARACTERS.length; i++) {
			if (Card.CHARACTERS[i] != c) {
				deck.add(new Card(Card.Type.CHARACTER, Card.CHARACTERS[i]));
			}
		}
		for (int i = 0; i < Card.WEAPONS.length; i++) {
			if (Card.WEAPONS[i] != w) {
				deck.add(new Card(Card.Type.WEAPON, Card.WEAPONS[i]));
			}
		}
		for (int i = 0; i < Card.ROOMS.length; i++) {
			if (Card.ROOMS[i] != r) {
				deck.add(new Card(Card.Type.ROOM, Card.ROOMS[i]));
			}
		}

		Collections.shuffle(deck,R);
		return deck;
	}
	
	// ---------------------------------------------------
	// HELPER METHODS
	// ---------------------------------------------------


	/** Prints the pages of rules. */
	private int printRules(int page) {
		if (page < 0 || page >= HELP_OPTIONS.length) { return page; }
		if (page == 0) { // Premise
			textUI.printText("-- "+ HELP_OPTIONS[0].toUpperCase());
			textUI.printText("You and your fellow detectives have been invited to stay at the home\n"
							+"of known recluse Dr. Body. However, one morning, you all wake only to be\n"
							+"informed that Dr. Body is nowhere to be found. Then word arrives that he\n"
							+"has been discovered some distance from his estate--murdered!\n"
							+"The body has clearly been moved and you are prevented from seeing it, \n"
							+"but the house has been left otherwise untouched so you may pursue your\n"
							+"investigations, and now it is up to you to solve this grisly crime.");
		} else if (page == 1) { // starting a game
			textUI.printText("-- "+ HELP_OPTIONS[1].toUpperCase());
			textUI.printText( "First, exit this help menu. How else will you start?\n"
							+ "Play begins once a number of players has been entered, between 3 and 6,\n"
							+ "and you have selected the character you want to play. \n"
							+ "All players then take turns moving, making suggestions and using the\n"
							+ "information they gather to deduce who killed Dr. Body. \n"
							+ "You're all competing against each other, so be sure to end your turn\n"
							+ "fully before you hand the controls to the next player!");
		} else if (page == 2) { // taking a turn
			textUI.printText("-- "+ HELP_OPTIONS[2].toUpperCase());
			textUI.printText("When your turn starts, you will be able to move your piece or check your\n"
						   + "notes. The game rolls between 1 and 6 spaces for you to move and works\n"
						   + "out the best squares for you to move to. Select one to move!\n"
						   + "If you can, move into a new room and make a suggestion to learn\n"
						   + "information about the murder from the other players.\n");
		} else if (page == 3) { // suggestions and accusations
			textUI.printText("-- "+ HELP_OPTIONS[3].toUpperCase());
			textUI.printText("Suggestions can be made when you start your turn in a new room.\n"
						   + "This room will be part of your suggestion. Choose the character and weapon\n"
						   + "to complete the suggestion, and then (following turn order) if a player\n"
						   + "holds a card that can refute any part of your hypothesis, they'll show it "
						   + "to you. (If you accuse a player, they'll move into the room too.)\n"
						   + "Accusations are how you win (or lose) the game. Once you are certain who,\n"
						   + "what and where, choose 'Make final accusation' and enter your results.\n"
						   + "If you are correct, you win! If you are wrong, you are out of the game (but\n"
						   + "your cards can still be used to refute other players' suggestions).");			
		} else if (page == 4) { // winning
			textUI.printText("-- "+ HELP_OPTIONS[4].toUpperCase());
			textUI.printText("Whoever makes the first correct final accusation wins the game! If only\n"
						   + "one player is left, they win by default.");			
		} else if (page == 5) { // dr body
			textUI.printText("-- "+ HELP_OPTIONS[5].toUpperCase());
			textUI.printText("Shame on you! Your dear friend? Slightly socially-awkward recluse?\n"
					       + "Invited you to stay here out of the kindness of his heart?"
					       + "\nYes, *that* Dr. Body!"+page);			
		}
		int select = HELP_OPTIONS.length;
		if (page < HELP_OPTIONS.length - 1) {
			textUI.printText("");
			textUI.printArray(HELP_OPTIONS);
			select = textUI.askIntBetween(PROMPT+NEWLINE,1,HELP_OPTIONS.length)-1;
		}
		return select;
	}
	
	/** Debugging method used to add a list of character names. 
	 * @throws GameStateModificationException If this method is called after play begins.*/ 
	public void addCharactersByName(List<String> characterNames) throws GameStateModificationException {
		if (players.size() != 0 && activePlayer >= 0) { throw new GameStateModificationException(); }
		players = new ArrayList<Player>();
		for (int i = 0; i < characterNames.size(); i++) {
			for (int j = 0; j < Card.CHARACTERS.length; j++) {
				if (Card.CHARACTERS[j].equals(characterNames.get(i))) {
					players.add(new Player(characterNames.get(i)));
				}
			}
		}
		Collections.sort(players);
	}

	/** While there are still at least two players in the game, returns true.
	 * @return
	 */
	public boolean isPlaying() {
		int n = 0;
		for (Player p : players) {
			if (p.isPlaying()) {
				n ++;
			}
		}
		return n > 1;
	}

	/** Returns the unique start coordinate for each character, as
	 * defined in Card.
	 *
	 */
	public static Coordinate getStart(String c) {
		switch(c) {
			case Card.SCARLET:
				return new Coordinate(7,24);
			case Card.MUSTARD:
				return new Coordinate(0,17);
			case Card.WHITE:
				return new Coordinate(9,0);
			case Card.GREEN:
				return new Coordinate(14,0);
			case Card.PEACOCK:
				return new Coordinate(23,6);
			case Card.PLUM:
				return new Coordinate(23,19);
			default:
				throw new IllegalArgumentException();
		}
	}

	/** Returns the Hypothesis containing the guilty cards.*/
	public Hypothesis getGuilty() {
		return guilty;
	}

	/** Returns a collection of player objects. For testing only:
	 * calling anything other than get methods on the players
	 * will disrupt the game state, if the game is in progress.
	 *
	 * @return The list of players in the game
	 */
	public Collection<Player> getPlayers() {
		return Collections.unmodifiableCollection(players);
	}



	/** Returns a random message, such that it completes the phrase:
	 * CHARACTER_NAME accuses ACCUSED_NAME of _____________ in the ROOM with the WEAPON */
	private String randomMurderDescription() {
		String[] m = { "bloody treachery",
					"violent actions",
					"unwanted attention",
					"killing Dr Body",
					"ending the life of Dr Body",
					"commiting murder",
					"homicide",
					"murder"
					};
		return m[R.nextInt(m.length-1)+1];
	}

	/** Returns a random message, to be printed instead of a
	 * 'dead' player taking their turn. */
	private String randomDeathMessage() {
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
		return m[R.nextInt(m.length-1)+1];
	}

	/** Given a coordinate, return a string describing its relative
	 * position on the board. 
	 * 
	 * @param c
	 * @return
	 */
	public String relativeBoardPosString(Coordinate c) {
		String s = "Moving";
		int pX = c.getX();
		int pY = c.getY();
		
		int division = (int)(BOARD.width() / 3 + 0.5);
		
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((guilty == null) ? 0 : guilty.hashCode());
		result = prime * result + ((players == null) ? 0 : players.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Game)) {
			return false;
		}
		Game other = (Game) obj;
		if (guilty == null) {
			if (other.guilty != null) {
				return false;
			}
		} else if (!guilty.equals(other.guilty)) {
			return false;
		}
		if (players == null) {
			if (other.players != null) {
				return false;
			}
		} else if (!players.equals(other.players)) {
			return false;
		}
		return true;
	}


	/** Given a player, prints the things they know are innocent.
	 * 
	 * @param p
	 * @param breakAfter
	 */
	private void viewHand(Player p, boolean breakAfter) {
		Collection<Card> hand = p.getHand();
		String print = "   ";
		Iterator<Card> iter = hand.iterator();
		for (int i = 0 ; i < hand.size(); i++) {
			print += " ( "+ capitaliseString(iter.next().getValue()) +" )";
			if (i % 5+1 == 0 && i != 0) {
				print += "\n   ";
			}
		}
		textUI.printText(print);
		if (breakAfter) { textUI.printText(""); }
	}

	/** It's useful to know which index belongs to which value.
	 * For a given Card.Type and value, returns the index of
	 * that value in the relevant array.
	 */
	private int indexOf(Card.Type t, String name) {
		String[] arr = null;
		switch (t) {
			case CHARACTER:
				arr = Card.CHARACTERS;
				break;
			case WEAPON:
				arr = Card.WEAPONS;
				break;
			case ROOM:
				arr = Card.ROOMS;
				break;
			default:
				throw new IllegalArgumentException();
		}
		for (int i = 0; i < arr.length; i++){
			if (arr[i].equalsIgnoreCase(name)) {
				return i;
			}
		}
		throw new IllegalArgumentException();
	}

}