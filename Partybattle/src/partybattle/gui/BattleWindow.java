package partybattle.gui;

import partybattle.Board;
import partybattle.Boat;
import partybattle.Guest;
import partybattle.PartyLog;
import partybattle.Position;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Random;

import javax.swing.*;

public class BattleWindow extends JFrame {
	private static final long serialVersionUID = 3427642868750313104L;

	public Board board;
	public StatusRows statusRows = new StatusRows();
	public ButtonBar buttonBar;
	public BattleGrid grid;
	private Random rng;
	
	public BattleWindow(Board board) {
		
		super("PartyBattle");
		
		this.board = board;
		grid = new BattleGrid(board, this);
		buttonBar = new ButtonBar(this);
		setLayout(new BorderLayout());
		add(grid, BorderLayout.CENTER);
		add(statusRows, BorderLayout.NORTH);
		add(buttonBar, BorderLayout.EAST);

		rng = new Random(1);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(800, 800));
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		
        pack();
        
        for (Position pos : board.getSpecialPositions())
        	grid.setImageAt(
        			pos, 
        			Assets.lightHouse, 
        			board.guestAt(pos).name);
	}
	
	
	public void shootAt(int col, int row) {
		Guest guest = board.guestAt(col, row);
		if (guest == null) {
			status("Miss!");
			grid.setImageAt(col, row, Assets.missImage, "");
			return;
		}

		if (guest.isSpecial()) {
			status("A guest of honor was hit! Bouncing...");
			shootAt(rng.nextInt(board.COLS), rng.nextInt(board.ROWS));
		} else {
			int direction = guest.boatPos();
			PartyLog.log("Direction: " + direction);
			PartyLog.log("Setting image to: " + Assets.sunkenBoats[direction]);
			grid.setImageAt(
					col, row, 
					Assets.sunkenBoats[direction], 
					guest.name);
			shootGuest(guest);
		}
	}
	
	
	
	private void shootGuest(Guest guest) {
		guest.alive = false;
		
		status("Waa! " + guest.name + " was hit!");
		
		Position crewmatePos = guest.crewmatePosition();
		Guest crewmate = board.guestAt(crewmatePos);
		
		if (crewmate.alive) {
			status(crewmate.name + " is now allowed to shoot!");
			grid.setImageAt(
					crewmatePos, 
					Assets.boats[crewmate.boatPos()], 
					crewmate.name);
		} else {
			status("The ship of " + guest.name + " and " + crewmate.name + "is no more! The guest of honor may take a shot!");
		}
	}
	
	public void showGuest(Position pos) {
		Guest g = board.guestAt(pos);
		ImageIcon[] imgs = g.alive ? Assets.boats : Assets.sunkenBoats;
		grid.setImageAt(pos, imgs[g.boatPos()], g.name);
	}
	
	public void showBoat(Boat boat) {
		status(boat.guest1.name + " and "+boat.guest2.name);
		showGuest(boat.pos1);
		showGuest(boat.pos2);
	}
	
	public void showAll() {
	    for (Position pos : board.getSpecialPositions())
        	grid.setImageAt(
        			pos, 
        			Assets.lightHouse, 
        			board.guestAt(pos).name);
	    for (Boat boat : board.getBoats())
	    	showBoat(boat);
	}
	
	
	public void status(String msg) {
		PartyLog.log(msg);
		statusRows.update(msg);
	}
}