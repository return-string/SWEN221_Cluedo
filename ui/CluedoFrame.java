package ui;

import game.Game;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.util.HashMap;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/** The CluedoFrame manages all panels in the game.
 *
 * @author Vicki
 *
 */
public class CluedoFrame extends JFrame {
	private static final long serialVersionUID = 2171235413597916591L;
	public static final int MENU_PANEL = 0;
	public static final int GAMESETUP_PANEL = 1;
	public static final int TURN_PANEL = 2;
	public static final int GAMEOVER_PANEL = 3;
	public static final int RULES_PANEL = 4;
	public static final int HYPOTHESIS_PANEL = 5;
	
	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 600;
	
	private Controller controller; // need to be able to set this when starting new game
	private CluedoPanel currentPanel;
	private MenuPanel menu;
	private GameOverPanel gameOver;
	private RulesPanel rules;
	private TurnPanel turnPanel;
	private GameSetupPanel gameSetup;
	private SuperSpecialAwesomeHypothesisPanel hypothesisPanel;
	private CluedoPanel[] panels; 

	public CluedoFrame() {
		super("Cluedo");
		this.controller = new Controller(this);

		initComponents();
		
		setLayout(new BorderLayout()); // use border layout
		// this.canvas = new CluedoCanvas(cluedoGame);
		// add(canvas, BorderLayout.CENTER); // add canvas
		
		panels = new CluedoPanel[] { menu, gameSetup, turnPanel, gameOver, rules, hypothesisPanel};
		setIconImage(icon());
        setPreferredSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT)); // V: just added this for sizing!
		pack(); // pack components tightly together
		//setResizable(false); // prevent us from being resizeable
		setVisible(true); // make sure we are visible!

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent we)
		    {
		        String ObjButtons[] = {"Yes","No"};
		        int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?","Cluedo",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
		        if(PromptResult==JOptionPane.YES_OPTION)
		        {
		            System.exit(0);
		        }
		    }
		});

	}

	private Image icon() {
		Image icon;
		java.net.URL url = ClassLoader.getSystemResource("assets/imgs/icon/clue_ico.png");
		Toolkit kit = Toolkit.getDefaultToolkit();
		icon = kit.createImage(url);
		return icon;
	}
	
	public void displayTurnPanel() {
		if (turnPanel == null) {
			turnPanel = new TurnPanel(controller);
		}
		displayPanel(turnPanel);
	}
	
	public void displayMenu() {
		if (menu == null) {
			menu = new MenuPanel(controller);
		}
		displayPanel(menu);
	}
	
	public void displayHypothesis() {
		if (hypothesisPanel == null) {
			hypothesisPanel = new SuperSpecialAwesomeHypothesisPanel(controller);
		}
		displayPanel(hypothesisPanel);
	}
	
	public void displayGameOver() {
		if (gameOver == null) {
			gameOver = new GameOverPanel(controller);
		}
		displayPanel(gameOver);
	}
	
	public void displaySetup() {
		if (gameSetup == null) {
			gameSetup = new GameSetupPanel(controller);
		}
		displayPanel(gameSetup);
	}
	
	public void displayRules() {
		if (rules == null) {
			rules = new RulesPanel(controller);
		}
		displayPanel(rules);
	}
	
	private void displayPanel(CluedoPanel toDisplay) {
		//System.err.println(toDisplay == null);
        this.getContentPane().removeAll();
		this.currentPanel = toDisplay;
		this.getContentPane().add(this.currentPanel, BorderLayout.CENTER);
		pack();
		repaint();
	}
	
	

	public void nextTurn(){
		_turnpanel.nextTurn();
	}

	/** Displays a popup dialog asking the users to input their player information */
	public void showGameSetup(){
		_setuppanel.showDialog();
		try {
			_setuppanel.getDialog().setLocation(getLocationToCenter(_setuppanel.getDialog().getWidth(),_setuppanel.getDialog().getHeight()));
			_setuppanel.getDialog().addWindowListener(new java.awt.event.WindowAdapter() {
			
			    @Override
			    public void windowClosing(java.awt.event.WindowEvent e) {
			    	Map<String,String> players = _setuppanel.getResult();
			        try {
						_setuppanel.getDialog().dispose();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			        controller.startGame(players);
			        nextTurn();
			    }
			});
		} catch (IllegalAccessException e) {
			// this isn't going to happen-- but if it does, someone should be told!
			System.err.println("Error! Couldn't open setup dialog!");
		}
	}

	/** Returns the Point a window should position its corner at, such that
	 * it will appear in the center of the window.
	 *
	 * @param windowWidth
	 * @return
	 */
	public static java.awt.Point getLocationToCenter(int windowWidth,int windowHeight) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		return new java.awt.Point(dim.width/2 - windowWidth/2, dim.height/2 - windowHeight/2);
	}

	public void showHypothesisPanel(){
		this.controller.startTestGame(Game.createDefaultMap());
		displayHypothesis();
	}

	private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        _menupanel = new MenuPanel(controller);
        _setuppanel = new GameSetupPanel(controller);
        _hypopanel = new HypothesisPanel(controller);
        _turnpanel = new TurnPanel(controller);
        //_playerspanel = new PlayersPanel(controller);
        //_deckpanel = new DeckPanel(controller);

