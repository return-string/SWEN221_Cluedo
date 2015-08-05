package game;

/** This exception is thrown if the Game tries to act on a
 * player when it is not their turn-- eg, printing the wrong player's
 * hand. */
public class ActingOutOfTurnException extends Exception {
	private static final long serialVersionUID = -3105780241566481185L;

	public ActingOutOfTurnException() {
	}

	public ActingOutOfTurnException(String string) {
		super(string);
	}

}
