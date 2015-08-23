package game;

import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Badi James
 *
 */
public class Board {

	public static final String HALLWAYSTRING = "hallway";
	
	public enum Compass {NORTH, EAST, SOUTH, WEST}
	
	private BoardSquare[][] squares;
	private File classic = new File("ClassicBoard.txt");

	public Board() {
		BoardParser bp = new BoardParser();
		squares = bp.buildBoard(classic);
	}

	/**Checks if the given coordinate is legal for
	 * this board (Might have use for testing? Shouldn't
	 * need it if everything goes well)
	 *@param coord Coordinate to check
	 *@return Boolean representing legality of coordinate
	 */
	public boolean isLegal(Coordinate coord){
		if(withinBounds(coord)){
			return !isEmpty(coord);
		} else {
			return false;
		}
	}

	public boolean isEmpty(Coordinate coord) {
		return squares[coord.getX()][coord.getY()] == null;
	}

	public boolean withinBounds(Coordinate coord) {
		if(coord.getX() < 0) {return false;}
		if(coord.getY() < 0) {return false;}
		if(coord.getX() >= squares.length) {return false;}
		if(coord.getY() >= squares[0].length) {return false;}
		return true;
	}



	/**Checks if the given coordinate is in a room. Returns
	 * string name of room. ("Hallway" if player is not in a
	 * room?)
	 *@param coord Coordinate to check
	 *@return String name of room coordinate is in
	 */
	public String getRoom(Coordinate coord){
		if(!isLegal(coord)) {throw new IllegalArgumentException();}
		return squares[coord.getX()][coord.getY()].getRoom();
	}

	public boolean isRoom(Coordinate coord){
		if(!isLegal(coord)) {return false;}
		return squares[coord.getX()][coord.getY()].isRoom();
	}

	/**
	 * Highlights all the board squares a player can reach with from a
	 * given square with a given number of steps.
	 * @param start Square to begin search from (typically square a player is on)
	 * @param steps Number of steps can travel from square
	 */
	public void highlightMoves(Coordinate start, int steps){
		//Checks start coordinate is legit
		if(!isLegal(start)) {throw new IllegalArgumentException();}
		BoardSquare startSquare = squares[start.getX()][start.getY()];
		//Creates a pathFringe for a breadth first traversal of the board
		LinkedList<PathFringeEntry> pathFringe = new LinkedList<PathFringeEntry>();
		pathFringe.offer(new PathFringeEntry(startSquare, null, 0));
		do{//polls square off fringe and highlights it
			PathFringeEntry current = pathFringe.poll();
			current.square.setVisited(true);
			current.square.setHighlight(true);
			//If the current square is not at the end of the player's reach, and is
			// either the start square or not a room, puts the current square's
			//neighbours on the fringe
			if(current.distance < steps && (!current.square.isRoom() || current.distance == 0)){
				for(BoardSquare neighbour : current.square.getNeighbours()){
					if(!neighbour.isVisited() && !neighbour.isOccupied()){
						pathFringe.offer(new PathFringeEntry(neighbour, current,
								current.distance+1));
					}
				}
			}
		} while(!pathFringe.isEmpty());
		clearVisits();
	}

	/**
	 * Goes through all the board squares in squares, unhighlighting them
	 */
	public void unhighlight(){
		for(int i = 0; i < this.squares.length; i++){
			for(int j = 0; j < squares[0].length; j++){
				BoardSquare bs = squares[i][j];
				if(bs != null){
					bs.setHighlight(false);
				}
			}
		}
	}

	/**
	 * Gets a coordinate that can be moved to from a start coordinate, determined by
	 * a coordinate that has been selected (i.e been clicked)
	 *
	 * Returns the selected coordinate if selected coordinate is highlighted.
	 * If the selected coordinate is a room, returns the square closest to the room
	 * that can be reached from the start square with the given number of steps
	 * Otherwise returns null
	 *
	 * @param clicked Selected coordinate on board
	 * @param start Coordinate to start movement from
	 * @param steps Number of steps to move
	 * @return Coordinate player can move to
	 */
	public Coordinate findMove(Coordinate clicked, Coordinate start, int steps){
		if(!isLegal(clicked)) {return null;}
		BoardSquare clickedSquare = squares[clicked.getX()][clicked.getY()];
		if(clickedSquare.isHighlighted()) {
			return clicked;
		} else if(clickedSquare.isRoom()) {
			return findClosestToRoom(getRoom(clicked), start, steps);
		} else {
			return null;
		}
	}

