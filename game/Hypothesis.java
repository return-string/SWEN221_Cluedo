/**
 *
 */
package game;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * @author mckayvick
 *
 */
public class Hypothesis implements Set<Card>, Comparable<Hypothesis> {
	private final Card[] set = new Card[3];

	/**
	 *
	 */
	public Hypothesis(Card character, Card weapon, Card room) {
		if (character.getType() != Card.Type.CHARACTER ||
				weapon.getType() != Card.Type.WEAPON ||
				room.getType() != Card.Type.ROOM ) {
			throw new IllegalArgumentException("A Hypothesis must be initialised with one of each card type, in the correct order.");
		}
		set[0] = character;
		set[1] = weapon;
		set[2] = room;
	}

	public Hypothesis() {
		
	}

	public void setCharacter(Card c) throws IllegalAccessException {
		if (set[0] != null) { throw new IllegalAccessException(); }
		set[0] = c;
	}

	public Card getCharacter() {
		return set[0];
	}

	public void setWeapon(Card c) throws IllegalAccessException {
		if (set[1] != null) { throw new IllegalAccessException(); }
		set[1] = c;
	}

	public Card getWeapon() {
		return set[1];
	}

	public void setRoom(Card c) throws IllegalAccessException {
		if (set[2] != null) { throw new IllegalAccessException(); }
		set[2] = c;
	}

	public Card getRoom() {
		return set[2];
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Hypothesis [set=" + Arrays.toString(set) + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(set);
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
		if (!(obj instanceof Hypothesis)) {
			return false;
		}
		Hypothesis other = (Hypothesis) obj;
		if (!Arrays.equals(set, other.set)) {
			return false;
		}
		return true;
		}
	@Override
	public int size() {
		int s = 0;
		for (int i = 0; i < 3; i++) {
			if (set[i] != null) {
				s++;
			}
		}
		return s;
	}



	@Override
	public boolean isEmpty() {
		return size() == 0;
	}



	@Override
	public boolean contains(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof Card)) {
			return false;
		}
		Card other = (Card) o;
		if (other.getType() == Card.Type.CHARACTER) {
			return set[0].equals(o);
		} else if (other.getType() == Card.Type.WEAPON) {
			return set[1].equals(o);
		} else {
			return set[2].equals(o);
		}
	}



	@Override
	public Iterator<Card> iterator() {
		return new HypothesisIter();
	}



	@Override
	public Object[] toArray() {
		Card[] a = new Card[size()];
		int i = 0;
		if (set[0] != null) {
			a[i] = set[0];
		}
		if (set[1] != null) {
			a[i] = set[1];
		}
		if (set[2] != null) {
			a[i] = set[2];
		}
		return a;
	}

	@Override
	public boolean add(Card e) {
		throw new UnsupportedOperationException();
	}



	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}



	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}



	@Override
	public boolean addAll(Collection c) {
		throw new UnsupportedOperationException();
	}



	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}



	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}



	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	public class HypothesisIter implements Iterator<Card> {
		int idx = 0;
		boolean moved = false;

		@Override
		public boolean hasNext() {
			return idx != 3;
		}

		@Override
		public Card next() {
			idx++;
			return set[idx-1];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	@Override
	public Hypothesis[] toArray(Object[] a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int compareTo(Hypothesis o) {
		if (o == this) {
			return 0;
		}
		if (o.getCharacter().compareTo(getCharacter()) == 0
				&& o.getWeapon().compareTo(getWeapon()) == 0
				&& o.getRoom().compareTo(getRoom()) == 0) {
			return 0;
		}
		int total = o.getRoom().compareTo(getRoom())*3;
			total += o.getWeapon().compareTo(getCharacter())*5;
			total += o.getCharacter().compareTo(getCharacter())*7;
		return total;
	}

}
