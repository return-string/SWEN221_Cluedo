package ui;

import game.Board;
import game.Card;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JRadioButton;


/** The HypothesisPanel consists of a header panel, which contains a JLabel 
 * displaying the developing hypothesis, and a content JPanel, which at 
 * any time contains one of three subpanels: AccuseCharacter, AccuseRoom or 
 * AccuseWeapon. 
 * 
 * It has three fields to remember the user's selection of a guilty character,
 * room and weapon, and will be able to return these as a Set or List. 
 *
 * @author mckayvick
 */
public class HypothesisPanel extends javax.swing.JPanel {
    static final String ELLIPSIS = "...";
    static final String THEORY = " theorise that it was ";
    static final String ACCUSE = " accuse the villain ";
    static final String IN_THE = " in the ";
    static final String WITH_THE = " with the ";
    private String guiltyChar = "";
    private String guiltyRoom = "";
    private String guiltyWeap = "";
    private JPanel currentHypothesis;
    private final boolean isAccusation;

    /**
     * Creates new HypothesisPanel to collect user's hypothesis selections.
     * 
     * @param room If the player is making a guess, let 'room' be the room 
     * in which they are making the accusation, as defined by Card.ROOMS. If they
     * are making a final accusation, let it be null. 
     */
    public HypothesisPanel(String room) {
    	if (!room.equals(Board.HALLWAYSTRING)) {
    		guiltyRoom = room;
    		isAccusation = false;
    	} else {
    		isAccusation = true;
    	}
        initComponents();
        doubleInit();
    	nextPanel(Card.Type.CHARACTER);
    }
    
    /** TODO comment */
    private void doubleInit() {
        /* will setup the currentHypothesis field with the required JPanel and
         display it.  */
    }

