package game;

public class Weapon implements Token {

	private String name;
	private Coordinate position;


	public Weapon(String name, Coordinate position) {
		super();
		this.name = name;
		this.position = position;
	}


	public String getName() {
		return name;
	}


	public Coordinate getPosition() {
		return position;
	}

}
