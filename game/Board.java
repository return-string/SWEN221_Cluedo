package game;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

public class Board {
	
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
		if(coord.getX() < 0) {return false;}
		if(coord.getY() < 0) {return false;}
		if(coord.getX() >= squares.length) {return false;}
		if(coord.getY() >= squares[0].length) {return false;}
		return squares[coord.getX()][coord.getY()] != null;
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

	/**Determines the legal moves that can be made from the
	 * given coordinate moving the given number of steps.
	 * Returns a map of Coordinates to Strings, where the 
	 * coordinates are the choice of new board positions and
	 * the strings are descriptions of distances to different
	 * rooms.
	 * @param start Coordinate to move from
	 * @param steps Number of steps to move
	 * @return Map of coordinate choices to string descriptions
	 * of choices 
	 */
	public Map<Coordinate, String> possibleMoves(Coordinate start, int steps){
		BoardSquare startSquare = squares[start.getX()][start.getY()];
		HashSet<Coordinate> destinations = new HashSet<Coordinate>();
		//does a breadth first search for squares move can end in
		LinkedList<PathFringeEntry> pathFringe = new LinkedList<PathFringeEntry>();
		pathFringe.offer(new PathFringeEntry(startSquare, null, 0));
		do{
			PathFringeEntry current = pathFringe.poll();
			/*if path length to current square equals the dice roll
			or if current square is a room, it's a possible move*/
			if(current.getDistance() == steps || current.getSquare().isRoom()){
				destinations.add(current.getSquare().getACoordinate());
			} else {
				/*puts all the neighbours on the fringe if they haven't been
				 * visited, with this entry as their 'from' and an incremented
				 * 'dist' (path length)*/
				for(BoardSquare neighbour : current.getSquare().getNeighbours()){
					if(!current.isFrom(neighbour)){
						pathFringe.offer(new PathFringeEntry(neighbour, current, 
								current.getDistance()+1));
					}
				}
			}
		} while(!pathFringe.isEmpty());
		HashMap<Coordinate, String> moves = new HashMap<Coordinate, String>();
		//Builds the map with the appropriate descriptions
		for(Coordinate coord : destinations){
			BoardSquare endSquare = squares[coord.getX()][coord.getY()];
			String description = "";
			if(endSquare.isRoom()){
				description = "Enter the " + endSquare.getRoom();
			} else {
				description = "Go down " + endSquare.getRoom() + ". Don't know distance to"
						+" rooms yet.";
			}
			moves.put(coord, description);
		}
		// TODO make methods to calculate distance to rooms
		return moves;
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

		public BoardSquare getSquare() {
			return square;
		}

		public int getDistance() {
			return distance;
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
			} else if(this.from.getSquare().equals(bs)){
				return true;
			} else {
				return this.from.isFrom(bs);
			}
		}
	}

	
}