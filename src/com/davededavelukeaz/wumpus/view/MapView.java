package com.davededavelukeaz.wumpus.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.davededavelukeaz.wumpus.model.GameMap;

/**
 * This class provides the game view.
 * You can see the position of the player.
 * You can see which cells have already been visited (but not necessarily safe zones!).
 * @author DaveDeDave
 * @author LukeAz
 * @see Observer
 */
public class MapView extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private JLabel[][] gridContent;
	private Point currentPosition;
	private int dim;
	
	/**
	 * Initialize the grid graphics.
	 * @param dim grid size
	 */
	public MapView(int dim) {
		super(new GridLayout(dim, dim, 0, 0));
		this.currentPosition = new Point();
		this.dim = dim;
		prepareGridContent();
		this.setVisible(true);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		int x, y;
		
		if(((String) arg) == "Start")
			this.restartGridContent();
		else if(((String) arg) == "Move"){
			this.gridContent[this.currentPosition.y][this.currentPosition.x].setIcon(new ImageIcon(getClass().getResource("/png/steps.png")));
		}
		
		x = ((GameMap) o).getPlayer().getPosition().x;
		y = ((GameMap) o).getPlayer().getPosition().y;
		this.currentPosition.setLocation(x, y);
		this.gridContent[this.currentPosition.y][this.currentPosition.x].setIcon(new ImageIcon(getClass().getResource("/png/player.png")));
	}
	
	/**
	 * Prepare the structure of the grid, initially empty.
	 */
	private void prepareGridContent() {
		this.gridContent = new JLabel[this.dim][this.dim];
		
		for(int i = 0; i < this.dim; i++) {
			for(int j = 0; j < this.dim; j++) {
				this.gridContent[i][j] = new JLabel(null, new ImageIcon(getClass().getResource("/png/empty.png")), JLabel.CENTER);
				this.gridContent[i][j].setOpaque(true);
				this.gridContent[i][j].setBackground(Color.white);
				this.gridContent[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 1));
				this.add(gridContent[i][j]);
			}
		}
	}
	
	/**
	 * Resets the grid structure, emptying it.
	 */
	private void restartGridContent() {
		for(int i = 0; i < this.dim; i++) {
			for(int j = 0; j < this.dim; j++) {
				this.gridContent[i][j].setIcon(new ImageIcon(getClass().getResource("/png/empty.png")));
			}
		}
	}
}
