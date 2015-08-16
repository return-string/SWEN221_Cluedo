package ui;

import java.util.Set;

/**
 * Currently, an empty class!
 * 
 * The role of the TurnPanel is to contain all panels related to the game and
 * to draw them when requested by the JFrame. 
 * 
 * Contains DeckPanel, PlayersPanel and BoardPanel, as well as any windows
 * that appear above them. (Eg making a hypothesis, notifications etc)
 * 
 * @author Vicki
 *
 */
public class TurnPanel extends AbstractPanel {
	private Set<AbstractPanel> contents;
	
	// contains DeckPanel, PlayersPanel and BoardPanel
}
