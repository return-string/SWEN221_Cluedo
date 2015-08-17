package ui;

import javax.swing.JPanel;

abstract class CluedoPanel extends JPanel {
	
	private final Controller C;
	
	public CluedoPanel(Controller c){
		C = c;
	}
        
        public Controller controller() { return C; }
	
	public abstract void nextTurn();
	
}
