package partybattle.gui;

import javax.swing.ImageIcon;

public class Assets {


	public ImageIcon explosionImage = new ImageIcon("hit.png");
	public ImageIcon boatImage = new ImageIcon("boat.png");
	public ImageIcon missImage = new ImageIcon("miss.png");
	
	public ImageIcon lightHouse = new ImageIcon("lighthouse2.png");
	
	public ImageIcon[] boats = {
		lightHouse,
		new ImageIcon("boat_hor1.png"),
		new ImageIcon("boat_hor2.png"),
		new ImageIcon("boat_ver1.png"),
		new ImageIcon("boat_ver2.png")
	};
	
	public ImageIcon[] sunkenBoats = {
			lightHouse,
		new ImageIcon("boat_hor1_sunk.png"),
		new ImageIcon("boat_hor2_sunk.png"),
		new ImageIcon("boat_ver1_sunk.png"),
		new ImageIcon("boat_ver2_sunk.png")
	};
}
