package ui;

import java.awt.event.ActionEvent;

public class RollDiceEvent extends ActionEvent {

	public RollDiceEvent(Object source, int id, String command, long when,
			int modifiers) {
		super(source, id, command, when, modifiers);
		// TODO Auto-generated constructor stub
	}

	public RollDiceEvent(Object source, int id, String command, int modifiers) {
		super(source, id, command, modifiers);
		// TODO Auto-generated constructor stub
	}

	public RollDiceEvent(Object source, int id, String command) {
		super(source, id, command);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
