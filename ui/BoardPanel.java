package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardPanel extends CluedoPanel implements MouseListener {
	private static final long serialVersionUID = 2501982205351713577L;
	
	// TODO Make more general solution for boardSquares for variable sized boards
	public static final int SQUARE_SIZE = (400/25);

	public BoardPanel(Controller c) {
		super(c);
		this.setPreferredSize(new Dimension(400, 400));
		
	}

	@Override
	public void nextTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		super.controller().repaintBoard(g, this.getSize());
	}
}
