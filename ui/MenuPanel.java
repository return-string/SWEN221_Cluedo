package ui;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPanel extends CluedoPanel {
	
	private final String boxNorth = "assets/cluedoNorth.jpg";
	private final String boxSouth = "assets/cluedoSouth.jpg";
	private final String boxWest = "assets/cluedoWest.jpg";
	private final String boxEast = "assets/cluedoEast.jpg";
	private final String boxNorthEast = "assets/cluedoNorthEast.jpg";
	private final String boxSouthWest = "assets/cluedoSouthWest.jpg";
	private final String boxNorthWest = "assets/cluedoNorthWest.jpg";
	private final String boxSouthEast = "assets/cluedoSouthEast.jpg";

	
	public MenuPanel(Controller c){
		super(c);
		this.setLayout(new GridLayout(3,3));
		
		addImageLabel(boxNorthWest);
		addImageLabel(boxNorth);
		addImageLabel(boxNorthEast);
		addImageLabel(boxWest);
		
		addButtonPanel();
		
		addImageLabel(boxEast);
		addImageLabel(boxSouthWest);
		addImageLabel(boxSouth);
		addImageLabel(boxSouthEast);
	}
	
	
	private void addButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(0,1));
		addButton("New Game", buttonPanel);
		addButton("Rules", buttonPanel);
		addButton("Exit", buttonPanel);	
		add(buttonPanel);
	}
	
	private void addButton(String buttonName, JPanel buttonPanel){
		JButton newGame = new JButton(buttonName);
		newGame.addActionListener(super.controller());
		buttonPanel.add(newGame);
	}

	private void addImageLabel(String imageFile) {
		ImageIcon imgIcon = new ImageIcon(imageFile);
		JLabel imgLabel = new JLabel(imgIcon){
			@Override
			public void paint(Graphics g){
				ImageIcon imgIcon = (ImageIcon) this.getIcon();
				imgIcon.setImage(imgIcon.getImage().getScaledInstance(this.getWidth(), 
						this.getHeight(), Image.SCALE_DEFAULT));
				super.paint(g);
			}
		};
		add(imgLabel);
	}


	@Override
	public void nextTurn() {
	}



}
