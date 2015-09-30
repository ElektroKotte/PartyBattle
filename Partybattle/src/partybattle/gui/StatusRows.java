package partybattle.gui;

import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class StatusRows extends JComponent {

	private JLabel row1 = new JLabel(" ", SwingConstants.CENTER);
	private JLabel row2 = new JLabel(" ", SwingConstants.CENTER);
	private JLabel row3 = new JLabel(" ", SwingConstants.CENTER);
	
	public StatusRows() {
		setLayout(new GridLayout(3, 1));
		add(row1);
		add(row2);
		add(row3);
	}
	
	public void update(String msg) {
		row1.setText(row2.getText());
		row2.setText(row3.getText());
		row3.setText(msg);
	}
}
