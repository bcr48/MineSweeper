package minesweeper;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

/**
 * This is the controller of the MVC implementation of the mine sweeper game.
 * It interprets events from the GUI and updates instances of MineSweeperModel 
 * and MineSweeperView as needed.
 * 
 * @author Benjamin Revard
 */
public class Controller implements MouseListener {
	
	private Model model; // The model of this MVC implementation of mine sweeper
	private View view; // The view of this MVC implementation of mine sweeper
	private Timer timer;
	
	/**
	 * Constructor
	 */
	public Controller(Model model, View view, Timer timer) {
		this.model = model;
		this.view = view;
		this.timer = timer;
	}
	
	/**
	 * Determines what to do with a mouse event generated by the GUI.
	 * Input: a mouse event
	 */
	public void mouseClicked(MouseEvent e) {
		
		// Get the button that was clicked
		JButton btn = (JButton) e.getSource();
		
		// Determine if the reset button or a field button was clicked, and take appropriate action
		String name = btn.getText();
		if (name == "Reset") {
			resetClicked();
		}
		else {
			fieldClicked(e, btn);
		}	
	}
	
	/**
	 * Processes a click on a mine field button and takes appropriate action.
	 * Input: a mouse event and a button
	 */
	private void fieldClicked(MouseEvent e, JButton btn) {
		
		// Get the type of mouse click
		boolean isLeftClick = isLeftClick(e);
		boolean isRightClick = isRightClick(e);
		boolean isDoubleClick = isDoubleClick(e);
					
		// Get all the information for the mine field button
		int col = (int) btn.getClientProperty("column");
		int row = (int) btn.getClientProperty("row");
		int val = model.getCells()[col][row].getValue();
		boolean isMine = model.getCells()[col][row].getIsMine();
		boolean leftClicked = model.getCells()[col][row].getLeftClicked();
		boolean rightClicked = model.getCells()[col][row].getRightClicked();
		int numFlagNeighbors = model.getNumFlaggedNeighbors(col, row); 
					
		// Take the appropriate action
		if (isMine && isLeftClick && !rightClicked) {
			mineClicked();
			timer.setGameStopped(true);
		}
		else if (!leftClicked && !rightClicked && isLeftClick) {
			model.getCells()[col][row].setLeftClicked(true);
			view.showValue(col, row, val);
			if (val == 0) { 
				zeroClicked(col, row);
			}
			if (model.hasWon()) {
				view.showVictoryMsg();
				timer.setGameStopped(true);
			}
		}
		else if (!rightClicked && !leftClicked && isRightClick) {
			model.getCells()[col][row].setRightClicked(true);
			model.incrementFlags(1);
			view.placeFlag(col, row);
			view.setCounter(model.getMines() - model.getFlags());
			if (model.hasWon()) {
				view.showVictoryMsg();
				timer.setGameStopped(true);
			}  
		}
		else if (rightClicked && isRightClick) { 
			model.getCells()[col][row].setRightClicked(false);
			model.incrementFlags(-1);
			view.setDefaultBackground(col, row);
			view.setCounter(model.getMines() - model.getFlags());
		}
		else if (leftClicked && isDoubleClick && numFlagNeighbors == val) {
			if (correctFlags(col, row)) {
				zeroClicked(col, row);
				if (model.hasWon()) {
					view.showVictoryMsg();
					timer.setGameStopped(true);
				}
			}
			else {
				mineClicked();
			}
		}
	}
	
	/**
	 * Determines if the mouse click was a left click.
	 * Input: mouse event
	 */
	private boolean isLeftClick(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Determines if the mouse click was a right click.
	 * Input: mouse event
	 */
	private boolean isRightClick(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Determines if the mouse click was a double click.
	 * Input: mouse event
	 */
	private boolean isDoubleClick(MouseEvent e) {
		if (e.getClickCount() == 2) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Displays the values of all the neighboring buttons that aren't flagged. If one of the neighboring 
	 * buttons has a value of zero, the method calls itself on that button.
	 * Input: the column and row of the button to investigate
	 */
	private void zeroClicked(int col, int row) {
		
		// Get all the neighbors
		ArrayList<Cell> neighbors = model.getNeighbors(col, row);
		
		// Check each neighbor that hasn't been clicked
    	for (Cell neighbor : neighbors) {
    		if (!neighbor.getLeftClicked() && !neighbor.getRightClicked()) {
    			int ncol = neighbor.getColumn();
    			int nrow = neighbor.getRow();
    			int nval = neighbor.getValue();
    			neighbor.setLeftClicked(true);
    			view.showValue(ncol, nrow, nval);
    			if (nval == 0) {
    				zeroClicked(ncol, nrow);
    			}
    		}
    	}
	}
	
	/**
	 * Shows all the buttons.
	 */
	private void mineClicked() {
		for (int i = 0; i < model.getLength(); i++) {
			for (int j = 0; j < model.getLength(); j++) {
				Cell cell = model.getCells()[j][i];
				if (!cell.getIsMine()) {
					view.showValue(cell.getColumn(), cell.getRow(), cell.getValue());
				}
				else if (cell.getIsMine()) {
					view.showMine(cell.getColumn(), cell.getRow());
				}
		    	cell.setLeftClicked(true);
			}
		}
	}
	
	/**
	 * Determines if the flags on the neighboring cells are placed correctly.
	 * Precondition: the number of flags on the neighboring cells is equal to the
	 * number of neighboring cells that are mines.
	 * Input: the column and row of the cell to investigate
	 */
	private boolean correctFlags(int col, int row) {
		
		// Get all the neighbors
		ArrayList<Cell> neighbors = model.getNeighbors(col, row);
				
		// Check each neighbor that hasn't been clicked
		for (Cell neighbor : neighbors) {
		    if (!neighbor.getLeftClicked() && !neighbor.getRightClicked()) {
		    	int ncol = neighbor.getColumn();
		    	int nrow = neighbor.getRow();
		    	if (model.getCells()[ncol][nrow].getIsMine()) {
		    		return false;
		    	}
		    }
		}
		return true;
	}
	
	/**
	 * Resets the game by generating new model and setting all the mine field buttons to the
	 * default background color and empty text. Also restarts the timer
	 */
	private void resetClicked() {
		int length = model.getLength();
		int mines = model.getMines();
		model = new Model(length, mines);
		view.reset();
		// The lines below don't work right.
	//	timer.setGameStopped(true);
	//	timer = new Timer(model, view);
	//	timer.run();
	}
	
	// Methods from interface MouseListener that must be implemented
		public void mousePressed(MouseEvent e) {};
		public void mouseReleased(MouseEvent e) {};
		public void mouseEntered(MouseEvent e) {};
		public void mouseExited(MouseEvent e) {};
	        
}

