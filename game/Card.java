/**
 * 
 */
package game;

/** The Card interface describes the basic methods required by all cards,
 * as well as the final Strings they all share. 
 * @author Vicki
 *
 */
public abstract class Card implements Comparable<Card> {
	public static enum Type {CHARACTER, WEAPON, ROOM};
	public static final String WHITE = "Mrs White";
	public static final String SCARLET = "Miss Scarlet";
	public static final String MUSTARD = "Colonel Mustard";
	public static final String GREEN = "Reverend Green";
	public static final String PEACOCK = "Mrs Peacock";
	public static final String PLUM = "Professor Plum";
	public static final String CANDLESTICK = "candlestick";
	public static final String DAGGER = "dagger";
	public static final String REVOLVER = "revolver";
	public static final String ROPE = "rope";
	public static final String PIPE = "pipe";
	public static final String WRENCH = "wrench";
	public static final String BALL = "ball room";
	public static final String BILLIARD = "billiard room";
	public static final String CONSERVATORY = "conservatory";
	public static final String DINING = "dining room";
	public static final String HALL = "hall";
	public static final String KITCHEN = "kitchen";
	public static final String LIBRARY = "library";
	public static final String LOUNGE = "lounge";
	public static final String STUDY = "study";
	/** note that the order of CHARACTERS determines the order of play. */
	public static final String[] CHARACTERS = { SCARLET,MUSTARD,WHITE,GREEN,PEACOCK,PLUM };
	public static final String[] WEAPONS = { CANDLESTICK, DAGGER, REVOLVER, ROPE, PIPE, WRENCH };
	public static final String[] ROOMS = { CONSERVATORY, BALL, BILLIARD, DINING, LIBRARY,
	HALL, KITCHEN, LOUNGE, STUDY };
	public static final int DECKSIZE = CHARACTERS.length + WEAPONS.length + ROOMS.length;

	public abstract Card.Type getType();
	public abstract String getValue();



	/** It's useful to know which index belongs to which value.
	 * For a given CardImpl.Type and value, returns the index of
	 * that value in the relevant array.
	 */
	public static int indexOf(Card.Type t, String name) {
		String[] arr = null;
		switch (t) {
			case CHARACTER:
				arr = Card.CHARACTERS;
				break;
			case WEAPON:
				arr = Card.WEAPONS;
				break;
			case ROOM:
				arr = Card.ROOMS;
				break;
			default:
				throw new IllegalArgumentException();
		}
		for (int i = 0; i < arr.length; i++){
			if (arr[i].equalsIgnoreCase(name)) {
				return i;
			}
		}
		throw new IllegalArgumentException();
	}
	
	public static Card.Type typeOf(String value) {
		for (String s : Card.CHARACTERS) {
			if (s.equals(value)) {
				return Card.Type.CHARACTER;
			}
		}
		for (String s : Card.WEAPONS) {
			if (s.equals(value)) {
				return Card.Type.WEAPON;
			}
		}
		for (String s : Card.ROOMS) {
			if (s.equals(value)) {
				return Card.Type.ROOM;
			}
		}
		throw new IllegalArgumentException();
	}

	/** Returns the unique start coordinate for each character name, as
	 * defined in CardImpl. */
	public static Coordinate getPlayerStart(String c) throws IllegalArgumentException{
		switch(c) {
			case Card.SCARLET:
				return new Coordinate(7,24);
			case Card.MUSTARD:
				return new Coordinate(0,17);
			case Card.WHITE:
				return new Coordinate(9,0);
			case Card.GREEN:
				return new Coordinate(14,0);
			case Card.PEACOCK:
				return new Coordinate(23,6);
			case Card.PLUM:
				return new Coordinate(23,19);
			default:
				throw new IllegalArgumentException();
		}
	}

	@Override
	public int compareTo(Card o) {
		Card c = (Card)o;
		if (c.getValue() == this.getValue()) {
			return 0;
		}
		if (c.getType() == Card.Type.CHARACTER &&
				getType() == Card.Type.CHARACTER) {
			for (int i = 0; i < Card.CHARACTERS.length; i++) {
				if (Card.CHARACTERS[i] == c.getValue()) {
					return 1;
				} else if (Card.CHARACTERS[i] == this.getValue()) {
					return -1;
				}
			}
		} else if (c.getType() == Card.Type.WEAPON &&
				getType() == Card.Type.WEAPON) {
			for (int i = 0; i < Card.WEAPONS.length; i++) {
				if (Card.WEAPONS[i] == c.getValue()) {
					return 1;
				} else if (Card.WEAPONS[i] == this.getValue()) {
					return -1;
				}
			}
		} else if (c.getType() == Card.Type.ROOM &&
				getType() == Card.Type.ROOM) {
			for (int i = 0; i < Card.ROOMS.length; i++) {
				if (Card.ROOMS[i] == c.getValue()) {
					return 1;
				} else if (Card.ROOMS[i] == this.getValue()) {
					return -1;
				}
			}
		}
		Card.Type[] t = Card.Type.values();
		for (int i = 0; i < t.length; i++) {
			if (t[i] == c.getType()) {
				return 1;
			} else if (t[i] == getType()) {
				return -1;
			}
		}
		throw new IllegalArgumentException();
	}
}
