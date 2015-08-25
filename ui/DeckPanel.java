package ui;

import game.Card;
import game.CardImpl;
import game.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

/** The deck panel is responsible for showing the
 * cards held by a player, show their notebook when requested, and
 * hold the textbox that will give a written representation of turn
 * events.
 *
 * @author Vicki
 *
 */
public class DeckPanel extends CluedoPanel {
	private static final long serialVersionUID = 1045273197150007538L;
        private Player curPlayer;
        private final CardDrawer cd;


    private JSplitPane deckPanelChild_deckContents;
    private JScrollPane playerHand;
    private JPanel handContents;
	private JPanel notesCharacter;
	private JCheckBox char1;
	private JPanel notesWeapons;
	private JCheckBox weapon1;
	private JPanel notesRooms;
	private JCheckBox room1;
	private JPanel frameChild_deckPanel;

	public DeckPanel(Controller c) {
            super(c);
            setPreferredSize(new Dimension(800, 120));
            setAlignmentX(RIGHT_ALIGNMENT);
            setAlignmentY(TOP_ALIGNMENT);
            cd = new CardDrawer(200,20,20);
            setBackground(new Color(34,34,94)); // need a way of passing colours around from the frame, ideally.

            frameChild_deckPanel = new javax.swing.JPanel();
            deckPanelChild_deckContents = new javax.swing.JSplitPane();
            playerHand = new javax.swing.JScrollPane();
            handContents = new javax.swing.JPanel();

            notesCharacter = new javax.swing.JPanel();
            char1 = new javax.swing.JCheckBox();

            notesWeapons = new javax.swing.JPanel();
            weapon1 = new javax.swing.JCheckBox();

            notesRooms = new javax.swing.JPanel();
            room1 = new javax.swing.JCheckBox();

            frameChild_deckPanel.setBackground(new java.awt.Color(80, 80, 10));
            frameChild_deckPanel.setMaximumSize(null);
            deckPanelChild_deckContents.setDividerLocation(300);
            deckPanelChild_deckContents.setDividerSize(10);
            frameChild_deckPanel.add(this);
	}

	@Override
	public void nextTurn() {
            // TODO Auto-generated method stub
            Player p = getNextPlayer();
            if (p != curPlayer) {
                for (Card c : p.getHand()) {
                    cd.repaintCard(null, c);
                }
            }

	}

	private Player getNextPlayer() {
		if (controller().checkGameState()) {
			// needs some way of getting access to players.
		}

		// FIXME for now, let's use a dummy player.
		List<Card> l = new ArrayList<Card>();
		l.add(new CardImpl(Card.Type.CHARACTER,Card.SCARLET));
		l.add(new CardImpl(Card.Type.CHARACTER,Card.PLUM));
		l.add(new CardImpl(Card.Type.ROOM,Card.BALL));
		l.add(new CardImpl(Card.Type.WEAPON,Card.DAGGER));
		Player p = new Player(Card.MUSTARD,Card.MUSTARD,l,Card.getPlayerStart(Card.MUSTARD));

		return p;
	}

	// contains player's hand, player's notebook and text output pane.
}
