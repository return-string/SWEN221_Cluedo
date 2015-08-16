package ui;

import java.awt.Graphics;
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
	
	public void nextTurn(){
		gameFrame.nextTurn();
	}
	
	public void repaintBoard(Graphics g){
		if(cluedoGame != null){
			cluedoGame.repaintBoard(g);
		}
	}
	
	public void testHypothesis(Set<String> hypothesis){
		if(cluedoGame != null){
			cluedoGame.testHypothesis(hypothesis);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("New Game")){
			gameFrame.showGameSetup();
		}
		if(e.getActionCommand().equals("Rules")){
			gameFrame.showRules();
		}
		if(e.getActionCommand().equals("Exit")){
			gameFrame.exit();
		}
		if(e.getActionCommand().equals("Roll Dice")){
			if(cluedoGame != null){
				cluedoGame.rollDice();
			}
		}
	}
	
	public boolean checkGameState(){
		if(cluedoGame != null){
			return cluedoGame.isPlaying();
		}
		return true;
	}

}
