package game;

import java.util.ArrayList;
import java.util.Collections;
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
public class Player implements Comparable<Player> {
	public final String NAME;

	private List<Card> hand;
	private boolean isPlaying = true;
	private HashSet<Card> guiltMap;
	private Coordinate pos;
	private boolean stopGivingCards = false;
	private boolean wasForced = false;
	private boolean hasMoved = false;

	public Player(String characterName, Coordinate startPos) {
		this.NAME = characterName;
		this.hand = new ArrayList<Card>();
		this.pos = startPos;
		this.guiltMap = new HashSet<Card>();
	}

	public Player(String characterName) {
		this.NAME = characterName;
		this.hand = new ArrayList<Card>();
		this.pos = Card.getCoordinate(characterName);
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
		return Collections.unmodifiableList(hand);
	}

	/** Updates the player's coordinates.
	 * @throws ActingOutOfTurnException */
	public void move(Coordinate pos) throws ActingOutOfTurnException {
		stopGivingCards = true;
		if (hasMoved) { throw new ActingOutOfTurnException(); }
		this.pos = pos;
		wasForced = false;
		hasMoved = true;
	}

	/** When a player is moved out of turn, call this method. */
	public void forciblyMove(Coordinate pos) {
		this.pos = pos;
		wasForced = true;
	}

	/** When a player's turn is ended, call this method to allow them to move again. */
	public void enableMovement() {
		hasMoved = false;
	}

	/** When play begins, cards should be dealt to Players using this method.
	 *
	 * @param c CardImpl to add to this player's hand.
	 * @return Whether the card was successfully added or not.
	 * @throws GameStateModificationException
	 */
	public boolean giveCard(Card c) throws GameStateModificationException {
		if (stopGivingCards) { throw new GameStateModificationException("Cannot add cards to a player's hand once they have acted!"); }
		if (c != null) {
			if (hand.contains(c)) {
				throw new IllegalArgumentException("Player "+ getName() +" hand cannot contain duplicate cards!");
			}
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
	 * @param CardImpl CardImpl to vindicate for this Player
	 */
	public void vindicate(Card c) {
		guiltMap.add(c);
	}

	public boolean isInnocent(Card c) {
		return guiltMap.contains(c);
	}

	public boolean isInnocent(Card.Type t, String c) {
		return guiltMap.contains(new CardImpl(t,c));
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public boolean wasForced() {
		return wasForced;
	}

	public boolean hasMoved() {
		return hasMoved;
	}

	public String getName() {
		return NAME;
	}

	@Override
	public int compareTo(Player o) {
		if (o.getName().equals(this.getName())) {
			return 0;
		}
		for (int i = 0; i < Card.CHARACTERS.length; i++) {
			if (Card.CHARACTERS[i] == o.getName()) {
				return 1;
			} else if (Card.CHARACTERS[i] == this.getName()) {
				return -1;
			}
		}
		return -1;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((NAME == null) ? 0 : NAME.hashCode());
		result = prime * result + ((hand == null) ? 0 : hand.hashCode());
		result = prime * result + (isPlaying ? 1231 : 1237);
		result = prime * result + ((pos == null) ? 0 : pos.hashCode());
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
		if (!(obj instanceof Player)) {
			return false;
		}
		Player other = (Player) obj;
		if (NAME == null) {
			if (other.NAME != null) {
				return false;
			}
		} else if (!NAME.equals(other.NAME)) {
			return false;
		}
		if (hand == null) {
			if (other.hand != null) {
				return false;
			}
		} else if (!hand.equals(other.hand)) {
			return false;
		}
		if (isPlaying != other.isPlaying) {
			return false;
		}
		return true;
	}

	public boolean equalsName(String s) {
		return NAME.equalsIgnoreCase(s);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return NAME + " ("+ (isPlaying?"1":"0") +")";
	}
}
