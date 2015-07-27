package game;

import game.Game.Characters;
import game.Game.Rooms;
import game.Game.Weapons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	private final GuiltCheck guiltCheck = new GuiltCheck();
	private boolean isPlaying = true;
	private List<Card> hand = new ArrayList<Card>();

	public Player(Characters c) {
		character = c;
	}

	/** Construct a player with a premade hand.
	 *
	 * @param c
	 * @param h
	 */
	public Player(Characters c, List<Card> h) {
		character = c;
		hand = h;
		for (Card card : hand) {
			guiltCheck.vindicate(card);
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
				guiltCheck.vindicate(c);
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
	 * @param card Card to vindicate for this Player
	 */
	public void vindicate(Card card) {
		guiltCheck.vindicate(card);
	}


	private class GuiltCheck{
		private HashMap<Card,Boolean> guiltMap = new HashMap<Card,Boolean>();

		public void vindicate(Card c) {
			guiltMap.put(c,true);
		}

		public boolean isInnocent(Card c) {
			if (!guiltMap.containsKey(c)) {
				return false;
			}
			return guiltMap.get(c) == true;
		}
	}


	public boolean isInnocent(Card card) {
		return guiltCheck.isInnocent(card);
	}

	public boolean isInnocent(Characters c) {
		return guiltCheck.isInnocent(new CharacterCard(c));
	}

	public boolean isInnocent(Weapons w) {
		return guiltCheck.isInnocent(new WeaponCard(w));
	}

	public boolean isInnocent(Rooms r) {
		return guiltCheck.isInnocent(new RoomCard(r));
	}
}
