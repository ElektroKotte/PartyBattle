package partybattle.gui;

import partybattle.utils.*;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import javax.swing.*;

public class BattlePlannerWindow extends JFrame implements ActionListener{
	private static final long serialVersionUID = 3427642868750313104L;
	
	private class ButtonData {
		public ButtonData(JButton button, int col, int row) {
			this.button = button;
			this.col = col;
			this.row = row;
		}
		JButton button;
		int col;
		int row;
	}
	
	private class Coord {
		Coord(int col, int row) {
			this.col = col;
			this.row = row;
		}
		int col;
		int row;
	}
	
	private Queue<PartyGuest> guestQueue = new LinkedList<PartyGuest>();
	
	private Map<String, ButtonData> buttonMap;
	
	private Set<PartyBoat> boatSet = new HashSet<PartyBoat>();
	
	private Set<PartyGuest> honorSet = new HashSet<PartyGuest>();
	
	private Map<PartyGuest, Coord> guestMap = new HashMap<PartyGuest, Coord>();

	private PartySettings settings;
	
	public BattlePlannerWindow(PartySettings settings)
	{
		super("PartyBattlePlanner");
		
		buttonMap = new HashMap<String, ButtonData>();
		
		this.settings = settings;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridLayout layout = new GridLayout(settings.getRows(), settings.getCols());

		JLabel background = new JLabel(new ImageIcon(settings.getBackgroundImagePath()));
		setContentPane(background);
		
		setLayout(layout);
		
		for (int row = 0; row < settings.getRows(); row++) {
			for (int col = 0; col < settings.getCols(); col++) {
				JButton button = new JButton();
				String identifier = new String(col + "," + row);

				button.setOpaque(false);
				button.setContentAreaFilled(false);
				button.setBorderPainted(true);
				button.setActionCommand(identifier);
				button.addActionListener(this);
				
				buttonMap.put(identifier, new ButtonData(button, col, row));

				add(button);
			}
		}

		setSize(new Dimension(800, 600));
		
        pack();
	}
	
	public void enqueueGuest(PartyGuest guest) {
		guestQueue.offer(guest);
	}

	public void actionPerformed(ActionEvent e) {
		PartyLog.log("Pressed button " + e.getActionCommand());
		
		PartyGuest guest = guestQueue.poll();
		if (guest == null) {
			// TODO output as JSON here
			// TODO Output geusts of honor
			for (PartyGuest honorGuest : honorSet) {
				PartyLog.log("HonorGuest: " + honorGuest.getName());
			}
			for (PartyBoat boat : boatSet) {
				PartyLog.log(boat.getName());
				for (PartyGuest partyGuest : boat.getCrew()) {
					Coord coord = guestMap.get(partyGuest);
					PartyLog.log("- " + partyGuest.getName() + " (" + coord.col + ", " + coord.row + ")");
				}
			}
			return;
		}
		
		ButtonData buttonData = buttonMap.get(e.getActionCommand());
		
		if (guest.getBoat() != null) {
			boatSet.add(guest.getBoat());
		} else {
			honorSet.add(guest);
		}
		guestMap.put(guest, new Coord(buttonData.col, buttonData.row));
		buttonData.button.setIcon(new ImageIcon(settings.getBoatImagePath()));
		
	}
}
