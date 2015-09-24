package partybattle.utils;

import javax.swing.JButton;

public class PartyGuest {
	
	private String name;
	private PartyBoat boat;
	private JButton button;
	private boolean alive;
	
	public PartyGuest(String name, PartyBoat boat) {
		this.name = name;
		this.boat = boat;
		
		alive = true;
	}

	public String getName() {
		return name;
	}
	
	public PartyBoat getBoat(){
		return boat;
	}
	
	public JButton getTriggerButton() {
		return button;
	}
	
	public void setTriggerButton(JButton button) {
		this.button = button;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public boolean isAlive() {
		return alive;
	}
}

