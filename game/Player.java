package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.stream.events.Characters;

/** The Player class models each active player in a game of Cluedo.
 *
 * A Player has to keep a set of cards, monitor whether they have made
 * an incorrect accusation (and are thus out of the game), and be
 * able to search their hand to match items in a Suggestion.
 *
 * Because Players are always potential suspects, Game.Character is used
 * to remember which player is which.
 * @author Vicki
 *
 */
public class Player {
	public final Characters character;
	private boolean isPlaying = true;
	private HashMap<Card,Boolean> guiltMap;
	private List<Card> hand;

	public Player(Characters c) {
		character = c;
		hand = new ArrayList<Card>();
		guiltMap = new HashMap<Card, Boolean>();
	}

	/** Construct a player with a given hand and mark all the cards as innocent.
	 *
	 * @param c
	 * @param h
	 */
	public Player(Characters c, List<Card> h) {
		guiltMap = new HashMap<Card, Boolean>();
		character = c;
		hand = h;
		for (Card Card : hand) {
			vindicate(Card);
		}
	}

	/** When play begins, cards should be dealt to Players
	 * using this method.
	 *
	 * @param c
	 * @return
	 */
	public boolean giveCard(Card c) {
		if (c != null && !hand.contains(c)) {
			if (hand.add(c)) {
				vindicate(c);
				return true;
			}
		}
		return false;
	}

	/** This method should only be used for debugging.
	 *
	 * @return
	 */
	public List<Card> getHand() {
		return hand;
	}

	/** Vindicating occurs when a player is shown that a
	 * character, weapon or room is innocent.
	 * @param Card Card to vindicate for this Player
	 */
	public void vindicate(Card c) {
		guiltMap.put(c,true);
	}

	public boolean isInnocent(Card c) {
		if (!guiltMap.containsKey(c)) {
			return false;
		}
		return guiltMap.get(c) == true;
	}

	public boolean isInnocent(Card.Character c) {
		return isInnocent(new Card(c));
	}

	public boolean isInnocent(Card.Weapon w) {
		return isInnocent(new Card(w));
	}

	public boolean isInnocent(Card.Room r) {
		return isInnocent(new Card(r));
	}
}
