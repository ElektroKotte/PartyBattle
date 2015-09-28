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
	private class Settings {
		int cols;
		int rows;
		
		String backgroundImage;
		String explosionImage;
		String boatImage;
		String missImage;
		
		Guest[] guestsOfHonor;
		Boat[] boats;
	}
	
	private class SettingsDeserializer implements JsonDeserializer<Settings> {

		@Override
		public Settings deserialize(JsonElement json, Type type, JsonDeserializationContext context)
				throws JsonParseException {
			final JsonObject thisObject = json.getAsJsonObject();
			Settings settings = new Settings();
			settings.cols = thisObject.get("cols").getAsInt();
			settings.rows = thisObject.get("rows").getAsInt(); 
			
			settings.backgroundImage = thisObject.get("backgroundImage").getAsString();
			settings.explosionImage = thisObject.get("explosionImage").getAsString();
			settings.boatImage = thisObject.get("boatImage").getAsString();
			
			settings.guestsOfHonor = context.deserialize(thisObject.get("guestsOfHonor"), Guest[].class);
			
			JsonArray jsonBoats = thisObject.get("boats").getAsJsonArray(); 
			
			settings.boats = new Boat[jsonBoats.size()];
			for (int i = 0; i < jsonBoats.size(); i++) {
				JsonObject jsonBoat = jsonBoats.get(i).getAsJsonObject();
				Boat boat = new Boat();
				boat.name = jsonBoat.get("name").getAsString();
				boat.guests = context.deserialize(jsonBoat.get("guests"), Guest[].class);
				settings.boats[i] = boat;
				
			}
			
			return settings;
		}
		
	}
	
	private int cols;
	private int rows;
	
	private PartyGuest[][] guests;
	private String mapImagePath;
	private String explosionImagePath;
	private String boatImagePath;
	private String missImagePath;
	
	private void addGuestAt(String guestName, PartyBoat boat, int col, int row) {
		guests[col][row] = new PartyGuest(guestName, boat);
		if (boat != null) {
			boat.addCrewMember(guests[col][row]);
		}
	}
	
	public PartySettings(String settingsFilePath) throws IOException {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Settings.class, new SettingsDeserializer());
		Gson gson = gsonBuilder.create();
		
		InputStream inputStream = new FileInputStream(settingsFilePath);
		InputStreamReader reader = new InputStreamReader(inputStream);
		Settings settings = gson.fromJson(reader, Settings.class);
		
		PartyLog.log("Reader cols = " + settings.cols + ", rows = " + settings.rows);
		
		PartyLog.log("The following guests are special");
		for (Guest guest : settings.guestsOfHonor) {
			PartyLog.log(guest.name + " at " + guest.col + ", " + guest.row);
		}
		
		PartyLog.log("The following boats exist");
		for (Boat boat : settings.boats) {
			PartyLog.log("boat: " + boat.name);
			for (Guest guest : boat.guests) {
				PartyLog.log(guest.name + " at " + guest.col + ", " + guest.row);
			}
		}

		this.boatImagePath = settings.boatImage;
		this.missImagePath = settings.missImage;
		this.explosionImagePath = settings.explosionImage;
		this.mapImagePath = settings.backgroundImage;
		
		this.cols = settings.cols;
		this.rows = settings.rows;
		
		this.guests = new PartyGuest[this.cols][this.rows];
		
		for (Guest guest : settings.guestsOfHonor) {
			addGuestAt(guest.name, null, guest.col, guest.row);
		}
		
		for (Boat boat : settings.boats) {
			PartyBoat tmpBoat = new PartyBoat(boat.name);
			for (Guest guest : boat.guests) {
				addGuestAt(guest.name, tmpBoat, guest.col, guest.row);	
			}
		}
	}
	
	public PartySettings() throws IOException {
		this("settings.json");
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
	
	public String getMissImagePath() {
		return missImagePath;
	}

	public PartyGuest getGuestAt(int col, int row) {
		return guests[col][row];
	}
}
