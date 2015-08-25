package ui;

import game.Coordinate;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class BoardPanel extends CluedoPanel implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 2501982205351713577L;

	private boolean mouseInRoom; //to represent if mouse is hovering over a room on the board
	private String mouseRoom = ""; //the name of the room the mouse is hovering over

	public BoardPanel(Controller c) {
		super(c);
		this.setPreferredSize(new Dimension(400, 400));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	/**
	 * Converts the coordinates of the mouse event to a coordinate object representing the
	 * grid coordinate of the square that was clicked on.
	 * @param e
	 * @return
	 */
	private Coordinate mousePosToCoordinate(MouseEvent e){
		//Gets dimension of board from controller
		Coordinate rowsCols = super.controller().getBoardRowsCols();
		/*Calculates square size. Idea is to find which of the numbers of rows or columns are
		 * limiting the size the board, then divide either width or height of boardpannel by
		 * that number*/
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

	/**
	 * Tells controller to highlight or unhighlight rooms depending on if mouse has
	 * left a room to a hallway or another room (or vice versa)
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		Coordinate boardCoord = mousePosToCoordinate(e);
		System.out.println("Mouse over " + boardCoord.toString());
		//checks if mouse has moved out of or into a room
		if(mouseInRoom != super.controller().coordinateInRoom(boardCoord)){
			mouseInRoom = !mouseInRoom;//change to represent new mouse position
			super.controller().highlightRoom(boardCoord, mouseInRoom);
			repaint();
		} else {
			String newRoom = super.controller().getRoomName(boardCoord);
			//if the mouse has changed rooms without entering hallway
			if(newRoom != null && mouseInRoom && !mouseRoom.equals(newRoom)){
				//unhighlight rooms
				super.controller().highlightRoom(boardCoord, false);
				mouseRoom = newRoom;
				//highlight the room the mouse is now hovering over
				super.controller().highlightRoom(boardCoord, true);
				repaint();
			}
		}

	}
}
