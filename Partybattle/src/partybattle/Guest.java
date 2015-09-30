package partybattle;

public class Guest {
	
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
		if (boat == null) 				return null;
		else if (boat.guest1 == this) 	return boat.pos2;
		else 							return boat.pos1;
	}
	
	public String toString() {
		return "Guest("+name+")";
	}
}

