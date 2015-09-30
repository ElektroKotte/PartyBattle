package partybattle;

import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;

import partybattle.gui.*;

public class PartyBattle implements Runnable {

	final int COLS = 8;
	final int ROWS = 8;
	
	List<String> guests;
	
	public PartyBattle(List<String> guests) {
		this.guests = guests;
	}
	
	public void run() {
		BattleWindow window = new BattleWindow(new Board(COLS, ROWS, guests));
		window.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new PartyBattle(someGuests()));	
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

	public static List<String> readGuests() {
		List<String> l = new LinkedList<String>();
		// TODO: Here goes code that opens a file with one guest per line and reads it.
		return l;
	}
}