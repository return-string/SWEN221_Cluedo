package ui;

import game.Coordinate;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class BoardPanel extends CluedoPanel implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 2501982205351713577L;

	public BoardPanel(Controller c) {
		super(c);
		this.setPreferredSize(new Dimension(400, 400));
		
	}
	
	private Coordinate mousePosToCoordinate(MouseEvent e){
		Coordinate rowsCols = super.controller().getBoardRowsCols();
		int squareSize = Math.min(getWidth()/rowsCols.getX(), getHeight()/rowsCols.getY());
		int x = e.getX()/squareSize;
		int y = e.getY()/squareSize;
		return new Coordinate(x, y);
	}
	
	@Override
	public void nextTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Coordinate boardCoord = mousePosToCoordinate(e);
		super.controller().movePlayer(boardCoord);
		
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

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
