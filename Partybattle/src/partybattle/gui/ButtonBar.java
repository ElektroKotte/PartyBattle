package partybattle.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class ButtonBar extends JComponent implements ActionListener {

	public JButton showRemaining = new JButton("The End.");
	BattleWindow bw;
	
	public ButtonBar(BattleWindow bw) {
		this.bw = bw;
		setLayout(new GridLayout(4, 1));
		add(new JLabel());
		add(new JLabel());
		add(new JLabel());
		add(showRemaining);
		showRemaining.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		bw.showAll();
	}
}
