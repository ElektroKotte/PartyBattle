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
	
	private class ButtonData {
		private JButton button;
		private PartyGuest guest;

		ButtonData(JButton button, PartyGuest guest) {
			this.button = button;
			this.guest = guest;
		}
	}
	
	private HashMap<String, ButtonData> buttonMap;
	
	private ImageIcon explosionImage;
	private ImageIcon boatImage;
	private ImageIcon splashImage;
	private Random rng;
	
	private final int COLS;
	private final int ROWS;
	
	private PartySettings settings;
	
	public BattleWindow(PartySettings settings)
	{
		super("PartyBattle");
		
		buttonMap = new HashMap<String, ButtonData>();
		
		explosionImage = new ImageIcon(settings.getExplsionImagePath());
		boatImage = new ImageIcon(settings.getBoatImagePath());
		splashImage = new ImageIcon(settings.getSplashImagePath());
		
		rng = new Random();
		
		this.settings = settings;
		
		COLS = settings.getCols();
		ROWS = settings.getRows();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridLayout layout = new GridLayout(settings.getRows(), settings.getCols());

		JLabel background = new JLabel(new ImageIcon(settings.getBackgroundImagePath()));
		setContentPane(background);
		
		setLayout(layout);
		
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				JButton button = new JButton();
				String identifier = new String(col + "," + row);
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

				buttonMap.put(identifier, new ButtonData(button, guest));
				
				add(button);
			}
		}

		setSize(new Dimension(800, 600));
		
        pack();
	}
	
	public void actionPerformed(ActionEvent e) {
		ButtonData buttonData = buttonMap.get(e.getActionCommand());
		if (buttonData.guest == null) {
			System.out.println("Miss!");
			buttonData.button.setIcon(splashImage);
			return;
		}

		PartyBoat boat = buttonData.guest.getBoat();
		if (boat == null) {
			System.out.println("A guest of honor was hit!");
			shootRandom();
		} else {
			shootGuest(buttonData.guest, boat);
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
}
