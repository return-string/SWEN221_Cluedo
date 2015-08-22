/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author mckayvick
 */
public class GameSetupPanel extends CluedoPanel {
	private static final long serialVersionUID = 7573664494946034515L;
    private Map<String,String> players;
    private JDialog setup;

    public GameSetupPanel(final Controller c) {
        super(c);
        this.players = new HashMap<String,String>();
        setup = new GameSetupDialogue(players);
        setup.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                for (Map.Entry<String, String> p : players.entrySet()) {
                	System.err.println(p.getValue()+", "+p.getKey());
                }
                cleanup();
            }
        });
        setup.setVisible(true);
    }
    
    public void cleanup() {
        System.out.println(players==null);                
        controller().setPlayers(players);
    }
/**
 * @param args the command line arguments
 */
    public static void main(String args[]) {
        /* Create and display the dialog */
    	JFrame f = new JFrame();
        GameSetupPanel gp = new GameSetupPanel(null);
        f.add(gp);
        f.setLocation(300, 300);
        f.setMinimumSize(new Dimension(300,300));
    }

    @Override
    public void nextTurn() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