	/**
	 * Finds the square closest to the given room that can be reached from the
	 * start square with the given number of steps
	 *
	 * @param rm Room to find closest reachable square to
	 * @param start Coordinate of square to start movement from
	 * @param steps Number of steps that can be moved
	 * @return Coordinate of closest reachable square to room
	 */
	private Coordinate findClosestToRoom(String rm, Coordinate start, int steps){
		if(!isLegal(start)) {throw new IllegalArgumentException();}
		BoardSquare startSquare = squares[start.getX()][start.getY()];

		Coordinate moveCoord = null;
		//does a breadth first search for squares of room rm
		LinkedList<PathFringeEntry> pathFringe = new LinkedList<PathFringeEntry>();
		pathFringe.offer(new PathFringeEntry(startSquare, null, 0));
		do{
			PathFringeEntry current = pathFringe.poll();
			current.square.setVisited(true);
			//if it finds the room it's currently looking for
			if(current.square.getRoom().equals(rm)){
				if(current.distance <= steps){//if can get to it this turn
					moveCoord = current.square.getClosestCoordinate(start);
				} else { //finds the square player can reach closest to room
					PathFringeEntry roomEntry = current;
					while(roomEntry.distance > steps){
						roomEntry = roomEntry.from;
					}
					moveCoord = roomEntry.square.getACoordinate();
				}
				clearVisits();
				break;
			} else {
				/*If the current square is the start square or isn't a room square,
				 * puts all the neighbours on the fringe if they haven't been visited
				 * and they are not occupied, with this entry as their 'from' and an
				 * incremented 'dist' (path length)*/
				if(!current.square.isRoom() || current.distance == 0){
					for(BoardSquare neighbour : current.square.getNeighbours()){
						if(!neighbour.isVisited() && !neighbour.isOccupied()){
							pathFringe.offer(new PathFringeEntry(neighbour, current,
									current.distance+1));
						}
					}
				}
			}
		} while(!pathFringe.isEmpty());
		return moveCoord;
	}

	/**
	 * Used when adding to a description of steps to room from a square to preserve the
	 * order of the description.
	 *
	 * Splits up the description to find a place to insert the addition, then rebuilds.
	 * Keeps the order shortest to longest.
	 *
	 * @param description Current description of steps to rooms from a square
	 * @param add Description of steps to a particular room to add
	 * @return Updated description
	 */
	private String addToMoveDescription(String description, String add) {
		// TODO Auto-generated method stub
		//System.out.printf("Adding \"%s\" to \"%s\"\n", add, description);
		List<String> dP = Arrays.asList(description.split("\n\t"));
		ArrayList<String> descriptPieces = new ArrayList<String>(dP);
		int addSteps = Integer.parseInt(add.split(" ")[0]);
		for(int i = 0; i < descriptPieces.size(); i++){
			int pieceSteps = Integer.parseInt(descriptPieces.get(i).split(" ")[0]);
			if(addSteps < pieceSteps){
				descriptPieces.add(i, add);
				break;
			}
		}
		if(!descriptPieces.contains(add)){
			descriptPieces.add(add);
		}
		String newDescription = descriptPieces.get(0);
		for(int i = 1; i < descriptPieces.size(); i++){
			newDescription += "\n\t" + descriptPieces.get(i);
		}
		return newDescription;
	}

	private void clearVisits() {
		for(int i = 0; i < this.squares.length; i++){
			for(int j = 0; j < squares[0].length; j++){
				BoardSquare bs = squares[i][j];
				if(bs != null){
					bs.setVisited(false);
				}
			}
		}

	}

	/**
	 * Finds the board square matching the coordinate, and if it is
	 * not a room square, switches its occupied state.
	 * @param c Coordinate of square to switch
	 */
	public void toggleOccupied(Coordinate c){
		BoardSquare square = squares[c.getX()][c.getY()];
		if(!square.isRoom()){
			square.setOccupied(!square.isOccupied());
		}
	}

	/**
	 * Tuple for the breadth first search for move options
	 *
	 * @author Badi James
	 *
	 */
	private class PathFringeEntry{

