package partybattle;

import java.util.LinkedList;
import java.util.List;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import partybattle.gui.*;

public class PartyBattle implements Runnable {

	int PNG_WIDTH = 800;
	int PNG_HEIGHT = 800;
	
	List<String> guests;
	Board board;
	
	public PartyBattle(int n, List<String> guests, boolean printPNGs) {
		this.guests = guests;
		board = new Board(n, n, guests);
		
		if (printPNGs)
			printBoatBoards(board);
		
	}
	
	public void run() {
		BattleWindow window = new BattleWindow(board);
		window.setVisible(true);
	}
	
	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("usage:\n PartyBattle N guestListPath pngFlag. Good bye!");
			System.exit(1);
		}
		
		int n = Integer.parseInt(args[0]);
		File guestList = new File(args[1]);
		boolean printPNGs = Boolean.parseBoolean(args[2]);
		
		try {
			SwingUtilities.invokeLater(new PartyBattle(n, readGuests(guestList), printPNGs));
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
		return l;
	}
	
	public void printBoatBoards(Board b) {
		for (Boat boat : b.getBoats()) {
			BattleWindow g = new BattleWindow(b);
			g.setSize(new Dimension(PNG_WIDTH, PNG_HEIGHT));
			g.setVisible(true);
			g.pack();
			g.showBoat(boat);
			
			BufferedImage bi = new BufferedImage(g.getSize().width, g.getSize().height, BufferedImage.TYPE_INT_ARGB);
		    Graphics2D ig2 = bi.createGraphics();
			g.paint(ig2);
			ig2.dispose();
			try {
				File dir = new File("boatBoards");
				if (!dir.exists())
					dir.mkdir();
				ImageIO.write(bi, "PNG", new File(dir, boat.guest1.name+"_"+boat.guest2.name+".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.dispose();
		}
	}
}