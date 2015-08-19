package cluedoview;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Set;

import game.Coordinate;
import game.Game;
import game.Board;
import game.BoardSquare;
import game.Player;

public class BoardDrawer {

	private Game cluedoGame;
	private Board cluedoBoard;

	private Color brown = new Color(250, 200, 120);

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
					g.setColor(Color.BLUE);
					g.fillRect(i*squareSize, j*squareSize, squareSize, squareSize);
				} else if(!cluedoBoard.isRoom(spotToDraw)){
					if(cluedoBoard.isHighlighted(spotToDraw)){
						g.setColor(Color.YELLOW);
					} else {
						g.setColor(Color.WHITE);
					}
					g.fillRect(i*squareSize, j*squareSize, squareSize, squareSize);
					g.setColor(Color.BLACK);
					g.drawRect(i*squareSize, j*squareSize, squareSize, squareSize);
				} else {
					g.setColor(Color.GRAY);
					g.fillRect(i*squareSize, j*squareSize, squareSize, squareSize);
				}
			}
		}
		paintDoors(g, squareSize);
		paintSecretPassages(g, squareSize);
		labelRooms(g, squareSize);
	}

	private void paintSecretPassages(Graphics g, int squareSize) {
		Set<Coordinate> secretPassages = cluedoBoard.getSecretPassages();
		for(Coordinate c : secretPassages){
			g.setColor(brown);
			int x = c.getX()*squareSize;
			int y = c.getY()*squareSize;
			g.fillRect(x, y, squareSize, squareSize);
			int ovalSize = squareSize/5;
			g.setColor(Color.GRAY);
			g.fillOval(x+ovalSize*2, y+ovalSize, ovalSize, ovalSize);
			g.setColor(Color.black);
			for(int i = 0; i < 5; i++){
				g.drawRect(x, y+(ovalSize*i), squareSize, ovalSize);
			}
		}
	}

	private void labelRooms(Graphics g, int squareSize) {
		Set<Coordinate> roomCenters = cluedoBoard.getRoomCenters();
		g.setColor(Color.WHITE);
		for(Coordinate c : roomCenters){
			String name = cluedoBoard.getRoom(c);
			int centerChar = name.length()/2;
			float fontSize = 0.8f * squareSize;
			g.setFont(g.getFont().deriveFont(fontSize));
			int nameXOffSet = (int) (c.getX()*squareSize + squareSize/2 - (centerChar * fontSize)/3);
			int nameYOffSet = c.getY()*squareSize + squareSize;
			g.drawString(name, nameXOffSet, nameYOffSet);
		}

	}

	private void paintDoors(Graphics g, int squareSize) {
		g.setColor(brown);
		int arcSize = squareSize*2;
		for(Coordinate c : cluedoBoard.northFacingDoors()){
			g.fillArc((c.getX()-1)*squareSize, (c.getY()-1)*squareSize, arcSize, arcSize, 0, -45);
		}
		for(Coordinate c : cluedoBoard.southFacingDoors()){
			g.fillArc((c.getX()-1)*squareSize, (c.getY())*squareSize, arcSize, arcSize, 0, 45);
		}
		for(Coordinate c : cluedoBoard.westFacingDoors()){
			g.fillArc((c.getX()-1)*squareSize, (c.getY()-1)*squareSize, arcSize, arcSize, -90, 45);
		}
		for(Coordinate c : cluedoBoard.eastFacingDoors()){
			g.fillArc((c.getX())*squareSize, (c.getY()-1)*squareSize, arcSize, arcSize, -90, -45);
		}
		g.setColor(Color.GRAY);
		arcSize = squareSize*2-7;
		for(Coordinate c : cluedoBoard.northFacingDoors()){
			g.fillArc((c.getX()-1)*squareSize+5, (c.getY()-1)*squareSize+4, arcSize, arcSize, 0, -45);
		}
		for(Coordinate c : cluedoBoard.southFacingDoors()){
			g.fillArc((c.getX()-1)*squareSize+6, (c.getY())*squareSize+3, arcSize, arcSize, 0, 45);
		}
		for(Coordinate c : cluedoBoard.westFacingDoors()){
			g.fillArc((c.getX()-1)*squareSize+3, (c.getY()-1)*squareSize+6, arcSize, arcSize, -90, 45);
		}
		for(Coordinate c : cluedoBoard.eastFacingDoors()){
			g.fillArc((c.getX())*squareSize+3, (c.getY()-1)*squareSize+6, arcSize, arcSize, -90, -45);
		}
	}

	private int calculateSquareSize(Board board, Dimension d) {
		return (int) Math.min((d.getWidth()/board.width()), (d.getHeight()/board.height()));
	}

}
