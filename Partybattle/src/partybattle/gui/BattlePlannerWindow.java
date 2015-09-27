package partybattle.gui;

import partybattle.utils.*;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Random;

import javax.swing.*;

public class BattlePlannerWindow extends JFrame implements ActionListener{
	private static final long serialVersionUID = 3427642868750313104L;
	
	private HashMap<String, PartyGuest> guest_map;
	
	private ImageIcon explosionImage;
	private ImageIcon boatImage;
	private Random rng;
	
	private final int COLS;
	private final int ROWS;
	
	public BattlePlannerWindow(PartySettings settings)
	{
		super("PartyBattle");
		/*
		
		guest_map = new HashMap<>();
		
		explosionImage = new ImageIcon(settings.getExplsionImagePath());
		boatImage = new ImageIcon(settings.getBoatImagePath());
		
		rng = new Random();
		
		this.settings = settings;
		*/
		
		COLS = settings.getCols();
		ROWS = settings.getRows();
		/*
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

				guest_map.put(identifier, guest);
				
				add(button);
			}
		}

		setSize(new Dimension(800, 600));
		
        pack();
        */
	}


	public void actionPerformed(ActionEvent e) {
	}
}
