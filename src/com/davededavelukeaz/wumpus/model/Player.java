package com.davededavelukeaz.wumpus.model;

/**
 * Player model.
 * @author DaveDeDave
 * @author LukeAz
 * @see Character
 */
public class Player extends Character {
	
	private int arrow;
	private int gold;
	
	/**
	 * Call the parent constructor.
	 */
	public Player() {
		super();
	}

	/**
	 * Returns the number of arrows.
	 * @return Returns the number of arrows
	 */
	public int getArrow() {
		return this.arrow;
	}

	/**
	 * Sets the number of arrows.
	 * @param arrow number of arrows to set
	 */
	public void setArrow(int arrow) {
		this.arrow = arrow;
	}

	/**
	 * Returns the number of gold.
	 * @return returns the number of gold
	 */
	public int getGold() {
		return this.gold;
	}

	/**
	 * Set the number of gold.
	 * @param gold the number of gold to set
	 */
	public void setGold(int gold) {
		this.gold = gold;
	}
	
	/**
	 * Shoot an arrow.
	 * If it doesn't have arrows, it doesn't shoot.
	 * @return boolean that determines whether or not you were able to shoot
	 */
	public boolean shoot() {
		boolean canIShoot = false;
		
		if(this.arrow > 0) {
			canIShoot = true;
			this.arrow--;
		}
			
		return canIShoot;
	}
	
	/**
	 * Earn an arrow or a gold piece.
	 * @param item the material that will be earned
	 */
	public void gainItem(int item) {
		if(item == 0)
			this.arrow++;
		else
			this.gold++;
	}
	
	/**
	 * loses an arrow or a piece of gold.
	 * @param item the material that will be lost
	 * @return boolean that determines if I lost the material or if I didn't lose it since the player didn't own anything
	 */
	public boolean loseItem(int item) {
		boolean loseItem = true;
		
		switch(item) {
			case 0:
				if(this.arrow > 0)
					this.arrow--;
				else if(this.gold > 0)
					this.gold--;
				else
					loseItem = false;
				break;
			case 1:
				if(this.gold > 0)
					this.gold--;
				else if(this.arrow > 0)
					this.arrow--;
				else
					loseItem = false;
				break;
			default:
				loseItem = false;
				break;
		}
		
		return loseItem;
	}
	
}