//        jMenuItem1 = new javax.swing.JMenuItem();
//        contents = new javax.swing.JPanel();
//        frameLevelMenu = new javax.swing.JPanel();
//        frameLevelSetup = new javax.swing.JPanel();
//        //setupPanel = new javax.swing.JPanel();
//        frameLevelTurn = new javax.swing.JPanel();
//        frameChild_boardPanel = new javax.swing.JPanel();
//        frameChild_playersPanel = new javax.swing.JPanel();
//        frameChild_deckPanel = new javax.swing.JPanel();
//        deckPanelChild_deckContents = new javax.swing.JSplitPane();
//        playerHand = new javax.swing.JScrollPane();
//        handContents = new javax.swing.JPanel();
//        playerNotebook = new javax.swing.JPanel();
//
//        notesCharacter = new javax.swing.JPanel();
//        char1 = new javax.swing.JCheckBox();
//
//        notesWeapons = new javax.swing.JPanel();
//        weapon1 = new javax.swing.JCheckBox();
//
//        notesRooms = new javax.swing.JPanel();
//        room1 = new javax.swing.JCheckBox();

        gameover = new javax.swing.JPanel();
//        theEndPanel = new javax.swing.JPanel();
//        theEndTitle = new javax.swing.JPanel();
//        theEndText = new javax.swing.JLabel();
//        theEndContents = new javax.swing.JPanel();
//        winnerIcon = new javax.swing.JLabel();
//        playerHasSolvedTheMurder = new javax.swing.JLabel();
//        guiltyWeapon = new javax.swing.JLabel();
//        guiltyCharacter = new javax.swing.JLabel();
//        guiltyRoom = new javax.swing.JLabel();
        menubar = new javax.swing.JMenuBar();
        menu_h_game = new javax.swing.JMenu();
        menu_newgame = new javax.swing.JMenuItem();
        menu_endturn = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        menu_main = new javax.swing.JMenuItem();
        menu_setup = new javax.swing.JMenuItem();
        menu_turn = new javax.swing.JMenuItem();
        menu_end = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setMinimumSize(new java.awt.Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setPreferredSize(new java.awt.Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));

//        cardLayout = new java.awt.CardLayout();
//        contents.setLayout(cardLayout);

        frameLevelMenu.setMinimumSize(new java.awt.Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        frameLevelMenu.setLayout(new java.awt.BorderLayout());
        frameLevelMenu.add(_menupanel);
        _menupanel.setPreferredSize(getPreferredSize());
        contents.add(frameLevelMenu);

        frameLevelSetup.setBackground(new java.awt.Color(10, 10, 10));
        frameLevelSetup.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout setupPanelLayout = new javax.swing.GroupLayout(frameLevelSetup);
        frameLevelSetup.setLayout(setupPanelLayout);
        setupPanelLayout.setHorizontalGroup(
            setupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, DEFAULT_WIDTH, Short.MAX_VALUE)
        );
        setupPanelLayout.setVerticalGroup(
            setupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, DEFAULT_HEIGHT, Short.MAX_VALUE)
        );
        frameLevelSetup.add(_setuppanel, java.awt.BorderLayout.CENTER);

        contents.add(frameLevelSetup);

