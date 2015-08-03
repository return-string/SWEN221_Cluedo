package game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
 * @author mckayvick
 *
 */
public class Game {
	public static final Board BOARD = new Board();
	public static final TextUI textUI = new TextUI();

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

	private List<Player> players;
	private Hypothesis guilty;
	private int activePlayer = -1;


	/** Constructs a new game, prints the rules.
	 *
	 * @param players
	 */
	public Game() {
		this.players = new ArrayList<Player>();
		textUI.printText("Welcome to Cluedo and stuff!");
	}

	/** This is the normal method that should be called to start a game.
	 * It asks the user how many players will be in this game, then
	 * creates the player list, sorts it, and tries to play.
	 */
	public void startGame() {
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

	/** Given the number of users playing, prints the remaining
	 * character options and repeatedly asks them to select which characters
	 * they would like to play.
	 *
	 * @param numPlayers
	 */
	private void selectCharacters(int numPlayers) {
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

	/**
	 * @param select
	 */
	public void addPlayer(int select) {
		players.add(new Player(Card.CHARACTERS[select]));
	}


	/** This is how we play a game of Cluedo!
	 * @throws InvalidAttributeValueException
	 */
	public void playGame() throws InvalidAttributeValueException {
		if (players == null) {
			throw new InvalidAttributeValueException("Can't start a game without players!");
		}
		if (guilty == null) {
			initialiseDeck();
		}
		activePlayer = 0;
		int roundCounter = 1;

		textUI.printText(guilty.toString());
		/** the game plays at least one turn. */
		do {
			textUI.printDivide();
			textUI.printText("\t\tRound "+roundCounter+" begins.");
			textUI.printDivide();
			for (activePlayer = 0; activePlayer < players.size(); activePlayer++) {
				/** whenever the turn begins, get the player taking a turn and print some nice-looking stuff.*/
				Player p = players.get(activePlayer);
				if (p.isPlaying()) {
					textUI.printText(p.getName() +"'s turn begins in the "+ BOARD.getRoom(p.position()) +".");
					viewInnocent(p,false);
					playerMoves(p);
					showPlayerOptions(p);
				} else { // if they're not playing, just print something interesting.
					textUI.printText(p.getName() + " " + randomDeathMessage());
				}
				if (!isPlaying()) {
					textUI.printText("All other players have failed!");
					break;
				}
				textUI.printDivide();
			}
			roundCounter++;
		} while (this.isPlaying());
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
		int roll = R.nextInt(5) + 1;
		textUI.printText(p.NAME +" rolls a "+roll+".");
		/** if the player was forced, print some acknowledging message */
		if (players.get(activePlayer).wasForced()) {
			textUI.printText("The questioning of "+ p.getName() +" is complete. \n (They are able to move or stay in the "+ BOARD.getRoom(p.position()) +".)");
		}

		/* get the map of options a player has and put them into an array */
		Map<Coordinate,String> moves = BOARD.possibleMoves(p.position(), roll);
		List<String> moveDescs = new ArrayList<String>();
		for (String s : moves.values()) {
			moveDescs.add(s);
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
				p.move(s.getKey());
			}
		}
	}

	/** Prints  the options available to a player once their movement phase
	 * is complete.
	 *
	 * @param p Player whose options are to be displayed.
	 */
	public void showPlayerOptions(Player p) {
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
		boolean clearScreen = false;
		/* do the selected option! */
		if (option == 0 && options[option].equals(PLAYER_OPTIONS[0])) {
			textUI.printText("MAKE GUESS");
			testHypothesis(p,null);
		} else if ((option == 0 || option == 1) && options[option].equals(PLAYER_OPTIONS[1])) {
			textUI.printText("MAKE ACCUSATION");
			testAccusation(p);
		} else if ((option == 1 || option == 2) && options[option].equals(PLAYER_OPTIONS[2])) {
			viewInnocent(p,true);
			showPlayerOptions(p);
			clearScreen = true;
		} else if ((option == 2 || option == 3) && options[option].equals(PLAYER_OPTIONS[3])) {
			textUI.printText("VIEW NOTEBOOK");
			viewNotebook(p);
			showPlayerOptions(p);
			clearScreen = true;
		} else {
			textUI.printText(p.getName() +" ends their turn.");
			clearScreen  =true;
		}
		if (clearScreen) {
			textUI.clearScreen();
		}
	}

	private void viewInnocent(Player p, boolean breakAfter) {
		List<Card> hand = p.getHand();
		String print = "   ";
		for (int i = 0; i < hand.size(); i++) {
			print += "  ( "+ capitaliseString(hand.get(i).getValue()) +" )";
			if (i % 2 == 0 && i != 0) {
				print += "\n   ";
			}
		}
		textUI.printText(print);
		if (breakAfter) { textUI.printText(""); }
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
					// TODO some board method for moving characters into the same room
					// players.get(i).forciblyMove();
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
	 */
	private void viewNotebook(Player p) {
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

	/** Returns the unique start coordinate for each character.
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

	/**
	 *
	 * @return The list of players in the game
	 */
	public Collection<Player> getPlayers() {
		return players;
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
		return m[(int)(R.nextInt(m.length-1)+1)];
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
		return m[(int)(R.nextInt(m.length-1)+1)];
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