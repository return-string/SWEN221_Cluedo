package tests;

import java.awt.Graphics;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.Board;
import game.Card;
import game.Game;

import org.junit.Test;

import ui.CluedoFrame;
import cluedoview.BoardDrawer;

public class PaintTests {

	@Test
	public void seeWhatHappens(){
		final Board clueBoard = new Board();
		JFrame window = new JFrame();
		window.setSize(500, 500);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel canvas = new JPanel(){
			@Override
			public void paint(Graphics g){
				this.setSize(400, 400);
				super.paint(g);
				new BoardDrawer(clueBoard).drawBoard(g, this.getSize());
			}
		};

		window.add(canvas);
		window.setVisible(true);
		while(true){}
	}
	
	@Test
	public void seeWhatHappensWithGame(){
		HashMap<String, String> players = new HashMap<String, String>();
		players.put("Badi", Card.WHITE);
		players.put("Vicki", Card.PEACOCK);
		players.put("Sam", Card.GREEN);
		players.put("Major Malfunction", Card.MUSTARD);
		players.put("Leeroy Jenkins", Card.SCARLET);
		players.put("Lester", Card.PLUM);
		final Game cluedoGame = new Game(players);
		JFrame window = new JFrame();
		window.setSize(700, 700);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel canvas = new JPanel(){
			private BoardDrawer boardDraw = new BoardDrawer(cluedoGame) ;
			
			@Override
			public void paint(Graphics g){
				this.setSize(600, 600);
				super.paint(g);
				boardDraw.paintBoardAndTokens(g, this.getSize());
			}
		};

		window.add(canvas);
		window.setVisible(true);
		while(true){}
	}

}
