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
		assertFalse(testBoard.isHighlighted(new Coordinate(5, 1)));
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

	/*
	 * Test findMove() returns expected
	 */

	@Test
	public void testFindMoveTrue1(){
		//test returns chosen highlighted square
		Coordinate start = new Coordinate(5, 9);
		Coordinate clicked = new Coordinate(11, 9);
		int steps = 6;
		testBoard.highlightMoves(start, steps);
		assertEquals(clicked, testBoard.findMove(clicked, start, steps));
	}

	@Test
	public void testFindMoveTrue2(){
		//test returns closest reachable square to clicked room
		Coordinate start = new Coordinate(5, 9);
		Coordinate clicked = new Coordinate(0, 11);
		int steps = 5;
		testBoard.highlightMoves(start, steps);
		assertEquals(new Coordinate(8, 11), testBoard.findMove(clicked, start, steps));
	}

	@Test
	public void testFindMoveTrue3(){
		//test returns null when choosing an unhighlighted hallway square
		Coordinate start = new Coordinate(5, 9);
		Coordinate clicked = new Coordinate(11, 9);
		int steps = 3;
		testBoard.highlightMoves(start, steps);
		assertEquals(null, testBoard.findMove(clicked, start, steps));
	}

	@Test
	public void testFindMoveTrue4(){
		//test returns clicked room coordinate when room within reach
		Coordinate start = new Coordinate(8, 9);
		Coordinate clicked = new Coordinate(0, 11);
		int steps = 5;
		testBoard.highlightMoves(start, steps);
		assertEquals(clicked, testBoard.findMove(clicked, start, steps));
	}

	@Test
	public void testFindMoveTrue5(){
		//test returns null when choosing an empty square
		Coordinate start = new Coordinate(9, 12);
		Coordinate clicked = new Coordinate(11, 12);
		int steps = 3;
		testBoard.highlightMoves(start, steps);
		assertEquals(null, testBoard.findMove(clicked, start, steps));
	}

	@Test
	public void testFindMoveTrue6(){
		//test returns null when clicking outside of board
		assertEquals(null, testBoard.findMove(new Coordinate(33, 33), new Coordinate(9, 12), 900));
	}

}
