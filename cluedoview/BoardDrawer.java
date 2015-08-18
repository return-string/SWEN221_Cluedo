package cluedoview;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import game.Coordinate;
import game.Game;
import game.Board;
import game.BoardSquare;
import game.Player;

public class BoardDrawer {
	
	private Game cluedoGame;
	private Board cluedoBoard;
	
	public BoardDrawer(Game game){
		this.cluedoGame = game;
		this.cluedoBoard = game.getBoard();
	}
	
	/**
	 * SHOULD ONLY BE USED FOR TESTS
	 * @param b
	 */
	public BoardDrawer(Board b){
		this.cluedoBoard = b;
	}
	
	
	public void paintBoardAndTokens(Graphics g, Dimension d){
		paintBoard(g, d);
		
	}

	public void paintBoard(Graphics g, Dimension d) {
		int squareSize = calculateSquareSize(cluedoBoard, d);
		for(int i = 0; i < cluedoBoard.width(); i++){
			for(int j = 0; j < cluedoBoard.height(); j++){
				Coordinate spotToDraw = new Coordinate(i, j);
				if(cluedoBoard.isEmpty(spotToDraw)){
					g.setColor(Color.BLACK);
					g.fillRect(i*squareSize, j*squareSize, squareSize, squareSize);
				} else if(!cluedoBoard.isRoom(spotToDraw)){
					g.setColor(Color.YELLOW);
					g.fillRect(i*squareSize, j*squareSize, squareSize, squareSize);
					g.setColor(Color.BLACK);
					g.drawRect(i*squareSize, j*squareSize, squareSize, squareSize);
				} else {
					g.setColor(Color.BLUE);
					g.fillRect(i*squareSize, j*squareSize, squareSize, squareSize);
					g.setColor(Color.WHITE);
					int nameXOffSet = i*squareSize + squareSize/2;
					int nameYOffSet = j*squareSize + squareSize/2;
					g.drawString(cluedoBoard.getRoom(spotToDraw), nameXOffSet, nameYOffSet);
				}
			}
		}
		
	}

	private int calculateSquareSize(Board board, Dimension d) {
		return (int) Math.min((d.getWidth()/board.width()), (d.getHeight()/board.height()));
	}
	
}
