package minesweeper;
import java.util.*;

/**
 * An instance represents a task for the timer, which is part of the controller
 * 
 * @author Benjamin Revard
 */
public class Task extends TimerTask {
	
	private View view; // The view of this MVC implementation of mine sweeper
	private int time; // How long the game has lasted (in seconds) (>= 0)
	
	/**
	 * Constructor
	 * Input: the view
	 */
	public Task(View view) {
		this.view = view;
		time = 0;
	}
	
	/**
	 * Displays and increments the time.
	 */
	public void run() {
		view.setTimer(time);
		time++;
	}
}
