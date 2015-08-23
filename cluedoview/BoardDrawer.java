package cluedoview;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import game.Card;
import game.Coordinate;
import game.Game;
import game.Board;
import game.BoardSquare;
import game.Player;
import game.Token;
import game.Weapon;

public class BoardDrawer {

	private Game cluedoGame;
	private Board cluedoBoard;
	private int squareSize;
	private HashSet<TokenDrawer> tokens;

	private Color brown = new Color(200, 170, 100);
	private Color hallwayTileColor = new Color(235, 235, 200);
	private Color emptyTileColor = new Color(75, 75, 25);

	public BoardDrawer(Game game){
		this.cluedoGame = game;
		this.cluedoBoard = game.getBoard();
		buildTokenDrawers();
	}

	private void buildTokenDrawers() {
		List<Token> gameTokens = cluedoGame.getTokens();
		this.tokens = new HashSet<TokenDrawer>();
		for(Token tok : gameTokens){
			tokens.add(new TokenDrawer(tok));
		}
	}

	/**
	 * SHOULD ONLY BE USED FOR TESTS
	 * @param b
	 */
	public BoardDrawer(Board b){
		this.cluedoBoard = b;
	}


	public void paintBoardAndTokens(Graphics g, Dimension d){
		calculateSquareSize(d);
		paintBoard(g);
		paintTokens(g);
		labelRooms(g);
	}

	private void paintTokens(Graphics g) {
		HashMap<String, ArrayList<TokenDrawer>> tokensInRooms =
				new HashMap<String, ArrayList<TokenDrawer>>();
		for(TokenDrawer wep : tokens){
			if(!cluedoBoard.isRoom(wep.getPosition())){
				wep.drawImage(g, squareSize);
			} else {
				String room = cluedoBoard.getRoom(wep.getPosition());
				if(tokensInRooms.containsKey(room)){
					tokensInRooms.get(room).add(wep);
				} else {
					ArrayList<TokenDrawer> tokenList = new ArrayList<TokenDrawer>();
					tokenList.add(wep);
					tokensInRooms.put(room, tokenList);
				}
			}
		}
		Map<String, Coordinate> roomCenters = cluedoBoard.getRoomCenters();
		// TODO make it get not so nasty when rooms get really full. I.E check if still drawing in room,
		// TODO change y values etc
		for(String rm : tokensInRooms.keySet()){
			Coordinate center = roomCenters.get(rm);
			int x = center.getX();
			int y = center.getY() - 1;
			int xShift = -1;
			Coordinate drawCoordinate = new Coordinate(x, y);
			for(TokenDrawer td : tokensInRooms.get(rm)){
				td.drawImage(g, squareSize, drawCoordinate);
				drawCoordinate = new Coordinate(drawCoordinate.getX() + xShift, drawCoordinate.getY());
				if(xShift < 0){
					xShift*=-1;
				} else {
					xShift++;
					xShift*=-1;
				}
			}
		}

	}


	public void drawBoard(Graphics g, Dimension d){
		calculateSquareSize(d);
		paintBoard(g);
		labelRooms(g);
	}

	private void paintBoard(Graphics g) {

		for(int i = 0; i < cluedoBoard.width(); i++){
			for(int j = 0; j < cluedoBoard.height(); j++){
				Coordinate spotToDraw = new Coordinate(i, j);
				if(cluedoBoard.isEmpty(spotToDraw)){
					g.setColor(emptyTileColor);
					g.fillRect(i*squareSize, j*squareSize, squareSize, squareSize);
				} else if(!cluedoBoard.isRoom(spotToDraw)){
					if(cluedoBoard.isHighlighted(spotToDraw)){
						g.setColor(Color.YELLOW);
					} else {
						g.setColor(hallwayTileColor);
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
		paintDoors(g);
		paintSecretPassages(g);

	}

	private void paintSecretPassages(Graphics g) {
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
			g.drawRect(x, y, squareSize, squareSize);
			for(int i = 0; i < 5; i++){
				g.drawRect(x, y+(ovalSize*i), squareSize, ovalSize);
			}
		}
	}

	private void labelRooms(Graphics g) {
		Map<String, Coordinate> roomCenters = cluedoBoard.getRoomCenters();
		g.setColor(Color.WHITE);
		for(String room : roomCenters.keySet()){
			int centerChar = room.length()/2;
			float fontSize = 0.8f * squareSize;
			g.setFont(g.getFont().deriveFont(fontSize));
			Coordinate center = roomCenters.get(room);
			int nameXOffSet = (int) (center.getX()*squareSize + squareSize/2 - (centerChar * fontSize)/3);
			int nameYOffSet = center.getY()*squareSize + squareSize;
			g.drawString(room, nameXOffSet, nameYOffSet);
		}

	}

	private void paintDoors(Graphics g) {
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

	private void calculateSquareSize(Dimension d) {
		squareSize = (int) Math.min((d.getWidth()/cluedoBoard.width()), (d.getHeight()/cluedoBoard.height()));
	}

}
