/**
 * 
 */
package game;

/**
 * @author Vicki
 *
 */
public final class RoomCard implements Card {
	public final Game.Rooms room;
	/**
	 * 
	 */
	public RoomCard(Game.Rooms r) {
		room = r;
	}

	public boolean equals (Object obj) {
		if (obj instanceof RoomCard) {
			return ((RoomCard) obj).room == this.room;
		}
		return false;
	}

	public String toString() {
		return new String(room.toString());
	}
}
