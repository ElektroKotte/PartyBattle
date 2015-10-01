package partybattle.gui;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import partybattle.Guest;
import partybattle.Position;

public class BattleGrid extends JComponent {

	private BattleSquare[][] squareGrid;
	private static String[] colNames = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"};
	
	public BattleGrid(BattleWindow bw) {
		int COLS = bw.board.COLS;
		int ROWS = bw.board.ROWS;
		GridLayout layout = new GridLayout(ROWS+1, COLS+1);
		
		//setContentPane(new JLabel(Assets.mapImage));
		setLayout(layout);
		
		squareGrid = new BattleSquare[COLS][ROWS];
		

		for (int row = -1; row < ROWS; row++) {
			for (int col = -1; col < COLS; col++) {
				if (row < 0 && col < 0) {
					add(legendLabel(""));
				} else if (row < 0 && col >= 0) {
					add(legendLabel(colNames[col]));
				} else if (col < 0 && row >= 0) {
					add(legendLabel(""+(row+1)));
				} else if (col >= 0 && row >= 0) {
					BattleSquare r 	= new BattleSquare(col, row, bw);
					Guest guest 	= bw.board.guestAt(col, row);
					squareGrid[col][row] = r;
					if (guest != null && guest.isSpecial()) {
						System.out.println("Setting image for " + col + ", " + row);
						r.setImage(Assets.lightHouse);
					}
					add(r);
				}
			}
		}
	}
	
	
	public void setImageAt(int col, int row, ImageIcon img) {
		squareGrid[col][row].setImage(img);
	}
	
	
	public void setImageAt(Position p, ImageIcon img) {
		squareGrid[p.col][p.row].setImage(img);
	}
	
	
	public static JLabel legendLabel(String str) {
		JLabel l = new JLabel(str, null, JLabel.CENTER);
		return l;
	}
}
