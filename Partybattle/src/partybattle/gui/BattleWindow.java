package partybattle.gui;

import partybattle.Board;
import partybattle.Guest;
import partybattle.PartyLog;
import partybattle.Position;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.*;

public class BattleWindow extends JFrame {
	private static final long serialVersionUID = 3427642868750313104L;

	private BattleSquare[][] squareGrid;
	private Board board;
	
	private ImageIcon explosionImage;
	private ImageIcon boatImage;
	private ImageIcon missImage;
	private Random rng;
	
	public BattleWindow(Board board) {
		
		super("PartyBattle");
		
		this.board = board;
		
		explosionImage = new ImageIcon("hit.png");
		boatImage = new ImageIcon("boat.png");
		missImage = new ImageIcon("miss.png");
		ImageIcon mapImage = new ImageIcon("map.png");
		ImageIcon lighthouseImage = new ImageIcon("lighthouse2.png");
		
		rng = new Random(1);
		
		squareGrid = new BattleSquare[board.COLS][board.ROWS];
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridLayout layout = new GridLayout(board.ROWS+1, board.COLS+1);
		
		setContentPane(new JLabel(mapImage));
		setLayout(layout);
		
		String[] colNames = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"};
		
		for (int row = -1; row < board.ROWS; row++) {
			for (int col = -1; col < board.COLS; col++) {
				if (row < 0 && col < 0) {
					add(legendLabel(""));
				} else if (row < 0 && col >= 0) {
					add(legendLabel(colNames[col]));
				} else if (col < 0 && row >= 0) {
					add(legendLabel(""+(row+1)));
				} else if (col >= 0 && row >= 0) {
					BattleSquare r 	= new BattleSquare(col, row, this);
					Guest guest 	= board.guestAt(col, row);
					squareGrid[col][row] = r;
					if (guest != null && guest.isSpecial()) {
						System.out.println("Setting image for " + col + ", " + row);
						r.setIcon(lighthouseImage);
					}
					add(r);
				}
			}
		}

		setSize(new Dimension(800, 600));
		
        pack();
	}
	
	
	public void shootAt(int col, int row) {
		Guest guest = board.guestAt(col, row);
		if (guest == null) {
			PartyLog.log("Miss!");
			squareGrid[col][row].setIcon(missImage);
			return;
		}

		if (guest.isSpecial()) {
			PartyLog.log("A guest of honor was hit! Bouncing...");
			shootAt(rng.nextInt(board.COLS), rng.nextInt(board.ROWS));
		} else {
			squareGrid[col][row].setIcon(explosionImage);
			shootGuest(guest);
		}
	}
	
	
	
	private void shootGuest(Guest guest) {
		guest.alive = false;
		
		System.out.println("Waa! The guest " + guest.name + " of " + guest.boat.name + " was hit!");
		
		boolean sunkBoat = true;
		Position crewmatePos = guest.crewmatePosition();
		Guest crewmate = board.guestAt(crewmatePos);
		
		if (crewmate.alive) {
			PartyLog.log(crewmate.name + " is now allowed to shoot!");
			setImageAt(crewmatePos, boatImage);
		} else {
			
		}
		
		if (sunkBoat) {
			PartyLog.log("The ship " + guest.boat.name + " is no moar! The guest of honor may take a shot!");
		}
	}
	
	
	private void setImageAt(Position p, ImageIcon img) {
		squareGrid[p.col][p.row].setIcon(img);
	}
	
	
	public static JLabel legendLabel(String str) {
		JLabel l = new JLabel(str, null, JLabel.CENTER);
		return l;
	}
}