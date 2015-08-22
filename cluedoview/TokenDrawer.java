package cluedoview;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.Board;
import game.Coordinate;
import game.Player;
import game.Token;

public class TokenDrawer {

	private Token token;
	private Image wepImg;

	private String imgPath = "images/";
	private String imgType = ".png";

	public TokenDrawer(Token token){
		this.token = token;
		retrieveImage();
	}

	private void retrieveImage() {
		java.net.URL imageURL = TokenDrawer.class.getResource(imgPath
				+ token.getName() + imgType);

		try {
			wepImg = ImageIO.read(imageURL);
		} catch (IOException e) {
			// we've encountered an error loading the image. There's not much we
			// can actually do at this point, except to abort the game.
			throw new RuntimeException("Unable to load image: " + token.getName() + imgType);
		}
	}

	public void drawImage(Graphics g, int squareSize){
		Image scaledImage = wepImg.getScaledInstance(squareSize, squareSize, Image.SCALE_DEFAULT);
		Coordinate tokenPosition = token.getPosition();
		int weaponX = tokenPosition.getX()*squareSize;
		int weaponY = tokenPosition.getY()*squareSize;
		g.drawImage(scaledImage, weaponX, weaponY, null);
	}

	public void drawImage(Graphics g, int squareSize, Coordinate tokenPosition){
		Image scaledImage = wepImg.getScaledInstance(squareSize, squareSize, Image.SCALE_DEFAULT);
		int weaponX = tokenPosition.getX()*squareSize;
		int weaponY = tokenPosition.getY()*squareSize;
		g.drawImage(scaledImage, weaponX, weaponY, null);
	}

	public Coordinate getPosition(){
		return token.getPosition();
	}

}
