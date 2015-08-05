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
	static final Random RNG = new Random(System.currentTimeMillis());
	static final String NEWLINE = "\n > ";
	static final String PROMPT = "Select an option: ";

	final Board BOARD = new Board();
	final TextUI textUI = new TextUI();

	List<Player> players;
	Hypothesis guilty;
	int activePlayer = -1; /* used to check when play has begun also, instead
										of creating and setting an additional field. 
										if activePlayer < 0, game has not really started
										and we can keep adding players. */

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
		textUI.printWelcomeAndRules();
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

	/** This is how we play a game of Cluedo!
	 * @throws InvalidAttributeValueException
	 * @param game TODO
	 * @throws ActingOutOfTurnException 
	 */
	public void playGame() throws InvalidAttributeValueException, ActingOutOfTurnException {
		if (players == null || players.size() < 3 || players.size() > 6) {
			throw new InvalidAttributeValueException((players==null||players.size()<3)? "Not enough":"Too many" +" players!");
		}
		if (guilty == null) {
			initialiseDeck();
		}
		activePlayer = 0;
		int roundCounter = 1;
		
		/** the game plays at least one turn. */
		do {
			textUI.printText("\t\tRound "+ roundCounter +" begins.");
			textUI.printDivide();
			for (activePlayer = 0; activePlayer < players.size(); activePlayer++) {
				/** whenever the turn begins, get the player taking a turn and print some nice-looking stuff.*/
				Player p = players.get(activePlayer);
				/* we can throw away the confirm value-- we just want the UI to wait until the next player's ready.*/
				textUI.askIntBetween("It's "+ p.getName() +"'s turn now! "+p.getName()+", are you there? (Enter 1 to confirm.)\n",1,1);
				/* once the player has confirmed */
				if (p.isPlaying()) {
					textUI.printText(p.getName() +"'s turn begins in the "+ 
							relativeBoardPosString(p.position()) + " "+ BOARD.getRoom(p.position()));
					
					while (managePlayerOptions(p));
					
					//textUI.showPlayerOptions(p);
				} else { // if they're not playing, just print something interesting.
					textUI.printText(p.getName() + " " + textUI.randomDeathMessage());
				}
				if (!isPlaying()) {
					break;
				}
				p.enableMovement();
				textUI.clearScreen();
				textUI.printDivide();
			}
			roundCounter++;
		} while (isPlaying());
		
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
			int realIndex = Card.indexOf(Card.Type.CHARACTER, options.get(select));
			addPlayer(realIndex);
			options.remove(select);
		}
	}

	/** Add a player to the game, using the integer index of the player's name
	 * declared in the Card.CHARACTERS array. 
	 * 
	 * @param nameIndex 
	 * @throws GameStateModificationException If this method is called after play begins.
	 */
	public void addPlayer(int nameIndex) throws GameStateModificationException {
		if (activePlayer < 0) {
			players.add(new Player(Card.CHARACTERS[nameIndex]));
		} else {
			throw new GameStateModificationException();
		}
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
		int roll = RNG.nextInt(6) + 1;
		textUI.printText(p.NAME +" rolls a "+roll+".");
		/** if the player was forced, print some acknowledging message */
		if (players.get(activePlayer).wasForced()) {
			textUI.printText("The questioning of "+ p.getName() +" is complete. \n"
					+ " (You are able to move or stay in the "
					+ BOARD.getRoom(p.position()) +".)");
		}
		textUI.printText("---------------------------------");
		/* get the map of options a player has and put them into an array */
		Map<Coordinate,String> moves = BOARD.possibleMoves(p.position(), roll);
		if(moves.isEmpty()){
			System.out.printf("\nERROR for %s at %s\nPossible move list is empty\n",
					p.getName(), p.position().toString());
			return;
		}
		List<String> moveDescs = new ArrayList<String>();
		List<Coordinate> moveCoords = new ArrayList<Coordinate>();
		for (Entry<Coordinate,String> s : moves.entrySet()) {
			moveDescs.add("Move "+ textUI.relativeMovementString(p.position(),s.getKey()) 
					+".\n\t"+ s.getValue());
			moveCoords.add(s.getKey());
		}
		/* if the player was forcibly moved, they may not want to leave this room. */
		if (players.get(activePlayer).wasForced()) {
			moveDescs.add("Stay here.");
			moveCoords.add(p.position());
		}
		/* then we can print these options and ask the user to select an option*/
		textUI.printText("\nEnter number assigned to square you want to move to:\n");
		textUI.printList(moveDescs);

		int userChoice = textUI.askIntBetween(PROMPT,1,moveDescs.size())-1;
		//gets the coordinate that matched the selection then updates player and board accordingly
		Coordinate newCoord = moveCoords.get(userChoice);
		toggleOccupied(p.position(),newCoord);
		try {
			p.move(newCoord);
		} catch (ActingOutOfTurnException e) {
			textUI.printText(p+" seems rooted to the spot.");
		}
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
	 * @return True if the turn continues after the option has been selected; otherwise false. 
	 * @throws ActingOutOfTurnException 
	 */
	public boolean managePlayerOptions(Player p) throws ActingOutOfTurnException {
		List<String> options = textUI.printPlayerOptions(BOARD, p);
		String option = options.get( textUI.askIntBetween(PROMPT,1,options.size()) - 1 ); // subtract one because we've asked for a number between 1 and length, and we want 0-to-length-minus-1
		/* do the selected option! */
		
		boolean turnContinues = true; // true if the turn continues after choosing this option
		
		// if they want to move...
		if (option.equals(textUI.OPT_MOVE)) {
			textUI.printText("ROLL TO MOVE");
			playerMoves(p);
		}
		// if they want to make a guess...
		else if (option.equals(textUI.OPT_SUGGEST)) {
			textUI.printText("MAKE GUESS");
			testHypothesis(p,null);
			turnContinues = false;
			textUI.askIntBetween("(Enter 1 to continue.) ",1,1);
		}
		// if they want to make a final accusation...
		else if (option.equals(textUI.OPT_ACCUSE)) {
			textUI.printText("MAKE FINAL ACCUSATION");
			textUI.printText("Are you sure you want to make your final accusation?");
			textUI.printArray(new String[] {"Yes, I would like to make my final choice.","No! Stop! I misclicked! Help!"});
			int select = textUI.askIntBetween(PROMPT+NEWLINE, 1, 2)-1;
			if (select == 0) {
				testAccusation(p);
			}
			turnContinues = false;
			textUI.askIntBetween("(Enter 1 to continue.) ",1,1);
		} 
		// if they want to view their hand...
		else if (option.equals(textUI.OPT_HAND)) {
			textUI.printText("YOUR HAND");
			viewHand(p, false);
		}
		// if they want to open their notebook...
		else if (option.equals(textUI.OPT_NOTES)) {
			textUI.printText("VIEW NOTEBOOK");
			textUI.viewNotebook(this, p);
		}
		// if they ask to end their turn
		else if (option.equals(textUI.OPT_END)) {
			turnContinues = false;
		}
		return turnContinues;
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
		textUI.printArray(textUI.createNotesToPrint(this, p, Card.Type.CHARACTER));
		int select = textUI.askIntBetween(PROMPT+NEWLINE,1,Card.CHARACTERS.length)-1;
		try {
			h.setCharacter(new Card(Card.Type.CHARACTER,Card.CHARACTERS[select]));
		} catch (IllegalAccessException e) {
			// this isn't actually possible
		}

		textUI.printText("These are the possible murder weapons:");
		textUI.printArray(textUI.createNotesToPrint(this, p, Card.Type.WEAPON));
		select = textUI.askIntBetween(PROMPT+NEWLINE,1,Card.WEAPONS.length)-1;
		try {
			h.setWeapon(new Card(Card.Type.WEAPON,Card.WEAPONS[select]));
		} catch (IllegalAccessException e) {
			// this isn't possible
		}

		Card roomCard;
		if (isFinalAccusation) {
			textUI.printText("These are the possible murder locations:");
			textUI.printArray(textUI.createNotesToPrint(this, p, Card.Type.ROOM));
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
						" accuses "+ accused +" of "+ textUI.randomMurderDescription()
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
				try {
					p.giveCard(deck.get(cardIdx));
				} catch (GameStateModificationException e) {
					e.printStackTrace();
					System.exit(1);
				}
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
		textUI.printText("Everyone knows that "+ textUI.toStringFromCards(subList) +" had nothing to do with the murder.");
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

		Collections.shuffle(deck,RNG);
		return deck;
	}
	
	// ---------------------------------------------------
	// HELPER METHODS
	// ---------------------------------------------------


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
	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
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
	 * @throws ActingOutOfTurnException 
	 */
	private void viewHand(Player p, boolean breakAfter) throws ActingOutOfTurnException {
		if (!p.equals(players.get(activePlayer))) { throw new ActingOutOfTurnException(); }
		Collection<Card> hand = p.getHand();
		textUI.printText(textUI.capitalise(textUI.toStringFromCollection(hand)));
		if (breakAfter) { textUI.printText(""); }
	}

}