/**
 *
 */
package tests;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import game.Board;
import game.Card;
import game.Game;
import game.Player;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
/**
 * @author Vicki
 *
 */
public class GameTests {
	@Test
	public void test1_createBoard() {
		Board b = new Board();
	}


	// ========= helper methods! =============

	private List<Card> fillHand(List<Card> list, Card[] cards) {
		for (int i = 0; i < cards.length; i++) {
			list.add(cards[i]);
		}
		return list;
	}
}
