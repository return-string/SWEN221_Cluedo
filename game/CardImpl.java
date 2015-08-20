package game;

/* A deck of Cards is made by the Game when play begins,
 * and each CardImpl is in the hand of a player, unless
 * it is the murderer, murder weapon or location.
 *
 * @author Badi James & Vicki McKay
 */

public class CardImpl extends Card {
    private String value;
    private Card.Type type;

    /**Constructor for class CardImpl
     * 
     *@param type Type of card
     *@param value Value of card
     */
    public CardImpl(Card.Type type, String value){
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

    public Card.Type getType(){
            return type;
    }

    public String getValue(){
            return value;
    }

    @Override
    public int hashCode() {
            final int prime1 = 31;
            final int prime2 = 1103;
            int result = 1;
            result = prime1 * result + ((type == null) ? 0 : type.hashCode());
            result = prime2 * result + ((value == null) ? 0 : value.hashCode());
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
            CardImpl other = (CardImpl) obj;
            if (type != other.type)
                    return false;
            if (value == null) {
                    if (other.value != null)
                            return false;
            } else if (!value.equalsIgnoreCase(other.value))
                    return false;
            return true;
    }
    @Override
    /** Returns the value of this card. */
    public String toString() {
            return value;
    }
}
