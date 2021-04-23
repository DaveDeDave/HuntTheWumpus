package com.davededavelukeaz.wumpus.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.davededavelukeaz.wumpus.model.CharacterSymbol;
import com.davededavelukeaz.wumpus.model.GameMap;

/**
 * This class provides a debug view.
 * You can see on the map where all the characters are in the game.
 * @author DaveDeDave
 * @author LukeAz
 * @see Observer
 */
public class DebugView extends JPanel implements Observer {
	
	private static final long serialVersionUID = 1L;
	private JLabel[][] gridContent;
	private int dim;

	/**
	 * Initialize the grid graphics.
	 * @param dim grid size
	 * @param isVisible determines whether or not to display the default
	 */
	public DebugView(int dim, boolean isVisible) {
		super(new GridLayout(dim, dim, 0, 0));
		this.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, Color.DARK_GRAY));
		this.dim = dim;
		prepareGridContent();
		this.setVisible(isVisible);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		this.drawGridContent(((GameMap) o).getGrid());
	}
	
	/**
	 * Draw the updated positions of the characters on the grid.
	 * @param grid character grid
	 */
	private void drawGridContent(CharacterSymbol[][] grid) {
		for(int i = 0; i < this.dim; i++) {
			for(int j = 0; j < this.dim; j++) {
				switch(grid[i][j]) {
					case PLAYER:
						this.gridContent[i][j].setIcon(new ImageIcon(getClass().getResource("/png/player.png")));
						break;
					case WUMPUS:
						this.gridContent[i][j].setIcon(new ImageIcon(getClass().getResource("/png/wumpus.png")));
						break;
					case PIT:
						this.gridContent[i][j].setIcon(new ImageIcon(getClass().getResource("/png/pit.png")));
						break;
					case MINIWUMPUS:
						this.gridContent[i][j].setIcon(new ImageIcon(getClass().getResource("/png/miniwumpus.png")));
						break;
					case SURVIVOR:
						this.gridContent[i][j].setIcon(new ImageIcon(getClass().getResource("/png/survivor.png")));
						break;
					default:
						this.gridContent[i][j].setBackground(Color.white);
						this.gridContent[i][j].setIcon(null);
						break;
				}
			}
		}
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
}
