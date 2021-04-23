package com.davededavelukeaz.wumpus.controller;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import com.davededavelukeaz.wumpus.model.Direction;
import com.davededavelukeaz.wumpus.model.GameMap;
import com.davededavelukeaz.wumpus.view.ControlView;
import com.davededavelukeaz.wumpus.view.DebugView;
import com.davededavelukeaz.wumpus.view.MapView;

/**
 * Class that handles the logic of the game.
 * It interfaces with the user and allows them to interact with the game.
 * @author DaveDeDave
 * @author LukeAz
 * @see ActionListener
 * @see FocusListener
 * @see KeyListener
 */
public class GameController implements ActionListener, KeyListener {

	private JFrame gameFrame;
	private MapView mapView;
	private DebugView debugView;
	private ControlView controlView;
	private GameMap gameMap;
	private boolean isDebugVisible;
	private Timer timer;
	private Timer countdown;
	
	/**
	 * Initializes all resources related to the game.
	 * @param width width of the main game window
	 * @param height height of the main game window
	 */
	public GameController(int width, int height) {
		this.gameMap = new GameMap(10);
		this.isDebugVisible = true;
		this.initializeGUI(width, height);
		this.gameMap.addObserver(this.mapView);
		this.gameMap.addObserver(this.debugView);
		this.gameMap.addObserver(this.controlView);
		this.timer = new Timer(10000, this);
		this.countdown = new Timer(1000, this);
		this.timer.setActionCommand("Timer");
		this.countdown.setActionCommand("Countdown");
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(this.gameMap.getIsPlaying()) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_UP:
					this.restartTimer();
					this.gameMap.movePlayer(Direction.UP);
					break;
				case KeyEvent.VK_LEFT:
					this.restartTimer();
					this.gameMap.movePlayer(Direction.LEFT);
					break;
				case KeyEvent.VK_DOWN:
					this.restartTimer();
					this.gameMap.movePlayer(Direction.DOWN);
					break;
				case KeyEvent.VK_RIGHT:
					this.restartTimer();
					this.gameMap.movePlayer(Direction.RIGHT);
					break;
				case KeyEvent.VK_W:
					this.restartTimer();
					this.gameMap.shoot(Direction.UP);
					break;
				case KeyEvent.VK_A:
					this.restartTimer();
					this.gameMap.shoot(Direction.LEFT);
					break;
				case KeyEvent.VK_S:
					this.restartTimer();
					this.gameMap.shoot(Direction.DOWN);
					break;
				case KeyEvent.VK_D:
					this.restartTimer();
					this.gameMap.shoot(Direction.RIGHT);
					break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "Play":
				if(!this.gameMap.getIsPlaying()) {
					this.startGame();
					this.timer.start();
					this.countdown.start();
					this.controlView.setTimerSeconds(10);
				}
				else {
					int choose = JOptionPane.showConfirmDialog(null, "Want to restart the game?", "Hunt The Wumpus", JOptionPane.YES_NO_OPTION);
					if(choose == JOptionPane.YES_OPTION) {
						this.startGame();
						this.restartTimer();
					}
				}
				break;
			case "Debug View":
				this.isDebugVisible = !this.isDebugVisible;
				this.debugView.setVisible(this.isDebugVisible);
				break;
			case "Options":
				JOptionPane.showMessageDialog(null, "Use the directional arrows to move around the map.\nTo shoot use WASD.\nAt the bottom of the interface are shown warnings, game items and the time remaining to make a move.", "Options", JOptionPane.INFORMATION_MESSAGE, null);
				break;
			case "Timer":
				if(this.gameMap.getIsPlaying()) {
					Random r = new Random();
					this.gameMap.movePlayer(Direction.values()[r.nextInt(4)]);
					this.restartTimer();
				} else {
					this.timer.stop();
				}
				break;
			case "Countdown":
				if(this.gameMap.getIsPlaying()) {
					this.controlView.decreaseTimer();
					this.countdown.restart();
				} else {
					this.countdown.stop();
				}
		}
	}
	
	/**
	 * Method that starts the game (or restarts it).
	 */
	private void startGame() {
		this.gameMap.startGame();
	}
	
	/**
	 * Method for getting the timer to restart.
	 */
	private void restartTimer() {
		this.timer.restart();
		this.countdown.restart();
		this.controlView.setTimerSeconds(10);
	}
	
	/**
	 * Initializes the graphics resources related to the game.
	 * @param width width of the main game window
	 * @param height height of the main game window
	 */
	private void initializeGUI(int width, int height) {
		this.gameFrame = new JFrame("Hunt The Wumpus");
		this.gameFrame.setSize(width, height);
		this.gameFrame.setMinimumSize(new Dimension(1000, 650));
		this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gameFrame.setLayout(new GridBagLayout());
		this.mapView = new MapView(this.gameMap.getDim());
		this.debugView = new DebugView(this.gameMap.getDim(), this.isDebugVisible);
		this.controlView = new ControlView();
		this.controlView.getStartGameBtn().addActionListener(this);
		this.controlView.getDebugViewBtn().addActionListener(this);
		this.controlView.getOptionBtn().addActionListener(this);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 3;
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.fill = GridBagConstraints.BOTH;
		this.gameFrame.add(this.mapView, gbc);
		gbc.gridx = 1;
	    gbc.gridy = 0;
	    gbc.fill = GridBagConstraints.BOTH;
		this.gameFrame.add(this.debugView, gbc);
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
	    gbc.gridy = 1;
	    gbc.gridwidth = 2;
	    gbc.fill = GridBagConstraints.BOTH;
		this.gameFrame.add(this.controlView,gbc);
		this.gameFrame.addKeyListener(this);
		this.gameFrame.setFocusable(true);
        this.gameFrame.addFocusListener(new MyFocusListener());
		this.gameFrame.setVisible(true);
	}
	
	private class MyFocusListener implements FocusListener {
		
		@Override
		public void focusGained(FocusEvent e) {}
		
		@Override
		public void focusLost(FocusEvent e) {
			e.getComponent().requestFocus();
		}
	}
}
