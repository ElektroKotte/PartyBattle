package partybattle;

import java.io.IOException;

import javax.swing.SwingUtilities;

import partybattle.gui.*;
import partybattle.utils.*;

public class PartyBattle implements Runnable {

	private PartySettings settings;
	
	public PartyBattle() {
		try {
			this.settings = new PartySettings();
		} catch (IOException e) {
			PartyLog.log("Error while reading settings file: " + e.getMessage());
		}
	}
	
	public void run() {
		BattleWindow window = new BattleWindow(settings);
		window.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new PartyBattle());		
	}
}
