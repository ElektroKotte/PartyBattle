package partybattle;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import partybattle.gui.*;
import partybattle.utils.*;

public class PartyBattle implements Runnable {

	private PartySettings settings;
	private volatile boolean planner_mode;
	
	public PartyBattle(boolean planner_mode) {
		PartyLog.log("Created PartyBattle object with param: " + planner_mode);
		this.planner_mode = planner_mode; 
		try {
			this.settings = new PartySettings();
		} catch (IOException e) {
			PartyLog.log("Error while reading settings file: " + e.getMessage());
		}
	}
	
	public void run() {
		if (planner_mode) {
			PartyLog.log("Running in planner mode");
			BattlePlannerWindow window = new BattlePlannerWindow(settings);
			window.setVisible(true);
			String[] guestsOfHonor = settings.getGuestsOfHonor();
			for (String guest : guestsOfHonor) {
				window.enqueueGuest(new PartyGuest(guest, null));
			}
			String[][] armada = settings.getGuests();
			for (String[] fleet : armada) {
				PartyBoat boat = new PartyBoat("MS Someboat");
				for (String guest : fleet) {
					PartyGuest partyGuest = new PartyGuest(guest, boat);
					boat.addCrewMember(partyGuest);
					window.enqueueGuest(partyGuest);
				}
			}

		} else {
			PartyLog.log("Running in game mode");
			BattleWindow window = new BattleWindow(settings);
			window.setVisible(true);
		}
	}
	
	public static void main(String[] args) {
		boolean planner_mode = false;
		
		for (String arg : args) {
			if (arg.equalsIgnoreCase("--plan")) planner_mode = true;
		}
		
		PartyLog.log("Planner_mode=" + planner_mode);
		
		SwingUtilities.invokeLater(new PartyBattle(planner_mode));
	}
}
