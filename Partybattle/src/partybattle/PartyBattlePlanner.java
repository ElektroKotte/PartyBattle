package partybattle;

import java.io.IOException;

import javax.swing.SwingUtilities;

import partybattle.gui.*;
import partybattle.utils.*;

public class PartyBattlePlanner implements Runnable {

	private PartySettings settings;
	
	public PartyBattlePlanner() {
		try {
			this.settings = new PartySettings();
		} catch (IOException e) {
			PartyLog.log("Error while reading settings file: " + e.getMessage());
		}
	}
	
	public void run() {
		BattlePlannerWindow window = new BattlePlannerWindow(settings);
		window.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new PartyBattlePlanner());		
	}
}
