package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.stream.events.Characters;

/** The Game class is used to model the Cluedo game, which
 * manages turn progression (calling takeTurn() on each player
 * while the game is in progress) and the guilty cards.
 *
 * @author mckayvick
 *
 */
public class Game {
	public final List<Player> players;

	private Card guiltyChar;
	private Card guiltyWeap;
	private Card guiltyRoom;

	private TextUI textUI = new TextUI();

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

		initialiseDeck();
	}

	/** This method selects the guilty character, weapon and room and deals
	 * the remaining cards to the players.
	 */
	public void initialiseDeck() {
		initialiseGame(null,null,null);
	}

	/** This method can be used to select the guilty cards manually.
	 * Leave any parameters as null to select randomly.
	 *
	 * @param c Guilty character
	 * @param w Guilty weapon
	 * @param r Guilty room
	 */
	public void initialiseGame(String c, String w, String r) {
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
		guiltyChar = new Card(Card.Type.CHARACTER, c);
		guiltyWeap = new Card(Card.Type.WEAPON, w);
		guiltyRoom = new Card(Card.Type.ROOM, r);

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
}