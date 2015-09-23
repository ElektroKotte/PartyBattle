package partybattle.gui;

import partybattle.utils.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class BattleWindow extends JFrame implements ActionListener{
	private static final long serialVersionUID = 3427642868750313104L;
	
	public BattleWindow(PartySettings settings)
	{
		super("PartyBattle");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridLayout layout = new GridLayout(settings.getRows(), settings.getCols());

		JLabel background = new JLabel(new ImageIcon(settings.getBackgroundImagePath()));
		setContentPane(background);
		
		setLayout(layout);
		
		for (int row = 0; row < settings.getRows(); row++) {
			for (int col = 0; col < settings.getCols(); col++) {
				JButton button = new JButton();
				ImageIcon image = settings.getImageForButton(col, row);
				if (image != null) {
					System.out.println("Setting image for " + col + ", " + row);
					button.setIcon(image);
				}
				button.setOpaque(false);
				button.setContentAreaFilled(false);
				button.setBorderPainted(true);
				button.setActionCommand("(" + col + ", " + row + ")");
				button.addActionListener(this);
				
				add(button);
			}
		}
		

		
		setSize(new Dimension(800, 600));
		
        pack();
	}
	
	public void actionPerformed(ActionEvent e) {
		System.out.println("Waa!" + e.getActionCommand());
	}
}
