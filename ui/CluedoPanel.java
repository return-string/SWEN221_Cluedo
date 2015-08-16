package ui;

import javax.swing.JPanel;

abstract class CluedoPanel extends JPanel {
	
	private final Controller C;
	
	public CluedoPanel(Controller c){
		C = c;
	}
	
	public abstract void nextTurn();
	
}
