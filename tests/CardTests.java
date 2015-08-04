package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import game.Card;
import game.Game;
import game.GameStateModificationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class CardTests {
	private static final long seed = 304494949;

	public static List<Card> sameDeck() {
		List<Card> deck = Game.createNewDeck(Card.SCARLET,Card.DAGGER,Card.CONSERVATORY);
		Collections.sort(deck);
		Collections.shuffle(deck, new Random(seed));
		return deck;
	}

	@Test
	public void test1_hypothesisEquality() {
		System.out.println("\tTEST HYPOTHESIS/CARD EQUALITY");
		Game g1 = new Game();
		Game g2 = new Game();
		List<String> p1 = new ArrayList<String>();
		List<String> p2 = new ArrayList<String>();

		for (String s : Card.CHARACTERS) {
			p1.add(s);
			p2.add(s);
		}
		try {
			g1.addCharactersByName(p1);
			g2.addCharactersByName(p2);
		} catch (GameStateModificationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g1.initialiseDeck(Card.SCARLET, Card.PIPE, Card.BALL);
		g2.initialiseDeck(Card.SCARLET, Card.PIPE, Card.BALL);
		assertEquals(g1.getGuilty(),g2.getGuilty());
	}

	@Test
	/** Tests the natural sort of Cards */
	public void test2_cardSort1() {
		List<Card> cards = sameDeck();
		assertEquals(cards,sameDeck());
		Collections.sort(cards);
		List<Card> deck = sameDeck();
		Collections.sort(deck);
		assertEquals(cards,deck);
	}

	@Test
	/** Also tests the natural sort of Cards, small-scale. */
	public void test3_cardSort2() {
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
	@Test
	/** Also tests the natural sort of Cards, small-scale. */
	public void test4_cardSort2() {
		Card green1 = new Card(Card.Type.CHARACTER, Card.GREEN);
		Card green2 = new Card(Card.Type.CHARACTER, Card.GREEN);

		assertEquals(green1,green2);
		assertEquals(green1.hashCode(),green2.hashCode());
		assertNotEquals(green1.hashCode(),new Card(Card.Type.ROOM,Card.CONSERVATORY));
	}
	// ========= helper methods! =============

	public static List<Card> fillHandWith(List<Card> list, Card[] cards) {
		for (int i = 0; i < cards.length; i++) {
			list.add(cards[i]);
		}
		return list;
	}

}
