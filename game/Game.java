package game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.management.InvalidAttributeValueException;


/** The Game class is used to model the Cluedo game, including the
 * list of players, turn progression and ending the game.
 *
 * It relies on the TextUI class to print messages to the console
 * and return user input.
 *
 * If this were a better design:
 * 	- most of Game's print methods would be in printUI instead.
 *  - whatever printed the messages would read these from a file instead.
 *
 * @author mckayvick
 *
 */

@SuppressWarnings("unused")
public class Game {
	public static final Random RNG = new Random(System.currentTimeMillis());
	public static final int WAITING = 0;
	public static final int PLAYER_ROLLING = 1;
	public static final int PLAYER_MOVING = 2;
	public static final int PLAYER_GUESSING = 3;
	public static final int PLAYER_ACCUSING = 4;
	public static final int PLAYER_NOACTIONS = 5;
	public static final int GAME_OVER = 10;

	private final Board BOARD = new Board();

	private List<Card> spareCards = null;

	private List<Player> players;
	private Theory guilty;
	private int gameState = WAITING;
	private int activePlayer = -1; /* used to check when play has begun also, instead
										of creating and setting an additional field.
										if activePlayer < 0, game has not really started
										and we can keep adding players. */
	private int roll = 0;

	public Game() {
		this.players = new ArrayList<Player>();
	}

	public Game(Set<String> players2) {
		// TODO Auto-generated constructor stub
	}

	/** This is the normal method that should be called to start a game.
	 * It asks the user how many players will be in this game, then
	 * creates the player list, sorts it, and tries to play.
	 * @throws GameStateModificationException
	 * @throws ActingOutOfTurnException
	 */
	public void startGame(Map<String,String> playerNames) throws GameStateModificationException, ActingOutOfTurnException {
		if (gameState != 0 || players != null) {
			throw new GameStateModificationException("Game is already in progress.");
		} else if (playerNames == null ^ (playerNames.size() < 3 || playerNames.size() > 6)) {
			throw new IllegalArgumentException("Cannot initialise a game with these players. (size of playerNames must be 3-6)");
		}
		players = new ArrayList<Player>();
		for (Map.Entry entry : playerNames.entrySet()) {
                    // TODO need some checking here, characters against Card.CHARACTERS. 
                    players.add(new Player((String)entry.getKey(),(String)entry.getValue()));
		}
		initialiseDeck();
		activePlayer = 0;
		gameState = PLAYER_ROLLING;
	}

	/** This is how we play a game of Cluedo!
	 * @throws InvalidAttributeValueException
	 * @param game TODO
	 * @throws ActingOutOfTurnException
	 */
	public void playGame() throws ActingOutOfTurnException {
		activePlayer = 0;
//		int roundCounter = 1;
//
//		/** the game plays at least one turn. */
//		do {
//			roundCounter = playTurn(roundCounter);
//		} while (isPlaying());

		/* once the game is over, work out who won and print the winning message.*/
		Player winner = null;
		for (Player p:players) {
			if (p.isPlaying() && winner == null) {
				winner = p;
			} else if (p.isPlaying() && winner != null) {
				throw new IllegalStateException();
			}
		}
		if (winner == null) { throw new IllegalStateException(); }
		gameState = GAME_OVER;
	}

	/** If it is a player's turn, this method will roll the dice and
	 * instruct the Board to highlight the spaces that can be moved to.
	 */
	public void rollDice() {
		Player p = players.get(activePlayer);

		if (gameState != PLAYER_ROLLING || p.hasMoved()) { return; }

		roll = RNG.nextInt(5)+1;
		BOARD.highlightMoves(p.position(),roll);
	}

	/** Returns the last rolled dice value.
	 * Note: if rollDice has not been called yet, rollDice may
	 * still contain the previous player's roll!
	 * @return
	 */
	public int getRoll() {
		return roll;
	}

	/** Moves the active player, if they can move and the coordinate is valid.
	 *
	 * @param Coordinate A Coordinate reflecting the square the player has clicked on.
	 * @throws ActingOutOfTurnException Passes on an exceptions thrown by moving/acting
	 * 	out of turn from descendant methods.
	 */
	public void playerMoves(Coordinate clicked) throws ActingOutOfTurnException {
		if (gameState != PLAYER_MOVING || roll == 0) { return; }

		Player p = players.get(activePlayer);
		if (p.hasMoved()) {
			throw new ActingOutOfTurnException();
		}
		Coordinate coord = BOARD.findMove(clicked, p.position(), roll);
		if (coord != null) {
			p.move(coord);
			roll = 0;
		}
	}

	/** Returns the player currently taking a turn.
	 * @return Player the activePlayer
	 */
	public Player getCurrentPlayer() {
		if (players == null || activePlayer < 0) {
			throw new UnsupportedOperationException();
		}
		return players.get(activePlayer);
	}

