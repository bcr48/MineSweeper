package minesweeper;
import javax.swing.*;

/**
 * This MineSweeper class, along with the MineSweeperController, MineSweeperView, 
 * MineSweeperModel and Cell classes implement a mine sweeper game.
 * 
 * This implementation is based on the MVC design pattern.
 * 
 * TODO: implement some indication of when the user has won the game (i.e., has flagged all the mine buttons 
 * and none of the non-mine buttons)
 * TODO: implement little pictures for the mines and flags - not just red and blue
 * TODO: implement counter displaying (numMines - numFlags)
 * 
 * @author Benjamin Revard
 */
public class MineSweeper {
	
	private static int length = 20; // The length of one side of the mine field (> 0)
	private static int mines = 50; // The number of mines in the mine field (>= 0)
	
	/**
	 * Create the model, view and controller objects,
	 * and launch the application
	 */
	public static void main(String[] args) {
		
		// initialize the model, view and controller
		Model model = new Model(length, mines);
		View view = new View(length, mines);
		Controller controller = new Controller(model, view);
		
		// register the controller as a listener
		view.registerListener(controller);
		
		// start it
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setSize(500, 500);
		view.setVisible(true);
	}
	
}
