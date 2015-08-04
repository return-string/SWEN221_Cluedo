/**
 * 
 */
package game;

/**
 * This Exception is thrown by a Game when methods are called that
 * would modify its state while the game is being played-- eg,
 * removing or adding players, giving cards.
 * @author Vicki
 *
 */
public class GameStateModificationException extends Exception {
	private static final long serialVersionUID = 6025660889236356099L;

	/**
	 * 
	 */
	public GameStateModificationException() {
	}

	/**
	 * @param arg0
	 */
	public GameStateModificationException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public GameStateModificationException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public GameStateModificationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public GameStateModificationException(String arg0, Throwable arg1,
			boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
