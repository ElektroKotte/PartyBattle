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
		
		StringBuilder sb = new StringBuilder();
		if (guest == null) {
			// This method is flawed, but is quick to implement, and easy to clean up using external tools
			sb.append("{");
			sb.append("\"guestOfHonor\":");
			sb.append("[");
			for (PartyGuest honorGuest : honorSet) {
				Coord coord = guestMap.get(honorGuest);
				PartyLog.log("HonorGuest: " + honorGuest.getName());
				
				sb.append("{");
				sb.append("\"name\":\"" + honorGuest.getName() + "\",");
				sb.append("\"col\":" + coord.col + ",");
				sb.append("\"row\":" + coord.row + "");				
				sb.append("},");
			}
			sb.append("],");
			sb.append("\"boats\":");
			sb.append("[");
			for (PartyBoat boat : boatSet) {
				PartyLog.log(boat.getName());
				sb.append("{");
				sb.append("\"guests\":");
				sb.append("[");
				for (PartyGuest partyGuest : boat.getCrew()) {
					Coord coord = guestMap.get(partyGuest);
					PartyLog.log("- " + partyGuest.getName() + " (" + coord.col + ", " + coord.row + ")");
					sb.append("{");
					sb.append("\"name\":\"" + partyGuest.getName() + "\",");
					sb.append("\"col\":" + coord.col + ",");
					sb.append("\"row\":" + coord.row);
					sb.append("}");
				}
				sb.append("]");
				sb.append("},");
			}
			sb.append("]");
			sb.append("}");	
			PartyLog.log("JSON: " + sb.toString());
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
