package com.davededavelukeaz.wumpus.model;

import java.util.Random;

/**
 * Survivor model.
 * @author DaveDeDave
 * @author LukeAz
 * @see Character
 */
public class Survivor extends Character {

	private boolean isPlaying;
	
	/**
	 * Call the parent constructor.
	 */
	public Survivor() {
		super();
	}
	
	/**
	 * Returns the boolean representing whether the survivor is in the game or not.
	 * @return Returns the boolean representing whether the survivor is in the game or not.
	 */
	public boolean getIsPlaying() {
		return this.isPlaying;
	}
	
	/**
	 * Sets the game state of the survivor.
	 * @param isPlaying boolean representing whether the survivor is in the game or not
	 */
	public void setIsPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}
	
	/**
	 * Decides to give away a random material when it is found by the player.
	 * @return integer randomly extracted that will decide what to give away
	 */
	public int found() {
		Random rand = new Random();
		this.isPlaying = false;
		
		return rand.nextInt(2);
	}
}
