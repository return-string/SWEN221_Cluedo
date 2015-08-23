package ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.Player;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;

public class PlayersPanel extends CluedoPanel {
	private static final long serialVersionUID = -8020775749716174732L;
	private Map<String,JPanel> panels = new HashMap<>();
	private List<Player> players = new ArrayList<>();
	private final int PP_HEIGHT = 480; // for now, the assumed height of the panel 
	private final int PP_WIDTH = 190; // likewise
	private final int PLAYER_REGULAR = 170; // width of a regular player panel
	private final int PLAYER_ACTIVE = 190; // width of the current player's panel
	private int panel_height;
	private final int icon_dims = 50;
	
	public PlayersPanel(Controller c) {
		super(c);
		initComponents();
		panel_height = panelHeight();
	}

	private int panelHeight() {
		return (players.size() >= 1 ?
				(int)((PP_HEIGHT / players.size()+2) / 100)
				: 1); // TODO get frame height
	}
	
	// width of the panel should be constant. 

	@Override
	public void nextTurn() {
		init();
	}
	
	/** This method performs the basic initialisation tasks: 
	 * creating the list of JPanels and Players. 
	 */
	private void init() {
		List<Player> players = controller().getPlayers();
		if (this.players.equals(players)) {
			addPlayers(); // we already have JPanels for these players, so don't bother.
		} else { // otherwise, set 'em up!			
			panels = new HashMap<>();
			for (Player p : players) {
				JPanel j = new JPanel();
				j.setMinimumSize(new Dimension(PLAYER_REGULAR,panelHeight()));
				JLabel name = new JLabel();
				name.setText(p.getName());
				name.setAlignmentX(RIGHT_ALIGNMENT);
				name.setAlignmentY(CENTER_ALIGNMENT);
				panels.put(p.getCharacter(),j);
			}
			addPlayers();
		}
	}

	/** This method is responsible for actually arranging the 
	 * components within the panel. 
	 */
	private void addPlayers() {
        setMinimumSize(new java.awt.Dimension(PP_WIDTH, PP_HEIGHT));
        setPreferredSize(new java.awt.Dimension(PP_WIDTH, PP_HEIGHT));
        
		for (Player p : players) {
			JPanel j = new PlayerBox(p);
			// if this player is current player
			if (p.equals(controller().getCurrentPlayer())) {
				j.setMinimumSize(new Dimension(PLAYER_ACTIVE,panelHeight()));
			}
			add(j);
		}
		repaint();
	}
	
	public static void main(String[] args) {
		Frame demoFrame = new Frame();
		demoFrame.setPreferredSize(new Dimension(800,600));
		PlayersPanel p = new PlayersPanel(null);
		p.setPreferredSize(new Dimension(190,480));
		demoFrame.add(p);
		demoFrame.pack();
		demoFrame.setVisible(true);
	}
	                 
    private void initComponents() {
        setMinimumSize(new java.awt.Dimension(PP_WIDTH, PP_HEIGHT));
        setPreferredSize(new java.awt.Dimension(PP_WIDTH, PP_HEIGHT));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        
        // create the horizontal group for the PlayerBoxes
		javax.swing.GroupLayout.ParallelGroup H = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);

		// originally a for-each loop... but, unfortunately, maps don't have indices. 
        for (int i = 0; i < game.Card.CHARACTERS.length; i++) {
        	if (panels.containsKey(game.Card.CHARACTERS[i])) {
        		H.addComponent(panels.get(game.Card.CHARACTERS[i]), javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        	}
        }
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(H))
        );
        
        // create the vertical group for PlayerBoxes
		javax.swing.GroupLayout.SequentialGroup V = layout.createSequentialGroup();

        for (int i = 0; i < game.Card.CHARACTERS.length; i++) {
        	if (panels.containsKey(game.Card.CHARACTERS[i])) {
        		V.addComponent(panels.get(game.Card.CHARACTERS[i]), javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        	}
        }
        
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(V))
        );
    }
    
    private class PlayerBox extends JPanel {
    	PlayerBox(Player p) {
    		init(p);
    	}
    	
    	private void init(Player p) {
	        // setBackground(new java.awt.Color(140, 40, 40));
    		JLabel playerName = new JLabel();
    		JLabel playerChar = new JLabel();
    		JPanel avatarPanel = new JPanel();
    		
    		BufferedImage avatar;
			try {
				avatar = ImageIO.read(new File("/assets/imgs/white_s.png"));
			} catch (IOException e) {
				avatar = null;
			} // TODO IMAGES
    		JLabel picLabel = new JLabel(new ImageIcon(avatar));
    		avatarPanel.add(picLabel);
    		
	        setMinimumSize(new java.awt.Dimension(PP_WIDTH, PP_HEIGHT));
	        setPreferredSize(new java.awt.Dimension(PLAYER_REGULAR, panel_height));
	
	        setMinimumSize(new java.awt.Dimension(icon_dims, icon_dims));
	
	        // Layout for the player avatar
	        javax.swing.GroupLayout iconLayout = new javax.swing.GroupLayout(avatarPanel);
	        avatarPanel.setLayout(iconLayout);
	        iconLayout.setHorizontalGroup(
	        		iconLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGap(0, 50, Short.MAX_VALUE)
	        );
	        iconLayout.setVerticalGroup(
	        		iconLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGap(0, 50, Short.MAX_VALUE)
	        );
	
	        playerName.setText("George");
	        playerName.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
	
	        playerChar.setText("Miss Scarlet");
	
	        // layout for the panel itself
	        javax.swing.GroupLayout playerLayout = new javax.swing.GroupLayout(this);
	        this.setLayout(playerLayout);
	        playerLayout.setHorizontalGroup(
        		playerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, playerLayout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(avatarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(playerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(playerName)
	                    .addComponent(playerChar))
	                .addContainerGap(45, Short.MAX_VALUE))
	        );
	        playerLayout.setVerticalGroup(
        		playerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(playerLayout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(playerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(avatarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addGroup(playerLayout.createSequentialGroup()
	                        .addComponent(playerName)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(playerChar)))
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	        );
    	}
    }
}