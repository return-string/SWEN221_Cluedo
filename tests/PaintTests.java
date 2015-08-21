package tests;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.Board;

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

}
