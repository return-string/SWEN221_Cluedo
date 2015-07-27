/**
 *
 */
package game;


/**
 *
 * @author Vicki
 *
 */
public final class WeaponCard implements Card {
	public final Game.Weapons weapon;
	/**
	 * When a card is made it must be initialized with a type.
	 */
	public WeaponCard(Game.Weapons w) {
		weapon = w;
	}

	public boolean equals (Object obj) {
		if (obj instanceof WeaponCard) {
			return ((WeaponCard) obj).weapon == this.weapon;
		}
		return false;
	}

	public String toString() {
		return new String(weapon.toString());
	}

}
