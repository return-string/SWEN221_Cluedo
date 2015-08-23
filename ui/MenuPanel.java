package ui;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPanel extends CluedoPanel {
	
	//File names of images for the JLabels
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
		/* Idea is to place the buttons for the menu options in the middle
		 * of a picture of the game's box art. Have implemented this by
		 * dividing up the box art picture into 3x3 pieces, and placing 
		 * JLabels with imageIcons of those pictures in a 3x3 grid layout,
		 * with the center piece replaced by a button panel with buttons
		 * for the menu options.
		 */
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
	
	/**
	 * Builds the button panel and adds it to the menu panel
	 */
	private void addButtonPanel() {
		JPanel buttonPanel = new JPanel();
		//Grid layout, one column, to appear as a vertical list
		buttonPanel.setLayout(new GridLayout(0,1));
		addButton("New Game", buttonPanel);
		addButton("Rules", buttonPanel);
		addButton("Exit", buttonPanel);	
		add(buttonPanel);
	}
	
	/**
	 * Adds buttons to a button panel
	 * @param buttonName Label, and Action Command, for the new button
	 * @param buttonPanel ButtonPanel to add the button to
	 */
	private void addButton(String buttonName, JPanel buttonPanel){
		JButton newGame = new JButton(buttonName);
		//Controller is the action listener
		newGame.addActionListener(super.controller());
		buttonPanel.add(newGame);
	}
	
	/**
	 * Creates an JLabel with an ImageIcon of the image file at the 
	 * given file address. Adds it to the menu panel
	 * @param imageFile address of image for JLabel
	 */
	private void addImageLabel(String imageFile) {
		ImageIcon imgIcon = new ImageIcon(imageFile);
		JLabel imgLabel = new JLabel(imgIcon){
			@Override
			/**
			 * Before painting as normal, scales the Image in the ImageIcon of the 
			 * JLabel to the dimensions of the JLabel 
			 */
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
