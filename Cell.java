package minesweeper;

/**
 * An instance represents a cell in the mine field
 * 
 * @author Benjamin Revard
 */
public class Cell {

	    private int column; // The column where this cell lives (>= 0 and < length)
	    private int row; // The row where this cell lives (>= 0 and < length)
		private int value; // The value of this cell (0-9)
		private boolean isMine; // Whether this cell is a mine
		private boolean leftClicked; // Whether this cell has been left clicked
		private boolean rightClicked; // Whether this cell has been right clicked
		
		/**
		 * Constructor for a cell object.
		 * Input: the column and row of this cell
		 */
		Cell(int col, int row) {
			this.column = col;
			this.row = row;
			value = 0;
			isMine = false;
			leftClicked = false;
			rightClicked = false;
		}
		
		// Getters	
		public int getValue() {
			return value;
		}
		
		public boolean getIsMine() {
			return isMine; 
		}
		
		public int getColumn() {
			return column;
		}
		
		public int getRow() {
			return row;
		}
		
		public boolean getLeftClicked() {
			return leftClicked;
		}
		
		public boolean getRightClicked() {
			return rightClicked;
		}
		
		// Setters
		public void setValue(int Value) {
			value = Value;
		}
		
		public void setIsMine(boolean IsMine) {
			isMine = IsMine;
		}
		
		public void setLeftClicked(boolean clicked) {
			leftClicked = clicked;
		}
		
		public void setRightClicked(boolean clicked) {
			rightClicked = clicked;
		}
		
}
