package game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.management.InvalidAttributeValueException;
import javax.xml.stream.events.Characters;

/** The Game class is used to model the Cluedo game.
 *
 * @author mckayvick
 *
 */
public class Game {
	private static final Random R = new Random(System.currentTimeMillis());
	public static final Board BOARD = new Board();

	private TextUI textUI = new TextUI();
	private List<Player> players;
	private Hypothesis guilty;
	private int activePlayer;

	private static final String PROMPT = "Select an option:";
	private static final String[] PLAYER_OPTIONS = {
		"Make a suggestion",
		"Make a final accusation",
		"View detective notebook",
		"End turn"
	};

	/** Given a list of the playing characters,
	 *
	 * @param players
	 */
	public Game() {
		this.players = new ArrayList<Player>();
		textUI.printText("Welcome to Cluedo and stuff!");
		int numPlayers = textUI.askInt("How many players? (Please enter a number between 3 and 6)");
		for (int i = 0; i < numPlayers; i++) {
			textUI.printArray(Card.CHARACTERS);
			textUI.askInt("Player "+ i+1 +", please select a character.");
		}
	}


	/** This is how we play a game of Cluedo!
	 */
	public void playGame() {
		if (players == null || activePlayer >= players.size()) {
			// throw new InvalidAttributeValueException("");
		}

		for (Player p : players) {
			if (p.isPlaying()) {
				playerMoves(p);
				showPlayerOptions(p);
			}
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
		int roll = R.nextInt(5) + 1;
		textUI.printText("You rolled a "+roll+"!");

		/* get the map of options a player has and put them into an array */
		Map<Coordinate,String> moves = BOARD.possibleMoves(p.position(), roll);
		Iterator<String> descsIter = moves.values().iterator();
		String[] moveDescs = new String[moves.size()];
		for (int i = 0; i < moves.size(); i++) {
			moveDescs[i] = descsIter.next();
		}
		/* then we can print these options and ask the user to select an option*/
		textUI.printArray(moveDescs);
		int userChoice = textUI.askIntBetween(PROMPT,1,moveDescs.length);
		for (Entry<Coordinate,String> s : moves.entrySet()) {
			/* when the user-selected move is found, move the player */
			if (s.getValue().equalsIgnoreCase(moveDescs[userChoice])) {
				p.move(s.getKey());
			}
		}
	}

	/** Shows the options available to a player once they have completed a move.*/
	public void showPlayerOptions(Player p) {
		String[] options;
		/* if the player is in a room, give them the option to make a suggestion.
		 * Otherwise limit their options to making an accusation, viewing their
		 * card or ending the turn. */
		if (BOARD.getRoom(p.position()).equals(BOARD.HALLWAYSTRING)) {
			options = new String[]{ PLAYER_OPTIONS[1], PLAYER_OPTIONS[2], PLAYER_OPTIONS[3] };
		} else {
			options = PLAYER_OPTIONS;
		}
		textUI.printArray(options);
		int option = textUI.askIntBetween(PROMPT,1,options.length) - 1; // subtract one because we've asked for a number between 1 and length
		/* do the selected option! */
		if (option == PLAYER_OPTIONS.length-1) { // the second-to-last option is always 'view notebook'
			viewNotebook(p);
			showPlayerOptions(p);
		} else if (option == 0){
			playerMakesSuggestion(p);
		} else if (option == 1) {
			playerMakesAccusation(p);
		} else {
			textUI.printText(p.toString() +" ends their turn.");
		}
	}

	private void playerMakesSuggestion(Player p) {
		Hypothesis guess = new Hypothesis();
		textUI.printText(p.toString() +" is considering the evidence...");
		textUI.printText("These are the possible guilty characters:");
		textUI.printArray(createNotesToPrint(p, Card.Type.CHARACTER));
		int select = textUI.askIntBetween(PROMPT,1,Card.CHARACTERS.length);
		try {
			guess.addCharacter(new Card(Card.Type.CHARACTER,Card.CHARACTERS[select]));
		} catch (IllegalAccessException e) {
			// this isn't actually possible
		}

		textUI.printText("These are the possible murder weapons:");
		textUI.printArray(createNotesToPrint(p, Card.Type.WEAPON));
		select = textUI.askIntBetween(PROMPT,1,Card.WEAPONS.length);
		try {
			guess.addWeapon(new Card(Card.Type.WEAPON,Card.WEAPONS[select]));
		} catch (IllegalAccessException e) {
			// this isn't actually possible
		}

		textUI.printText("These are the possible murder locations:");
		textUI.printArray(createNotesToPrint(p, Card.Type.ROOM));
		select = textUI.askIntBetween(PROMPT,1,Card.ROOMS.length);
		try {
			guess.addRoom(new Card(Card.Type.ROOM,Card.ROOMS[select]));
		} catch (IllegalAccessException e) {
			// this isn't actually possible
		}
	}


	private void playerMakesAccusation(Player p) {

	}


	private void viewNotebook(Player p) {
		textUI.printDivide();
		textUI.printArray(createNotesToPrint(p,Card.Type.CHARACTER));
		textUI.printArray(createNotesToPrint(p,Card.Type.WEAPON));
		textUI.printArray(createNotesToPrint(p,Card.Type.ROOM));
		textUI.printDivide();
	}


	/**
	 * @param p
	 */
	private String[] createNotesToPrint(Player p, Card.Type t) {
		String[] printing;
		if (t == Card.Type.CHARACTER) {
			printing = Card.CHARACTERS;
		} else if (t == Card.Type.WEAPON) {
			printing = Card.WEAPONS;
		} else if (t == Card.Type.ROOM) {
			printing = Card.ROOMS;
		} else {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < printing.length; i++) {
			if (p.isInnocent(t,Card.CHARACTERS[i])) {
				printing[i] = Card.CHARACTERS[i] +" (X)";
			} else {
				printing[i] = Card.CHARACTERS[i] +" ( )";
			}
		}
		return printing;
	}


	public void playerAccuses(Player p) {
	}

	public void printSuspectCharacters() {
		textUI.printArray(Card.CHARACTERS);
	}

	public void printSuspectWeapons() {
		textUI.printArray(Card.WEAPONS);
	}

	public void printSuspectRooms() {
		textUI.printArray(Card.ROOMS);
	}

	/** This method selects the guilty character, weapon and room and deals
	 * the remaining cards to the players.
	 */
	public void initialiseDeck() {
		initialiseDeck(null,null,null);
	}

	/** This method can be used to select the guilty cards manually.
	 * Leave any parameters as null to select randomly.
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
		int handSize = (int) Math.floor((Card.DECKSIZE-3) / this.players.size());

		// add cards to each player's hand
		int cardIdx = 0;
		for (Player p : players) {
			for (int i = cardIdx; i < handSize; i++) {
				p.giveCard(deck.get(cardIdx));
				cardIdx ++;
			}
		}


		// if there are any cards remaining, print a message
		if (handSize * players.size() != Card.DECKSIZE) {
			//Main.showSpareCards(deck.subList(cardIdx, deck.size()));
		}
		// then ensure every player has these cards...
		for (Player p : players) {
			for (Card card : deck.subList(cardIdx,deck.size())) {
				p.vindicate(card);
			}
		}
		// finally, discard these extra cards
		for (int i = cardIdx; i < deck.size(); i++) {
			deck.remove(i);
		}
	}

	/** Given the values of the guilty cards, this method assembles a shuffled list
	 * containing the remaining innocent cards and returns it.
	 *
	 * @param c
	 * @param w
	 * @param r
	 * @return A shuffled list of cards to be dealt to players.
	 */
	private ArrayList<Card> createNewDeck(String c, String w, String r) {
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
			if (Card.WEAPONS[i] == w) {
				deck.add(new Card(Card.Type.WEAPON, Card.WEAPONS[i]));
			}
		}
		for (int i = 0; i < Card.WEAPONS.length; i++) {
			if (Card.WEAPONS[i] == r) {
				deck.add(new Card(Card.Type.ROOM, Card.ROOMS[i]));
			}
		}

		Collections.shuffle(deck);
		return deck;
	}



	/**
	 *
	 * @return
	 */
	public Collection<Player> getPlayers() {
		return players;
	}
}