package partybattle;

public class Boat {

	public Position pos1;
	public Position pos2;
	public Guest guest1;
	public Guest guest2;
	
	public String name;

	
	public void setGuests(Guest g1, Guest g2) {
		guest1 = g1;
		guest2 = g2;
		g1.boat = this;
		g2.boat = this;
	}
}
