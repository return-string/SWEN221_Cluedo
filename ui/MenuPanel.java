package ui;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class MenuPanel extends CluedoPanel {
	
	private JButton newGame;
	private JButton rules;
	private JButton exit;
	private ImageIcon logo;
	private JLabel logoLabel;
	
	static final String LOGOFILE = "assets/cluedologo.gif";
	
	public MenuPanel(Controller c){
		super(c);
		this.newGame = new JButton("New Game");
		this.newGame.setAlignmentX(CENTER_ALIGNMENT);
		this.rules = new JButton("Rules");
		this.rules.setAlignmentX(CENTER_ALIGNMENT);
		this.exit = new JButton("Exit");
		this.exit.setAlignmentX(CENTER_ALIGNMENT);
		this.logo = new ImageIcon(LOGOFILE);
		this.logoLabel = new JLabel(this.logo);
		this.logoLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		add(this.logoLabel);
		add(this.newGame);
		add(this.rules);
		add(this.exit);
		this.newGame.addActionListener(c);
		this.rules.addActionListener(c);
		this.exit.addActionListener(c);
	}
	
	@Override
	public void nextTurn() {
	}

	

}
