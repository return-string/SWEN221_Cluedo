package ui;

import java.awt.Color;
import java.awt.Font;

public interface CluedoSkin {
	public Color cardBack();
	public Color cardFace();
	
	public Color primaryBG();
	public Color secondaryBG();
	public Color tertiaryBG();
	
	public Color primaryText();
	public Color secondaryText();
	
	public Font titleFont();
        public Font subtitleFont();
        public Font emphasisFont();
        public Font normalFont();
        public Font subtextFont();
}
