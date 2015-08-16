package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.Game;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CluedoFrame extends JFrame implements ActionListener {

	public static final Controller c = new Controller();
	private Game cluedoGame;
	private JPanel canvas;
	private TurnPanel turnpanel;
	private JButton rulesButton;
	private JButton diceButton;

	public CluedoFrame(Game cluedoGame) {
		super("Cluedo");
		this.cluedoGame = cluedoGame;
		setLayout(new BorderLayout()); // use border layout
		// this.canvas = new CluedoCanvas(cluedoGame);
		// add(canvas, BorderLayout.CENTER); // add canvas
		
		turnpanel = new TurnPanel(c);		
		
		this.rulesButton = new JButton("Display Rules");
		this.diceButton = new JButton("Roll Dice");
		add(diceButton, BorderLayout.SOUTH);
		add(rulesButton, BorderLayout.NORTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack(); // pack components tightly together
		setResizable(false); // prevent us from being resizeable
		setVisible(true); // make sure we are visible!
		rulesButton.addActionListener(this);
		diceButton.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Display Rules")){
			// cluedoGame.printRules();
			new RequestRulesEvent(e.getSource(),e.getModifiers(),"Rules");
		} else if (e.getActionCommand().equals("Roll Dice")){
			// cluedoGame.printDiceRoll();
			new RollDiceEvent(e.getSource(),e.getModifiers(),"Roll dice");
		}
	}


}
