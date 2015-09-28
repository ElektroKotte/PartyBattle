package partybattle.gui;

import partybattle.utils.*;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Random;

import javax.swing.*;

public class BattleWindow extends JFrame implements ActionListener{
	private static final long serialVersionUID = 3427642868750313104L;
	
	private HashMap<String, PartyGuest> guest_map;
	
	private ImageIcon explosionImage;
	private ImageIcon boatImage;
	private Random rng;
	
	private final int COLS;
	private final int ROWS;
	
	private PartySettings settings;
	
	public BattleWindow(PartySettings settings)
	{
		super("PartyBattle");
		
		guest_map = new HashMap();
		
		explosionImage = new ImageIcon(settings.getExplsionImagePath());
		boatImage = new ImageIcon(settings.getBoatImagePath());
		
		rng = new Random();
		
		this.settings = settings;
		
		COLS = settings.getCols();
		ROWS = settings.getRows();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridLayout layout = new GridLayout(settings.getRows()+1, settings.getCols()+1);

		JLabel background = new JLabel(new ImageIcon(settings.getBackgroundImagePath()));
		setContentPane(background);
		
		setLayout(layout);
		
		String[] colNames = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"};
		
		for (int row = -1; row < ROWS; row++) {
			for (int col = -1; col < COLS; col++) {
				if (row < 0 && col < 0) {
					add(legendLabel(""));
				} else if (row < 0 && col >= 0) {
					add(legendLabel(colNames[col]));
				} else if (col < 0 && row >= 0) {
					add(legendLabel(""+(row+1)));
				} else if (col >= 0 && row >= 0) {
					JButton button = new JButton();
					String identifier = col + "," + row;
					PartyGuest guest = settings.getGuestAt(col, row);
					if (guest != null) {
						guest.setTriggerButton(button);
					}
					
					ImageIcon image = settings.getImageForButton(col, row);
					if (image != null) {
						System.out.println("Setting image for " + col + ", " + row);
						button.setIcon(image);
					}
					button.setOpaque(false);
					button.setContentAreaFilled(false);
					button.setBorderPainted(true);
					button.setActionCommand(identifier);
					button.addActionListener(this);
	
					guest_map.put(identifier, guest);
					
					add(button);
				}
			}
		}

		setSize(new Dimension(800, 600));
		
        pack();
	}
	
	public void actionPerformed(ActionEvent e) {
		PartyGuest guest = guest_map.get(e.getActionCommand());
		if (guest == null) {
			System.out.println("Miss!");
			return;
		}

		PartyBoat boat = guest.getBoat();
		if (boat == null) {
			System.out.println("A guest of honor was hit! Has taken ");
			shootRandom();
		} else {
			shootGuest(guest, boat);
		}
	}
	
	private void shootRandom() {
		PartyGuest guest = null;
		
		// TODO Should be a better method for this. Also would be good to check if there more people alive
		while (guest == null || !guest.isAlive() || guest.getBoat() == null) {
			int row = Math.abs(rng.nextInt()) % ROWS;
			int col = Math.abs(rng.nextInt()) % COLS;
			System.out.println("Bouncing to " + col + ", " + row);
			guest = settings.getGuestAt(col, row);
		}
		shootGuest(guest, guest.getBoat());
	}
	
	private void shootGuest(PartyGuest guest, PartyBoat boat) {
		
		
		guest.getTriggerButton().setIcon(explosionImage);
		guest.setAlive(false);
		
		System.out.println("Waa! The guest " + guest.getName() + " of " + boat.getName() + " was hit!");
		
		boolean sunkBoat = true;
		for (PartyGuest crew : boat.getCrew()) {
			if (crew != guest && crew.isAlive()) {
				crew.getTriggerButton().setIcon(boatImage);
				System.out.println(crew.getName() + " is now allowed to shoot!");
				sunkBoat = false;
			}
		}
		
		if (sunkBoat) {
			System.out.println("The ship " + boat.getName() + " is no moar! The guest of honor may take a shot!");
		}
	}
	
	public static JLabel legendLabel(String str) {
		JLabel l = new JLabel(str, null, JLabel.CENTER);
		return l;
	}
}
