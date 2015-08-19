package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import game.ActingOutOfTurnException;
import game.Game;
import game.GameStateModificationException;

import java.util.EventListener;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements ActionListener, EventListener {
	
	private Game cluedoGame;
	private CluedoFrame gameFrame;
	
	public Controller(CluedoFrame gameFrame){
		this.gameFrame = gameFrame;
	}
	
	public void startGame(Map<String,String> players){
            this.cluedoGame = new Game(players);
	}
	
	public void nextTurn(){
		gameFrame.nextTurn();
	}
	
	public void repaintBoard(Graphics g, Dimension d){
		if(cluedoGame != null){
			cluedoGame.repaintBoard(g, d);
		}
	}
	
	public void testHypothesis(Set<String> hypothesis){
		if(cluedoGame != null){
			try {
				cluedoGame.testHypothesis(hypothesis);
			} catch (ActingOutOfTurnException e) {
			}
		}
	}
	
        public void setupGame(Map<String,String> playersToCharacters) {
            try { 
                cluedoGame.startGame();
            } catch (GameStateModificationException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, "Tried to start a game already in progress.", ex);
            } catch (ActingOutOfTurnException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, "Tried to start a game already in progress.", ex);
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
			gameFrame.dispose();
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
