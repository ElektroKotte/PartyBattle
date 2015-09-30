package partybattle;

public class Guest {

	public static int SPECIAL = 0;
	public static int LEFT = 1;
	public static int RIGHT = 2;
	public static int UP = 3;
	public static int DOWN = 4;
	
	public String name;
	public Boat boat;
	public boolean alive = true;
	
	public Guest(String name, Boat boat) {
		this.name = name;
		this.boat = boat;
	}

	public boolean isSpecial() {
		return boat == null;
	}
	
	public Position crewmatePosition() {
		if (boat == null) 			return null;
		if (boat.guest1 == this) 	return boat.pos2;
		return boat.pos1;
	}
	
	public String toString() {
		return "Guest("+name+")";
	}
	
	public int boatPos() {
		if (boat == null) return SPECIAL;
		if (boat.guest1 == this)
			if (boat.pos1.col != boat.pos2.col) return LEFT;
			else return UP;
		else
			if (boat.pos1.col != boat.pos2.col) return RIGHT;
			else return DOWN;
	}
}