	/** This method is used when the controller reports that a suggestion is to be made.
	 *
	 * Game uses the player to the active player's left as the start of the round,
	 * then goes around the players asking if they are able to refute the hypothesis.
	 *
	 * This method does not allow a player to select which of their cards is used
	 * to refute the hypothesis, which means the asking player will be shown the same
	 * card every time they make the same hypothesis.
	 * @throws ActingOutOfTurnException
	 *
	 */
	public void testHypothesis(Set<String> hypothesis) throws ActingOutOfTurnException {
		Player p = players.get(activePlayer);
		if (hypothesis.size() != 3) {
			throw new IllegalArgumentException("Hypothesis must have exactly 3 parameters.");
		}
		else if (!p.hasMoved()) {
			throw new ActingOutOfTurnException(p.toString() +" hasn't moved and cannot make a suggestion yet!");
		}
		else if (BOARD.getRoom(p.position()) == Board.HALLWAYSTRING) {
			throw new ActingOutOfTurnException(p.toString() +" cannot make a suggestion when not in a room!");
		}
		gameState = PLAYER_GUESSING;

		Theory h = new Hypothesis(hypothesis);

		/* if the hypothesis requires a player, find and move them here. */
		Player accused = getPlayer(h.getCharacter());
		if (accused != null && !(accused.equals(p))) {
			accused.forciblyMove(p.position());
		}

		/* now, go through all the players and check their hands for cards to refute
		 * the active player's hypothesis. */
		int i = (activePlayer+1) % players.size();
		do {
			List<Card> l = players.get(i).refuteTheory(h);
			if (l.size() > 0) {
				// the ternary check just says: if this is new information to the asking player, mention that fact in the printout.
//				textUI.printText(players.get(i).getName() +" shares some "+ (p.isInnocent(l.get(0))? "":"new ") +"evidence with "+ p.getName() +".");
				p.vindicate(l.get(0));
				return;
			}
			i = (i+1) % players.size();
		} while (i!=activePlayer);

	}

	/** If the given Card matches a player in the game, return them. */
	public Player getPlayer(Card card) {
		for (Player p : players) {
			if (p.getCharacter().equals(card.getValue())) {
				return p;
			}
		}
		throw new IllegalArgumentException("The given card does not represent a played character.");
	}

	/** The player has chosen to make an accusation! Assemble their
	 * hypothesis, see if it equals the guilty one. If not, end them.
	 * Otherwise, they win. The player list is cleared and the game
	 * ends.
	 *
	 * @param p
	 */
	private boolean testAccusation(Set<String> hs) {
            Player p = players.get(activePlayer);
		Theory h = new Hypothesis(hs);
		if (!h.equals(guilty)) {
//			textUI.printText(p.getName()+"'s accusation was proven false! "+ p.getName() +" has been barred from the investigation.");
			p.kill();
			BOARD.toggleOccupied(p.position());
			return false;
		} else {
//			textUI.printText("Success! "+ p.getName() +" has made a correct accusation and the guilty party will be brought to justice.");
			if (p.getCharacter().equals(h.getCharacter().getValue())) {
//				textUI.printText("('Accusation' sounds kinder than 'loud, weeping confession into "+ players.get( (activePlayer + 1) % players.size()).getName() +"'s arms.)");
			}
			for (Player ps : players) {
				if (p != ps) {
					ps.kill();
				}
			}
			return true;
		}
	}

	/** Iff the given Card matches a player in the game, return true. */
	public boolean isPlayer(Card character) {
            if (players == null) { return false; }
		for (Player p : players) {
			if (p.getCharacter().equals(character.getValue())) {
				return true;
			}
		}
		return false;
	}

	/** This method selects the guilty character, weapon and room and deals
	 * the remaining cards to the players.
	 */
	public void initialiseDeck() {
		initialiseDeck(null,null,null);
	}

	/** This method takes the guilty character, weapon and room and uses
	 * this information to create the guilty Hypothesis, then deals
	 * the remaining cards to the players.
	 * Leave any parameter(s) as null to select randomly.
	 *
	 * This method exists only for testing purposes.
	 *
	 * @param c Guilty character
	 * @param w Guilty weapon
	 * @param r Guilty room
	 */
	public void initialiseDeck(String c, String w, String r) {
		// for each null value, pick a random card to be guilty
		if (c == null) {
			int i = (int) (Math.random()*Card.CHARACTERS.length);
			c = Card.CHARACTERS[i];
		}
		if (w == null) {
			int i = (int) (Math.random()*Card.WEAPONS.length);
			w = Card.WEAPONS[i];
		}
		if (r == null) {
			int i = (int) (Math.random()*Card.ROOMS.length);
			r = Card.ROOMS[i];
		}

		// put guilty cards into fields
		guilty = new Hypothesis(new CardImpl(Card.Type.CHARACTER, c),
								new CardImpl(Card.Type.WEAPON, w),
								new CardImpl(Card.Type.ROOM, r));
		List<Card> deck = createNewDeck(c, w, r); // creates, shuffles a new deck of cards
		int remainder = (Card.DECKSIZE-3) % players.size();
		int handSize = (Card.DECKSIZE-3-remainder) / players.size();

		// add cards to each player's hand
		int cardIdx = 0;

		for (Player p : players) {
			for (int i = 0; i < handSize; i++) {
				try {
					p.giveCard(deck.get(cardIdx));
				} catch (GameStateModificationException e) {
					e.printStackTrace();
					System.exit(1);
				}
				cardIdx ++;
			}
		}

		// if there are any cards remaining, print a message
		if (remainder != 0) {
			// then ensure every player has these cards marked off...
			for (Player p : players) {
				for (Card spareCard : deck.subList(cardIdx,deck.size())) {
					p.vindicate(spareCard);
				}
			}
		}
	}

