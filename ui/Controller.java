package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import game.ActingOutOfTurnException;
import game.Board;
import game.Coordinate;
import game.Game;
import game.Player;

import java.util.EventListener;
import java.util.List;
import java.util.Map;

import cluedoview.BoardDrawer;

public class Controller implements ActionListener, EventListener {

	private Game cluedoGame;
	private Board cluedoBoard;
	private CluedoFrame gameFrame;
	private BoardDrawer boardDrawer;

	public Controller(CluedoFrame gameFrame){
		this.gameFrame = gameFrame;
	}

	public void startGame(Map<String,String> players){
        this.cluedoGame = new Game(players);
        this.boardDrawer = new BoardDrawer(this.cluedoGame);
        this.cluedoBoard = cluedoGame.getBoard();
		System.err.println("got "+2);
        gameFrame.showPanel(2);
		System.err.println("done");
	}

	public void startTestGame(Map<String,String> players){
        this.cluedoGame = new Game(players);
        this.boardDrawer = new BoardDrawer(this.cluedoGame);
        this.cluedoBoard = cluedoGame.getBoard();
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

	public void testAccusation(Set<String> hypothesis){
		if(cluedoGame != null){
			try {
				cluedoGame.testAccusation(hypothesis);
			} catch (ActingOutOfTurnException e) {
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("New Game")){
//			gameFrame.showPanel(CluedoFrame.GAMESETUP_PANEL);
			// REAL gameFrame.showGameSetup();
			gameFrame.testingGameSetup(); // TODO fake demo method!
		}
		if(e.getActionCommand().equals("Rules")){
			gameFrame.showPanel(CluedoFrame.RULES_PANEL);
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

	public Player getCurrentPlayer() {
		return cluedoGame.getCurrentPlayer();
	}

	public List<Player> getPlayers() {
		return cluedoGame.getPlayers();
	}

	public boolean coordinateInRoom(Coordinate boardCoord){
		return cluedoBoard.isRoom(boardCoord);
	}

	/**
	 * Get the name of the room argument coordinate is part of
	 * Returns null if coordinate invalid for board
	 * @param boardCoord Coordinate to get room name of
	 * @return name of room. Null if coordinate invalid
	 */
	public String getRoomName(Coordinate boardCoord){
		try{
			return cluedoBoard.getRoom(boardCoord);
		} catch (IllegalArgumentException e){
			return null;
		}
	}

	/**
	 * For use of BoardPanel to tell board to highlight rooms according to
	 * a coordinate. If inRoom true, highlights the room the boardCoord is part of
	 * Otherwise tells board to clear highlighted rooms
	 * @param boardCoord
	 * @param inRoom
	 */
	public void highlightRoom(Coordinate boardCoord, boolean inRoom) {
		if(inRoom){
			cluedoBoard.highlightSquare(boardCoord);
		} else {
			cluedoBoard.unhighlightRooms();
		}
	}
}
