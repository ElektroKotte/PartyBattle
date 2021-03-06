package partybattle.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import partybattle.Board;
import partybattle.PartyLog;
import partybattle.Position;

public class BattleGrid extends JComponent {
	private static final long serialVersionUID = -7774286544990588175L;
	private BattleSquare[][] squareGrid;
	private static String[] colNames = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"};
	
	public BattleGrid(Board b, BattleWindow bw) {
		int COLS = b.COLS;
		int ROWS = b.ROWS;
		GridLayout layout = new GridLayout(ROWS+1, COLS+1);

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
					squareGrid[col][row] = r;
					add(r);
				}
			}
		}
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension dim = getSize();
		Image background = Assets.mapImage.getImage();
		g.drawImage(background, 0, 0, dim.width, dim.height, null);
	}
	
	public void setImageAt(int col, int row, ImageIcon img, String str) {
		squareGrid[col][row].setImage(img, str);
	}
	
	
	public void setImageAt(Position p, ImageIcon img, String str) {
		squareGrid[p.col][p.row].setImage(img, str);
	}
	
	
	public static JLabel legendLabel(String str) {
		JLabel l = new JLabel(str, null, JLabel.CENTER);
		l.setForeground(Color.WHITE);
		return l;
	}
}
