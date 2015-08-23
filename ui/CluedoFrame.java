package ui;

import game.Game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class CluedoFrame extends JFrame {
	private static final long serialVersionUID = 2171235413597916591L;
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
	private CluedoPanel[] panels; 

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
		this.gameSetup = new GameSetupPanel(controller);
		this.currentPanel = this.menu;
		panels = new CluedoPanel[] { menu, gameSetup, turnPanel, gameOver, rules};
		
		setIconImage(icon());
		add(this.currentPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800,600)); // V: just added this for sizing!
		pack(); // pack components tightly together
		setResizable(false); // prevent us from being resizeable
		setVisible(true); // make sure we are visible!
	}

	private Image icon() {
		Image icon;
		java.net.URL url = ClassLoader.getSystemResource("assets/imgs/icon/clue_ico.png");
		Toolkit kit = Toolkit.getDefaultToolkit();
		icon = kit.createImage(url);
		return icon;
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

	/** actual method */
	public void showGameSetup(){
		if(this.gameSetup == null){
			this.gameSetup = new GameSetupPanel(controller);
		}
		this.currentPanel = this.gameSetup;
		add(this.currentPanel);
		gameSetup.showDialog();
		try {
			gameSetup.getDialog().addWindowListener(new java.awt.event.WindowAdapter() {
			    @Override
			    public void windowClosing(java.awt.event.WindowEvent e) {
			    	Map<String,String> players = gameSetup.getResult();
//			        for (Map.Entry<String, String> p : players.entrySet()) {
//			        	System.err.println(p.getValue()+", "+p.getKey());
//			        }
			        if (players != null && players.size() > 3) { // make sure the user finished entering info
			        	controller.startGame(players);
			        }
			    }
			});
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** dummy method; replaces need for GameSetupPanel 
	 * (Default: 6 players, named "Player "+ 1-6 */
	public void testingGameSetup(){
		controller.startGame(Game.createDefaultMap());
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
