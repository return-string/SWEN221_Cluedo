package ui;

<<<<<<< HEAD
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import game.Game;

public class Controller implements ActionListener {
	
	private Game cluedoGame;
	private CluedoFrame gameFrame;
	
	public Controller(CluedoFrame gameFrame){
		this.gameFrame = gameFrame;
	}
	
	public void startGame(Set<String> players){
		this.cluedoGame = new Game(players);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

=======
public class Controller {

//	public void listenTo(JComponent... things) {
//		for (JComponent j : things) {
//			j.add
//		}
//	}
>>>>>>> 59cdfbf979ee603e91db0a514ce3f629e4c3fc93
}
