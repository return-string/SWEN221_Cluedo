/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 *
 * @author mckayvick
 */
public class GameSetupPanel extends CluedoPanel implements ActionListener {
    private Frame window = new WindowType();
    private Map<String,String> players;
    private JDialog setup;

    public GameSetupPanel(Controller c) {
        super(c);
        this.players = new HashMap<String,String>();
        setup = new GameSetupDialogue(players);
        setup.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                processMap();
            }

            private void processMap() {
                GameSetupPanel.super.controller().setupGame(players);
            }
        });
        setup.setVisible(true);
    }
/**
 * @param args the command line arguments
 */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(GameSetupDialogue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(GameSetupDialogue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(GameSetupDialogue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(GameSetupDialogue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                HashMap<String,String> some = new HashMap<String,String>();
//                GameSetupDialogue dialog = new GameSetupDialogue(some);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        processMap();
//                    }
//
//                    private void processMap() {
//                        c.setupGame();
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    @Override
    public void nextTurn() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(GameSetupDialogue.START)) {
        	
        }
        System.out.println("GameSetup is listening");
    }
    
    /**
     *
     */
    class WindowType extends java.awt.Frame {
        WindowType() {
            
        }
    }
}
