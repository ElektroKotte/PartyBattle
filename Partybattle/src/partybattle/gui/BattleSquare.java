package partybattle.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BattleSquare extends JButton implements ActionListener {
	private static final long serialVersionUID = 8818691559117820755L;
	BattleWindow window;
	int col;
	int row;
	
	public BattleSquare(int col, int row, BattleWindow window) {
		this.window = window;
		this.col = col;
		this.row = row;
		
		setPreferredSize(new Dimension(100, 100));
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(true);
		setForeground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

		setHorizontalTextPosition(JButton.CENTER);
		setVerticalTextPosition(JButton.CENTER);
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		window.shootAt(col, row);
	}
	
	public void setImage(ImageIcon ii, String str) {
		Dimension dim = getSize();

		Image scaledImg = ii.getImage().getScaledInstance(dim.width, dim.height, java.awt.Image.SCALE_SMOOTH);
		setIcon(new ImageIcon(scaledImg));
		setText(str);

	}
}
