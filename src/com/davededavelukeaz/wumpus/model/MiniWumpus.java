package com.davededavelukeaz.wumpus.model;

import java.util.Random;

/**
 * Miniwumpus model.
 * @author DaveDeDave
 * @author LukeAz
 * @see Character
 */
public class MiniWumpus extends Character {

	private boolean isPlaying;
	
	/**
	 * Call the parent constructor.
	 */
	public MiniWumpus() {
		super();
	}
	
	/**
	 * Returns the boolean representing whether the miniwumpus is in the game or not.
	 * @return Returns the boolean representing whether the miniwumpus is in the game or not.
	 */
	public boolean getIsPlaying() {
		return this.isPlaying;
	}
	
	/**
	 * Sets the game state of the miniwumpus.
	 * @param isPlaying boolean representing whether the miniwumpus is in game or not
	 */
	public void setIsPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}
	
	/**
	 * He decides whether to steal an arrow or a piece of gold.
	 * @return integer randomly extracted that will decide what to steal
	 */
	public int steal() {
		Random rand = new Random();
		this.isPlaying = false;
		
		return rand.nextInt(2);
	}
}
