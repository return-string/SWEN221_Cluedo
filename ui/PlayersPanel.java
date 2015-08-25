package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.Card;
import game.Game;
import game.Player;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;

public class PlayersPanel extends CluedoPanel {
	private static final long serialVersionUID = -8020775749716174732L;
	private List<JPanel> panels = new ArrayList<JPanel>();
	private final int PP_HEIGHT = 480; // for now, the assumed height of the panel
	private final int PP_WIDTH = 190; // likewise
	private final int PLAYER_REGULAR = 170; // width of a regular player panel
	private final int PLAYER_ACTIVE = 190; // width of the current player's panel
	private int panel_height;
	private final int icon_dims = 50;


	public PlayersPanel(Controller c) {
		super(c);
		addPlayers();
		this.setLayout(new GridLayout(0,1));
		for(JPanel panel : this.panels){
			add(panel);
		}
		panel_height = panelHeight();
		repaint();
	}

	private int panelHeight() {
		return (panels.size() >= 1 ?
				(int)((PP_HEIGHT / panels.size()+2/ 100))
				: 1); // TODO get frame height
	}

	// width of the panel should be constant.

	@Override
	public void nextTurn() {

	}

	/** This method performs the basic initialisation tasks:
	 * creating the list of JPanels and Players.
	 */
	private void addPlayers() {
		List<Player> players = controller().getPlayers();
		panels = new ArrayList<>();
		for (Player p : players) {
			panels.add(new PlayerBox(p));
		}
	}

	public static void main(String[] args) {
		CluedoFrame f = new CluedoFrame();
		Controller c = new Controller(f);
		c.startGame(Game.createDefaultMap());
		PlayersPanel toShow = new PlayersPanel(c);
		JFrame testFrame = new JFrame();
		testFrame.add(toShow);
		testFrame.pack();
		testFrame.setVisible(true);
	}





    private class PlayerBox extends JPanel {



    	PlayerBox(Player p) {
    		init(p);
    	}

    	private void init(Player p) {
    		System.err.println("creating "+p.getName()+" as "+p.getCharacter());
//    		// first, the dimensions of the box
	        setMaximumSize(new java.awt.Dimension(PP_WIDTH, PP_HEIGHT));


    		JLabel playerName = new JLabel();
    		JLabel playerChar = new JLabel();
    		JPanel avatarPanel = new JPanel();

    		playerName.setText(p.getName());
    		playerChar.setText(p.getCharacter());

    		add(playerChar);
    		add(playerName);

    		setBackground(java.awt.Color.blue);
			setPreferredSize(new Dimension(PLAYER_ACTIVE,panelHeight()));

//
//
//			// now the avatar
//    		BufferedImage avatar;
//			try {
//				avatar = ImageIO.read(new File("/assets/imgs/white_s.png")); // TODO read from appropriate file
//			} catch (IOException e) {
//				avatar = null;
//			} // TODO IMAGES
//    		JLabel picLabel;
//    		if (avatar != null) {
//    			picLabel = new JLabel(new ImageIcon(avatar));
//    		} else {
//    			picLabel = new JLabel("[ ]");
//    		}
//    		avatarPanel.add(picLabel);
//	        avatarPanel.setMinimumSize(new java.awt.Dimension(icon_dims, icon_dims));
//	        avatarPanel.setPreferredSize(new java.awt.Dimension(icon_dims, icon_dims));
//	        // Layout for the player avatar
//	        javax.swing.GroupLayout iconLayout = new javax.swing.GroupLayout(avatarPanel);
//	        avatarPanel.setLayout(iconLayout);
//	        iconLayout.setHorizontalGroup(
//	        		iconLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//	            .addGap(0, 50, Short.MAX_VALUE)
//	        );
//	        iconLayout.setVerticalGroup(
//	        		iconLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//	            .addGap(0, 50, Short.MAX_VALUE)
//	        );
//
//	        // now the player name
//	        playerName.setText(p.getName());
//	        playerName.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
//	        playerChar.setText(p.getCharacter());
//	        // layout for the panel itself
//	        javax.swing.GroupLayout playerLayout = new javax.swing.GroupLayout(this);
//	        //this.setLayout(playerLayout);
//	        playerLayout.setHorizontalGroup(
//        		playerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, playerLayout.createSequentialGroup()
//	                .addContainerGap()
//	                .addComponent(avatarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//	                .addGroup(playerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//	                    .addComponent(playerName)
//	                    .addComponent(playerChar))
//	                .addContainerGap(45, Short.MAX_VALUE))
//	        );
//	        playerLayout.setVerticalGroup(
//        		playerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//	            .addGroup(playerLayout.createSequentialGroup()
//	                .addContainerGap()
//	                .addGroup(playerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//	                    .addComponent(avatarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//	                    .addGroup(playerLayout.createSequentialGroup()
//	                        .addComponent(playerName)
//	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//	                        .addComponent(playerChar)))
//	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//	        );
//	    	this.setLayout(playerLayout);
    	}

    	public void setActive(boolean b){
    		if(b){
    			this.setBackground(Color.YELLOW);
    		} else {
    			this.setBackground(Color.DARK_GRAY);
    		}
    		this.repaint();
    	}

    }

}