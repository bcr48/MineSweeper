package minesweeper;

/**
 * class specification
 * @author benjaminrevard
 *
 */

public class Timer implements Runnable {
	
	private Model model; // The model
	private View view; // The view
	private boolean gameStopped; // Flag to indicate if the reset button has been clicked
	
	/**
	 * Constructor
	 */
	public Timer(Model model, View view) {
		this.model = model;
		this.view = view;
		gameStopped = false;
	}

	/**
	 * Method to increment the timer. Should start the timer at 0 and add 1 every second.
	 * Needs to be able to tell if the reset button has been clicked, even during a sleep period.
	 * Not sure how to solve this. Maybe have two threads, one for sleeping and incrementing the 
	 * timer, and one to listen for when the reset button is clicked.
	 */
	// This doesn't work because of the sleep period I think
	public void run() {
		int time = 0;
		gameStopped = false;
		while(!gameStopped) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			time++;
			view.setTimer(time);
		}
		return;
	}
	
	// Setter
	public void setGameStopped(boolean isStopped) {
		gameStopped = isStopped;
	}
}
