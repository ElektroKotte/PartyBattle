package partybattle.gui;

import partybattle.utils.*;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.*;

public class BattleWindow extends JFrame implements ActionListener{
	private static final long serialVersionUID = 3427642868750313104L;
	
	private HashMap<String, PartyGuest> guest_map;
	
	public BattleWindow(PartySettings settings)
	{
		super("PartyBattle");
		
		guest_map = new HashMap<>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridLayout layout = new GridLayout(settings.getRows(), settings.getCols());

		JLabel background = new JLabel(new ImageIcon(settings.getBackgroundImagePath()));
		setContentPane(background);
		
		setLayout(layout);
		
		for (int row = 0; row < settings.getRows(); row++) {
			for (int col = 0; col < settings.getCols(); col++) {
				JButton button = new JButton();
				String identifier = new String(col + "," + row);
				
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
				
				guest_map.put(identifier, settings.getGuestAt(col, row));
				
				add(button);
			}
		}
		

		
		setSize(new Dimension(800, 600));
		
        pack();
	}
	
	public void actionPerformed(ActionEvent e) {
		PartyGuest guest = guest_map.get(e.getActionCommand());
		if (guest == null) {
			System.out.println("Poop!");
			return;
		}
		
		// TODO use the boat to get the other guest in the boat
		PartyBoat boat = guest.getBoat();
		System.out.println("Waa! The guest at (" +e.getActionCommand() + "), " +
						   guest.getName() + " of " + boat.getName() + " was hit!");
	}
}
