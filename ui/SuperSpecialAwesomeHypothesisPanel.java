package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.Card;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SuperSpecialAwesomeHypothesisPanel extends CluedoPanel implements ActionListener{

	private String chosenCharacter;
	private String chosenWeapon;
	private String chosenRoom;

	private RadioListener characterMonitor = new RadioListener(Card.Type.CHARACTER);
	private RadioListener weaponMonitor = new RadioListener(Card.Type.WEAPON);
	private RadioListener roomMonitor = new RadioListener(Card.Type.ROOM);

	public SuperSpecialAwesomeHypothesisPanel(Controller c) {
		super(c);

		this.setLayout(new BorderLayout());
		JPanel selectionPanel = new JPanel();
		selectionPanel.setLayout(new GridLayout(1,3));
		addCharacterSelection(selectionPanel);
		addWeaponSelection(selectionPanel);

	}


	private void addWeaponSelection(JPanel selectionPanel) {
		JPanel weaponSelection = new JPanel();
		weaponSelection.setLayout(new GridLayout(0,1));
		ButtonGroup weaponGroup = new ButtonGroup();
		weaponGroup.add(addRadioButton(Card.WHITE, weaponSelection, weaponMonitor));
		weaponGroup.add(addRadioButton(Card.PEACOCK, weaponSelection, weaponMonitor));
		weaponGroup.add(addRadioButton(Card.PLUM, weaponSelection, weaponMonitor));
		weaponGroup.add(addRadioButton(Card.MUSTARD, weaponSelection, weaponMonitor));
		weaponGroup.add(addRadioButton(Card.SCARLET, weaponSelection, weaponMonitor));
		weaponGroup.add(addRadioButton(Card.GREEN, weaponSelection, weaponMonitor));
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
		// TODO Auto-generated method stub

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

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
