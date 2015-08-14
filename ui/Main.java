/**
 *
 */
package ui;

import game.ActingOutOfTurnException;
import game.Game;
import game.GameStateModificationException;

/**
 * @author mckayvick
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Game g = new Game();
		try {
			new CluedoFrame(g);
			g.startGame();
		} catch (ActingOutOfTurnException e) {
			e.printStackTrace();
		} catch (GameStateModificationException m) {
			m.printStackTrace();
		}
	}
}
