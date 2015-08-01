package game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/** The Player class models each active player in a game of Cluedo.
 *
 * A Player has to keep a set of cards, monitor whether they have made
 * an incorrect accusation (and are thus out of the game), and be
 * able to search their hand to match items in a Suggestion.
 *
 * Because Players' names can also be suspects, Game.Character is used
 * to remember which player is which.
 * @author Vicki
 *
 */
public class Player implements Comparable {
	public final String NAME;

	private List<Card> hand;
	private boolean isPlaying = true;
	private HashSet<Card> guiltMap;
	private Coordinate pos;
	private boolean wasForced = false;

	public Player(String characterName, Coordinate startPos) {
		this.NAME = characterName;
		this.hand = new ArrayList<Card>();
		this.pos = pos;
		this.guiltMap = new HashSet<Card>();
	}

	/** Construct a player with a given hand and mark all the cards as innocent.
	 *
	 * @param c
	 * @param h
	 */
	public Player(String characterName, List<Card> hand, Coordinate startPos) {
		this.NAME = characterName;
		this.hand = hand;
		this.pos = startPos;
		for (Card card : hand) {
			vindicate(card);
		}
		this.guiltMap = new HashSet<Card>();
	}

	/** This method should only be used for debugging/testing.
	 *
	 * @return This player's List of cards in their hand.
	 */
	public List<Card> getHand() {
		return hand;
	}

	/** Updates the player's coordinates. */
	public void move(Coordinate pos) {
		this.pos = pos;
		wasForced = false;
	}

	/** When a player is moved out of turn, call this method. */
	public void forciblyMove(Coordinate pos) {
		this.pos = pos;
		wasForced = true;
	}

	/** When play begins, cards should be dealt to Players using this method.
	 *
	 * @param c Card to add to this player's hand.
	 * @return Whether the card was successfully added or not.
	 */
	public boolean giveCard(Card c) {
		if (c != null) {
			if (hand.add(c)) {
				vindicate(c);
				return true;
			}
		}
		return false;
	}

	/**
	 * @return
	 */
	public void kill() {
		isPlaying = false;
	}

	/** The player shows a card to another player if they can refute their hypothesis.
	 *
	 * This is... really not a safe method to have for networked play. Grr. Any alternatives?
	 */
	public List<Card> refuteHypothesis(Hypothesis h) {
		List<Card> l = new ArrayList<Card>();
		for (Card c : hand) {
			if (h.contains(c)) {
				l.add(c);
			}
		}
		return l;
	}

	/** Returns the player's current position on the board. */
	public Coordinate position() {
		return pos;
	}

	/** A player vindicates a card when it is shown to them by another player.
	 * @param Card Card to vindicate for this Player
	 */
	public void vindicate(Card c) {
		guiltMap.add(c);
	}

	public boolean isInnocent(Card c) {
		return guiltMap.contains(c);
	}

	public boolean isInnocent(Card.Type t, String c) {
		return guiltMap.contains(new Card(t,c));
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public String getName() {
		return NAME;
	}

	public class PlayerComp<T extends Player> implements Comparator{
		@Override
		public int compare(Object o1, Object o2) {
			if (!(o1 instanceof Player) || !(o2 instanceof Player)) {
				throw new IllegalArgumentException();
			}
			Player first = (Player)o1;
			Player second = (Player)o2;
			int index1 = 0;
			int index2 = 0;
			for (int i = 0; i < Card.CHARACTERS.length; i++) {
				if (first.getName().equals(Card.CHARACTERS[i])) {
					index1 = i;
				}
				if (second.getName().equals(Card.CHARACTERS[i])) {
					index2 = i;
				}
			}
			return index2 - index1;
		}

	}

	@Override
	public int compareTo(Object o) {
		if (!(o instanceof Player)) {
			throw new IllegalArgumentException();
		}
		Player second = (Player)o;
		int index1 = 0;
		int index2 = 0;
		for (int i = 0; i < Card.CHARACTERS.length; i++) {
			if (this.getName().equals(Card.CHARACTERS[i])) {
				index1 = i;
			}
			if (second.getName().equals(Card.CHARACTERS[i])) {
				index2 = i;
			}
		}
		return index1 - index2;
	}
}
