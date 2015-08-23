package tests;

import static org.junit.Assert.*;
import game.ActingOutOfTurnException;
import game.Card;
import game.CardImpl;
import game.CardImpl;
import game.Coordinate;
import game.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class PlayerTests {


	@Test
	public void test1_playerSort() {
		Player[] ps = { new Player("p1",Card.MUSTARD),
				new Player("p2",Card.SCARLET),
				new Player("p3",Card.PLUM),
				new Player("p4",Card.WHITE),
				new Player ("p5",Card.PEACOCK),
				new Player ("p6",Card.GREEN)
			};
		List<Player> players = new ArrayList<Player>();
		for (int i = 0; i < ps.length; i++) {
			players.add(ps[i]);
		}
		List<Player> ps2 = new ArrayList<Player>();
		ps2.add(new Player("p1",Card.SCARLET));
		ps2.add(new Player("p2",Card.MUSTARD));
		ps2.add(new Player("p3",Card.WHITE));
		ps2.add(new Player("p4",Card.GREEN));
		ps2.add(new Player("p5",Card.PEACOCK));
		ps2.add(new Player("p6",Card.PLUM));
		Collections.sort(players);
		assertEquals(players.toString() +" \n=/=\n "+ ps2.toString()+"\n",ps2,players);
	}

	/** Tests updating of player position */
	@Test
	public void test2_playerMovement1() {
		Coordinate c = new Coordinate(9,0);
		Player white = new Player("p1",Card.WHITE);
		assertEquals(c,white.getPosition());
		try {
			white.move(new Coordinate (16,23));
		} catch (ActingOutOfTurnException e) {
			fail();
		} // player doesn't check its moves
		assertNotEquals(c,white.getPosition());
	}

	/** Tests invalid player moves */
	@Test
	public void test3_playerMovement2() {
		Player white = new Player("p1",Card.WHITE);
		try {
			white.move(new Coordinate(9,0));
		} catch (ActingOutOfTurnException e1) {
			fail();
		}
		try {
			white.move(new Coordinate(9,1));
		} catch (ActingOutOfTurnException e) {
			return;
		}
		fail();
	}

	/** Tests that adding cards to hand also marks them innocent */
	@Test
	public void test4_vindication1() {
		Player white = new Player("p1",Card.WHITE);
		List<Card> hand = white.getHand();
		for(Card c : hand) {
			assertTrue(white.isInnocent(c));
			assertTrue(white.isInnocent(c.getType(), c.getValue()));
			if (!c.getType().equals(Card.Type.CHARACTER)) {
				assertFalse(white.isInnocent(Card.Type.CHARACTER, c.getValue()));
			} else {
				assertFalse(white.isInnocent(Card.Type.ROOM, c.getValue()));
			}

		}
	}
}
