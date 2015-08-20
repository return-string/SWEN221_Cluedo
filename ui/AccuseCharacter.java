/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mckayvick
 */
public class AccuseCharacter extends javax.swing.JDialog {
    private CluedoSkin sk;

    /**
     * Creates new form HypothesisPanel
     */
    public AccuseCharacter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        try {
            sk = new MutedSkin();
        } catch (FontFormatException ex) {
            Logger.getLogger(AccuseCharacter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AccuseCharacter.class.getName()).log(Level.SEVERE, null, ex);
        }
;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        scarlet = new javax.swing.JRadioButton();
        mustard = new javax.swing.JRadioButton();
        white = new javax.swing.JRadioButton();
        green = new javax.swing.JRadioButton();
        peacock = new javax.swing.JRadioButton();
        plum = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        buttonGroup1.add(scarlet);
        scarlet.setAlignmentX(0.5F);
        scarlet.setBorderPainted(true);
        scarlet.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/scarlet_s.png"))); // NOI18N
        scarlet.setDisabledSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/scarlet_s.png"))); // NOI18N
        scarlet.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        scarlet.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        scarlet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/scarlet.png"))); // NOI18N
        scarlet.setRolloverEnabled(false);
        scarlet.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/scarlet.png"))); // NOI18N
        scarlet.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/scarlet_c.png"))); // NOI18N
        scarlet.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/scarlet_c.png"))); // NOI18N

        buttonGroup1.add(mustard);
        mustard.setAlignmentX(0.5F);
        mustard.setBorderPainted(true);
        mustard.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/mustard_s.png"))); // NOI18N
        mustard.setDisabledSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/mustard_s.png"))); // NOI18N
        mustard.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mustard.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mustard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/mustard.png"))); // NOI18N
        mustard.setRolloverEnabled(false);
        mustard.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/mustard_c.png"))); // NOI18N
        mustard.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/mustard_c.png"))); // NOI18N

        buttonGroup1.add(white);
        white.setAlignmentX(0.5F);
        white.setBorderPainted(true);
        white.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/white_s.png"))); // NOI18N
        white.setDisabledSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/white_s.png"))); // NOI18N
        white.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        white.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        white.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/white.png"))); // NOI18N
        white.setRolloverEnabled(false);
        white.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/white.png"))); // NOI18N
        white.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/white_c.png"))); // NOI18N
        white.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/white_c.png"))); // NOI18N

        buttonGroup1.add(green);
        green.setAlignmentX(0.5F);
        green.setBorderPainted(true);
        green.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/green_s.png"))); // NOI18N
        green.setDisabledSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/green_s.png"))); // NOI18N
        green.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        green.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        green.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/green.png"))); // NOI18N
        green.setRolloverEnabled(false);
        green.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/green.png"))); // NOI18N
        green.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/green_c.png"))); // NOI18N
        green.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/green_c.png"))); // NOI18N
        green.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                greenActionPerformed(evt);
            }
        });

        buttonGroup1.add(peacock);
        peacock.setAlignmentX(0.5F);
        peacock.setBorderPainted(true);
        peacock.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/peacock_s.png"))); // NOI18N
        peacock.setDisabledSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/peacock_s.png"))); // NOI18N
        peacock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        peacock.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        peacock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/peacock.png"))); // NOI18N
        peacock.setRolloverEnabled(false);
        peacock.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/peacock.png"))); // NOI18N
        peacock.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/peacock_c.png"))); // NOI18N
        peacock.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/peacock_c.png"))); // NOI18N

        plum.setAlignmentX(0.5F);
        plum.setBorderPainted(true);
        plum.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/plum_s.png"))); // NOI18N
        plum.setDisabledSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/plum_s.png"))); // NOI18N
        plum.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        plum.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        plum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/plum.png"))); // NOI18N
        plum.setRolloverEnabled(false);
        plum.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/plum.png"))); // NOI18N
        plum.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/plum_c.png"))); // NOI18N
        plum.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/imgs/plum_c.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scarlet)
                .addGap(3, 3, 3)
                .addComponent(mustard)
                .addGap(3, 3, 3)
                .addComponent(white))
            .addGroup(layout.createSequentialGroup()
                .addComponent(green)
                .addGap(3, 3, 3)
                .addComponent(peacock)
                .addGap(3, 3, 3)
                .addComponent(plum))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scarlet, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mustard, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(white, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(green, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(peacock, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(plum, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void greenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_greenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_greenActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AccuseCharacter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AccuseCharacter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AccuseCharacter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AccuseCharacter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AccuseCharacter dialog = new AccuseCharacter(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton green;
    private javax.swing.JRadioButton mustard;
    private javax.swing.JRadioButton peacock;
    private javax.swing.JRadioButton plum;
    private javax.swing.JRadioButton scarlet;
    private javax.swing.JRadioButton white;
    // End of variables declaration//GEN-END:variables
}
