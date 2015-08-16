package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import game.Game;

public class Controller implements ActionListener {
	
	private Game cluedoGame;
	private CluedoFrame gameFrame;
	
	public Controller(CluedoFrame gameFrame){
		this.gameFrame = gameFrame;
	}
	
	public void startGame(Set<String> players){
		this.cluedoGame = new Game(players);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
