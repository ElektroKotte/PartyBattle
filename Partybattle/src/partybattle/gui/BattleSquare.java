package partybattle.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class BattleSquare extends JButton implements ActionListener {

	BattleWindow window;
	int col;
	int row;
	
	public BattleSquare(int col, int row, BattleWindow window) {
		this.window = window;
		this.col = col;
		this.row = row;
		
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(true);
		setBorder(BorderFactory.createLineBorder(Color.black));
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		window.shootAt(col, row);
	}
}
