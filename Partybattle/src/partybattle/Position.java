package partybattle;

public class Position {

	public int col;
	public int row;
	
	public Position(int col, int row) {
		this.col = col;
		this.row = row;
	}
	
	public Position() {}

	public String toString() {
		return "Position("+col+","+row+")";
	}
}
