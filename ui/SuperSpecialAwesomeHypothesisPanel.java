package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import game.Card;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SuperSpecialAwesomeHypothesisPanel extends CluedoPanel implements ActionListener{

	private String chosenCharacter;
	private String chosenWeapon;
	private String chosenRoom;

	private RadioListener characterMonitor = new RadioListener(Card.Type.CHARACTER);
	private RadioListener weaponMonitor = new RadioListener(Card.Type.WEAPON);
	private RadioListener roomMonitor = new RadioListener(Card.Type.ROOM);
	
	private String buttonString = "Make Hypothesis";

	public SuperSpecialAwesomeHypothesisPanel(Controller c) {
		super(c);

		this.setLayout(new BorderLayout());
		JPanel selectionPanel = new JPanel();
		selectionPanel.setLayout(new GridLayout(1,3));
		addCharacterSelection(selectionPanel);
		addWeaponSelection(selectionPanel);
		addRoomSelection(selectionPanel);
		add(selectionPanel, BorderLayout.CENTER);
		JButton makeHypothesis = new JButton(buttonString);
		makeHypothesis.addActionListener(this);
		add(makeHypothesis, BorderLayout.SOUTH);
	}
	
	public SuperSpecialAwesomeHypothesisPanel(Controller c, String room) {
		super(c);

		this.chosenRoom = room;
		this.setLayout(new BorderLayout());
		JPanel selectionPanel = new JPanel();
		selectionPanel.setLayout(new GridLayout(1,2));
		addCharacterSelection(selectionPanel);
		addWeaponSelection(selectionPanel);
		add(selectionPanel, BorderLayout.CENTER);
		JButton makeHypothesis = new JButton(buttonString);
		makeHypothesis.addActionListener(this);
		add(makeHypothesis, BorderLayout.SOUTH);
	}


	private void addRoomSelection(JPanel selectionPanel) {
		JPanel roomSelection = new JPanel();
		roomSelection.setLayout(new GridLayout(0,1));
		ButtonGroup roomGroup = new ButtonGroup();
		roomGroup.add(addRadioButton(Card.BALL, roomSelection, roomMonitor));
		roomGroup.add(addRadioButton(Card.BILLIARD, roomSelection, roomMonitor));
		roomGroup.add(addRadioButton(Card.CONSERVATORY, roomSelection, roomMonitor));
		roomGroup.add(addRadioButton(Card.DINING, roomSelection, roomMonitor));
		roomGroup.add(addRadioButton(Card.HALL, roomSelection, roomMonitor));
		roomGroup.add(addRadioButton(Card.KITCHEN, roomSelection, roomMonitor));
		roomGroup.add(addRadioButton(Card.LIBRARY, roomSelection, roomMonitor));
		roomGroup.add(addRadioButton(Card.LOUNGE, roomSelection, roomMonitor));
		roomGroup.add(addRadioButton(Card.STUDY, roomSelection, roomMonitor));
		selectionPanel.add(roomSelection);
	}


	private void addWeaponSelection(JPanel selectionPanel) {
		JPanel weaponSelection = new JPanel();
		weaponSelection.setLayout(new GridLayout(0,1));
		ButtonGroup weaponGroup = new ButtonGroup();
		weaponGroup.add(addRadioButton(Card.PIPE, weaponSelection, weaponMonitor));
		weaponGroup.add(addRadioButton(Card.REVOLVER, weaponSelection, weaponMonitor));
		weaponGroup.add(addRadioButton(Card.CANDLESTICK, weaponSelection, weaponMonitor));
		weaponGroup.add(addRadioButton(Card.DAGGER, weaponSelection, weaponMonitor));
		weaponGroup.add(addRadioButton(Card.ROPE, weaponSelection, weaponMonitor));
		weaponGroup.add(addRadioButton(Card.WRENCH, weaponSelection, weaponMonitor));
		selectionPanel.add(weaponSelection);

	}


	private void addCharacterSelection(JPanel selectionPanel) {
		JPanel characterSelection = new JPanel();
		characterSelection.setLayout(new GridLayout(0,1));
		ButtonGroup characterGroup = new ButtonGroup();
		characterGroup.add(addRadioButton(Card.WHITE, characterSelection, characterMonitor));
		characterGroup.add(addRadioButton(Card.PEACOCK, characterSelection, characterMonitor));
		characterGroup.add(addRadioButton(Card.PLUM, characterSelection, characterMonitor));
		characterGroup.add(addRadioButton(Card.MUSTARD, characterSelection, characterMonitor));
		characterGroup.add(addRadioButton(Card.SCARLET, characterSelection, characterMonitor));
		characterGroup.add(addRadioButton(Card.GREEN, characterSelection, characterMonitor));
		selectionPanel.add(characterSelection);
	}


	private AbstractButton addRadioButton(String buttonName,
			JPanel radioPanel, RadioListener listener) {
		JRadioButton toAdd = new JRadioButton(buttonName);
		toAdd.addActionListener(listener);
		radioPanel.add(toAdd);
		return toAdd;
	}


	@Override
	public void nextTurn() {
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(buttonString)){
			HashSet<String> hypothesis = new HashSet<String>();
			hypothesis.add(chosenCharacter);
			hypothesis.add(chosenWeapon);
			hypothesis.add(chosenRoom);
			super.controller().testHypothesis(hypothesis);
		}

	}

	class RadioListener implements ActionListener{

		private Card.Type toMonitor;

		public RadioListener(Card.Type toMonitor){
			this.toMonitor = toMonitor;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(toMonitor.equals(Card.Type.CHARACTER)){
				SuperSpecialAwesomeHypothesisPanel.this.chosenCharacter = e.getActionCommand();
			} else if(toMonitor.equals(Card.Type.WEAPON)){
				SuperSpecialAwesomeHypothesisPanel.this.chosenWeapon = e.getActionCommand();
			} else {
				SuperSpecialAwesomeHypothesisPanel.this.chosenRoom = e.getActionCommand();
			}
		}

	}


}
