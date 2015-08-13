package game;

import java.util.Set;

/** The Theory interface abstracts the requirements of a general
 * guess, accusation or theory in a game of Cluedo. Its chief
 * requirement is that it contains three Card objects, one each of 
 * Card.Type Character, Weapon and Room, and that ideally these
 * should be immutable once set. 
 * 
 * @author Vicki
 *
 */
public interface Theory extends Set<Card>, Comparable<Theory>{

	/** This method allows the theory's Card of Type Character to be set.
	 * @param c A card of Card.Type.CHARACTER
	 * @throws IllegalAccessException If the Theory already has a Character card.
	 */
	public void setCharacter(Card c) throws IllegalAccessException;


	/** @return Returns the Theory's Character card. Can be null.
	 */
	public Card getCharacter();

	/** This method allows the theory's Card of Type Weapon to be set.
	 * @param c A card of Card.Type.WEAPON
	 * @throws IllegalAccessException If the Theory already has a Weapon card.
	 */
	public void setWeapon(Card c) throws IllegalAccessException;
	
	/** @return Returns the Theory's Weapon card. Can be null.
	 */
	public Card getWeapon();

	/** This method allows the theory's Card of Type Room to be set.
	 * @param c A card of Card.Type.ROOM
	 * @throws IllegalAccessException If the Theory already has a Room card.
	 */
	public void setRoom(Card c) throws IllegalAccessException;
	
	/** @return Returns the Theory's Room card. Can be null.
	 */
	public Card getRoom();

	int compareTo(Theory o);

}