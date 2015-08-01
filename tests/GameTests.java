/**
 *
 */
package tests;
import static org.junit.Assert.*;
import game.Board;
import game.Card;
import game.Coordinate;
import game.Game;
import game.Player;

import java.util.ArrayList;
import java.util.HashMap;
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

	@Test
	public void test2_getMoves2(){
		Board b = new Board();
		Map<Coordinate, String> moves = b.possibleMoves(new Coordinate(9, 0), 4);
		Map<Coordinate, String> expected = new HashMap<Coordinate, String>();
		expected.put(new Coordinate(7, 2), "Go down hallway. Don't know distance to rooms yet.");
		assertEquals(expected, moves);
	}

	// ========= helper methods! =============

	private List<Card> fillHand(List<Card> list, Card[] cards) {
		for (int i = 0; i < cards.length; i++) {
			list.add(cards[i]);
		}
		return list;
	}
}
