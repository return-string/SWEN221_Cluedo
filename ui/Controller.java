package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import game.ActingOutOfTurnException;
import game.Coordinate;
import game.Game;
import game.GameStateModificationException;

import java.util.EventListener;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import cluedoview.BoardDrawer;

public class Controller implements ActionListener, EventListener {

	private Game cluedoGame;
	private CluedoFrame gameFrame;
	private BoardDrawer boardDrawer;

	public Controller(CluedoFrame gameFrame){
		this.gameFrame = gameFrame;
	}

	public void startGame(Map<String,String> players){
        this.cluedoGame = new Game(players);
        this.boardDrawer = new BoardDrawer(this.cluedoGame);
	}

	public void nextTurn(){
		gameFrame.nextTurn();
	}

	public void repaintBoard(Graphics g, Dimension d){
		if(cluedoGame != null){
			this.boardDrawer.paintBoardAndTokens(g, d);
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

	public Coordinate getBoardRowsCols(){
		return new Coordinate(cluedoGame.getBoard().width(), cluedoGame.getBoard().height());
	}

	public void movePlayer(Coordinate boardCoord) {
		try {
			cluedoGame.movePlayer(boardCoord);
		} catch (ActingOutOfTurnException e) {

		}

	}
}
