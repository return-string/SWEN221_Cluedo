package tests;
import static org.junit.Assert.*;

import org.junit.Test;

import game.Card;
import game.Game;
import game.GameStateModificationException;
import game.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ReflectPermission;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameTests {

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
		for (int i = 0; i < 3; i++) {
			try {
				g2.addPlayer(i);
				g1.addPlayer(i);
			} catch (GameStateModificationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		assertTrue("The player lists should be the same for these games.",
				g1.getPlayers().equals(g2.getPlayers()));
		Object[] g1Arr = g1.getPlayers().toArray();
		Object[] g2Arr = g2.getPlayers().toArray();
		assertTrue("The first player should be the same in these games. ("+ g1Arr[0] +" =/= "+ g2Arr[0] +")",
				g1Arr[0].equals(g2Arr[0]));
	}
	
	@Test
	public void test3_winning() {
		
	}
}
