package partybattle;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Board {

	public int COLS;
	public int ROWS;

	private Guest[][] guestGrid;
	private List<Boat> boats = new LinkedList<Boat>();
	private List<Position> specialPositions = new LinkedList<Position>();
	private Random rng = new Random(1);
	
	public Board(int cols, int rows, List<String> guests) {
		COLS = cols;
		ROWS = rows;
		
		guestGrid = new Guest[COLS][ROWS];

		
		java.util.Collections.shuffle(guests, rng);
		addSpecial("Ufuk Kirik");
		
		for (int i = 0; i < guests.size()-1; i += 2) {
			Boat boat = randomValidBoat(guests.get(i), guests.get(i+1));
			addValidBoat(boat);
		}
		
		
		//TODO: handle the odd guest!
		//	either make special, or partner with him/herself
	}
	
	public void addSpecial(String name) {
		int c = rng.nextInt(COLS);
		int r = rng.nextInt(ROWS);
		if (guestGrid[c][r] == null) {
			Guest g = new Guest(name, null);
			specialPositions.add(new Position(c, r));
			guestGrid[c][r] = g;
		} else addSpecial(name);
	}
	
	public Guest guestAt(Position p) {
		return guestGrid[p.col][p.row];
	}
	
	public Guest guestAt(int col, int row) {
		return guestGrid[col][row];
	}
	
	public Iterable<Boat> getBoats() {
		return boats;
	}
	
	public Iterable<Position> getSpecialPositions() {
		return specialPositions;
	}
	
	private void addValidBoat(Boat boat) {
		boats.add(boat);
		guestGrid[boat.pos1.col][boat.pos1.row] = boat.guest1;
		guestGrid[boat.pos2.col][boat.pos2.row] = boat.guest2;
	}
	
	private Boat randomValidBoat(String g1, String g2) {
		Boat boat;
		if (rng.nextBoolean())
			boat = randomHorizontal();
			else
				boat = randomVertical();
		if (isValid(boat)) {
			boat.setGuests(new Guest(g1, boat), new Guest(g2, boat));
			return boat;
		} else
			return randomValidBoat(g1, g2);
	}
	
	private boolean isValid(Boat boat) {
		return guestGrid[boat.pos1.col][boat.pos1.row] == null && 
				guestGrid[boat.pos2.col][boat.pos2.row] == null;
	}
	
	private Boat randomHorizontal() {
		Boat boat = new Boat();
		boat.pos1 = new Position(rng.nextInt(COLS-1), rng.nextInt(ROWS));
		boat.pos2 = new Position(boat.pos1.col + 1, boat.pos1.row);
		return boat;
	}
	
	private Boat randomVertical() {
		Boat boat = new Boat();
		boat.pos1 = new Position(rng.nextInt(COLS), rng.nextInt(ROWS-1));
		boat.pos2 = new Position(boat.pos1.col, boat.pos1.row + 1);
		return boat;
	}
}
