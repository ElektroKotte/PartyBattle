package partybattle.utils;

import java.util.LinkedList;
import java.util.List;

public class PartyBoat {
	private List<PartyGuest> crew;
	private String name;

	public PartyBoat(String name) {
		this.crew = new LinkedList<PartyGuest>();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void addCrewMember(PartyGuest guest) {
		crew.add(guest);
	}
	
	public List<PartyGuest> getCrew() {
		return crew;
	}
}
