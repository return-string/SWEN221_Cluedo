package ui;

import java.awt.event.ActionEvent;

public class RequestRulesEvent extends ActionEvent {

	public RequestRulesEvent(Object source, int id, String command) {
		super(source, id, command);
		// TODO Auto-generated constructor stub
	}

	public RequestRulesEvent(Object source, int id, String command,
			int modifiers) {
		super(source, id, command, modifiers);
		// TODO Auto-generated constructor stub
	}

	public RequestRulesEvent(Object source, int id, String command, long when,
			int modifiers) {
		super(source, id, command, when, modifiers);
		// TODO Auto-generated constructor stub
	}

}