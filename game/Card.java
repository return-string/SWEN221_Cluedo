package game;

/* A deck of Cards is made by the Game when play begins,
 * and each CardInter is in the hand of a player, unless
 * it is a the murderer, murder weapon or location.
 *
 * See WeaponCard, CharacterCard or RoomCard.
 */

public class Card {
	public static enum Character { WHITE,SCARLET,MUSTARD,GREEN,PEACOCK,PLUM };
	public static enum Weapon { CANDLESTICK, DAGGER, REVOLVER, ROPE, PIPE, WRENCH };
	public static enum Room { CONSERVATORY, BALL, BILLIARD, DINING, HALL, KITCHEN, LOUNGE, STUDY };

	private final Character character;
	private final Weapon weapon;
	private final Room room;

	public Card(Room r) {
		room = r;
		character = null;
		weapon = null;
	}

	public Card(Weapon w) {
		room = null;
		character = null;
		weapon = null;
	}

	public Card(Character c) {
		room = null;
		character = c;
		weapon = null;
	}

	public boolean isCharacter() {
		return character == null;
	}

	public boolean isRoom() {
		return room == null;
	}

	public boolean isWeapon() {
		return weapon == null;
	}
}
