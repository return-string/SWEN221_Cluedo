package ui;

import java.awt.Dimension;

/** A new DeckPanel is created every turn. Its role is to show the
 * cards held by a player, show their notebook when requested, and
 * hold the textbox that will give a written representation of turn
 * events. 
 * 
 * @author Vicki
 *
 */
public class DeckPanel extends AbstractPanel {
	private static final long serialVersionUID = 1045273197150007538L;

	public DeckPanel(Controller c) {
		super(c);
		setPreferredSize(new Dimension(800, 120));
		setAlignmentX(RIGHT_ALIGNMENT);
		setAlignmentY(TOP_ALIGNMENT);
	}

	// contains player's hand, player's notebook and text output pane. 
}
