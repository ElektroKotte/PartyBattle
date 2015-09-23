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
		 *    "guests": {
		 *        "someId": {
		 *            "name": "Foo Barsson",
		 *            "compadre": "otherId",
		 *            "col": 5,
		 *            "row": 7
		 *        },
		 *        "otherId": {
		 *            "name": "Bar Foosson",
		 *            "compadre": "otherId",
		 *            "col": 5,
		 *            "row": 7
		 *        }
		 *        "ufuk": {
		 *            "name": "Mr. Turkelton",
		 *            "special": true,
		 *            "col": 6,
		 *            "row": 6
		 *        }
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
}
