package minesweeper;

import java.awt.*;
import javax.swing.*;

/**
 * This is the view of the MVC implementation of the mine sweeper game.
 * It creates the panels and components of the window.
 * 
 * @author Benjamin Revard
 */
public class View extends JFrame {
	
	private int length; // The length of one side of the mine field (> 0)
	private int mines; // The number of mines in the mine field (> 0)
	private JPanel topPanel; // JPanel to hold the reset button
	private JLabel mineCounter; // Display to hold the number of mines minus the number of flags 
	private JLabel timer; // Display to show how long the game has lasted
	private JPanel buttonsPanel;  // JPanel to hold all the buttons
	private JButton[][] buttons; // 2D array to hold all the mine field buttons
	
	/**
	 * Constructor: creates the panels and buttons for the JFrame.
	 * Input: the length of one side of the mine field, and the number of mines
	 */
	public View(int length, int mines) {
		super("Mine Sweeper");
		this.length = length;
		this.mines = mines;
		mineCounter = new JLabel("  " + Integer.toString(mines) + "  ");
		timer = new JLabel("  0  ");
				
		// Create the panel to hold the mine counter, the reset button, and the timer
		topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new BorderLayout(150, 1));
		topPanel.add(mineCounter, BorderLayout.LINE_START);
		topPanel.add(new JButton("Reset"), BorderLayout.CENTER);
		topPanel.add(timer, BorderLayout.LINE_END);
		
