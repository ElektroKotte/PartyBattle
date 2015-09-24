package partybattle.utils;

import javax.swing.ImageIcon;

public class PartySettings {
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
	
	public PartySettings(String settingsFilePath) {
		/* TODO open the given file
		 * 
		 * Probably good if the file is of JSON format. e.g.,
		 * 
		 * {
		 *    "cols": 7,
		 *    "rows": 7,
		 *    "backgroundImage": "/path/to/image"
		 *    "boats": [
		 *        [
		 *            {
		 *                "name": "Foo Barsson",
		 *                "col": 5,
		 *                "row": 7
		 *            },
		 *            {
		 *                "name": "Bar Foosson",
		 *                "col": 5,
		 *                "row": 7
		 *            }
		 *        ]
		 *    ],
		 *    "guestOfHonors": [
		 *        {
		 *            "name": "Ufuk",
		 *            "col": 1,
		 *            "row": 1
		 *        }
		 *    ]
		 *    }
		 * }
		 */
		
		// TODO read settings to find out cols and rows
		cols = 8;
		rows = 8;
		guests = new PartyGuest[cols][rows];
		
		mapImagePath = "map.jpg";
		explosionImagePath = "fire.png";
		boatImagePath = "boat.png";
		
		PartyBoat boat;
		
		boat = new PartyBoat("MS Whatknot");
		addGuestAt("John Dorian", boat, 3, 3);
		addGuestAt("Christoffer Turk", boat, 3, 4);
		
		boat = new PartyBoat("MS Herpaderp");
		addGuestAt("Perry Cox", boat, 2, 2);
		addGuestAt("Dr. Kelso", boat, 2, 3);
		
		addGuestAt("Ufuk Kirik", null, 1, 1);
	}
	
	public PartySettings() {
		this("/replace/me/with/good/path/later");
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
}
