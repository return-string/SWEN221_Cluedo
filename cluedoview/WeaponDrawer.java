package cluedoview;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.Weapon;

public class WeaponDrawer {

	private Weapon weapon;
	private Image wepImg;

	private String imgPath = "images/";
	private String imgType = ".png";

	public WeaponDrawer(Weapon weapon){
		this.weapon = weapon;
		retrieveImage();
	}

	private void retrieveImage() {
		java.net.URL imageURL = WeaponDrawer.class.getResource(imgPath
				+ weapon.getName() + imgType);

		try {
			wepImg = ImageIO.read(imageURL);
		} catch (IOException e) {
			// we've encountered an error loading the image. There's not much we
			// can actually do at this point, except to abort the game.
			throw new RuntimeException("Unable to load image: " + weapon.getName() + imgType);
		}
	}

	public void drawImage(Graphics g, int squareSize){
		Image scaledImage = wepImg.getScaledInstance(squareSize, squareSize, Image.SCALE_DEFAULT);
		int weaponX = weapon.getPosition().getX()*squareSize;
		int weaponY = weapon.getPosition().getY()*squareSize;
		g.drawImage(scaledImage, weaponX, weaponY, null);
	}


}
