package ui;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** Displays once the game is over.
 *
 * @author Vicki
 *
 */
public class GameOverPanel extends CluedoPanel {
	private static final long serialVersionUID = 8982074883978347937L;

	//File names of images for the JLabels
		private final String boxNorth = "assets/gameoverNorth.jpg";
		private final String boxSouth = "assets/gameoverSouth.jpg";
		private final String boxWest = "assets/gameoverWest.jpg";
		private final String boxEast = "assets/gameoverEast.jpg";
		private final String boxNorthEast = "assets/gameoverNorthEast.jpg";
		private final String boxSouthWest = "assets/gameoverSouthWest.jpg";
		private final String boxNorthWest = "assets/gameoverNorthWest.jpg";
		private final String boxSouthEast = "assets/gameoverSouthEast.jpg";


		public GameOverPanel(Controller c){
			super(c);
			/* Idea is to place the buttons for the menu options in the middle
			 * of a picture of the game's box art. Have implemented this by
			 * dividing up the box art picture into 3x3 pieces, and placing
			 * JLabels with imageIcons of those pictures in a 3x3 grid layout,
			 * with the center piece replaced by a panel displaying the game
			 * result and two buttons for starting a new game or exiting
			 */
			this.setLayout(new GridLayout(3,3));

			addImageLabel(boxNorthWest);
			addImageLabel(boxNorth);
			addImageLabel(boxNorthEast);
			addImageLabel(boxWest);

			addMiddlePanel();

			addImageLabel(boxEast);
			addImageLabel(boxSouthWest);
			addImageLabel(boxSouth);
			addImageLabel(boxSouthEast);
		}

		/**
		 * Builds the button panel and adds it to the menu panel
		 */
		private void addMiddlePanel() {
			JPanel middlePanel = new JPanel();
			//Grid layout, one column, to appear as a vertical list
			middlePanel.setLayout(new GridLayout(0,1));
			addResultDisplay(middlePanel);
			addButton("New Game", middlePanel);
			addButton("Exit", middlePanel);
			add(middlePanel);
		}

		private void addResultDisplay(JPanel middlePanel) {
			String result = super.controller().getGameResult();
			JLabel resultDisplay = new JLabel(result, JLabel.CENTER);
			resultDisplay.setVerticalTextPosition(JLabel.CENTER);
			resultDisplay.setHorizontalTextPosition(JLabel.CENTER);
			middlePanel.add(resultDisplay);

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
