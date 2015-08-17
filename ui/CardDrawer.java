/**
 * 
 */
package ui;

import game.Card;
import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.IOException;

/**
 * @author Vicki
 *
 */
public class CardDrawer {
	private int x = Integer.MIN_VALUE;
	private int y = Integer.MIN_VALUE;
	private int width = 215;
        private CluedoSkin skin = null;
	
	/** CardDrawer needs to know the card it's drawing, the location to draw it at, 
	 * and the desired width of the card (cards are rectangles with the ratio 2.15 : 3).
	 * @param x
	 * @param y
	 */
	public CardDrawer(int x, int y, int width) {
		this.x = x;
		this.y = y;
                this.width = width;
	}
	
        public int width() {
            return width;
        }
	public int height() {
		return (int)(width*1.4);
	}
	
	public void setX(int x) 	{ this.x = x; }
	public void setY(int y) 	{ this.y = y; }
	
	public void repaintCard(Graphics g, Card c) throws Error {
            CluedoSkin sk = skin;
            if (sk == null) {
                try {
                    sk = new MutedSkin();
                } catch (FontFormatException | IOException e) {
                    throw new Error("Can't find an appropriately-formatted font!");
                }
            }
            drawCard(g, sk.cardFace(), c);
            // we'll print some pretty pictures later, but for now, this is OK
            String text = c.getType().toString().toUpperCase();
            FontMetrics fm = g.getFontMetrics();
            int textX = centerWidth(text);
            int textY = (int) (y + height()*0.05 + fm.getAscent());
            g.setFont(sk.emphasisFont());
            g.drawString(text, textX, textY);

            text = c.getValue();
            textX = centerWidth(text);
            textY = (int) (y + height()*0.05 + fm.getHeight());
            g.setFont(sk.subtitleFont());
            g.drawString(text,textX,textY);
	}
	
	int centerWidth(String s) {
		return (width) / 2 + x - (width/s.length()*25);
	}

    void recolor(CluedoSkin sk) {
        this.skin = sk;
    }
    
    /** Moves the upper left corner to the given X and Y */
    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /* Moves the center of the card to the given X and Y   */
    public void moveCenterTo(int x, int y) {
        this.x = x - width/2;
        this.y = y - height()/2;
    }

    private void drawCard(Graphics g, Color sk, Card c) {
        int cornerDiam = (int) (x*0.07);
        int h = height();
        int w = width;
        g.setColor(sk);
        // we're doing some rounded corners on the cards, so first draw the main rectangle...
        g.fillRect(x-(cornerDiam/2),y-(cornerDiam/2), 
                width - (2*cornerDiam),h - (2*cornerDiam));
        // then draw the rounded corners
        g.fillOval(x, y, cornerDiam, cornerDiam); // top left
        g.fillOval(x+w-cornerDiam, y, cornerDiam, cornerDiam); // top right
        g.fillOval(x-cornerDiam, y+h-cornerDiam, cornerDiam, cornerDiam); // bottom left
        g.fillOval(x+w-cornerDiam, y+h-cornerDiam, cornerDiam, cornerDiam); // bottom right
        // and now, fill in the space between the ovals!
        g.fillRect(x+cornerDiam/2, y, width-cornerDiam, cornerDiam); // top
        g.fillRect(x+width-cornerDiam, y+cornerDiam/2, cornerDiam, h-cornerDiam); // right
        g.fillRect(x+cornerDiam/2, y+h-cornerDiam/2, width-cornerDiam, cornerDiam/2); // bottom
        g.fillRect(x,y+cornerDiam/2, cornerDiam, h-cornerDiam); // left
    }
}
