package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class CluedoFrame extends JFrame {

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
		if(this.gameSetup == null){
			this.gameSetup = new GameSetupPanel(controller);
		}
		remove(this.currentPanel);
		this.currentPanel = this.gameSetup;
		add(this.currentPanel);
	}


}
