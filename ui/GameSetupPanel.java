/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.Map;

import game.Card;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
/**
 *
 * @author mckayvick
 */
public class GameSetupPanel extends CluedoPanel {
	private static final long serialVersionUID = -620972152778453236L;
<<<<<<< HEAD
	private static final Map<String,String> players = new java.util.concurrent.ConcurrentHashMap<>();
=======
	private static final Map<String,String> players =new java.util.concurrent.ConcurrentHashMap<>();
>>>>>>> 1cecf3b03e757f523e9478d80086a1f6508c6b6c
    private SetupDialog setup;
    private volatile boolean hasSubmitted = false;

    public GameSetupPanel(Controller c) {
        super(c);
    }
/**
 * @param args the command line arguments
 */
    public static void main(String args[]) {
    	GameSetupPanel x = new GameSetupPanel(null);
    }

    @Override
    public void nextTurn() {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

<<<<<<< HEAD
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(SetupDialog.START)) {

        }
        System.out.println("GameSetup is listening");
    }


    /** makes no guarantees about whether this has been filled or not.
     *
=======
    
    /** makes no guarantees about whether this has been filled or not. 
     * 
>>>>>>> 1cecf3b03e757f523e9478d80086a1f6508c6b6c
     * @return
     */
	public Map<String, String> getResult() {
		if (players.size() >= 3) {
			hasSubmitted = true;
		}
		return players;
	}


    public SetupDialog getDialog() throws IllegalAccessException {
    	if (setup==null) { throw new IllegalAccessException(); }
    	return setup;
    }


	public void showDialog() {
        setup = new SetupDialog(players);
        setup.setVisible(true);
	}


	/**
	 * A GameSetupDialogue is created to ask the user which characters will be played
	 * during a game and the names associated with these Player objects.
	 * It must be initialised with an empty map of Strings to Strings, which it will
	 * fill with user-entered names as keys and character names (as defined by
	 * Card.CHARACTERS) as values.
	 *
	 * It opens in a new window (on account of, you know, being a dialog.)
	 *
	 * @author mckayvick
	 */
	class SetupDialog extends javax.swing.JDialog {
		private static final long serialVersionUID = -8204809907341739549L;
		static final String SUBMIT = "submitChars";
	    static final String START = "startGame";
<<<<<<< HEAD
	    private final Map<String,String> futurePlayers;

=======
	    
>>>>>>> 1cecf3b03e757f523e9478d80086a1f6508c6b6c

	    /** fix this */
	    SetupDialog(Map<String,String> players) {
	        if (players == null || players.size() != 0) {
	            throw new IllegalArgumentException("Cannot use a non-empty map!");
	        }
	        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	        initComponents();
	        setupButtons();
	    }

	    public void setupButtons() {
	        if (characters.size() <= 0) {
	            characters.put(Card.SCARLET,scarlet);
	            characters.put(Card.MUSTARD,mustard);
	            characters.put(Card.WHITE,white);
	            characters.put(Card.GREEN,green);
	            characters.put(Card.PEACOCK,peacock);
	            characters.put(Card.PLUM,plum);
	        }
	        for (Map.Entry<String,String> player : players.entrySet()) {
	            for (Map.Entry<String,JRadioButton> button : characters.entrySet()) {
	                ((JRadioButton)button.getValue()).setSelected(false);
	                if (player.getValue().equals(button.getKey())) {
	                    ((JRadioButton)button.getValue()).setEnabled(false);
	                    ((JRadioButton)button.getValue()).setRequestFocusEnabled(false);
	                }
	            }
	        }

	        progress.setString("3 characters needed to play.");
	        progress.setStringPainted(true);
<<<<<<< HEAD

	        if (futurePlayers.size() == 3) {
=======
	        
	        if (players.size() == 3) {
>>>>>>> 1cecf3b03e757f523e9478d80086a1f6508c6b6c
	            startButton.setVisible(true);
	        }
	        if (players.size() == 6) {
	            okButton.setVisible(false);
	            nameTextInput.setEnabled(false);
	        }
	    }

	    /** TODO comment     */
	    private void initComponents() {

	        charSelect = new javax.swing.ButtonGroup();
	        NameSelection = new javax.swing.JPanel();
	        nameLabel = new javax.swing.JLabel();
	        nameTextInput = new javax.swing.JTextField();
	        selectPanel = new javax.swing.JPanel();
	        scarlet = new javax.swing.JRadioButton();
	        mustard = new javax.swing.JRadioButton();
	        white = new javax.swing.JRadioButton();
	        green = new javax.swing.JRadioButton();
	        peacock = new javax.swing.JRadioButton();
	        plum = new javax.swing.JRadioButton();
	        submitPanel = new javax.swing.JPanel();
	        okButton = new javax.swing.JButton();
	        okButton.setActionCommand(SUBMIT);
	        startButton = new javax.swing.JButton();
	        startButton.setActionCommand(START);
	        progress = new javax.swing.JProgressBar();
	        textPanel = new javax.swing.JPanel();

	        setTitle("Player Setup");
	        setAlwaysOnTop(true);
	        setBackground(new java.awt.Color(204, 204, 255));
	        setMinimumSize(new java.awt.Dimension(435, 526));
	        setSize(new java.awt.Dimension(435, 516));

	        nameLabel.setText("Enter your name:");

	        nameTextInput.setHorizontalAlignment(javax.swing.JTextField.LEFT);
	        nameTextInput.setMinimumSize(new java.awt.Dimension(4, 4));
	        nameTextInput.setName("name"); // NOI18N

	        javax.swing.GroupLayout NameSelectionLayout = new javax.swing.GroupLayout(NameSelection);
	        NameSelection.setLayout(NameSelectionLayout);
	        NameSelectionLayout.setHorizontalGroup(
	            NameSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addComponent(nameTextInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	            .addGroup(NameSelectionLayout.createSequentialGroup()
	                .addComponent(nameLabel)
	                .addGap(0, 0, Short.MAX_VALUE))
	        );
	        NameSelectionLayout.setVerticalGroup(
	            NameSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(NameSelectionLayout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(nameLabel)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(nameTextInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	        );

	        selectPanel.setLayout(new java.awt.GridLayout(2, 3, 0, 3));

	        charSelect.add(scarlet);
	        scarlet.setAlignmentX(0.5F);
	        scarlet.setBorderPainted(true);
	        scarlet.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	        scarlet.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/scarlet_s.png"))); // NOI18N
	        scarlet.setHideActionText(true);
	        scarlet.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	        scarlet.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
	        scarlet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/scarlet.png"))); // NOI18N
	        scarlet.setOpaque(false);
	        scarlet.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/scarlet.png"))); // NOI18N
	        scarlet.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/scarlet_c.png"))); // NOI18N
	        scarlet.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/scarlet_c.png"))); // NOI18N
	        selectPanel.add(scarlet);

	        charSelect.add(mustard);
	        mustard.setAlignmentX(0.5F);
	        mustard.setBorderPainted(true);
	        mustard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	        mustard.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/mustard_s.png"))); // NOI18N
	        mustard.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	        mustard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/mustard.png"))); // NOI18N
	        mustard.setOpaque(false);
	        mustard.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/mustard.png"))); // NOI18N
	        mustard.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/mustard_c.png"))); // NOI18N
	        mustard.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/mustard_c.png"))); // NOI18N
	        selectPanel.add(mustard);

	        charSelect.add(white);
	        white.setAlignmentX(0.5F);
	        white.setBorderPainted(true);
	        white.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	        white.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/white_s.png"))); // NOI18N
	        white.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	        white.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/white.png"))); // NOI18N
	        white.setOpaque(false);
	        white.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/white.png"))); // NOI18N
	        white.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/white_c.png"))); // NOI18N
	        white.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/white_c.png"))); // NOI18N
	        selectPanel.add(white);

	        charSelect.add(green);
	        green.setAlignmentX(0.5F);
	        green.setBorderPainted(true);
	        green.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	        green.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/green_s.png"))); // NOI18N
	        green.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	        green.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
	        green.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/green.png"))); // NOI18N
	        green.setOpaque(false);
	        green.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/green.png"))); // NOI18N
	        green.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/green_c.png"))); // NOI18N
	        green.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/green_c.png"))); // NOI18N
	        selectPanel.add(green);

	        charSelect.add(peacock);
	        peacock.setAlignmentX(0.5F);
	        peacock.setBorderPainted(true);
	        peacock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	        peacock.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/peacock_s.png"))); // NOI18N
	        peacock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	        peacock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/peacock.png"))); // NOI18N
	        peacock.setOpaque(false);
	        peacock.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/peacock.png"))); // NOI18N
	        peacock.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/peacock_c.png"))); // NOI18N
	        peacock.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/peacock_c.png"))); // NOI18N
	        selectPanel.add(peacock);

	        charSelect.add(plum);
	        plum.setAlignmentX(0.5F);
	        plum.setBorderPainted(true);
	        plum.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	        plum.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/plum_s.png"))); // NOI18N
	        plum.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	        plum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/plum.png"))); // NOI18N
	        plum.setOpaque(false);
	        plum.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/plum.png"))); // NOI18N
	        plum.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/plum_c.png"))); // NOI18N
	        plum.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/plum_c.png"))); // NOI18N
	        selectPanel.add(plum);

	        okButton.setText("OK");
	        okButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	        okButton.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                okButtonActionPerformed(evt);
	            }
	        });

	        startButton.setText("Start Game");
	        startButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	        startButton.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                startButtonActionPerformed(evt);
	            }
	        });

	        progress.setMaximum(3);
	        progress.setFocusable(false);

	        javax.swing.GroupLayout submitPanelLayout = new javax.swing.GroupLayout(submitPanel);
	        submitPanel.setLayout(submitPanelLayout);
	        submitPanelLayout.setHorizontalGroup(
	            submitPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(submitPanelLayout.createSequentialGroup()
	                .addComponent(okButton)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(progress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(startButton))
	        );
	        submitPanelLayout.setVerticalGroup(
	            submitPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, submitPanelLayout.createSequentialGroup()
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addGroup(submitPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
	                    .addGroup(submitPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                        .addComponent(okButton)
	                        .addComponent(startButton))
	                    .addComponent(progress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	                .addContainerGap())
	        );

	        if (players.size() >= 3) {     startButton.setVisible(true); } else {     startButton.setVisible(false); }

	        textPanel.setToolTipText("This will be the character you play as during the game.");
	        textPanel.setMinimumSize(new java.awt.Dimension(0, 0));
	        textPanel.setOpaque(false);

	        javax.swing.GroupLayout textPanelLayout = new javax.swing.GroupLayout(textPanel);
	        textPanel.setLayout(textPanelLayout);
	        textPanelLayout.setHorizontalGroup(
	            textPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGap(0, 0, Short.MAX_VALUE)
	        );
	        textPanelLayout.setVerticalGroup(
	            textPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGap(0, 0, Short.MAX_VALUE)
	        );

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	        getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(NameSelection, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                    .addComponent(submitPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                    .addComponent(selectPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                    .addComponent(textPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	                .addContainerGap())
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(NameSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(textPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(selectPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(submitPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addContainerGap())
	        );

	        getAccessibleContext().setAccessibleName("GameSetup");
	        getAccessibleContext().setAccessibleDescription("Select your player name and character.");

	        pack();
	    }

	    /** This action is thrown when a user asks to submit their profile (their
	     * name and the character they want to play). Using the currentPlayer field,
	     * which gets progressively filled out as each character/field loses focus,
	     * this method checks that the player has entered a name of more than three
	     * characters, containing no special characters, and is not a duplicate of
	     * any existing name.
	     *
	     * @param evt Event triggered by submitting the form.
	     */
	    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
	        if (((JButton) evt.getSource()).getActionCommand().equalsIgnoreCase(SUBMIT)) { // if the button has been pressed...
	            createCharacterAtState();
	            progressUpdate();
	            setupButtons();
	        }
	    }

	    /** TODO comment */
	    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
<<<<<<< HEAD
	        // in order for this button to even appear we know there must be at
	        // least 3 characters created already.
	        if (futurePlayers.size() < 6 && nameTextInput.getText().length() != 0) {
	            int saveChar = JOptionPane.showConfirmDialog(rootPane,
=======
	        // in order for this button to even appear we know there must be at 
	        // least 3 characters created already. 
	        if (players.size() < 6 && nameTextInput.getText().length() != 0) {
	            int saveChar = JOptionPane.showConfirmDialog(rootPane, 
>>>>>>> 1cecf3b03e757f523e9478d80086a1f6508c6b6c
	                "Would you like to include "+ nameTextInput.getText() +" in the game?","Wait!", JOptionPane.YES_NO_OPTION);

	            if (saveChar == 0) { // user says 'yes, save'
	                createCharacterAtState();
	            }
	        }
	        dispatchEvent(new java.awt.event.WindowEvent(this, java.awt.event.WindowEvent.WINDOW_CLOSING));
	    }

	    /** A simple method for updating the progress towards starting
	     * a new game.  */
	    private void progressUpdate() {
	        if (players.size() == 1) {
	            progress.setValue(1);
	            progress.setString("Two more players...");
	        } else if (players.size() == 2) {
	            progress.setValue(2);
	            progress.setString("Just one more...");
	        } else if (players.size() >= 3) {
	            progress.setValue(3);
	            progress.setString("");
	        }
	    }
	    
	    public void kill() {
	    	if (hasSubmitted) {
	        	dispose();
	    	}
	    }

	    /** TODO comment */
	    private void createCharacterAtState() {
	        String gotName = nameTextInput.getText();
	        String gotCharacter = null;

	        // go through some elaborate error-checking on the name.
	        // 1. is it long enough?
	        if (gotName.length() < 3 || gotName.length() >= 20) {
	            JOptionPane.showMessageDialog(rootPane,
	                    "Please enter a name between 3 and 20 characters.","Invalid name!", JOptionPane.OK_OPTION);
	            return;
	        }
	        // 2. which character have they selected?
	        for (Map.Entry<String,JRadioButton> option : characters.entrySet()) {
	            if (((JRadioButton)option.getValue()).isSelected()) {
	                gotCharacter = option.getKey();
	            }
	        }
<<<<<<< HEAD
	        // 3. does someone already have this name/character?
	        for (Map.Entry<String,String> player : futurePlayers.entrySet()) {
=======
	        // 3. does someone already have this name/character? 
	        for (Map.Entry<String,String> player : players.entrySet()) {
>>>>>>> 1cecf3b03e757f523e9478d80086a1f6508c6b6c
	            if (player.getKey().equals(gotName)) {
		            JOptionPane.showMessageDialog(rootPane,
		                    "Someone is already called "+gotName+"! Do be more original with your epithet, please.","Invalid name!", JOptionPane.OK_OPTION);
	                return;
	            } else if (player.getValue().equals(gotCharacter)) {
		            JOptionPane.showMessageDialog(rootPane,
		                    "Someone is already playing "+gotCharacter+"!","Invalid character!", JOptionPane.OK_OPTION);
	                return;

	            }
	        }
	        // 4. or, wait, did they forget?
	        if (gotCharacter == null) {
	            JOptionPane.showMessageDialog(rootPane, "Fair citizen, we must have something to call you!","No name!", JOptionPane.OK_OPTION);
	            return;
	        }

	        // great! now we have a currentPlayer that describes what the
	        // player wants. Let's add it to the list of futurePlayers
<<<<<<< HEAD
	        // and update the progress bar.
	        futurePlayers.put(gotName,gotCharacter);
=======
	        // and update the progress bar. 
	        players.put(gotName,gotCharacter);
>>>>>>> 1cecf3b03e757f523e9478d80086a1f6508c6b6c
	        nameTextInput.setText("");
	    }

	    public SetupDialog getDialog() {
	    	return setup;
	    }

	    private final Map<String,JRadioButton> characters = new java.util.concurrent.ConcurrentHashMap<String,JRadioButton>();
	    // Variables declaration - do not modify//GEN-BEGIN:variables
	    private javax.swing.JPanel NameSelection;
	    private javax.swing.ButtonGroup charSelect;
	    private javax.swing.JRadioButton green;
	    private javax.swing.JRadioButton mustard;
	    private javax.swing.JLabel nameLabel;
	    private javax.swing.JTextField nameTextInput;
	    private javax.swing.JButton okButton;
	    private javax.swing.JRadioButton peacock;
	    private javax.swing.JRadioButton plum;
	    private javax.swing.JProgressBar progress;
	    private javax.swing.JRadioButton scarlet;
	    private javax.swing.JPanel selectPanel;
	    private javax.swing.JButton startButton;
	    private javax.swing.JPanel submitPanel;
	    private javax.swing.JPanel textPanel;
	    private javax.swing.JRadioButton white;
	    // End of variables declaration//GEN-END:variables

	}
}