		private BoardSquare square;
		private PathFringeEntry from;
		private int distance;

		public PathFringeEntry(BoardSquare square, PathFringeEntry from,
				int distance) {
			super();
			this.square = square;
			this.from = from;
			this.distance = distance;
		}

		/**
		 * Recursively checks the 'from' entries. Used to check if
		 * a square neighboring this square to be added to the fringe
		 * has already been visited in the path to get to this square
		 * @param bs The board square to check if it is already in the path
		 * @return True if bs is in the path
		 */
		public boolean isFrom(BoardSquare bs){
			if(this.from == null){
				return false;
			} else if(this.from.square.equals(bs)){
				return true;
			} else {
				return this.from.isFrom(bs);
			}
		}
	}

	public int width() {
		return squares.length;
	}

	public int height() {
		return squares[0].length;
	}
	
	/**
	 * @param coord Coordinate of square to check
	 * @return True of square at coordinate is highlighted
	 */
	public boolean isHighlighted(Coordinate coord) {
		if(!isLegal(coord)) {throw new IllegalArgumentException();}
		return squares[coord.getX()][coord.getY()].isHighlighted();
	}
	
	/**
	 * Methods for finding coordinates of hallway squares next to doors to rooms.
	 * Compass direction refers to direction from hallway to door.  
	 * @return List of coordinates of hallway squares next to doors in the relevant direction
	 */
	public List<Coordinate> getDoors(Compass dir){
		
		/* Determines the appropriate array access adjustments for looking at a square's
		 * neighbour in the given direction.
		 */
		int xShift = dir.equals(Compass.WEST) ? -1 : 0;
		xShift = dir.equals(Compass.EAST) ? 1 : xShift;
		int yShift = dir.equals(Compass.NORTH) ? -1 : 0;
		yShift = dir.equals(Compass.SOUTH) ? 1 : yShift;
		int startX = dir.equals(Compass.WEST) ? 1 : 0;
		int startY = dir.equals(Compass.NORTH) ? 1 : 0;
		int endX = dir.equals(Compass.EAST) ? width() - 1 : width();
		int endY = dir.equals(Compass.SOUTH) ? height() - 1 : height();
		
		List<Coordinate> toReturn = new ArrayList<Coordinate>();
		//goes through the array of board squares
		for(int i = startX; i < endX; i++){
			for(int j = startY; j < endY; j++){
				/* if the current square is a hallway square, and its neighbour in the 
				 * given direction is a room square the current square is connected to,
				 * adds the current square's coordinates to the list of coordinates to
				 * return
				 */
				if(squares[i][j] != null && squares[i+xShift][j+yShift] != null){
					if(squares[i][j].isRoom() && !squares[i+xShift][j+yShift].isRoom() &&
							squares[i][j].getNeighbours().contains(squares[i+xShift][j+yShift])){
						toReturn.add(new Coordinate(i,j));
					}
				}
			}
		}
		return toReturn;
	}

	public Map<String, Coordinate> getRoomCenters(){
		Map<String, Coordinate> toReturn = new HashMap<String, Coordinate>();
		for(int i = 0; i < width(); i++){
			for(int j = 0; j < height(); j++){
				if(squares[i][j] != null && squares[i][j].isRoom()){
					toReturn.put(squares[i][j].getRoom(), squares[i][j].getCenter());
				}
			}
		}
		return toReturn;
	}

	public Set<Coordinate> getSecretPassages(){
		Set<Coordinate> toReturn = new HashSet<Coordinate>();
		for(int i = 0; i < width(); i++){
			for(int j = 0; j < height(); j++){
				if(squares[i][j] != null && squares[i][j].isRoom()){
					Coordinate passage = squares[i][j].findSecretPassage();
					if(passage != null){
						toReturn.add(squares[i][j].findSecretPassage());
					}
				}
			}
		}
		return toReturn;
	}

	public void highlightSquare(Coordinate boardCoord) {
		squares[boardCoord.getX()][boardCoord.getY()].setHighlight(true);
		
	}

	public void unhighlightRooms() {
		for(Coordinate center : getRoomCenters().values()){
			squares[center.getX()][center.getY()].setHighlight(false);
		}
		
	}

}
