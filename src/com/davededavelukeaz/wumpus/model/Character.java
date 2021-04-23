package com.davededavelukeaz.wumpus.model;

import java.awt.Point;

/**
 * Abstract class that serves as a model for all characters in the game.
 * @author DaveDeDave
 * @author LukeAz
 *
 */
public abstract class Character {

	private Point position;
	
	/**
	 * Initializes the position of type {@link Point Point} of the character.
	 */
	public Character() {
		this.position = new Point();
	}
	
	/**
	 * Returns the position of the character.
	 * @return returns the position of the character
	 */
	public Point getPosition() {
		return this.position;
	}
	
	/**
	 * Sets the position of the character.
	 * @param x x coordinate of the character
	 * @param y y coordinate of the character
	 */
	public void setPosition(int x, int y) {
		this.position.setLocation(x, y);
	}
	
	/**
	 * Compares the position of the current character with the position of the input character.
	 * @param o character to compare position
	 * @return boolean that determines whether they are in the same position
	 */
	public boolean comparePosition(Character o) {
		Point position = o.getPosition();
		
		return this.position.equals(position);
	}
}
