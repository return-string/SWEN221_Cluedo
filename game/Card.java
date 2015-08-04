package game;

/* A deck of Cards is made by the Game when play begins,
 * and each CardInter is in the hand of a player, unless
 * it is the murderer, murder weapon or location.
 *
 * @author Someone
 */

public class Card implements Comparable<Card> {

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

	public static final String[] CHARACTERS = { SCARLET,MUSTARD,WHITE,GREEN,PEACOCK,PLUM };
	public static final String[] WEAPONS = { CANDLESTICK, DAGGER, REVOLVER, ROPE, PIPE, WRENCH };
	public static final String[] ROOMS = { CONSERVATORY, BALL, BILLIARD, DINING, LIBRARY,
											HALL, KITCHEN, LOUNGE, STUDY };

	public static final int DECKSIZE = CHARACTERS.length + WEAPONS.length + ROOMS.length;

	private String value;
	private Type type;

	/**Constructor for class Card
	 * 
	 *@param type Type of card
	 *@param value Value of card
	 */
	public Card(Type type, String value){
		this.type = type;
		if (type.equals(Card.Type.CHARACTER)) {
			for (String s : Card.CHARACTERS) {
				if (s.equals(value)) {
					this.value = value;
					return;
				}
			}
		} else if (type.equals(Card.Type.WEAPON)) {
			for (String s : Card.WEAPONS) {
				if (s.equals(value)) {
					this.value = value;
					return;
				}
			}
		} else if (type.equals(Card.Type.ROOM)) {
			for (String s : Card.ROOMS) {
				if (s.equals(value)) {
					this.value = value;
					return;
				}
			}
		}
		throw new IllegalArgumentException(type+" uhoh" +value);
	}

	public Type getType(){
		return type;
	}

	public String getValue(){
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (type != other.type)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equalsIgnoreCase(other.value))
			return false;
		return true;
	}

	public String toString() {
		return value;
	}

	@Override
	public int compareTo(Card o) {
		Card c = (Card)o;
		if (c.getValue() == this.getValue()) {
			return 0;
		}
		if (c.getType() == Type.CHARACTER &&
				getType() == Type.CHARACTER) {
			for (int i = 0; i < CHARACTERS.length; i++) {
				if (CHARACTERS[i] == c.getValue()) {
					return 1;
				} else if (CHARACTERS[i] == this.getValue()) {
					return -1;
				}
			}
		} else if (c.getType() == Card.Type.WEAPON &&
				getType() == Type.WEAPON) {
			for (int i = 0; i < WEAPONS.length; i++) {
				if (WEAPONS[i] == c.getValue()) {
					return 1;
				} else if (WEAPONS[i] == this.getValue()) {
					return -1;
				}
			}
		} else if (c.getType() == Card.Type.ROOM &&
				getType() == Type.ROOM) {
			for (int i = 0; i < ROOMS.length; i++) {
				if (ROOMS[i] == c.getValue()) {
					return 1;
				} else if (ROOMS[i] == this.getValue()) {
					return -1;
				}
			}
		}
		Type[] t = Type.values();
		for (int i = 0; i < t.length; i++) {
			if (t[i] == c.getType()) {
				return 1;
			} else if (t[i] == getType()) {
				return -1;
			}
		}
		throw new IllegalArgumentException();
	}

	/** It's useful to know which index belongs to which value.
	 * For a given Card.Type and value, returns the index of
	 * that value in the relevant array.
	 */
	static int indexOf(Type t, String name) {
		String[] arr = null;
		switch (t) {
			case CHARACTER:
				arr = CHARACTERS;
				break;
			case WEAPON:
				arr = WEAPONS;
				break;
			case ROOM:
				arr = ROOMS;
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

	/** Returns the unique start coordinate for each character, as
	 * defined in Card.
	 *
	 */
	public static Coordinate getStart(String c) {
		switch(c) {
			case SCARLET:
				return new Coordinate(7,24);
			case MUSTARD:
				return new Coordinate(0,17);
			case WHITE:
				return new Coordinate(9,0);
			case GREEN:
				return new Coordinate(14,0);
			case PEACOCK:
				return new Coordinate(23,6);
			case PLUM:
				return new Coordinate(23,19);
			default:
				throw new IllegalArgumentException();
		}
	}
}
