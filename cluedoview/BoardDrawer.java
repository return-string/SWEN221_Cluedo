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

import game.Board.Compass;
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
	
	/**
	 * Builds a TokenDrawer object from each of the tokens retrieved from 
	 * cluedoGame
	 */
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

	/**
	 * Draws a visual representation of the board and the tokens on it based on
	 * the information retrieved from cluedoGame and cluedoBoard
	 * @param g Graphics object to draw board on
	 * @param d Dimension used to calculate size of board squares
	 */
	public void paintBoardAndTokens(Graphics g, Dimension d){
		calculateSquareSize(d);
		paintBoard(g);
		paintTokens(g);
		labelRooms(g);
	}
	
	/**
	 * Uses the TokenDrawers to paint the tokens onto the graphics object. If tokens
	 * are in hallway, paint them at their coordinate. If tokens are in a room, paint
	 * them at neat positions around the center of the room.
	 * @param g
	 */
	private void paintTokens(Graphics g) {
		//map to store which tokens are in which room
		HashMap<String, ArrayList<TokenDrawer>> tokensInRooms =
				new HashMap<String, ArrayList<TokenDrawer>>();
		for(TokenDrawer wep : tokens){
			if(!cluedoBoard.isRoom(wep.getPosition())){//if in hallway
				wep.drawImage(g, squareSize);//draw at token's coordinate
			} else {
				String room = cluedoBoard.getRoom(wep.getPosition());
				if(tokensInRooms.containsKey(room)){
					//add token to the map entry for the room token is in
					tokensInRooms.get(room).add(wep);
				} else {
					//Create a map entry for the room token in, and add token to it
					ArrayList<TokenDrawer> tokenList = new ArrayList<TokenDrawer>();
					tokenList.add(wep);
					tokensInRooms.put(room, tokenList);
				}
			}
		}
		Map<String, Coordinate> roomCenters = cluedoBoard.getRoomCenters();
		/*
		 * Finds center coordinates of rooms that have tokens in them, and draws those tokens in
		 * a row above the room center, starting at x coordinate of room center, then towards the west
		 * and east walls of rooms
		 * 
		 *  TODO make it get not so nasty when rooms get really full. I.E check if still drawing in room,
		 *       change y values etc
		 */
		for(String rm : tokensInRooms.keySet()){
			Coordinate center = roomCenters.get(rm);
			//Coordinate to draw token at
			int x = center.getX();
			int y = center.getY() - 1;
			int xShift = -1;//used to adjust coordinate for next token
			Coordinate drawCoordinate = new Coordinate(x, y);
			
			for(TokenDrawer td : tokensInRooms.get(rm)){
				td.drawImage(g, squareSize, drawCoordinate);
				//adjust coordinate for next token
				drawCoordinate = new Coordinate(drawCoordinate.getX() + xShift, drawCoordinate.getY());
				if(xShift < 0){
					//last shift was towards left, so new shift towards right, same distance from center
					xShift*=-1;
				} else {
					//last shift was towards right, so increment shift, and invert it so that x is shifted
					//towards the left at the new distance from the center
					xShift++;
					xShift*=-1;
				}
			}
		}

	}

	/**
	 * TESTING USE ONLY
	 * Draws board without tokens
	 * @param g
	 * @param d
	 */
	public void drawBoard(Graphics g, Dimension d){
		calculateSquareSize(d);
		paintBoard(g);
		labelRooms(g);
	}
	
	/**
	 * Paints a visual representation of cluedoBoard square by square. 
	 * Afterwards, paints on doors and icons labeling secret passages
	 * @param g Graphics object to paint board on
	 */
	private void paintBoard(Graphics g) {

		for(int i = 0; i < cluedoBoard.width(); i++){
			for(int j = 0; j < cluedoBoard.height(); j++){
				Coordinate spotToDraw = new Coordinate(i, j);
				
				// Draws square depending on type and state of square
				if(cluedoBoard.isEmpty(spotToDraw)){
					//Empty, unaccessible square:
					g.setColor(emptyTileColor);
					g.fillRect(i*squareSize, j*squareSize, squareSize, squareSize);
				} 
				else if(!cluedoBoard.isRoom(spotToDraw)){
					//Hallway square:
					if(cluedoBoard.isHighlighted(spotToDraw)){
						//Highlighted hallway square (current player can move here):
						g.setColor(Color.YELLOW);
					} else {
						//Unhighlighted hallway square:
						g.setColor(hallwayTileColor);
					}
					g.fillRect(i*squareSize, j*squareSize, squareSize, squareSize);
					//Give hallway square a black border
					g.setColor(Color.BLACK);
					g.drawRect(i*squareSize, j*squareSize, squareSize, squareSize);
				} else {
					//Room square:
					if(cluedoBoard.isHighlighted(spotToDraw)){
						//Highlighted room
						g.setColor(Color.YELLOW.brighter());
					} else {
						//Unhighlighted room
						g.setColor(Color.GRAY);
					}
					g.fillRect(i*squareSize, j*squareSize, squareSize, squareSize);
				}
			}
		}
		paintDoors(g);
		paintSecretPassages(g);

	}
	
	/**
	 * Paints the icon that marks out secret passage squares on the board
	 * @param g Graphics object to paint secret passages to
	 */
	private void paintSecretPassages(Graphics g) {
		Set<Coordinate> secretPassages = cluedoBoard.getSecretPassages();
		
		for(Coordinate c : secretPassages){
			/*
			 * Draws an icon (arbitrary). Currently a brown square with a 
			 * black border, with a grey circle near the top and rows of 
			 * black lines.  
			 */
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
	
	/**
	 * Paints the room names onto the rooms. 
	 * @param g Graphics object to paint the names of rooms too
	 */
	private void labelRooms(Graphics g) {
		Map<String, Coordinate> roomCenters = cluedoBoard.getRoomCenters();
		
		for(String room : roomCenters.keySet()){
			//index of middle character of room name, used to align name to center of room
			int centerChar = room.length()/2;
			//calculate font size and set font with new size
			float fontSize = 0.8f * squareSize;
			g.setFont(g.getFont().deriveFont(fontSize));
			
			Coordinate center = roomCenters.get(room);
			if(cluedoBoard.isHighlighted(center)){
				g.setColor(Color.BLACK);
			} else {
				g.setColor(Color.WHITE);
			}
			/* Calculate x, y coordinates for drawString, such that string is aligned to the center of the
			 * room*/
			int nameXOffSet = (int) (center.getX()*squareSize + squareSize/2 - (centerChar * fontSize)/3);
			int nameYOffSet = center.getY()*squareSize + squareSize;
			
			g.drawString(room, nameXOffSet, nameYOffSet);
		}

	}
	
	/**
	 * Draws doors onto representation of board
	 * @param g Graphics object to draws doors onto
	 */
	private void paintDoors(Graphics g) {
		//Fill brown arcs where doors are, based on which direction they are facing
		//Arcs arc into rooms
		g.setColor(brown);
		int arcSize = squareSize*2;
		for(Coordinate c : cluedoBoard.getDoors(Compass.NORTH)){
			g.fillArc((c.getX()-1)*squareSize, (c.getY()-1)*squareSize, arcSize, arcSize, 0, -45);
		}
		for(Coordinate c : cluedoBoard.getDoors(Compass.SOUTH)){
			g.fillArc((c.getX()-1)*squareSize, (c.getY())*squareSize, arcSize, arcSize, 0, 45);
		}
		for(Coordinate c : cluedoBoard.getDoors(Compass.WEST)){
			g.fillArc((c.getX()-1)*squareSize, (c.getY()-1)*squareSize, arcSize, arcSize, -90, 45);
		}
		for(Coordinate c : cluedoBoard.getDoors(Compass.EAST)){
			g.fillArc((c.getX())*squareSize, (c.getY()-1)*squareSize, arcSize, arcSize, -90, -45);
		}
		
		/*Fill smaller arcs, the same colour as rooms, inside the brown arcs. Results in bordered but
		 * not filled wedge to represent door*/
		g.setColor(Color.GRAY);
		arcSize = squareSize*2-7;
		for(Coordinate c : cluedoBoard.getDoors(Compass.NORTH)){
			g.fillArc((c.getX()-1)*squareSize+5, (c.getY()-1)*squareSize+4, arcSize, arcSize, 0, -45);
		}
		for(Coordinate c : cluedoBoard.getDoors(Compass.SOUTH)){
			g.fillArc((c.getX()-1)*squareSize+6, (c.getY())*squareSize+3, arcSize, arcSize, 0, 45);
		}
		for(Coordinate c : cluedoBoard.getDoors(Compass.WEST)){
			g.fillArc((c.getX()-1)*squareSize+3, (c.getY()-1)*squareSize+6, arcSize, arcSize, -90, 45);
		}
		for(Coordinate c : cluedoBoard.getDoors(Compass.EAST)){
			g.fillArc((c.getX())*squareSize+3, (c.getY()-1)*squareSize+6, arcSize, arcSize, -90, -45);
		}
	}
	
	/**
	 * Calculates square size. Idea is to find which of the numbers of rows or columns are 
	 * limiting the size the board, then divide either width or height of given dimension by
	 * that number
	 * @param d Dimension used to get width and height. Would be dimension of panel who's graphics object
	 * BoardDrawer is to draw on.
	 */
	private void calculateSquareSize(Dimension d) {
		squareSize = (int) Math.min((d.getWidth()/cluedoBoard.width()), (d.getHeight()/cluedoBoard.height()));
	}

}
