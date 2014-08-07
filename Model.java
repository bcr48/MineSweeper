package minesweeper;
import java.util.ArrayList;

/**
 * This is the model of the MVC implementation of the mine sweeper game. 
 * It constructs a representation of the mine field.
 * 
 * @author Benjamin Revard
 */
public class Model {
	
	private int length; // The length of one side of the mine field (> 0)
	private int mines; // The number of mines in the mine field (>= 0)
	private int flags; // The number of flags placed by the player (>= 0)
	private Cell[][] cells; // The cells that represent the mine field
	
	/**
	 * Constructor: creates the mine field.
	 * Input: the length of one side of the mine field, the number of mines in the mine field
	 */
	public Model(int length, int mines) {
		
		// Initialize the class variables
		this.length = length;
		this.mines = mines;
		flags = 0;
		cells = new Cell[length][length];
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				cells[i][j] = new Cell(i, j);
			}
		}
				
		// Randomly place the mines
		int count = 0;
		while (count < mines) {
			int randCol = getRandomInt(0, length);
			int randRow = getRandomInt(0, length);
			if (cells[randCol][randRow].getIsMine()) {
				continue;
			}
			else {
				cells[randCol][randRow].setIsMine(true);
				cells[randCol][randRow].setValue(9);
				count++;
			}
		} 
		
		// Update the values of the non-mine cells.
		for (int a = 0; a < length; a++) {
			for (int b = 0; b < length; b++) {
				if (!cells[a][b].getIsMine()) {
					cells[a][b].setValue(getNumMineNeighbors(a, b));
				}
			}
		}
	}
	
	/**
	 * Returns a random integer between a and b.
	 * Input: integers a and b
	 */
	private int getRandomInt(int a, int b) {
		return a + (int)(Math.random()*((b - a))); 
	}
	
	/**
	 * Returns an ArrayList of neighboring mine field cells.
	 * Input: the column and row of the cell to investigate
	 * TODO: find a better way to do this
	 */
	public ArrayList<Cell> getNeighbors(int col, int row) {
		
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		
		if (col == 0 && row == 0) {
			neighbors.add(cells[col][row + 1]);
			neighbors.add(cells[col + 1][row + 1]);
			neighbors.add(cells[col + 1][row]);
		}
		else if (col > 0 && col < length - 1 && row == 0) {
			neighbors.add(cells[col - 1][row]);
			neighbors.add(cells[col - 1][row + 1]);
			neighbors.add(cells[col][row + 1]);
			neighbors.add(cells[col + 1][row + 1]);
			neighbors.add(cells[col + 1][row]);
		}
		else if (col == length - 1 && row == 0) {
			neighbors.add(cells[col - 1][row]);
			neighbors.add(cells[col - 1][row + 1]);
			neighbors.add(cells[col][row + 1]);
		}
		else if (col == length -1 && row > 0 && row < length -1) {
			neighbors.add(cells[col][row -1]);
			neighbors.add(cells[col - 1][row - 1]);
			neighbors.add(cells[col - 1][row]);
			neighbors.add(cells[col - 1][row + 1]);
			neighbors.add(cells[col][row + 1]);
		}
		else if (col == length - 1 && row == length -1) {
			neighbors.add(cells[col][row - 1]);
			neighbors.add(cells[col - 1][row - 1]);
			neighbors.add(cells[col - 1][row]);
		}
		else if (col > 0 && col < length - 1 && row == length - 1) {
			neighbors.add(cells[col + 1][row]);
			neighbors.add(cells[col + 1][row - 1]);
			neighbors.add(cells[col][row - 1]);
			neighbors.add(cells[col - 1][row - 1]);
			neighbors.add(cells[col - 1][row]);
		}
		else if (col ==  0 && row == length - 1) {
			neighbors.add(cells[col + 1][row]);
			neighbors.add(cells[col + 1][row - 1]);
			neighbors.add(cells[col][row - 1]);
		}
		else if (col == 0 && row < length - 1 && row > 0) {
			neighbors.add(cells[col][row + 1]);
			neighbors.add(cells[col + 1][row + 1]);
			neighbors.add(cells[col + 1][row]);
			neighbors.add(cells[col + 1][row - 1]);
			neighbors.add(cells[col][row - 1]);
		}
		else {
			neighbors.add(cells[col - 1][row - 1]);
			neighbors.add(cells[col][row - 1]);
			neighbors.add(cells[col + 1][row - 1]);
			neighbors.add(cells[col + 1][row]);
			neighbors.add(cells[col + 1][row + 1]);
			neighbors.add(cells[col][row + 1]);
			neighbors.add(cells[col - 1][row + 1]);
			neighbors.add(cells[col - 1][row]);
		}
		return neighbors;
	}
	
	/**
	 * Returns the number of neighboring cells that are mines.
	 * Input: the column and row of the cell to investigate
	 */
	private int getNumMineNeighbors(int col, int row) {
		
		int numMines = 0;
		ArrayList<Cell> neighbors = getNeighbors(col, row);
		
		for (Cell cell : neighbors) {
			if (cell.getIsMine()) {
				numMines++;
			}
		}
		return numMines;
	}
	
	/**
	 * Returns the number of neighboring cells that have been right clicked.
	 * Input: the column and row of the cell to investigate
	 * @return
	 */
	public int getNumFlaggedNeighbors(int col, int row) {
		int numFlags = 0;
		ArrayList<Cell> neighbors = getNeighbors(col, row);

		for (Cell cell : neighbors) {
			if (cell.getRightClicked()) {
				numFlags++;
			}
		}
		return numFlags;
	}
	
	/**
	 * Increments the number of flags.
	 * Input: the amount by which to increment the number of flags
	 * @return
	 */
	public void incrementFlags(int incr) {
		flags = flags + incr;
	}
	
	/**
	 * Checks if the player has won the game.
	 */
	public boolean hasWon() {
		int length = getLength();
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				Cell cell = getCells()[i][j];
				if ( !( (cell.getIsMine() && cell.getRightClicked()) || (!cell.getIsMine() && cell.getLeftClicked()) ) ) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Checks if the player has lost the game.
	 */
	public boolean hasLost() {
		int length = getLength();
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				Cell cell = getCells()[i][j];
				if ( cell.getIsMine() && cell.getLeftClicked() ) {
					return true;
				}
			}
		}
		return false;
	}
	
	// Getters
	public Cell[][] getCells() {
		return cells;
	}
	
	public int getLength() {
		return length;
	}
	
	public int getMines() {
		return mines;
	}
	
	public int getFlags() {
		return flags;
	}
	
}
