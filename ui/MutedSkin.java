package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.awt.Font.*;

public class MutedSkin implements CluedoSkin {
	final Font primaryTitleFont;
	final Font secondaryTitleFont;
	
	public MutedSkin() throws FontFormatException, IOException {
		primaryTitleFont =  Font.createFont(Font.PLAIN,
					new File("/font/IMFell_Normal.ttf"));
		secondaryTitleFont =  Font.createFont(Font.PLAIN,
				new File("/font/PermanentMarker.ttf"));
	}
	
	public Color cardBack() {
		return new Color(20,41,38);
	}

	public Color cardFace() {
		return new Color(33,20,92);
	}

	public Color primaryBG() {
		return Color.white;
	}

	public Color secondaryBG() {
		return new Color(34,8,91);
	}

	public Color tertiaryBG() {
		return new Color(33,34,86);
	}

	public Color primaryText() {
		return new Color(33,32,22);
	}

	public Color secondaryText() {
		return new Color(33,20,25);
	}
	
	public Font titleFont() {
		return primaryTitleFont;
	}
}
