package game;

import java.util.ArrayList;
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
public class Player {
	public final String CHARACTER;

	private List<Card> hand;
	private boolean isPlaying = true;
	private HashSet<Card> guiltMap;

	public Player(String c) {
		CHARACTER = c;
		hand = new ArrayList<Card>();
		guiltMap = new HashSet<Card>();
	}

	/** Construct a player with a given hand and mark all the cards as innocent.
	 *
	 * @param c
	 * @param h
	 */
	public Player(String c, List<Card> h) {
		CHARACTER = c;
		hand = h;
		for (Card Card : hand) {
			vindicate(Card);
		}
		guiltMap = new HashSet<Card>();
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

	/** This method should only be used for debugging/testing.
	 *
	 * @return This player's List of cards in their hand.
	 */
	public List<Card> getHand() {
		return hand;
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

	public boolean isPlaying() {
		return isPlaying;
	}
}
