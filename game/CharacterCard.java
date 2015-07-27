package game;

/** Models a Character card in the game */
public final class CharacterCard implements Card {
	public final Game.Characters character;

	public CharacterCard(Game.Characters c) {
		character = c;
	}

	public boolean equals (Object obj) {
		if (obj instanceof CharacterCard) {
			return ((CharacterCard) obj).character == this.character;
		}
		return false;
	}

	public String toString() {
		return new String(character.toString());
	}
}
