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

	public static final int DEFAULT_WIDTH = 1200;
	public static final int DEFAULT_HEIGHT = 800;

	private Controller controller;

	public CluedoFrame() {
		super("Cluedo");
		this.controller = new Controller(this);

		initComponents();

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

	public void showPanel(int panelNo){
		if (panelNo >= 0 && panelNo < panels.length) {
			showPanel(panels[panelNo]);
		}
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
		HypothesisPanel hp = new HypothesisPanel(this.controller);
		showPanel(CARD_HYPOTHESIS);
	}

    public void showPanel(String card) {
		System.out.println("showing "+card);
    	cardLayout.show(contents, card);
    	System.out.println("Board panel visible?: " +_boardpanel.isVisible());
    	repaint();
	}

	private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        _menupanel = new MenuPanel(controller);
        _setuppanel = new GameSetupPanel(controller);
        _hypopanel = new HypothesisPanel(controller);
        _turnpanel = new TurnPanel(controller);
        _turnpanel.setSize(getSize());
        _boardpanel = _turnpanel.getBoardPanel();
        System.out.println("is this ok"+ _boardpanel == null);
        _playerspanel = new PlayersPanel(controller);
        _deckpanel = new DeckPanel(controller);

        jMenuItem1 = new javax.swing.JMenuItem();
        contents = new javax.swing.JPanel();
        frameLevelMenu = new javax.swing.JPanel();
        frameLevelSetup = new javax.swing.JPanel();
        //setupPanel = new javax.swing.JPanel();
        frameLevelTurn = new javax.swing.JPanel();

        gameover = new javax.swing.JPanel();
        theEndPanel = new javax.swing.JPanel();
        theEndTitle = new javax.swing.JPanel();
        theEndText = new javax.swing.JLabel();
        theEndContents = new javax.swing.JPanel();
        winnerIcon = new javax.swing.JLabel();
        playerHasSolvedTheMurder = new javax.swing.JLabel();
        guiltyWeapon = new javax.swing.JLabel();
        guiltyCharacter = new javax.swing.JLabel();
        guiltyRoom = new javax.swing.JLabel();
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

        cardLayout = new java.awt.CardLayout();
        contents.setLayout(cardLayout);

        frameLevelMenu.setMinimumSize(new java.awt.Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        frameLevelMenu.setLayout(new java.awt.BorderLayout());
        frameLevelMenu.add(_menupanel);
        _menupanel.setPreferredSize(getPreferredSize());
        contents.add(frameLevelMenu, CARD_MENU);

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

        contents.add(frameLevelSetup, CARD_SETUP);

        frameLevelTurn.setBackground(new java.awt.Color(200, 200, 100));

        frameLevelTurn.add(_turnpanel);
        contents.add(frameLevelTurn, CARD_TURNS);

        gameover.setBackground(new java.awt.Color(200, 30, 30));

        theEndText.setText("The End!");

        javax.swing.GroupLayout theEndTitleLayout = new javax.swing.GroupLayout(theEndTitle);
        theEndTitle.setLayout(theEndTitleLayout);
        theEndTitleLayout.setHorizontalGroup(
            theEndTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(theEndTitleLayout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addComponent(theEndText)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        theEndTitleLayout.setVerticalGroup(
            theEndTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(theEndText, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        );

        winnerIcon.setText("jLabel2");

        playerHasSolvedTheMurder.setText("Player has solved the murder!");

        guiltyWeapon.setText("jLabel4");

        guiltyCharacter.setText("jLabel5");

        guiltyRoom.setText("jLabel6");

        javax.swing.GroupLayout theEndContentsLayout = new javax.swing.GroupLayout(theEndContents);
        theEndContents.setLayout(theEndContentsLayout);
        theEndContentsLayout.setHorizontalGroup(
            theEndContentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(theEndContentsLayout.createSequentialGroup()
                .addGroup(theEndContentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(theEndContentsLayout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addGroup(theEndContentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(winnerIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(playerHasSolvedTheMurder, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(theEndContentsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(guiltyWeapon, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(guiltyCharacter, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(guiltyRoom, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)))
                .addContainerGap())
        );
        theEndContentsLayout.setVerticalGroup(
            theEndContentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(theEndContentsLayout.createSequentialGroup()
                .addComponent(winnerIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(playerHasSolvedTheMurder)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(theEndContentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(guiltyWeapon, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                    .addComponent(guiltyCharacter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(guiltyRoom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout theEndPanelLayout = new javax.swing.GroupLayout(theEndPanel);
        theEndPanel.setLayout(theEndPanelLayout);
        theEndPanelLayout.setHorizontalGroup(
            theEndPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(theEndPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(theEndPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(theEndTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(theEndContents, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        theEndPanelLayout.setVerticalGroup(
            theEndPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(theEndPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(theEndTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(theEndContents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout gameoverLayout = new javax.swing.GroupLayout(gameover);
        gameover.setLayout(gameoverLayout);
        gameoverLayout.setHorizontalGroup(
            gameoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gameoverLayout.createSequentialGroup()
                .addGap(199, 199, 199)
                .addComponent(theEndPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(242, Short.MAX_VALUE))
        );
        gameoverLayout.setVerticalGroup(
            gameoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gameoverLayout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(theEndPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(101, Short.MAX_VALUE))
        );

        contents.add(gameover, CARD_ENDGAME);

        contents.add(_hypopanel,CARD_HYPOTHESIS);

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
    	showPanel(CARD_ENDGAME);
    }

    private void menu_newgameActionPerformed(java.awt.event.ActionEvent evt) {
    	controller = new Controller(this);
    	showPanel(CARD_SETUP);
    }

    private void setupActionPerformed(java.awt.event.ActionEvent evt) {
    	showPanel(CARD_SETUP);
    }

    private void showMenuActionPerformed(java.awt.event.ActionEvent evt) {
        showPanel(CARD_MENU);
    }

    private void showTurnsActionPerformed(java.awt.event.ActionEvent evt) {
    	showPanel(CARD_TURNS);
    }

    private void showHypothesisActionPerformed(java.awt.event.ActionEvent evt) {
        showPanel(CARD_HYPOTHESIS);
    }

    public static final String CARD_MENU = "showMenuPanel";
    public static final String CARD_SETUP = "showSetupPanel";
    public static final String CARD_RULES = "showRulesPanel";
    public static final String CARD_TURNS = "showTurnsPanel";
    public static final String CARD_ENDGAME = "showEndGamePanel";
    public static final String CARD_HYPOTHESIS = "showHypothesisPanel";

    private static final String[] panels = {CARD_MENU,CARD_SETUP,CARD_RULES,CARD_TURNS,CARD_HYPOTHESIS,CARD_ENDGAME};

    private MenuPanel _menupanel;
    private GameSetupPanel _setuppanel;
    private HypothesisPanel _hypopanel;
    private TurnPanel _turnpanel;
    private BoardPanel _boardpanel;
    private PlayersPanel _playerspanel;
    private DeckPanel _deckpanel;

    private CardLayout cardLayout;

    // Variables declaration - do not modify
    private javax.swing.JPanel contents;
    private javax.swing.JPanel gameover;
    private javax.swing.JLabel guiltyCharacter;
    private javax.swing.JLabel guiltyRoom;
    private javax.swing.JLabel guiltyWeapon;
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
    private javax.swing.JPanel theEndContents;
    private javax.swing.JPanel theEndPanel;
    private javax.swing.JLabel theEndText;
    private javax.swing.JPanel theEndTitle;
    private javax.swing.JPanel frameLevelTurn;
    private javax.swing.JLabel winnerIcon;
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
		showPanel(CARD_TURNS);
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
