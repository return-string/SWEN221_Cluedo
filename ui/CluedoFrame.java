package ui;

import game.Game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Map;

import javax.swing.JFrame;

public class CluedoFrame extends JFrame {
	private static final long serialVersionUID = 2171235413597916591L;
	private final Controller controller;
	private CluedoPanel currentPanel;
	private MenuPanel menu;
	private GameOverPanel gameOver;
	private RulesPanel rules;
	private TurnPanel turnPanel;
	private GameSetupPanel gameSetup;

	public CluedoFrame() {
		super("Cluedo");
		this.controller = new Controller(this);
		setLayout(new BorderLayout()); // use border layout
		// this.canvas = new CluedoCanvas(cluedoGame);
		// add(canvas, BorderLayout.CENTER); // add canvas
		this.menu = new MenuPanel(controller);
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
		this.currentPanel.repaint();
	}

	public void showRules(){
		if(rules == null){
			this.rules = new RulesPanel(controller);
		}
		remove(this.currentPanel);
		this.currentPanel = this.rules;
		add(this.currentPanel);
	}

	public void showTurn(){
		if(turnPanel == null){
			this.turnPanel = new TurnPanel(controller);
		}
		remove(this.currentPanel);
		this.currentPanel = this.turnPanel;
		add(this.currentPanel);
	}

	public void showGameOver(){
		if(this.gameOver == null){
			this.gameOver = new GameOverPanel(controller);
		}
		remove(this.currentPanel);
		this.currentPanel = this.gameOver;
		add(this.currentPanel);
	}

	public void nextTurn(){
		this.currentPanel.nextTurn();
	}

	public void showGameSetup(){
		// THE ACTUAL CODE IS COMMENTED OUT BELOW
		// IT'S BEEN COMMENTED OUT SO WE DON'T HAVE TO SELECT CHARACTERS ALL THE TIME
		// WHENEVER WE'RE TESTING THIS.
		if(this.gameSetup == null){
			//Keep: this.gameSetup = new GameSetupPanel(controller);
		}
		remove(this.currentPanel);
//KEEP:	this.currentPanel = this.gameSetup;
//		add(this.currentPanel);
//      gameSetup.getDialog().addWindowListener(new java.awt.event.WindowAdapter() {
//            @Override
//            public void windowClosing(java.awt.event.WindowEvent e) {
//            	Map<String,String> players = gameSetup.getResult();
//                for (Map.Entry<String, String> p : players.entrySet()) {
//                	System.err.println(p.getValue()+", "+p.getKey());
//                }
//                if (players != null && players.size() > 3) { // make sure the user finished entering info
//                	controller.startGame(players);
//                }
//            }
//        });
		controller.startGame(Game.createDefaultMap()); // throw away
	}

}
