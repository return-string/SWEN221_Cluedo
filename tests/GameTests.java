package tests;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.management.InvalidAttributeValueException;

import jdk.nashorn.internal.ir.SetSplitState;

import org.junit.Test;

import game.ActingOutOfTurnException;
import game.Card;
import game.Game;
import game.GameStateModificationException;
import game.Player;

public class GameTests {
	private Game setupGame() {
		Game g = new Game();
		List<String> names = new ArrayList<String>();
		names.add(Card.SCARLET);
		names.add(Card.PLUM);
		names.add(Card.MUSTARD);
		try {
			g.addCharactersByName(names);
		} catch (GameStateModificationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			g.playGame();
		} catch (ActingOutOfTurnException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return g;
	}

	@Test
	public void test1_newGamePlayerEquality() {
		System.out.println("\tTEST EMPTY PLAYER SET EQUALITY");
		Game g1 = new Game();
		Game g2 = new Game();
		assertEquals("",g1.getPlayers(),g2.getPlayers());
	}

	@Test
	public void test2_playerListEquality() {
		System.out.println("\tTEST HYPOTHESIS/CARD EQUALITY");
		Game g1 = new Game();
		Game g2 = new Game();
		
		Set<String> s1 = new HashSet<String>();
		Set<String> s2 = new HashSet<String>();
		
		s1.add(Card.SCARLET);
		s2.add(Card.SCARLET);
		s1.add(Card.GREEN);
		s2.add(Card.GREEN);
		s1.add(Card.PLUM);
		s2.add(Card.PLUM);
		
		try {
			g1.startGame(s1);
			g2.startGame(s2);
		} catch (GameStateModificationException | ActingOutOfTurnException e) {
			fail();
		}
		assertTrue("The player lists should be the same for these games.",
				g1.getPlayers().equals(g2.getPlayers()));
		Object[] g1Arr = g1.getPlayers().toArray();
		Object[] g2Arr = g2.getPlayers().toArray();
		assertTrue("The first player should be the same in these games. ("+ g1Arr[0] +" =/= "+ g2Arr[0] +")",
				g1Arr[0].equals(g2Arr[0]));
	}
	
	@Test
	public void invalidNumOfPlayers() {
		Game g1 = new Game();
		List<String> names = new ArrayList<String>();
		names.add(Card.SCARLET);
		names.add(Card.PLUM);
		try {
			g1.addCharactersByName(names);
		} catch (GameStateModificationException e1) {
			fail();
		}
		try {
			g1.playGame();
		} catch (ActingOutOfTurnException e) {
			fail();
		}
		fail();
	}
}
