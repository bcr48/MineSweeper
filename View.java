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
	private JLabel mineCounter; // Display to hold the number of mines left 
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
				
		// Create the panel to hold the mine counter, the reset button, and the timer
		topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new GridLayout(1, 3, 0, 0));
		topPanel.add(new JLabel(Integer.toString(mines)));
		topPanel.add(new JButton("Reset"));
		topPanel.add(new JLabel("0"));
		
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
		buttons[col][row].setBackground(Color.BLUE);
		buttons[col][row].setOpaque(true);
		buttons[col][row].setBorderPainted(false);
		this.setVisible(true);
	}
	
	/**
	 * Shows that this button is mine by turning its background color red.
	 * Input: the column and row of the button
	 * 
	 * TODO: make this show a little mine or something, not just turn red
	 */
	public void showMine(int col, int row) {
		buttons[col][row].setBackground(Color.RED);
		buttons[col][row].setOpaque(true);
		buttons[col][row].setBorderPainted(false);
		this.setVisible(true);
	}
	
	/**
	 * Sets all the buttons in the mine field to the default background color and empty text. 
	 */
	public void reset() {
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				buttons[j][i].setText("");
				setDefaultBackground(j, i);
			}
		}
	}
	
}
