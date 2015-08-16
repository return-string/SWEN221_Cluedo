package ui;

import javax.swing.JPanel;

public abstract class AbstractPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private final Controller C; 

	public AbstractPanel(Controller c) {
		C = c;
	}
}