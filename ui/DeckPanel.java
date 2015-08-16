package ui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

/** A new DeckPanel is created every turn. Its role is to show the
 * cards held by a player, show their notebook when requested, and
 * hold the textbox that will give a written representation of turn
 * events. 
 * 
 * @author Vicki
 *
 */
public class DeckPanel extends CluedoPanel {
	private static final long serialVersionUID = 1045273197150007538L;

	public DeckPanel(Controller c) {
		setPreferredSize(new Dimension(800, 120));
		setAlignmentX(RIGHT_ALIGNMENT);
		setAlignmentY(TOP_ALIGNMENT);
		//needs some way of getting access to the skin class, but for now
		setBackground(new Color(33,34,86));
	}

	// contains player's hand, player's notebook and text output pane. 
}
