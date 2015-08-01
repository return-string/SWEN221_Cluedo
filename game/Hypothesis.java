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
public class Hypothesis implements Set {
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
		// TODO Auto-generated constructor stub
	}

	public void addCharacter(Card c) throws IllegalAccessException {
		if (set[0] != null) { throw new IllegalAccessException(); }
		set[0] = c;
	}

	public void addWeapon(Card c) throws IllegalAccessException {
		if (set[1] != null) { throw new IllegalAccessException(); }
		set[1] = c;
	}

	public void addRoom(Card c) throws IllegalAccessException {
		if (set[2] != null) { throw new IllegalAccessException(); }
		set[1] = c;
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
		return 3;
	}



	@Override
	public boolean isEmpty() {
		return false;
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
	public Iterator iterator() {
		return new HypothesisIter();
	}



	@Override
	public Object[] toArray() {
		return new Card[]{set[0],set[1],set[2]};
	}

	@Override
	public boolean add(Object e) {
		throw new UnsupportedOperationException();
	}



	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}



	@Override
	public boolean containsAll(Collection c) {
		throw new UnsupportedOperationException();
		/*if (c.size() > 3) { return false; }
		int tested = 0;
		for (int i = 0; i < 3; ) {
			for (Object o : c) {
				if (!o instanceof Card) {
					return false;
				} else if ((Card)o.getType() )
			}
		} */
	}



	@Override
	public boolean addAll(Collection c) {
		throw new UnsupportedOperationException();
	}



	@Override
	public boolean retainAll(Collection c) {
		throw new UnsupportedOperationException();
	}



	@Override
	public boolean removeAll(Collection c) {
		throw new UnsupportedOperationException();
	}



	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	public class HypothesisIter implements Iterator {
		int idx = 0;
		boolean moved = false;

		@Override
		public boolean hasNext() {
			return idx != 3;
		}

		@Override
		public Object next() {
			idx++;
			return set[idx-1];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	@Override
	public Object[] toArray(Object[] a) {
		throw new UnsupportedOperationException();
	}

}
