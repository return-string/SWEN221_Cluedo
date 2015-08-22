package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JFrame;

public class CluedoFrame extends JFrame {
	
	public static final int MENU_PANEL = 0;
	public static final int GAMESETUP_PANEL = 1;
	public static final int TURN_PANEL = 2;
	public static final int GAMEOVER_PANEL = 3;
	public static final int RULES_PANEL = 4;
	
	private final Controller controller;
	private CluedoPanel currentPanel;
	private MenuPanel menu;
	private GameOverPanel gameOver;
	private RulesPanel rules;
	private TurnPanel turnPanel;
	private GameSetupPanel gameSetup;
	private CluedoPanel[] panels = {menu, gameSetup, turnPanel, gameOver, rules}; 

	public CluedoFrame() {
		super("Cluedo");
		this.controller = new Controller(this);
		setLayout(new BorderLayout()); // use border layout
		// this.canvas = new CluedoCanvas(cluedoGame);
		// add(canvas, BorderLayout.CENTER); // add canvas
		this.menu = new MenuPanel(controller);
		this.rules = new RulesPanel(controller);
		this.turnPanel = new TurnPanel(controller);
		this.gameOver = new GameOverPanel(controller);
		// TODO Uncomment when fixed
		//this.gameSetup = new GameSetupPanel(controller);
		this.currentPanel = this.menu;
		add(this.currentPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800,600)); // V: just added this for sizing!
		pack(); // pack components tightly together
		setResizable(false); // prevent us from being resizeable
		setVisible(true); // make sure we are visible!

	}


	public void repaint(){
		super.repaint();
		//this.currentPanel.repaint();
	}
	
	public void showPanel(int panelNo){
		if(panelNo < 0 || panelNo >= panels.length){
			throw new IllegalArgumentException("Not a valid panel number");
		}
		CluedoPanel toDisplay = panels[panelNo];
		displayPanel(toDisplay);
	}
	
	private void displayPanel(CluedoPanel toDisplay) {
		remove(this.currentPanel);
		this.currentPanel = toDisplay;
		add(this.currentPanel, BorderLayout.CENTER);
		pack();
	}

	public void nextTurn(){
		this.currentPanel.nextTurn();
	}

	/**
	 * FOR TESTING ONLY
	 */
	public void showBoardPanel(HashMap<String, String> players){
		this.controller.startTestGame(players);
		BoardPanel bp = new BoardPanel(this.controller);
		displayPanel(bp);
	}

}
