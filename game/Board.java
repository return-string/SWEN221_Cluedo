package game;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

public class Board {

	public static final String HALLWAYSTRING = "hallway";

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
		if(!isLegal(start)) {throw new IllegalArgumentException();}
		BoardSquare startSquare = squares[start.getX()][start.getY()];
		HashMap<Coordinate, String> moves = new HashMap<Coordinate, String>();
		HashSet<String> roomsToFind = new HashSet<String>(Arrays.asList(Card.ROOMS));
		roomsToFind.remove(startSquare.getRoom());
		for(String rm : roomsToFind){
			clearVisits();
			String descriptionString = "";
			Coordinate moveCoord = null;
			//does a breadth first search for squares move can end in
			LinkedList<PathFringeEntry> pathFringe = new LinkedList<PathFringeEntry>();
			pathFringe.offer(new PathFringeEntry(startSquare, null, 0));
			do{
				PathFringeEntry current = pathFringe.poll();
				current.square.setVisited(true);
				//if it finds the room it's currently looking for
				if(current.square.getRoom().equals(rm)){
					if(current.distance <= steps){//if can get to it this turn
						descriptionString = "Enter the " + rm;
						moveCoord = current.square.getClosestCoordinate(current.from.square.getACoordinate());
					} else { //finds the square player can reach closest to room
						int finalLength = current.distance;
						PathFringeEntry roomEntry = current;
						while(roomEntry.distance > steps){
							roomEntry = roomEntry.from;
						}
						int stepsToRoom = finalLength - steps;
						descriptionString = stepsToRoom + " steps away from " + rm;
						moveCoord = roomEntry.square.getACoordinate();
						if(moves.containsKey(moveCoord)){
							descriptionString = moves.get(moveCoord)
									+ "\n\t" + descriptionString;
						}
					}
					moves.put(moveCoord, descriptionString);
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
		}
		return moves;
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


}