//        frameLevelTurn.setBackground(new java.awt.Color(200, 200, 100));
//
//        java.awt.GridBagLayout turnLayout = new java.awt.GridBagLayout();
//        turnLayout.columnWidths = new int[] {610, 190};
//        turnLayout.columnWeights = new double[] {0.8, 0.2};
//        frameLevelTurn.setLayout(turnLayout);
//
//        frameChild_boardPanel.setBackground(new java.awt.Color(230, 230, 90));
//        frameChild_boardPanel.setMaximumSize(null);
//        frameChild_boardPanel.setMinimumSize(new java.awt.Dimension(610, 430));
//        frameChild_boardPanel.setPreferredSize(new java.awt.Dimension(610, 430));
//
//        javax.swing.GroupLayout boardPanelLayout = new javax.swing.GroupLayout(frameChild_boardPanel);
//        frameChild_boardPanel.setLayout(boardPanelLayout);
//        boardPanelLayout.setHorizontalGroup(
//            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 610, Short.MAX_VALUE)
//        );
//        boardPanelLayout.setVerticalGroup(
//            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 480, Short.MAX_VALUE)
//        );
////        frameChild_boardPanel.add(_boardpanel);
//
//        gridBagConstraints = new java.awt.GridBagConstraints();
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 0;
//        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
//        gridBagConstraints.ipady = 50;
//        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
//        frameLevelTurn.add(frameChild_boardPanel, gridBagConstraints);
//
//        frameChild_playersPanel.setBackground(new java.awt.Color(180, 180, 40));
//        frameChild_playersPanel.setMaximumSize(null);
//        frameChild_playersPanel.setPreferredSize(new java.awt.Dimension(190, 480));
//
//        javax.swing.GroupLayout playersPanelLayout = new javax.swing.GroupLayout(frameChild_playersPanel);
//        frameChild_playersPanel.setLayout(playersPanelLayout);
//        playersPanelLayout.setHorizontalGroup(
//            playersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 184, Short.MAX_VALUE)
//        );
//        playersPanelLayout.setVerticalGroup(
//            playersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 0, Short.MAX_VALUE)
//        );
////        frameChild_playersPanel.add(_playerspanel);
//
//        gridBagConstraints = new java.awt.GridBagConstraints();
//        gridBagConstraints.gridx = 1;
//        gridBagConstraints.gridy = 0;
//        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
//        gridBagConstraints.ipadx = 184;
//        gridBagConstraints.ipady = 480;
//        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
//        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 0);
//        frameLevelTurn.add(frameChild_playersPanel, gridBagConstraints);
//
//        frameChild_deckPanel.setBackground(new java.awt.Color(80, 80, 10));
//        frameChild_deckPanel.setMaximumSize(null);
//        deckPanelChild_deckContents.setDividerLocation(300);
//        deckPanelChild_deckContents.setDividerSize(10);
////        frameChild_deckPanel.add(_deckpanel);
//
//        playerHand.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
//
//        handContents.setLayout(new java.awt.GridLayout(1, 0));
//        playerHand.setViewportView(handContents);
//
//        deckPanelChild_deckContents.setLeftComponent(playerHand);
//
//        playerNotebook.setLayout(new javax.swing.BoxLayout(playerNotebook, javax.swing.BoxLayout.X_AXIS));
//
//        notesCharacter.setLayout(new java.awt.GridLayout(3, 2));
//
//        for (String chara : game.Card.CHARACTERS) {
//        	char1 = new JCheckBox();
//	        char1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
//	        char1.setText(chara);
//	        notesCharacter.add(char1);
//        }
//        playerNotebook.add(notesCharacter);
//
//        notesWeapons.setLayout(new java.awt.GridLayout(3, 2));
//
//        for (String wep : game.Card.WEAPONS) {
//        	weapon1 = new JCheckBox();
//	        weapon1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
//	        weapon1.setText(wep);
//	        notesWeapons.add(weapon1);
//        }
//        playerNotebook.add(notesWeapons);
//
//        notesRooms.setLayout(new java.awt.GridLayout(4, 3));
//
//        for (String room : game.Card.ROOMS) {
//        	room1 = new JCheckBox();
//        	room1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
//        	room1.setText(room);
//        	notesRooms.add(room1);
//        }
//        playerNotebook.add(notesRooms);
//
//        deckPanelChild_deckContents.setRightComponent(playerNotebook);
//
//        javax.swing.GroupLayout deckPanelLayout = new javax.swing.GroupLayout(frameChild_deckPanel);
//        frameChild_deckPanel.setLayout(deckPanelLayout);
//        deckPanelLayout.setHorizontalGroup(
//            deckPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addComponent(deckPanelChild_deckContents)
//        );
//        deckPanelLayout.setVerticalGroup(
//            deckPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addComponent(deckPanelChild_deckContents, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
//        );
//
//        gridBagConstraints = new java.awt.GridBagConstraints();
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 1;
//        gridBagConstraints.gridwidth = 2;
//        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
//        gridBagConstraints.ipadx = 211;
//        gridBagConstraints.ipady = 28;
//        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
//        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
//        frameLevelTurn.add(frameChild_deckPanel, gridBagConstraints);
//
//        contents.add(frameLevelTurn);
//
//        frameLevelTurn.add(_turnpanel);
//        contents.add(frameLevelTurn, CARD_TURNS);

        gameover.setBackground(new java.awt.Color(200, 30, 30));

