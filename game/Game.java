package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/** The Game class is used to model the Cluedo game.
 * It has fields to remember the guilty person, weapon and room and
 *
 * @author mckayvick
 *
 */
public class Game {
	public static enum Characters { WHITE, SCARLET, MUSTARD, GREEN, PEACOCK, PLUM };
	public static enum Weapons { CANDLESTICK, DAGGER, REVOLVER, ROPE, PIPE, WRENCH };
	public static enum Rooms { CONSERVATORY, BALL, BILLIARD, DINING, HALL, KITCHEN, LOUNGE, STUDY };
	public static final int DECK_SIZE = Characters.values().length + Weapons.values().length + Rooms.values().length;
	private CharacterCard guiltyChar;
	private WeaponCard guiltyWeap;
	private RoomCard guiltyRoom;
	private int handSize;
	public final List<Player> players;

	/** Given a list of the playing characters,
	 *
	 * @param players
	 */
	public Game(List<Game.Characters> players) {
		this.players = new ArrayList<Player>();
		for (Game.Characters c : players) {
			this.players.add(new Player(c));
		}
		handSize = (int) Math.floor((DECK_SIZE-3) / this.players.size());
	}

	/**
	 *
	 */
	public void initialiseGame() {
		initialiseGame(null,null,null);
	}

	/** This method selects the guilty character, weapon and room and deals
	 * the remaining cards to the players.
	 *
	 * @param c Guilty character, null to select randomly
	 * @param w Guilty weapon, null to select randomly
	 * @param r Guilty room, null to select randomly
	 */
	public void initialiseGame(Game.Characters c, Game.Weapons w, Game.Rooms r) {
		// for each null value, pick a random card to be guilty
		if (c == null) {
			int i = (int) (Math.random()*Characters.values().length-1);
			c = Characters.values()[i];
		}
		if (w == null) {
			int i = (int) (Math.random()*Weapons.values().length-1);
			w = Weapons.values()[i];
		}
		if (r == null) {
			int i = (int) (Math.random()*Rooms.values().length-1);
			r = Rooms.values()[i];
		}

		// put guilty cards into fields
		guiltyChar = new CharacterCard(c);
		guiltyWeap = new WeaponCard(w);
		guiltyRoom = new RoomCard(r);

		ArrayList<Card> deck = createNewDeck(c, w, r); // creates, shuffles a new deck of cards

		// add cards to each player's hand
		int cardIdx = 0;
		for (Player p : players) {
			for (int i = cardIdx; i < handSize; i++) {
				p.giveCard(deck.get(cardIdx));
				cardIdx ++;
			}
		}


		// if there are any cards remaining, print a message
		if (handSize * players.size() != DECK_SIZE) {
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

	/** Given the guilty cards, this method assembles a shuffled list
	 * containing the remaining innocent cards and returns it.
	 *
	 * @param c
	 * @param w
	 * @param r
	 * @return A shuffled list of cards to be dealt to players.
	 */
	private ArrayList<Card> createNewDeck(Game.Characters c, Game.Weapons w,
			Game.Rooms r) {
		ArrayList<Card> deck = new ArrayList<Card>();

		// add some cards to the deck
		for (int i = 0; i < Characters.values().length; i++) {
			if (Characters.values()[i] != c) {
				deck.add(new CharacterCard(Characters.values()[i]));
			}
		}
		for (int i = 0; i < Weapons.values().length; i++) {
			if (Weapons.values()[i] == w) {
				deck.add(new WeaponCard(Weapons.values()[i]));
			}
		}
		for (int i = 0; i < Rooms.values().length; i++) {
			if (Rooms.values()[i] == r) {
				deck.add(new RoomCard(Rooms.values()[i]));
			}
		}

		Collections.shuffle(deck);
		return deck;
	}
}