    /**
     * TODO comment
     */
    private void initComponents() {
        contents = new javax.swing.JPanel(); // contains each currentHypothesis panel
        header = new javax.swing.JPanel(); // contains headertext
        headerText = new javax.swing.JLabel(); // prints the current state of the hypothesis

        javax.swing.GroupLayout contentsLayout = new javax.swing.GroupLayout(contents);
        contents.setLayout(contentsLayout);
        contentsLayout.setHorizontalGroup(
            contentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        contentsLayout.setVerticalGroup(
            contentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 411, Short.MAX_VALUE)
        );

        headerText.setText("jLabel1");

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addComponent(headerText)
                .addGap(0, 468, Short.MAX_VALUE))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerText)
        );
        
        add(contents);
        header.add(headerText);
        add(header);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    	JFrame f = new JFrame();
    	HypothesisPanel hp = new HypothesisPanel(Card.BILLIARD);
    	f.setSize(400, 400);
    	f.add(hp);
    	f.setUndecorated(false);
    	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	hp.setMinimumSize(new Dimension(300,300));
    	hp.setBackground(java.awt.Color.RED);
    	f.pack();
    	f.setVisible(true);
    	System.out.println("--- all done!");
    }
    
    /** TODO comment */
    public void nextPanel(Card.Type t) {
    	String name = "";
		if (currentHypothesis != null) {
			name = currentHypothesis.getName();
			currentHypothesis.setVisible(false);
		}
    	
    	if (t == Card.Type.CHARACTER) {
    		currentHypothesis = new HypothesisPanel.AccuseCharacter(null, false);
    		System.out.println("making char");
    	} else if (t == Card.Type.WEAPON) {
    		currentHypothesis = new HypothesisPanel.AccuseWeapon(null, false);
    		System.out.println("making weap");
    	} else if (isAccusation && t == Card.Type.ROOM){ // just in case
    		currentHypothesis = new HypothesisPanel.AccuseRoom(null, false);
    		System.out.println("making room");
    	} else {
    		throw new IllegalAccessError();
    	}
    	
    	currentHypothesis.setAlignmentX(JPanel.CENTER_ALIGNMENT);
    	currentHypothesis.setAlignmentY(JPanel.CENTER_ALIGNMENT);
    	currentHypothesis.setPreferredSize(new Dimension(300,300));
    	currentHypothesis.setMinimumSize(getPreferredSize());
    	contents.add(currentHypothesis);
    	currentHypothesis.setVisible(true);
    	contents.validate();
    	repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contents;
    private javax.swing.JPanel header;
    private javax.swing.JLabel headerText;
    // End of variables declaration//GEN-END:variables


    /** ACCUSE CHARACTER FORM
     *
     * TODO comment
     */
    public class AccuseCharacter extends javax.swing.JPanel {
		private static final long serialVersionUID = -2424783744068703861L;
		public AccuseCharacter(java.awt.Frame parent, boolean modal) {
            initComponents();
        }
                   
		/* TODO comment */
        private void initComponents() {
        	setBackground(java.awt.Color.YELLOW);
            System.out.println("Init");
            chars = new javax.swing.ButtonGroup();
            scarlet = new javax.swing.JRadioButton();
            mustard = new javax.swing.JRadioButton();
            white = new javax.swing.JRadioButton();
            green = new javax.swing.JRadioButton();
            peacock = new javax.swing.JRadioButton();
            plum = new javax.swing.JRadioButton();

            chars.add(scarlet);
            characterSet.put("scarlet",scarlet);
            scarlet.setToolTipText(game.Card.SCARLET);

            chars.add(mustard);
            characterSet.put("mustard",mustard);
            scarlet.setToolTipText(game.Card.MUSTARD);
            
            chars.add(white);
            characterSet.put("white",white);
            scarlet.setToolTipText(game.Card.WHITE);

            chars.add(green);
            characterSet.put("green",green);
            scarlet.setToolTipText(game.Card.GREEN);

            chars.add(peacock);
            characterSet.put("peacock",peacock);
            scarlet.setToolTipText(game.Card.PEACOCK);

            chars.add(plum);
            characterSet.put("plum",plum);
            scarlet.setToolTipText(game.Card.PLUM);

//            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
//            getContentPane().setLayout(layout);
//            layout.setHorizontalGroup(
//                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                .addGroup(layout.createSequentialGroup()
//                    .addComponent(scarlet)
//                    .addGap(3, 3, 3)
//                    .addComponent(mustard)
//                    .addGap(3, 3, 3)
//                    .addComponent(white))
//                .addGroup(layout.createSequentialGroup()
//                    .addComponent(green)
//                    .addGap(3, 3, 3)
//                    .addComponent(peacock)
//                    .addGap(3, 3, 3)
//                    .addComponent(plum))
//            );
//            layout.setVerticalGroup(
//                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                .addGroup(layout.createSequentialGroup()
//                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addComponent(scarlet, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
//                        .addComponent(mustard, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
//                        .addComponent(white, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
//                    .addGap(3, 3, 3)
//                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addComponent(green, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
//                        .addComponent(peacock, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
//                        .addComponent(plum, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
//            );

            System.out.println("Done");
            for (Map.Entry<String, JRadioButton> e : characterSet.entrySet()) {
                String nm = e.getKey();
                JRadioButton b = e.getValue();
                b.setAlignmentX(0.5F);
                b.setBorderPainted(true);
                b.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/"+nm+"_s.png"))); // NOI18N
                b.setDisabledSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/"+nm+".png"))); // NOI18N
                b.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/"+nm+".png"))); // NOI18N
                b.setRolloverEnabled(false);
                b.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/"+nm+"_c.png"))); // NOI18N
                b.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                b.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            }
        }

        private Map<String,JRadioButton> characterSet = new HashMap<>();
        // Variables declaration - do not modify                     
        private javax.swing.ButtonGroup chars;
        private javax.swing.JRadioButton green;
        private javax.swing.JRadioButton mustard;
        private javax.swing.JRadioButton peacock;
        private javax.swing.JRadioButton plum;
        private javax.swing.JRadioButton scarlet;
        private javax.swing.JRadioButton white;
        // End of variables declaration 
        
    }
    
    /** ACCUSE WEAPON FORM 
     *
     * TODO comment
     */
    public class AccuseWeapon extends javax.swing.JPanel {
		private static final long serialVersionUID = 4264304989944437564L;
		public AccuseWeapon(java.awt.Frame parent, boolean modal) {
            initComponents();
        }
                         
		/** TODO comment */
        private void initComponents() {
        	setBackground(java.awt.Color.GREEN);
            weapons = new javax.swing.ButtonGroup();
            candlestick = new javax.swing.JRadioButton();
            dagger = new javax.swing.JRadioButton();
            revolver = new javax.swing.JRadioButton();
            rope = new javax.swing.JRadioButton();
            pipe = new javax.swing.JRadioButton();
            wrench = new javax.swing.JRadioButton();

            weapons.add(candlestick);
            candlestick.setText(game.Card.CANDLESTICK);
            weaponSet.add(candlestick);

            weapons.add(dagger);
            dagger.setText(game.Card.DAGGER);
            weaponSet.add(dagger);

            weapons.add(revolver);
            revolver.setText(game.Card.REVOLVER);
            weaponSet.add(revolver);

            weapons.add(rope);
            rope.setText(game.Card.ROPE);
            weaponSet.add(rope);

            weapons.add(pipe);
            pipe.setText(game.Card.PIPE);
            weaponSet.add(pipe);
            
            weapons.add(wrench);
            wrench.setText(game.Card.WRENCH);
            weaponSet.add(wrench);

            for (JRadioButton b : weaponSet) {
                b.setPreferredSize(null);
                b.setRolloverEnabled(false);
//                getContentPane().add(b);
            }
        }

        private Set<JRadioButton> weaponSet = new HashSet<JRadioButton>();
        // Variables declaration - do not modify                     
        private javax.swing.JRadioButton candlestick;
        private javax.swing.JRadioButton dagger;
        private javax.swing.JRadioButton pipe;
        private javax.swing.JRadioButton revolver;
        private javax.swing.JRadioButton rope;
        private javax.swing.ButtonGroup weapons;
        private javax.swing.JRadioButton wrench;
        // End of variables declaration                   
    }

    /** ACCUSE ROOM FORM 
     * TODO comment
     *
     */
    public class AccuseRoom extends javax.swing.JPanel {
		private static final long serialVersionUID = 1L;
		public AccuseRoom(java.awt.Frame parent, boolean modal) {
            initComponents();
        }
                       
		/* TODO comment */
        private void initComponents() {
        	setBackground(java.awt.Color.BLUE);
            rooms = new javax.swing.ButtonGroup();
            ballroom = new javax.swing.JRadioButton();
            billiard = new javax.swing.JRadioButton();
            conservatory = new javax.swing.JRadioButton();
            dining = new javax.swing.JRadioButton();
            hall = new javax.swing.JRadioButton();
            kitchen = new javax.swing.JRadioButton();
            lounge = new javax.swing.JRadioButton();
            study = new javax.swing.JRadioButton();
            library = new javax.swing.JRadioButton();

            rooms.add(ballroom);
            ballroom.setText(game.Card.BALL);
            roomSet.add(ballroom);

            rooms.add(billiard);
            billiard.setText(game.Card.BILLIARD);
            roomSet.add(billiard);

            rooms.add(conservatory);
            conservatory.setText(game.Card.CONSERVATORY);
            roomSet.add(conservatory);

            rooms.add(dining);
            dining.setText(game.Card.DINING);
            roomSet.add(dining);

            rooms.add(hall);
            hall.setText(game.Card.HALL);
            roomSet.add(hall);

            rooms.add(kitchen);
            kitchen.setText(game.Card.KITCHEN);
            roomSet.add(kitchen);

            rooms.add(lounge);
            lounge.setText(game.Card.LOUNGE);
            roomSet.add(lounge);

            rooms.add(study);
            study.setText(game.Card.STUDY);
            roomSet.add(study);

            rooms.add(library);
            library.setText(game.Card.LIBRARY);
            roomSet.add(library);

            for (JRadioButton b : roomSet) {
                b.setMinimumSize(new java.awt.Dimension(16, 30));
                b.setPreferredSize(null);
                b.setRolloverEnabled(false);
            }
        }

        private Set<JRadioButton> roomSet = new HashSet<JRadioButton>();
        // Variables declaration - do not modify                     
        private javax.swing.JRadioButton ballroom;
        private javax.swing.JRadioButton billiard;
        private javax.swing.JRadioButton conservatory;
        private javax.swing.JRadioButton dining;
        private javax.swing.JRadioButton hall;
        private javax.swing.JRadioButton kitchen;
        private javax.swing.JRadioButton library;
        private javax.swing.JRadioButton lounge;
        private javax.swing.ButtonGroup rooms;
        private javax.swing.JRadioButton study;
        // End of variables declaration                   
    }
}
