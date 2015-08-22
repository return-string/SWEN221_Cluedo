package ui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mckayvick
 */
public class AccuseRoom extends javax.swing.JDialog {
	private static final long serialVersionUID = 1062001214996760412L;
	/**
     * Creates new form AccuseWeapon
     */
    public AccuseRoom(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(3, 3, 3, 3));

        rooms.add(ballroom);
        ballroom.setText(game.Card.BALL);
        ballroom.setMinimumSize(new java.awt.Dimension(16, 30));
        ballroom.setPreferredSize(null);
        getContentPane().add(ballroom);

        rooms.add(billiard);
        billiard.setText(game.Card.BILLIARD);
        billiard.setMinimumSize(new java.awt.Dimension(16, 30));
        billiard.setPreferredSize(null);
        getContentPane().add(billiard);

        rooms.add(conservatory);
        conservatory.setText(game.Card.CONSERVATORY);
        conservatory.setMinimumSize(new java.awt.Dimension(16, 30));
        conservatory.setPreferredSize(null);
        getContentPane().add(conservatory);

        rooms.add(dining);
        dining.setText(game.Card.DINING);
        dining.setMinimumSize(new java.awt.Dimension(16, 30));
        dining.setPreferredSize(null);
        getContentPane().add(dining);

        rooms.add(hall);
        hall.setText(game.Card.HALL);
        hall.setMinimumSize(new java.awt.Dimension(16, 30));
        hall.setPreferredSize(null);
        getContentPane().add(hall);

        rooms.add(kitchen);
        kitchen.setText(game.Card.KITCHEN);
        kitchen.setMinimumSize(new java.awt.Dimension(16, 30));
        kitchen.setPreferredSize(null);
        getContentPane().add(kitchen);

        rooms.add(lounge);
        lounge.setText(game.Card.LOUNGE);
        lounge.setMinimumSize(new java.awt.Dimension(16, 30));
        lounge.setPreferredSize(null);
        getContentPane().add(lounge);

        rooms.add(study);
        study.setText(game.Card.STUDY);
        study.setMinimumSize(new java.awt.Dimension(16, 30));
        study.setPreferredSize(null);
        getContentPane().add(study);

        rooms.add(library);
        library.setText(game.Card.LIBRARY);
        library.setMinimumSize(new java.awt.Dimension(16, 30));
        library.setPreferredSize(null);
        getContentPane().add(library);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    // End of variables declaration//GEN-END:variables
}
