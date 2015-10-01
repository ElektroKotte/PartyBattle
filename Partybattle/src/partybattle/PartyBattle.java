package partybattle;

import java.util.LinkedList;
import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.SwingUtilities;

import partybattle.gui.*;

public class PartyBattle implements Runnable {

	int COLS = 8;
	int ROWS = 8;
	
	List<String> guests;
	
	public PartyBattle(int n, List<String> guests) {
		this.guests = guests;
		COLS = n;
		ROWS = n;
	}
	
	public void run() {
		Board board = new Board(COLS, ROWS, guests);
		BattleWindow window = new BattleWindow(board);
		printBoatBoards(board);
		window.setVisible(true);
	}
	
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("missing n and guestlist. Good bye!");
			System.exit(1);
		}
		
		int n = Integer.parseInt(args[0]);
		File guestList = new File(args[1]);
			
		try {
			SwingUtilities.invokeLater(new PartyBattle(n, readGuests(guestList)));
		} catch(IOException e) {
			System.out.println("Failed reading guestList");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static List<String> someGuests() {
		List<String> l = new LinkedList<String>();
		l.add("Deniz");
		l.add("Tim");
		l.add("Marianne S");
		l.add("Kotte");
		l.add("Teleman");
		l.add("Oscar");
		l.add("Catten");
		l.add("Sofia");
		l.add("Jenny");
		l.add("Niklas");
		l.add("Angelica");
		return l;
	}

	public static List<String> readGuests(File f) throws IOException {
		List<String> l = new LinkedList<String>();
		BufferedReader r = new BufferedReader(new FileReader(f));
		String line = r.readLine(); 
		while (line != null) {
			l.add(line);
			line = r.readLine();
		}
		r.close();
		// TODO: Here goes code that opens a file with one guest per line and reads it.
		return l;
	}
	
	public static void printBoatBoards(Board b) {
		for (Boat boat : b.getBoats()) {
			BattleWindow g = new BattleWindow(b);
			g.showBoat(boat);
			g.paint(g);
		}
	}
}