package tests;
import static org.junit.Assert.*;

import org.junit.Test;

import game.Card;
import game.Game;
import game.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameTests2 {
	private final long seed = 304494949;

	public List<Card> sameDeck() {
		List<Card> deck = Game.createNewDeck(Card.SCARLET,Card.DAGGER,Card.CONSERVATORY);
		Collections.sort(deck);
		Collections.shuffle(deck, new Random(seed));
		return deck;
	}

	@Test
	public void test1() {
		System.out.println("\tTEST EMPTY PLAYER SET EQUALITY");
		Game g1 = new Game();
		Game g2 = new Game();
		assertEquals("",g1.getPlayers(),g2.getPlayers());
	}

	@Test
	public void test2() {
		System.out.println("\tTEST HYPOTHESIS/CARD EQUALITY");
		Game g1 = new Game();
		Game g2 = new Game();
		g1.initialiseDeck(Card.SCARLET, Card.PIPE, Card.BALL);
		g2.initialiseDeck(Card.SCARLET, Card.PIPE, Card.BALL);
		assertEquals(g1.getGuilty(),g2.getGuilty());
	}

	@Test
	public void test3() {
		System.out.println("\tTEST HYPOTHESIS/CARD EQUALITY");
		Game g1 = new Game();
		Game g2 = new Game();
		for (int i = 0; i < 3; i++) {
			g1.addPlayer(i);
			g2.addPlayer(i);
		}
		assertEquals("The player lists should be the same for these games. ("+g1.getPlayers().toString()+" =/= "+ g2.getPlayers().toString() +")",
				g1.getPlayers(),g2.getPlayers());
		Object[] g1Arr = g1.getPlayers().toArray();
		Object[] g2Arr = g2.getPlayers().toArray();
		assertTrue("The first player should be the same in these games. ("+ g1Arr[0] +" =/= "+ g2Arr[0] +")",
				g1Arr[0].equals(g2Arr[0]));
	}

	@Test
	/** Tests the natural sort of Players */
	public void test4() {
		Player[] ps = { new Player(Card.MUSTARD),
				new Player(Card.SCARLET),
				new Player(Card.PLUM),
				new Player(Card.WHITE),
				new Player (Card.PEACOCK),
				new Player (Card.GREEN)
			};
		List<Player> players = new ArrayList<Player>();
		for (int i = 0; i < ps.length; i++) {
			players.add(ps[i]);
		}
		List<Player> ps2 = new ArrayList<Player>();
		ps2.add(new Player(Card.SCARLET));
		ps2.add(new Player(Card.MUSTARD));
		ps2.add(new Player(Card.WHITE));
		ps2.add(new Player(Card.GREEN));
		ps2.add(new Player(Card.PEACOCK));
		ps2.add(new Player(Card.PLUM));
		Collections.sort(players);
		assertEquals(players.toString() +" \n=/=\n "+ ps2.toString()+"\n",ps2,players);
	}

	@Test
	/** Tests the natural sort of Cards */
	public void test5() {
		List<Card> cards = new ArrayList<Card>();
		cards.addAll(sameDeck());
		assertEquals(cards,sameDeck());
		Collections.sort(cards);
		List<Card> deck = sameDeck();
		Collections.sort(deck);
		assertEquals(cards,deck);
	}

	@Test
	/** Also tests the natural sort of Cards, small-scale. */
	public void test6() {
		List<Card> cards = new ArrayList<Card>();
		cards.add(new Card(Card.Type.CHARACTER, Card.GREEN));
		cards.add(new Card(Card.Type.WEAPON, Card.DAGGER));
		cards.add(new Card(Card.Type.ROOM, Card.BILLIARD));

		List<Card> sortedCards = new ArrayList<Card>();
		sortedCards.add(new Card(Card.Type.WEAPON, Card.DAGGER));
		sortedCards.add(new Card(Card.Type.ROOM, Card.BILLIARD));
		sortedCards.add(new Card(Card.Type.CHARACTER, Card.GREEN));
		Collections.sort(sortedCards);

		assertEquals(cards,sortedCards);
	}
}
