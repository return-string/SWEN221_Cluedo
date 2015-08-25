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

import game.Card;
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
    
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JPanel jPanel7;
	private javax.swing.JPanel jPanel8;
	private javax.swing.JPanel jPanel9;
	private javax.swing.JPanel peacock;
	private javax.swing.JPanel peacockAv;
	private javax.swing.JLabel peacockChar;
	private javax.swing.JLabel peacockName;
	private javax.swing.JPanel scarlet;
	private javax.swing.JPanel scarletAv;
	private javax.swing.JLabel scarletChar;
	private javax.swing.JLabel scarletName;
	public PlayersPanel(Controller c) {
		super(c);
		//addPlayers();
		initComponents();
		//panel_height = panelHeight();
		//repaint();
	}

	private int panelHeight() {
		return (panels.size() >= 1 ?
				(int)((PP_HEIGHT / panels.size()+2) / 100)
				: 1); // TODO get frame height
	}
	
	// width of the panel should be constant. 

	@Override
	public void nextTurn() {
		addPlayers();
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
		f.testingGameSetup();
		f.showPanel(CluedoFrame.CARD_MENU);
	}
	                 
    private void initComponents() {
        jPanel2 = new javax.swing.JPanel();
        peacock = new javax.swing.JPanel();
        peacockName = new javax.swing.JLabel();
        peacockChar = new javax.swing.JLabel();
        peacockAv = new javax.swing.JPanel();
        scarlet = new javax.swing.JPanel();
        scarletAv = new javax.swing.JPanel();
        scarletName = new javax.swing.JLabel();
        scarletChar = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();

        jPanel2.setBackground(new java.awt.Color(40, 40, 123));
        jPanel2.setMinimumSize(new java.awt.Dimension(178, 70));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 178, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        setMinimumSize(new java.awt.Dimension(190, 480));
        setPreferredSize(new java.awt.Dimension(190, 480));

        peacock.setBackground(new java.awt.Color(40, 40, 123));
        peacock.setMinimumSize(new java.awt.Dimension(178, 70));
        peacock.setPreferredSize(new java.awt.Dimension(178, 70));

        peacockName.setText("Captain Obvious");
        peacockName.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        peacockChar.setText("Mrs Peacock");

        peacockAv.setMinimumSize(new java.awt.Dimension(50, 50));
        peacockAv.setPreferredSize(new java.awt.Dimension(50, 50));

        javax.swing.GroupLayout peacockAvLayout = new javax.swing.GroupLayout(peacockAv);
        peacockAv.setLayout(peacockAvLayout);
        peacockAvLayout.setHorizontalGroup(
            peacockAvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        peacockAvLayout.setVerticalGroup(
            peacockAvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout peacockLayout = new javax.swing.GroupLayout(peacock);
        peacock.setLayout(peacockLayout);
        peacockLayout.setHorizontalGroup(
            peacockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, peacockLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(peacockAv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(peacockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(peacockName)
                    .addComponent(peacockChar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        peacockLayout.setVerticalGroup(
            peacockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(peacockLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(peacockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(peacockAv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(peacockLayout.createSequentialGroup()
                        .addComponent(peacockName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(peacockChar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        scarlet.setBackground(new java.awt.Color(140, 40, 40));
        scarlet.setMinimumSize(new java.awt.Dimension(178, 70));
        scarlet.setPreferredSize(new java.awt.Dimension(178, 70));

        scarletAv.setMinimumSize(new java.awt.Dimension(50, 50));

        javax.swing.GroupLayout scarletAvLayout = new javax.swing.GroupLayout(scarletAv);
        scarletAv.setLayout(scarletAvLayout);
        scarletAvLayout.setHorizontalGroup(
            scarletAvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        scarletAvLayout.setVerticalGroup(
            scarletAvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        scarletName.setText("George");
        scarletName.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        scarletChar.setText("Miss Scarlet");

        javax.swing.GroupLayout scarletLayout = new javax.swing.GroupLayout(scarlet);
        scarlet.setLayout(scarletLayout);
        scarletLayout.setHorizontalGroup(
            scarletLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, scarletLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scarletAv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(scarletLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scarletName)
                    .addComponent(scarletChar))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        scarletLayout.setVerticalGroup(
            scarletLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scarletLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(scarletLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scarletAv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(scarletLayout.createSequentialGroup()
                        .addComponent(scarletName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scarletChar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(120, 120, 40));
        jPanel6.setMinimumSize(new java.awt.Dimension(178, 70));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(120, 40, 120));
        jPanel7.setMinimumSize(new java.awt.Dimension(178, 70));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        jPanel8.setBackground(new java.awt.Color(40, 80, 120));
        jPanel8.setMinimumSize(new java.awt.Dimension(178, 70));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        jPanel9.setBackground(new java.awt.Color(120, 130, 90));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(peacock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scarlet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(peacock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scarlet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
//        setMinimumSize(new java.awt.Dimension(PP_WIDTH, PP_HEIGHT));
//        setPreferredSize(new java.awt.Dimension(PP_WIDTH, PP_HEIGHT));
//
//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
//        this.setLayout(layout);
//        
//        // create the horizontal group for the PlayerBoxes
//		javax.swing.GroupLayout.ParallelGroup H = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
//
//		// originally a for-each loop... but, unfortunately, maps don't have indices. 
//        for (int i = 0; i < panels.size(); i++) {
//    		H.addComponent(panels.get(i), javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
//        }
//        
//        layout.setHorizontalGroup(H);
//        
//        // create the vertical group for PlayerBoxes
//		javax.swing.GroupLayout.SequentialGroup V = layout.createSequentialGroup();
//
//        for (int i = 0; i < panels.size(); i++) {
//    		V.addComponent(panels.get(i), javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
//        }
//        
//        layout.setVerticalGroup(V);
        
//        layout.setVerticalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(layout.createSequentialGroup()
//                .addContainerGap()
//                .addGroup(V))
//        );
    }
    
    
    
    private class PlayerBox extends JPanel {
    	PlayerBox(Player p) {
    		init(p);
    	}
    	
    	private void init(Player p) {
    		System.err.println("creating "+p.getName()+" as "+p.getCharacter());
//    		// first, the dimensions of the box
	        setMaximumSize(new java.awt.Dimension(PP_WIDTH, PP_HEIGHT));
	        // if this is the active player, draw them wider
			if (p.equals(controller().getCurrentPlayer())) {
				setMinimumSize(new Dimension(PLAYER_ACTIVE,panelHeight()));
				setPreferredSize(new Dimension(PLAYER_ACTIVE,panelHeight()));
			} else {
				setMinimumSize(new Dimension(PLAYER_REGULAR,panelHeight()));
		        setPreferredSize(new java.awt.Dimension(PLAYER_REGULAR,panelHeight()));
			}
	        setBackground(java.awt.Color.black); // TODO remove this
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
    }
    
}