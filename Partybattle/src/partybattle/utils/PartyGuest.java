package partybattle.utils;

public class PartyGuest {
	
	private String name;
	private PartyBoat boat;
	
	public PartyGuest(String name, PartyBoat boat) {
		this.name = name;
		this.boat = boat;
	}

	public String getName() {
		return name;
	}
	
	public PartyBoat getBoat(){
		return boat;
	}
}
