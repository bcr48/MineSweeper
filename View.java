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
	 * Creates the panels and buttons for the JFrame.
	 */
	public View(int length, int mines) {
		super("Mine Sweeper");
		this.length = length;
		this.mines = mines;
		mineCounter = new JLabel(Integer.toString(mines));
		timer = new JLabel("timer");
				
		// Create the panel to hold the mine counter, the reset button, and the timer
		topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new GridLayout(1, 3, 0, 0));
		topPanel.add(mineCounter);
		topPanel.add(new JButton("Reset"));
		topPanel.add(timer);
		
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
		
		Component reset = topPanel.getComponent(1); 
		AbstractButton resetButton = (AbstractButton) reset;
		resetButton.addMouseListener(controller);
		
		Component[] components = buttonsPanel.getComponents();
		for (Component component : components) {
            if (component instanceof AbstractButton) {
                    AbstractButton button = (AbstractButton) component;
                    button.addMouseListener(controller);
            }
		}
	}
	
	/**
	 * Sets this button's background color to the default, and adjusts the flag count by the amount indicated
	 * Input: the column and row of the button, and the amount to increment the flag count
	 */
	public void setDefaultBackground(int col, int row) {
		buttons[col][row].setBackground(null);
		buttons[col][row].setBorderPainted(true);
		this.setVisible(true);
	}
	
	/**
	 * Sets this button's background color to the default and this button's text to its value.
	 * Input: the column, row, and value of the button
	 * 
	 * TODO: make the background color light gray for non-zero values too
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
	 * 
	 * TODO: make this show a little flag or something, not just turn blue
	 */
	public void placeFlag(int col, int row) {
		paintTile(col, row, Color.BLUE);
	}
	
	/**
	 * Sets the value of the counter, which should be showing (number of mines) - (the number of flags)
	 * Input: the number to set the counter to
	 * @param incr
	 */
	public void setCounter(int counter) {
		mineCounter.setText(Integer.toString(counter));
	}
	
	/**
	 * Shows that this button is mine by turning its background color red.
	 * Input: the column and row of the button
	 * 
	 * TODO: make this show a little mine or something, not just turn red
	 */
	public void showMine(int col, int row) {
		paintTile(col, row, Color.RED);
	}
	
	/**
	 * Sets all the buttons in the mine field to the default background color and empty text. 
	 * Sets the mine counter label to the total number of mines and sets flags to zero.
	 */
	public void reset() {
		mineCounter.setText(Integer.toString(mines));
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				buttons[j][i].setText("");
				setDefaultBackground(j, i);
			}
		}	
	}
	
	/**
	 * Paints all the tiles black to indicate the player has won. Prints a victory message is there's room.
	 * @return
	 */
	public void showVictoryMsg() {
		// paint all the cells black
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				buttons[i][j].setText("");
				paintTile(i, j, Color.BLACK);
			}
		}
		// paint a victory message
		if (length > 18) {
			// the Y
			paintTile(2, 4, Color.GREEN);
			paintTile(1, 2, Color.GREEN);
			paintTile(1, 3, Color.GREEN);
			paintTile(4, 4, Color.GREEN);
			paintTile(5, 2, Color.GREEN);
			paintTile(5, 3, Color.GREEN);
			paintTile(3, 5, Color.GREEN);
			paintTile(3, 6, Color.GREEN);
			paintTile(3, 7, Color.GREEN);

			// the O
			paintTile(7, 3, Color.GREEN);
			paintTile(7, 4, Color.GREEN);
			paintTile(7, 5, Color.GREEN);
			paintTile(7, 6, Color.GREEN);
			paintTile(11, 3, Color.GREEN);
			paintTile(11, 4, Color.GREEN);
			paintTile(11, 5, Color.GREEN);
			paintTile(11, 6, Color.GREEN);
			paintTile(8, 2, Color.GREEN);
			paintTile(9, 2, Color.GREEN);
			paintTile(10, 2, Color.GREEN);
			paintTile(8, 7, Color.GREEN);
			paintTile(9, 7, Color.GREEN);
			paintTile(10, 7, Color.GREEN);
		
			// the U
			paintTile(13, 2, Color.GREEN);
			paintTile(13, 3, Color.GREEN);
			paintTile(13, 4, Color.GREEN);
			paintTile(13, 5, Color.GREEN);
			paintTile(13, 6, Color.GREEN);
			paintTile(14, 7, Color.GREEN);
			paintTile(15, 7, Color.GREEN);
			paintTile(16, 7, Color.GREEN);
			paintTile(17, 6, Color.GREEN);
			paintTile(17, 5, Color.GREEN);
			paintTile(17, 4, Color.GREEN);
			paintTile(17, 3, Color.GREEN);
			paintTile(17, 2, Color.GREEN);
		
			// the W
			paintTile(1, 11, Color.GREEN);
			paintTile(1, 12, Color.GREEN);
			paintTile(1, 13, Color.GREEN);
			paintTile(1, 14, Color.GREEN);
			paintTile(1, 15, Color.GREEN);
			paintTile(2, 16, Color.GREEN);
			paintTile(3, 15, Color.GREEN);
			paintTile(4, 16, Color.GREEN);
			paintTile(5, 15, Color.GREEN);
			paintTile(5, 14, Color.GREEN);
			paintTile(5, 13, Color.GREEN);
			paintTile(5, 12, Color.GREEN);
			paintTile(5, 11, Color.GREEN);
		
			// the second O
			paintTile(7, 12, Color.GREEN);
			paintTile(7, 13, Color.GREEN);
			paintTile(7, 14, Color.GREEN);
			paintTile(7, 15, Color.GREEN);
			paintTile(11, 12, Color.GREEN);
			paintTile(11, 13, Color.GREEN);
			paintTile(11, 14, Color.GREEN);
			paintTile(11, 15, Color.GREEN);
			paintTile(8, 11, Color.GREEN);
			paintTile(9, 11, Color.GREEN);
			paintTile(10, 11, Color.GREEN);
			paintTile(8, 16, Color.GREEN);
			paintTile(9, 16, Color.GREEN);
			paintTile(10, 16, Color.GREEN);

			// the N
			paintTile(13, 11, Color.GREEN);
			paintTile(13, 12, Color.GREEN);
			paintTile(13, 13, Color.GREEN);
			paintTile(13, 14, Color.GREEN);
			paintTile(13, 15, Color.GREEN);
			paintTile(13, 16, Color.GREEN);
			paintTile(17, 11, Color.GREEN);
			paintTile(17, 12, Color.GREEN);
			paintTile(17, 13, Color.GREEN);
			paintTile(17, 14, Color.GREEN);
			paintTile(17, 15, Color.GREEN);
			paintTile(17, 16, Color.GREEN);
			paintTile(14, 12, Color.GREEN);
			paintTile(15, 13, Color.GREEN);
			paintTile(15, 14, Color.GREEN);
			paintTile(16, 15, Color.GREEN);
		}
	}
	
	/**
	 * Paints a tile
	 * Input: column and row of the tile to paint, and the color to paint it. The input color should have 
	 * the format Color.GREEN, for example
	 */
	public void paintTile(int col, int row, Color color) {
		buttons[col][row].setBackground(color);
		buttons[col][row].setOpaque(true);
		buttons[col][row].setBorderPainted(false);
		this.setVisible(true);
	}
	
}
