/**
 *
 */
package tests;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import game.Board;
import game.Card;
import game.Coordinate;
import game.Game;
import game.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	@Test
	public void test2_getMoves(){
		Board b = new Board();
		Map<Coordinate, String> moves = b.possibleMoves(new Coordinate(5,8), 6);
		for(Coordinate coord : moves.keySet()){
			System.out.printf("%s : %s\n", coord.toString(), moves.get(coord));
		}
	}

	// ========= helper methods! =============

	private List<Card> fillHand(List<Card> list, Card[] cards) {
		for (int i = 0; i < cards.length; i++) {
			list.add(cards[i]);
		}
		return list;
	}
}
