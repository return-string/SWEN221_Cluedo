/**
 * 
 */
package game;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.IOException;

import ui.MutedSkin;

/**
 * @author Vicki
 *
 */
public class CardDrawer {
	
	private int x = Integer.MIN_VALUE;
	private int y = Integer.MIN_VALUE;
	private int width = 215;
	private Card c;
	
	/** CardDrawer needs to know the card it's drawing, the location to draw it at, 
	 * and the desired width of the card (cards are rectangles with the ratio 2.15 : 3).
	 * @param card
	 * @param x
	 * @param y
	 * @param scale
	 */
	public CardDrawer(Card card, int x, int y, int width) {
		this.x = x;
		this.y = y;
		c = card;
	}
	
	public CardDrawer(Card card) {
		c = card;
	}
	
	public int height() {
		return (int)(width*1.4);
	}
	
	public void setX(double x)  { this.x = (int)(x+0.5); }
	public void setX(int x) 	{ this.x = x; }
	public void setY(double y)  { this.y = (int)(y+0.5); }
	public void setY(int y) 	{ this.y = y; }
	
	public void repaint(Graphics g) {
		MutedSkin sk = null;
		try {
			sk = new MutedSkin();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.setColor(sk.cardBack());
		g.fillRect(x,y,width,height());
		// we'll print some pretty pictures later, but for now, this is OK
		String text = c.getType().toString().toUpperCase();
        FontMetrics fm = g.getFontMetrics();
        int textX = centerWidth(text);
        int textY = (int) (y + height()*0.05 + fm.getAscent());
		g.drawString(text, textX, textY);
		
		text = c.getValue();
		textX = centerWidth(text);
		textY = (int) (y + height()*0.05 + fm.getHeight());
		g.drawString(text,textX,textY);
	}
	
	int centerWidth(String s) {
		return (width) / 2 + x - (width/s.length()*25);
	}
}