	/** Given the values of the guilty cards, this method assembles a shuffled list
	 * containing the remaining innocent cards and returns it.
	 *
	 * @param c One of the CHARACTER strings defined in Cards
	 * @param w One of the WEAPON strings
	 * @param r One of the ROOM strings.
	 * @return A shuffled list of the remaining cards, ready to be dealt to players.
	 */
	public static List<Card> createNewDeck(String c, String w, String r) {
		ArrayList<Card> deck = new ArrayList<Card>();
		if (c == null || w == null || r == null) {
			throw new IllegalArgumentException("Parameters cannot be null when creating a deck.");
		}

		// add some cards to the deck
		for (int i = 0; i < Card.CHARACTERS.length; i++) {
			if (Card.CHARACTERS[i] != c) {
				deck.add(new CardImpl(Card.Type.CHARACTER, Card.CHARACTERS[i]));
			}
		}
		for (int i = 0; i < Card.WEAPONS.length; i++) {
			if (Card.WEAPONS[i] != w) {
				deck.add(new CardImpl(Card.Type.WEAPON, Card.WEAPONS[i]));
			}
		}
		for (int i = 0; i < Card.ROOMS.length; i++) {
			if (Card.ROOMS[i] != r) {
				deck.add(new CardImpl(Card.Type.ROOM, Card.ROOMS[i]));
			}
		}

		Collections.shuffle(deck,RNG);
		return deck;
	}

	/** If there are spare cards, return an unmodifiable list of them.
	 * Returns null if all cards were dealt. */
	private List<Card> showSpareCards() {
		return Collections.unmodifiableList(spareCards);
	}







	// ---------------------------------------------------
	// HELPER METHODS
	// ---------------------------------------------------


	/** Debugging method used to add a list of character names.
	 * @throws GameStateModificationException If this method is called after play begins.*/
	public void addCharactersByName(List<String> characterNames) throws GameStateModificationException {
		if (players.size() != 0 && activePlayer >= 0) { throw new GameStateModificationException(); }
		players = new ArrayList<Player>();
		for (int i = 0; i < characterNames.size(); i++) {
			for (int j = 0; j < Card.CHARACTERS.length; j++) {
				if (Card.CHARACTERS[j].equals(characterNames.get(i))) {
					players.add(new Player(characterNames.get(i),characterNames.get(i)));
				}
			}
		}
		Collections.sort(players);
	}

	/** While there are still at least two players in the game, returns true.
	 * @return
	 */
	public boolean isPlaying() {
		int n = 0;
		for (Player p : players) {
			if (p.isPlaying()) {
				n ++;
			}
		}
		return n > 1;
	}

	/**
	 * @return the Board object used by this game to calculate valid moves,
	 * player location etc.
	 */
	public Board getBoard() {
		return BOARD;
	}

	/** Returns the Theory containing the guilty cards.*/
	public Theory getGuilty() {
		return guilty;
	}

	/** Returns a collection of player objects. For testing only:
	 * calling anything other than get methods on the players
	 * will disrupt the game state, if the game is in progress.
	 *
	 * @return The list of players in the game
	 */
	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((guilty == null) ? 0 : guilty.hashCode());
		result = prime * result + ((players == null) ? 0 : players.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Game)) {
			return false;
		}
		Game other = (Game) obj;
		if (guilty == null) {
			if (other.guilty != null) {
				return false;
			}
		} else if (!guilty.equals(other.guilty)) {
			return false;
		}
		if (players == null) {
			if (other.players != null) {
				return false;
			}
		} else if (!players.equals(other.players)) {
			return false;
		}
		return true;
	}


	/** Given a player, prints the things they know are innocent.
	 *
	 * @param p
	 * @throws ActingOutOfTurnException
	 */
	private List<Card> viewHand(Player p) throws ActingOutOfTurnException {
		if (!p.equals(players.get(activePlayer))) { throw new ActingOutOfTurnException(); }
		return Collections.unmodifiableList(p.getHand());
	}

	public void repaintBoard(Graphics g) {
            BOARD.repaint(g);
	}
        
	public void repaintBoard(Graphics g, Dimenson d) {
            BOARD.repaint(g,d);
	}
}