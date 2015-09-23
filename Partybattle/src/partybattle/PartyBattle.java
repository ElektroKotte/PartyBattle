package partybattle;

import javax.swing.SwingUtilities;

import partybattle.gui.*;
import partybattle.utils.*;

public class PartyBattle implements Runnable {

	private PartySettings settings;
	
	public PartyBattle()
	{
		this.settings = new PartySettings();
		// TODO set up variables and so
	}
	
	public void run()
	{
		BattleWindow window = new BattleWindow(settings);
		window.setVisible(true);
	}
	
	public static void main(String[] args) {
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		SwingUtilities.invokeLater(new PartyBattle());		
	}
}