		// Create all the mine field buttons
		buttonsPanel = new JPanel();
		add(buttonsPanel, BorderLayout.CENTER);
		buttonsPanel.setLayout(new GridLayout(length, length, 0, 0));
		buttons = new JButton[length][length];
		
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				buttons[j][i] = new JButton();
				buttons[j][i].putClientProperty("column", j);
				buttons[j][i].putClientProperty("row", i);
				buttonsPanel.add(buttons[j][i]);
			}
		}
	}
	
	/**
	 * Registers the controller as the listener to the buttons.
	 */
	public void registerListener(Controller controller) {
		
		// register listener to the reset button
		Component reset = topPanel.getComponent(1); 
		AbstractButton resetButton = (AbstractButton) reset;
		resetButton.addMouseListener(controller);
		
		// register listener to the mine field buttons
		Component[] components = buttonsPanel.getComponents();
		for (Component component : components) {
            if (component instanceof AbstractButton) {
                    AbstractButton button = (AbstractButton) component;
                    button.addMouseListener(controller);
            }
		}
	}
	
	/**
	 * Sets this button's background color to the default.
	 * Input: the column and row of the button
	 */
	public void setDefaultBackground(int col, int row) {
		buttons[col][row].setBackground(null);
		buttons[col][row].setBorderPainted(true);
		this.setVisible(true);
	}
	
	/**
	 * Sets this button's background color to the default and this button's text to its value.
	 * Input: the column, row, and value of the button
	 */
	public void showValue(int col, int row, int value) {
		
		setDefaultBackground(col, row);
		String val = Integer.toString(value);
		buttons[col][row].setText(val);
		
		if (value == 0) {
			buttons[col][row].setText("");
			buttons[col][row].setBackground(Color.LIGHT_GRAY);
			buttons[col][row].setOpaque(true);
			buttons[col][row].setBorderPainted(false);
		}
		else if (value == 1) {
			buttons[col][row].setForeground(Color.BLUE);
		}
		else if (value == 2) {
			buttons[col][row].setForeground(Color.GREEN);
		}
		else if (value == 3) {
			buttons[col][row].setForeground(Color.RED);
		}
		else if (value == 4) {
			buttons[col][row].setForeground(Color.MAGENTA);
		}
		else if (value == 5) {
			buttons[col][row].setForeground(Color.ORANGE);
		}
		else if (value == 6) {
			buttons[col][row].setForeground(Color.PINK);
		}
		else if (value == 7) {
			buttons[col][row].setForeground(Color.CYAN);
		}
		else if (value == 8) {
			buttons[col][row].setForeground(Color.YELLOW);
		}
		this.setVisible(true);
	}
	
	/**
	 * Adds a flag to this button by turning its background color blue.
	 * Input: the column and row of the button
	 */
	public void placeFlag(int col, int row) {
		paint(col, row, Color.BLUE);
	}
	
	/**
	 * Sets the value of the counter, which should be showing (number of mines) - (the number of flags).
	 * Input: the number to set the counter to
	 */
	public void setCounter(int counter) {
		mineCounter.setText("  " + Integer.toString(counter) + "  ");
	}
	
	
	/**
	 * Sets the value of the timer, which should be showing the number of seconds since the first click.
	 * Input: the number to set the timer to
	 * @param time
	 */
	public void setTimer(int time) {
		timer.setText("  " + time + "  ");
	}
	
	/**
	 * Shows that this button is a mine by turning its background color red.
	 * Input: the column and row of the button
	 */
	public void showMine(int col, int row) {
		paint(col, row, Color.RED);
	}
	
	/**
	 * Sets all the buttons in the mine field to the default background color and empty text, and
	 * sets the mine counter label to the total number of mines and the timer laber to 0.
	 */
	public void reset() {
		mineCounter.setText("  " + Integer.toString(mines) + "  ");
		timer.setText("  0  ");
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				buttons[j][i].setText("");
				setDefaultBackground(j, i);
			}
		}
	}
	
	/**
	 * Paints all the tiles black to indicate the player has won. Prints a victory message if there's room.
	 * @return
	 */
	public void showVictoryMsg() {
		
		// paint all the cells black
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				buttons[i][j].setText("");
				paint(i, j, Color.BLACK);
			}
		}
		
		// paint a victory message
		if (length > 18) {
			// the Y
			paint(2, 4, Color.GREEN);
			paint(1, 2, Color.GREEN);
			paint(1, 3, Color.GREEN);
			paint(4, 4, Color.GREEN);
			paint(5, 2, Color.GREEN);
			paint(5, 3, Color.GREEN);
			paint(3, 5, Color.GREEN);
			paint(3, 6, Color.GREEN);
			paint(3, 7, Color.GREEN);

			// the O
			paint(7, 3, Color.GREEN);
			paint(7, 4, Color.GREEN);
			paint(7, 5, Color.GREEN);
			paint(7, 6, Color.GREEN);
			paint(11, 3, Color.GREEN);
			paint(11, 4, Color.GREEN);
			paint(11, 5, Color.GREEN);
			paint(11, 6, Color.GREEN);
			paint(8, 2, Color.GREEN);
			paint(9, 2, Color.GREEN);
			paint(10, 2, Color.GREEN);
			paint(8, 7, Color.GREEN);
			paint(9, 7, Color.GREEN);
			paint(10, 7, Color.GREEN);
		
			// the U
			paint(13, 2, Color.GREEN);
			paint(13, 3, Color.GREEN);
			paint(13, 4, Color.GREEN);
			paint(13, 5, Color.GREEN);
			paint(13, 6, Color.GREEN);
			paint(14, 7, Color.GREEN);
			paint(15, 7, Color.GREEN);
			paint(16, 7, Color.GREEN);
			paint(17, 6, Color.GREEN);
			paint(17, 5, Color.GREEN);
			paint(17, 4, Color.GREEN);
			paint(17, 3, Color.GREEN);
			paint(17, 2, Color.GREEN);
		
			// the W
			paint(1, 11, Color.GREEN);
			paint(1, 12, Color.GREEN);
			paint(1, 13, Color.GREEN);
			paint(1, 14, Color.GREEN);
			paint(1, 15, Color.GREEN);
			paint(2, 16, Color.GREEN);
			paint(3, 15, Color.GREEN);
			paint(4, 16, Color.GREEN);
			paint(5, 15, Color.GREEN);
			paint(5, 14, Color.GREEN);
			paint(5, 13, Color.GREEN);
			paint(5, 12, Color.GREEN);
			paint(5, 11, Color.GREEN);
		
			// the second O
			paint(7, 12, Color.GREEN);
			paint(7, 13, Color.GREEN);
			paint(7, 14, Color.GREEN);
			paint(7, 15, Color.GREEN);
			paint(11, 12, Color.GREEN);
			paint(11, 13, Color.GREEN);
			paint(11, 14, Color.GREEN);
			paint(11, 15, Color.GREEN);
			paint(8, 11, Color.GREEN);
			paint(9, 11, Color.GREEN);
			paint(10, 11, Color.GREEN);
			paint(8, 16, Color.GREEN);
			paint(9, 16, Color.GREEN);
			paint(10, 16, Color.GREEN);

			// the N
			paint(13, 11, Color.GREEN);
			paint(13, 12, Color.GREEN);
			paint(13, 13, Color.GREEN);
			paint(13, 14, Color.GREEN);
			paint(13, 15, Color.GREEN);
			paint(13, 16, Color.GREEN);
			paint(17, 11, Color.GREEN);
			paint(17, 12, Color.GREEN);
			paint(17, 13, Color.GREEN);
			paint(17, 14, Color.GREEN);
			paint(17, 15, Color.GREEN);
			paint(17, 16, Color.GREEN);
			paint(14, 12, Color.GREEN);
			paint(15, 13, Color.GREEN);
			paint(15, 14, Color.GREEN);
			paint(16, 15, Color.GREEN);
		}
	}
	
	/**
	 * Paints a mine field button.
	 * Input: column and row of the button to paint, and the color to paint it
	 */
	private void paint(int col, int row, Color color) {
		buttons[col][row].setBackground(color);
		buttons[col][row].setOpaque(true);
		buttons[col][row].setBorderPainted(false);
		this.setVisible(true);
	}
	
}
