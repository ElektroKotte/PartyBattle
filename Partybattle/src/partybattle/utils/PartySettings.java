package partybattle.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import javax.swing.ImageIcon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class PartySettings {
	private class Guest {
		String name;
		int col;
		int row;
	}
	
	private class Boat {
		String name;
		Guest[] guests;
	}
		
	private class BattlePlan {
		Guest[] guestsOfHonor;
		Boat[] boats;
	}
	
	private class Settings {
		int cols;
		int rows;
		String backgroundImagePath;
		String explosionImagePath;
		String boatImagePath;
	}
	
	private class Guests {
		String[] guestsOfHonor;
		String[][] guests;
	}
	
	private String[][] guestNames;
	private String[] guestsOfHonor;
	
	private class SettingsDeserializer implements JsonDeserializer<BattlePlan> {

		@Override
		public BattlePlan deserialize(JsonElement json, Type type, JsonDeserializationContext context)
				throws JsonParseException {
			final JsonObject thisObject = json.getAsJsonObject();
			BattlePlan guests = new BattlePlan();

			guests.guestsOfHonor = context.deserialize(thisObject.get("guestsOfHonor"), Guest[].class);

			JsonArray jsonBoats = thisObject.get("boats").getAsJsonArray(); 
			guests.boats = new Boat[jsonBoats.size()];
			for (int i = 0; i < jsonBoats.size(); i++) {
				JsonObject jsonBoat = jsonBoats.get(i).getAsJsonObject();
				Boat boat = new Boat();
				boat.name = jsonBoat.get("name").getAsString();
				boat.guests = context.deserialize(jsonBoat.get("guests"), Guest[].class);
				guests.boats[i] = boat;

			}

			return guests;
		}
	}
	
	private int cols;
	private int rows;
	
	private PartyGuest[][] guests;
	private String mapImagePath;
	private String explosionImagePath;
	private String boatImagePath;
	
	private void addGuestAt(String guestName, PartyBoat boat, int col, int row) {
		guests[col][row] = new PartyGuest(guestName, boat);
		if (boat != null) {
			boat.addCrewMember(guests[col][row]);
		}
	}
	
	public PartySettings(String settingsFilePath, String battleSettingsFilePath, String guestsFilePath) throws IOException {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(BattlePlan.class, new SettingsDeserializer());
		Gson gson = gsonBuilder.create();
		
		InputStream settingsInput = new FileInputStream(battleSettingsFilePath);
		InputStreamReader settingsReaderInput = new InputStreamReader(settingsInput);
		Settings settings = gson.fromJson(settingsReaderInput, Settings.class);
		
		InputStream battleSettingsInput = new FileInputStream(settingsFilePath);
		InputStreamReader battleSettingsInputReader = new InputStreamReader(battleSettingsInput);
		BattlePlan battlePlan = gson.fromJson(battleSettingsInputReader, BattlePlan.class);
		
		InputStream guestsInput = new FileInputStream(guestsFilePath);
		InputStreamReader guestsInputStreamReader = new InputStreamReader(guestsInput);
		Guests guests = gson.fromJson(guestsInputStreamReader, Guests.class);
		
		this.guestNames = guests.guests;
		this.guestsOfHonor = guests.guestsOfHonor;
		
		PartyLog.log("Reader cols = " + settings.cols + ", rows = " + settings.rows);
		
		PartyLog.log("The following guests are special");
		for (Guest guest : battlePlan.guestsOfHonor) {
			PartyLog.log(guest.name + " at " + guest.col + ", " + guest.row);
		}
		
		PartyLog.log("The following boats exist");
		for (Boat boat : battlePlan.boats) {
			PartyLog.log("boat: " + boat.name);
			for (Guest guest : boat.guests) {
				PartyLog.log(guest.name + " at " + guest.col + ", " + guest.row);
			}
		}
		
		this.boatImagePath = settings.boatImagePath;
		this.explosionImagePath = settings.explosionImagePath;
		this.mapImagePath = settings.backgroundImagePath;
		
		this.cols = settings.cols;
		this.rows = settings.rows;
		
		this.guests = new PartyGuest[this.cols][this.rows];
		
		for (Guest guest : battlePlan.guestsOfHonor) {
			addGuestAt(guest.name, null, guest.col, guest.row);
		}
		
		for (Boat boat : battlePlan.boats) {
			PartyBoat tmpBoat = new PartyBoat(boat.name);
			for (Guest guest : boat.guests) {
				addGuestAt(guest.name, tmpBoat, guest.col, guest.row);	
			}
		}
	}
	
	public PartySettings() throws IOException {
		this("settings.json", "battle_settings.json", "guests.json");
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public String getBackgroundImagePath() {
		return mapImagePath;
	}

	public ImageIcon getImageForButton(int col, int row) {
		ImageIcon image = null;
		PartyGuest guest =  guests[col][row];
		
		if (guest != null && guest.getBoat() == null) {
			image = new ImageIcon("lighthouse.png");
		}
		
		return image;
	}
	
	public String getExplsionImagePath() {
		return explosionImagePath;
	}
	
	public String getBoatImagePath() {
		return boatImagePath;
	}

	public PartyGuest getGuestAt(int col, int row) {
		return guests[col][row];
	}
	
	public String[][] getGuests() {
		return guestNames;
	}
	
	public String[] getGuestsOfHonor() {
		return guestsOfHonor;
	}
}
