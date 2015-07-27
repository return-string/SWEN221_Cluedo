/**
 *
 */
package tests;
import org.junit.*;

import static org.junit.Assert.*;
import game.*;

import java.util.*;
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
	public void test2_createHand() {
		List<Card> hand = new ArrayList<Card>();
		Player p = new Player(Game.Characters.WHITE,hand);
		assertFalse("Player should have a hand!",p.getHand()==null);
		assertTrue("Player must have a colour!",p.character==Game.Characters.WHITE);
	}

	@Test
	public void test3_dealHand() {
		List<Game.Characters> characters = new ArrayList<Game.Characters>();
		characters.add(Game.Characters.SCARLET);
		characters.add(Game.Characters.PLUM);
		characters.add(Game.Characters.WHITE);
		Game g = new Game(characters);
		g.initialiseGame();
	}

	@Test
	public void test4_guiltOfInnocentTypes() {
		List<Card> hand = new ArrayList<Card>();
		hand.add(new CharacterCard(Game.Characters.GREEN));
		hand.add(new WeaponCard(Game.Weapons.ROPE));
		hand.add(new RoomCard(Game.Rooms.DINING));
		Player p = new Player(Game.Characters.WHITE,hand);
		assertTrue(Game.Characters.GREEN +" should be innocent if "+p.toString()+" has seen this card!",p.isInnocent(Game.Characters.GREEN));
		assertTrue(Game.Weapons.ROPE +" should be innocent if "+p.toString()+" has seen this card!",p.isInnocent(Game.Weapons.ROPE));
		assertTrue(Game.Rooms.DINING +" should be innocent if "+p.toString()+" has seen this card!",p.isInnocent(Game.Rooms.DINING));
	}

	@Test
	public void test5_guiltOfUnprovenTypes() {
		List<Card> hand = new ArrayList<Card>();
		hand.add(new CharacterCard(Game.Characters.GREEN));
		hand.add(new WeaponCard(Game.Weapons.ROPE));
		hand.add(new RoomCard(Game.Rooms.DINING));
		Player p = new Player(Game.Characters.WHITE,hand);
		assertFalse(Game.Characters.SCARLET+" shouldn't be innocent if "+p.toString()+" has not seen this card!",p.isInnocent(Game.Characters.SCARLET));
		assertFalse(Game.Weapons.PIPE +" shouldn't be innocent if "+p.toString()+" has not seen this card!",p.isInnocent(Game.Weapons.PIPE));
		assertFalse(Game.Rooms.KITCHEN +" shouldn't be innocent if "+p.toString()+" has not seen this card!",p.isInnocent(Game.Rooms.KITCHEN));
	}

	//@Test
	//public void test6_guiltOfInnocentCards()


	// ========= helper methods! =============

	private List<Card> fillHand(List<Card> list, Card[] cards) {
		for (int i = 0; i < cards.length; i++) {
			list.add(cards[i]);
		}
		return list;
	}
}
