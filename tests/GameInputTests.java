package tests;

import game.ActingOutOfTurnException;
import game.Game;
import game.GameStateModificationException;

import java.io.ByteArrayInputStream;

import org.junit.After;
import org.junit.Before;

public class GameInputTests {
	ByteArrayInputStream in;
	@Before
	public void setup() {
		in = new ByteArrayInputStream("\n".getBytes());
		System.setIn(in);
	}
	
	@After
	public void cleanup() {
		System.setIn(System.in);
	}

	public void test_charSelect() {
		Game g1 = new Game();
		try {
			g1.startGame();
		} catch (GameStateModificationException | ActingOutOfTurnException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
