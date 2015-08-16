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
public class TurnPanel extends CluedoPanel {
	private static final long serialVersionUID = -7388102847863707082L;
	private Set<CluedoPanel> contents;
	
	// contains DeckPanel, PlayersPanel and BoardPanel
	
	public TurnPanel(Controller c) {
		super(c);
		contents.add(new BoardPanel(c));
		contents.add(new PlayersPanel(c));
		contents.add(new DeckPanel(c));
	}

	@Override
	public void nextTurn() {
		// TODO Auto-generated method stub
		
	}
}
