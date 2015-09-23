package partybattle.utils;

import javax.swing.ImageIcon;

public class PartySettings {
	
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
	}
	
	public PartySettings() {
		this("/replace/me/with/good/path/later");
	}

	public int getRows() {
		// TODO read from settings file
		return 5;
	}

	public int getCols() {
		// TODO read value from settings file
		return 5;
	}

	public String getBackgroundImagePath() {
		// TODO read from settings file
		return "map.jpg";
	}

	public ImageIcon getImageForButton(int col, int row) {
		// TODO return icon if the coordinates should use special image
		if (col == 1 && row == 1) {
			return new ImageIcon("lighthouse.png");
		}
		
		return null;
	}

	public PartyGuest getGuestAt(int col, int row) {
		// TODO read from file
		return new PartyGuest("Foo Barsson", new PartyBoat());
	}
}
