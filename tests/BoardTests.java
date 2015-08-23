package tests;

import static org.junit.Assert.*;

import game.Board;
import game.CardImpl;
import game.Coordinate;
import game.Game;
import game.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/** Tests covering the Board classes 
 * 
 * @author Badi James
 *
 */
public class BoardTests {
	
	private Board testBoard = new Board();
	
	/*
	 * Tests highlightMoves() highlights expected squares
	 */
	
	@Test
	public void testHighlightMovesTrue1(){
		testBoard.highlightMoves(new Coordinate(4, 8), 1);
		assertTrue(testBoard.isHighlighted(new Coordinate(4, 7)));
		assertTrue(testBoard.isHighlighted(new Coordinate(3, 8)));
		assertTrue(testBoard.isHighlighted(new Coordinate(5, 8)));
		testBoard.unhighlight();
	}
	
	@Test
	public void testHighlightMovesTrue2(){
		testBoard.highlightMoves(new Coordinate(4, 8), 2);
		assertTrue(testBoard.isHighlighted(new Coordinate(4, 7)));
		assertTrue(testBoard.isHighlighted(new Coordinate(3, 8)));
		assertTrue(testBoard.isHighlighted(new Coordinate(5, 8)));
		assertTrue(testBoard.isHighlighted(new Coordinate(3, 7)));
		assertTrue(testBoard.isHighlighted(new Coordinate(2, 8)));
		assertTrue(testBoard.isHighlighted(new Coordinate(6, 8)));
		assertTrue(testBoard.isHighlighted(new Coordinate(5, 9)));
		assertTrue(testBoard.isHighlighted(new Coordinate(5, 7)));
		//test room is highlighted
		assertTrue(testBoard.isHighlighted(new Coordinate(1, 1)));
		testBoard.unhighlight();
	}
	
	/*
	 * Test highlightMoves() does not highlight unexpected squares
	 */
	
	@Test
	public void testHighlightMovesFalse1(){
		//test that it does not highlight through walls
		testBoard.highlightMoves(new Coordinate(4, 8), 1);
		assertFalse(testBoard.isHighlighted(new Coordinate(4, 9)));
		testBoard.unhighlight();
	}
	
	@Test
	public void testHighlightMovesFalse2(){
		//test that it does not just highlight every hallway square
		testBoard.highlightMoves(new Coordinate(4, 8), 1);
		assertFalse(testBoard.isHighlighted(new Coordinate(0, 17)));
		testBoard.unhighlight();
	}
	
	@Test
	public void testHighlightMovesFalse3(){
		//test that it does not highlight secret passages
		testBoard.highlightMoves(new Coordinate(4, 7), 2);
		assertFalse(testBoard.isHighlighted(new Coordinate(1, 5)));
		testBoard.unhighlight();
	}
	
	@Test
	public void testHighlightMovesFalse4(){
		//test that it does not highlight occupied squares
		testBoard.toggleOccupied(new Coordinate(3, 8));
		testBoard.highlightMoves(new Coordinate(4, 8), 1);
		assertFalse(testBoard.isHighlighted(new Coordinate(3, 8)));
		testBoard.toggleOccupied(new Coordinate(3, 8));
		testBoard.unhighlight();
	}
	
	/*
	 * Test highlightMoves() handles invalid input
	 */
	
	@Test
	public void testHighlightMovesInvalidInput1(){
		//test empty square in board
		try{
			testBoard.highlightMoves(new Coordinate(11, 12), 1);
			fail();
		} catch(IllegalArgumentException e){
			
		}
	}
	
	@Test
	public void testHighlightMovesInvalidInput2(){
		//test out side of board
		try{
			testBoard.highlightMoves(new Coordinate(50, 50), 1);
			fail();
		} catch(IllegalArgumentException e){
			
		}
	}
	
	private void printMoves(Map<Coordinate, String> moves) {
		for(Coordinate coord : moves.keySet()){
			System.out.printf("%s : %s\n", coord.toString(), moves.get(coord));
		}
	}

}
