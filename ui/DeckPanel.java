package ui;

import game.Card;
import game.Card.Type;
import game.CardImpl;
import game.Player;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
	private JPanel notesWeapons;
	private JPanel notesRooms;
	private JPanel playerNotebook;

	public DeckPanel(Controller c) {
		super(c);
		setPreferredSize(new Dimension(800, 120));
		setAlignmentX(RIGHT_ALIGNMENT);
		setAlignmentY(TOP_ALIGNMENT);
		cd = new CardDrawer(200,20,20);
		setBackground(new Color(34,34,94)); // need a way of passing colours around from the frame, ideally.

		deckPanelChild_deckContents = new javax.swing.JSplitPane();
		playerHand = new javax.swing.JScrollPane();
		handContents = new javax.swing.JPanel();
		playerNotebook = new javax.swing.JPanel();

		notesCharacter = new javax.swing.JPanel();
		JCheckBox char1 = new javax.swing.JCheckBox();

		notesWeapons = new javax.swing.JPanel();
		JCheckBox weapon1 = new javax.swing.JCheckBox();

		notesRooms = new javax.swing.JPanel();
		JCheckBox room1 = new javax.swing.JCheckBox();

		deckPanelChild_deckContents.setDividerLocation(300);
		deckPanelChild_deckContents.setDividerSize(10);

		playerHand.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

		handContents.setLayout(new java.awt.GridLayout(1, 0));
		playerHand.setViewportView(handContents);

		deckPanelChild_deckContents.setLeftComponent(playerHand);

		playerNotebook.setLayout(new javax.swing.BoxLayout(playerNotebook, javax.swing.BoxLayout.X_AXIS));

		notesCharacter.setLayout(new java.awt.GridLayout(3, 2));

		for (String chara : game.Card.CHARACTERS) {
			char1 = new JCheckBox();
			char1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
			char1.setText(chara);
			notesCharacter.add(char1);
		}
		playerNotebook.add(notesCharacter);

		notesWeapons.setLayout(new java.awt.GridLayout(3, 2));

		for (String wep : game.Card.WEAPONS) {
			weapon1 = new JCheckBox();
			weapon1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
			weapon1.setText(wep);
			notesWeapons.add(weapon1);
		}
		playerNotebook.add(notesWeapons);

		notesRooms.setLayout(new java.awt.GridLayout(4, 3));

		for (String room : game.Card.ROOMS) {
			room1 = new JCheckBox();
			room1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
			room1.setText(room);
			notesRooms.add(room1);
		}
		playerNotebook.add(notesRooms);

		deckPanelChild_deckContents.setRightComponent(playerNotebook);

	}

	@Override
	public void nextTurn() {
		// TODO Auto-generated method stub
		Player p = getNextPlayer();
		displayPlayerNotebook(p);

		if (p != curPlayer) {
			for (Card c : p.getHand()) {
				cd.repaintCard(null, c);
			}
		}

	}

	private void displayPlayerNotebook(Player p) {
		reenableCheckBoxes();
		Set<Card> innocents = p.getInnocents();
		greyOutInnocents(innocents, notesCharacter, Card.Type.CHARACTER);
		greyOutInnocents(innocents, notesWeapons, Card.Type.WEAPON);
		greyOutInnocents(innocents, notesRooms, Card.Type.ROOM);
	}

	private void reenableCheckBoxes() {
		reenableCheckPanel(notesCharacter);
		reenableCheckPanel(notesWeapons);
		reenableCheckPanel(notesRooms);

	}

	private void reenableCheckPanel(JPanel checkBoxPanel) {
		Component[] checkBoxes = checkBoxPanel.getComponents();
		for(int i = 0; i < checkBoxes.length; i++){
			if(checkBoxes[i] instanceof JCheckBox){
				JCheckBox box = (JCheckBox) checkBoxes[i];
				box.setEnabled(true);
			}
		}
	}

	private void greyOutInnocents(Set<Card> innocents, JPanel checkBoxPanel, Type type) {
		Component[] checkBoxes = checkBoxPanel.getComponents();
		for(int i = 0; i < checkBoxes.length; i++){
			if(checkBoxes[i] instanceof JCheckBox){
				JCheckBox box = (JCheckBox) checkBoxes[i];
				Card cardOfBox = new CardImpl(type, box.getActionCommand());
				if(innocents.contains(cardOfBox)){
					box.setEnabled(false);
				}
			}
		}
	}

	private Player getNextPlayer() {
		if (controller().checkGameState()) {
			return controller().getCurrentPlayer();
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

	public Component getContentsPanel() {
		return deckPanelChild_deckContents;
	}

	// contains player's hand, player's notebook and text output pane.
}
