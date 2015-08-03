package game;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Parses the text files representing cluedo boards and
 * returns a 2D array of BoardSquares
 *
 * @author Badi James
 *
 */
public class BoardParser {

	private char hallway = 'h';
	private char doorUp = '^';
	private char doorDown = 'v';
	private char doorLeft = '<';
	private char doorRight = '>';
	private char kitchen = 'k';
	private char ballroom = 'b';
	private char conservatory = 'c';
	private char dining = 'd';
	private char billiard = 'i';
	private char library = 'l';
	private char lounge = 'o';
	private char study = 's';
	private char hall = 'a';

	private int width;
	private int height;
	private BoardSquare[][] board;

	private HashSet<BoardSquare> rooms = new HashSet<BoardSquare>();
	private HashSet<Coordinate> doorRightDownCoords = new HashSet<Coordinate>();

	/**
	 * Scans the board file. Gets the width and the height of the board
	 * and then builds the 2D array of BoardSquares row by row, from the
	 * character strings scanned line by line
	 * @param boardFile cluedo board file to be scanned
	 * @return 2D array of boardSquares representing board
	 */
	public BoardSquare[][] buildBoard(File boardFile) {
		Scanner sc = null;
		try{
			sc = new Scanner(boardFile);
			getDimensions(sc);
			board = new BoardSquare[width][height];
			for(int row = 0; row < height; row++){
				buildRow(sc, row);
			}
			sc.close();
//			for(BoardSquare room : rooms){
//				System.out.println(room.toString());
//			}
			return this.board;
		} catch(IOException e){
			System.out.print("Error reading board file: " + e);
			return null;
		}
	}

	/**
	 * Scans line character by character then builds the appropriate
	 * squares
	 * @param sc Scanner scanning the cluedo board file
	 * @param row Number of row in file scanner is up to
	 */
	private void buildRow(Scanner sc, int row) {
		String rowData = sc.nextLine();
		for(int col = 0; col < width; col++){
			char square = rowData.charAt(col);
			if(square != '-'){//if not an empty, inaccessible square
				makeBoardSquare(square, col, row);
			}
		}
	}

	/**
	 * Makes a coordinate from the row and col number and builds a
	 * BoardSquare appropriate to the character representing it
	 * @param square character representing the square
	 * @param col
	 * @param row
	 */
	private void makeBoardSquare(char square, int col, int row) {
		Coordinate coordinate = new Coordinate(col, row);
		String roomName = determineName(square);
		boolean isRoom = !roomName.equals(Board.HALLWAYSTRING);
		if(isRoom){
			makeRoomSquare(col, row, coordinate, roomName);
		} else {
			makeHallwaySquare(col, row, coordinate, square);
		}
	}

	/**
	 * Makes a board square representing a square that is not part of any room
	 * Adds it to the 2D BoardSquare array in a cell that matches it's coordinate
	 * @param col Column number
	 * @param row Row number
	 * @param coordinate Coordinate of square
	 * @param square Character representing the square
	 */
	private void makeHallwaySquare(int col, int row, Coordinate coordinate,
			char square) {
		BoardSquare toMake = new BoardSquare(coordinate, Board.HALLWAYSTRING, false);
		//adds adjacent squares to neighbours that are also non-room squares
		if(col != 0 && this.board[col-1][row] != null && !this.board[col-1][row].isRoom()){
			toMake.addNeigbour(this.board[col-1][row]);
		}
		if(row != 0 && this.board[col][row-1] != null && !this.board[col][row-1].isRoom()){
			toMake.addNeigbour(this.board[col][row-1]);
		}
		//if character represents an adjacent door, adds the room the door is for as neigbour
		if(square == doorUp){
			toMake.addNeigbour(this.board[col][row-1]);
		} else if(square == doorLeft){
			toMake.addNeigbour(this.board[col-1][row]);
		}
		//notes down which squares are adjacent to doors to rooms that have not been scanned yet
		else if(square == doorDown){
			doorRightDownCoords.add(coordinate);
		} else if(square == doorRight){
			doorRightDownCoords.add(coordinate);
		}
		this.board[col][row] = toMake;
	}

	/**
	 * Checks if the square is representing a room that has already been scanned.
	 * If so, adds the coordinate to the room's BoardSquare. Otherwise creates a
	 * new BoardSquare representing the room
	 * @param col
	 * @param row
	 * @param coordinate
	 * @param roomName
	 */
	private void makeRoomSquare(int col, int row, Coordinate coordinate, String roomName) {
		BoardSquare toMake = null;
		for(BoardSquare room : this.rooms){
			if(room.getRoom().equals(roomName)){
				toMake = room;
				toMake.addCoordinate(coordinate);
				break;
			}
		}
		if(toMake == null){
			toMake = new BoardSquare(coordinate, roomName, true);
			this.rooms.add(toMake);
		}
		//Adds adjacent squares to neighbours that are either also rooms, or squares adjacent
		//to a door
		if(col != 0){
			BoardSquare adjacent = this.board[col-1][row];
			Coordinate left = new Coordinate(col-1, row);
			if(adjacent != null && ((adjacent.isRoom() && !toMake.hasRoomInNeigbours(adjacent))
					|| this.doorRightDownCoords.contains(left))){
				toMake.addNeigbour(adjacent);
			}
		}
		if(row != 0){
			BoardSquare adjacent = this.board[col][row-1];
			Coordinate up = new Coordinate(col, row-1);
			if(adjacent != null && ((adjacent.isRoom() && !toMake.hasRoomInNeigbours(adjacent))
					|| this.doorRightDownCoords.contains(up))){
				toMake.addNeigbour(adjacent);
			}
		}
		this.board[col][row] = toMake;
	}

	/**
	 * Matches square characters to the string for that room
	 * @param square Character to match
	 * @return Matching string
	 */
	private String determineName(char square) {
		if(square == this.conservatory){
			return Card.CONSERVATORY;
		} else if(square == this.ballroom){
			return Card.BALL;
		} else if(square == this.billiard){
			return  Card.BILLIARD;
		} else if(square == this.dining){
			return  Card.DINING;
		} else if(square == this.kitchen){
			return Card.KITCHEN;
		} else if(square == this.library){
			return Card.LIBRARY;
		} else if(square == this.lounge){
			return Card.LOUNGE;
		} else if(square == this.study){
			return Card.STUDY;
		} else if(square == this.hall){
			return  Card.HALL;
		} else {
			return Board.HALLWAYSTRING;
		}
	}

	private void getDimensions(Scanner sc) {
		this.width = sc.nextInt();
		this.height = sc.nextInt();
		sc.nextLine();
	}

}
