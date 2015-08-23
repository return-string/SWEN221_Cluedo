package ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

abstract class CluedoPanel extends JPanel {
	private static final long serialVersionUID = 5699276180798470094L;
	private final Controller C;
	
	public CluedoPanel(Controller c){
		C = c;
	}
	
	/** Apparently, JPanels need to be initialised with a layout sometimes! */
	public CluedoPanel(Controller c, BorderLayout bl) {
		super(bl);
		C = c;
	}
        
    public Controller controller() { return C; }
	
	public abstract void nextTurn();
	
}
