package com.davededavelukeaz.wumpus.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.davededavelukeaz.wumpus.model.GameMap;

/**
 * This class provides a control view.
 * There are buttons to interact with the game.
 * There are boxes containing game information such as warnings and item possessed.
 * @author DaveDeDave
 * @author LukeAz
 * @see Observer
 */
public class ControlView extends JPanel implements Observer {
	
	private static final long serialVersionUID = 1L;
	private CustomButton startGameBtn;
	private CustomButton debugViewBtn;
	private CustomButton optionBtn;
	private CustomTextBox warning;
	private CustomTextBox item;
	private CustomTextBox timer;
	private int countdown;
	
	/**
	 * Initialize graphics.
	 */
	public ControlView() {
		super(new GridBagLayout());
		
		this.startGameBtn = new CustomButton("Play");
		this.debugViewBtn = new CustomButton("Debug View");
		this.optionBtn = new CustomButton("Options");
		this.warning = new CustomTextBox();
		this.item = new CustomTextBox();
		this.timer = new CustomTextBox();
		this.timer.setForeground(Color.red);
		this.timer.setFont(new Font("Monospaced", Font.BOLD, 50));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.fill = GridBagConstraints.BOTH;
		this.add(this.startGameBtn, gbc);
		gbc.gridx = 0;
	    gbc.gridy = 1;
	    gbc.fill = GridBagConstraints.BOTH;
		this.add(this.debugViewBtn, gbc);
		gbc.gridx = 0;
	    gbc.gridy = 2;
	    gbc.fill = GridBagConstraints.BOTH;
	    this.add(this.optionBtn, gbc);
	    gbc.weightx = 2;
	    gbc.gridx = 1;
	    gbc.gridy = 0;
	    gbc.gridheight = 3;
		this.add(this.warning, gbc);
		gbc.gridx = 2;
	    gbc.gridy = 0;
		this.add(this.item, gbc);
		gbc.gridx = 3;
	    gbc.gridy = 0;
		this.add(this.timer, gbc);
		
		this.setVisible(true);
	}
	
	/**
	 * Returns the button to start the game.
	 * @return returns the button to start the game
	 */
	public JButton getStartGameBtn() {
		return this.startGameBtn;
	}
	
	/**
	 * Returns the button to activate/deactivate the debug view.
	 * @return returns the button to activate/deactivate the debug view
	 */
	public JButton getDebugViewBtn() {
		return this.debugViewBtn;
	}
	
	/**
	 * Returns the button to display the options.
	 * @return Returns the button to display the options
	 */
	public JButton getOptionBtn() {
		return this.optionBtn;
	}
	
	/**
	 * Sets the countdown seconds to be displayed.
	 * @param countdown the countdown seconds to be displayed
	 */
	public void setTimerSeconds(Integer countdown) {
		this.countdown = countdown;
		this.timer.setText(Integer.toString(this.countdown));
	}
	
	/**
	 * Decreases by one second the countdown to be displayed
	 */
	public void decreaseTimer() {
		this.setTimerSeconds(this.countdown - 1);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		String warnings = "";
		
		for(String warning : ((GameMap) o).getWarnings())
			warnings += warning;
		this.warning.setText("");
		
		if(((GameMap) o).getIsPlaying()) {
			if(warnings != "")
				this.warning.setText(warnings);
			else
				this.warning.setText("Nothing is happening.");
			if(((GameMap) o).getPlayer().getArrow() == 1)
				this.item.setText(((GameMap) o).getPlayer().getArrow() + " arrow\n");
			else
				this.item.setText(((GameMap) o).getPlayer().getArrow() + " arrows\n");
			this.item.setText(this.item.getText() + ((GameMap) o).getPlayer().getGold() + " gold");
		}
		else
			JOptionPane.showMessageDialog(null, warnings, "Hunt The Wumpus", JOptionPane.INFORMATION_MESSAGE, null);
		
	}
	
	/**
	 * Button with custom graphics.
	 * @author DaveDeDave
	 * @author LukeAz
	 * @see JButton
	 */
	private class CustomButton extends JButton {

		private static final long serialVersionUID = 1L;

		/**
		 * Set up customizations.
		 * @param name text to be displayed on the button
		 */
		public CustomButton(String name) {
			super(name);
			this.setBackground(Color.DARK_GRAY);
			this.setForeground(Color.white);
		}
	}
	
	/**
	 * TextArea with custom graphics.
	 * @author DaveDeDave
	 * @author LukeAz
	 * @see JTextArea
	 */
	private class CustomTextBox extends JTextArea {

		private static final long serialVersionUID = 1L;

		/**
		 * Set up customizations.
		 */
		public CustomTextBox() {
			super();
			this.setBackground(Color.DARK_GRAY);
			this.setForeground(Color.white);
			this.setFont(new Font("Monospaced", Font.BOLD, 15));
			this.setEditable(false);
			this.setMargin(new Insets(0, 100, 0, 0));
		}
	}
}