//        theEndText.setText("The End!");

        

        contents.add(gameover);

        contents.add(_hypopanel);

        menu_h_game.setText("Cluedo");

        menu_newgame.setText("New Game");
        menu_newgame.setEnabled(false);
        menu_newgame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_newgameActionPerformed(evt);
            }
        });
        menu_h_game.add(menu_newgame);

        menu_endturn.setText("End Turn");
        menu_endturn.setEnabled(false);
        menu_endturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_endturnActionPerformed(evt);
            }
        });
        menu_h_game.add(menu_endturn);

        menubar.add(menu_h_game);

        jMenu1.setText("jMenu1");

        menu_main.setText("Main");
        menu_main.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showMenuActionPerformed(evt);
            }
        });
        jMenu1.add(menu_main);

        menu_setup.setText("Setup");
        menu_setup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setupActionPerformed(evt);
            }
        });
        jMenu1.add(menu_setup);

        menu_turn.setText("Turn");
        menu_turn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showTurnsActionPerformed(evt);
            }
        });
        jMenu1.add(menu_turn);

        menu_end.setText("End turn");
        menu_end.addActionListener(controller);

        jMenu1.add(menu_end);

        menubar.add(jMenu1);

        setJMenuBar(menubar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contents, javax.swing.GroupLayout.DEFAULT_SIZE, DEFAULT_WIDTH, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contents, javax.swing.GroupLayout.DEFAULT_SIZE, DEFAULT_HEIGHT, Short.MAX_VALUE)
        );

        add(contents);
        pack();
    }// </editor-fold>

    private void menu_endturnActionPerformed(java.awt.event.ActionEvent evt) {
    	displayTurnPanel();
    }

    private void menu_newgameActionPerformed(java.awt.event.ActionEvent evt) {
    	displayMenu();
    }

    private void setupActionPerformed(java.awt.event.ActionEvent evt) {
    	displaySetup();
    }

    private void showMenuActionPerformed(java.awt.event.ActionEvent evt) {
        displayMenu();
    }

    private void showTurnsActionPerformed(java.awt.event.ActionEvent evt) {
    	displayTurnPanel();
    }

    private void showHypothesisActionPerformed(java.awt.event.ActionEvent evt) {
        displayHypothesis();
    }

//    public static final String CARD_MENU = "showMenuPanel";
//    public static final String CARD_SETUP = "showSetupPanel";
//    public static final String CARD_RULES = "showRulesPanel";
//    public static final String CARD_TURNS = "showTurnsPanel";
//    public static final String CARD_ENDGAME = "showEndGamePanel";
//    public static final String CARD_HYPOTHESIS = "showHypothesisPanel";

//    private static final String[] panels = {CARD_MENU,CARD_SETUP,CARD_RULES,CARD_TURNS,CARD_HYPOTHESIS,CARD_ENDGAME};

    private MenuPanel _menupanel;
    private GameSetupPanel _setuppanel;
    private HypothesisPanel _hypopanel;
    private TurnPanel _turnpanel;
    private BoardPanel _boardpanel;
//    private PlayersPanel _playerspanel;
//    private DeckPanel _deckpanel;

    // Variables declaration - do not modify
    private javax.swing.JPanel contents;

    private javax.swing.JPanel gameover;
//    private javax.swing.JLabel guiltyCharacter;
//    private javax.swing.JLabel guiltyRoom;
//    private javax.swing.JLabel guiltyWeapon;
    private javax.swing.JPanel handContents;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel frameLevelMenu;
    private javax.swing.JMenuItem menu_end;
    private javax.swing.JMenuItem menu_endturn;
    private javax.swing.JMenu menu_h_game;
    private javax.swing.JMenuItem menu_main;
    private javax.swing.JMenuItem menu_newgame;
    private javax.swing.JMenuItem menu_setup;
    private javax.swing.JMenuItem menu_turn;
    private javax.swing.JMenuBar menubar;
    private javax.swing.JLabel playerHasSolvedTheMurder;
    private javax.swing.JPanel frameLevelSetup;
    private javax.swing.JPanel setupPanel;
//    private javax.swing.JPanel theEndContents;
//    private javax.swing.JPanel theEndPanel;
//    private javax.swing.JLabel theEndText;
//    private javax.swing.JPanel theEndTitle;
    private javax.swing.JPanel frameLevelTurn;
//    private javax.swing.JLabel winnerIcon;
    // End of variables declaration

	// ==============================================================================================
	// TESTING METHODS BELOW THIS POINT
	// ==============================================================================================

	/** dummy method; replaces need for _gamesetupPanel
	 * (Default: 6 players, named "Player "+ 1-6 */
	public void testingGameSetup(){
		controller.startGame(Game.createDefaultMap());
	}

	/**
	 * FOR TESTING ONLY
	 */
	public void showBoardPanel(HashMap<String, String> players){
		this.controller.startTestGame(players);
		displayTurnPanel();
	}

	public void showPanel(CluedoPanel p){
		this.removeAll();
		this.setLayout(new BorderLayout());
		this.add(p, BorderLayout.CENTER);
		pack();
		System.out.println("Panel is visible: " + p.isVisible());
		repaint();
	}


}
