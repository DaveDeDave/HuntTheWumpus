package com.davededavelukeaz.wumpus.model;

import java.awt.Point;
import java.util.HashSet;
import java.util.Observable;
import java.util.Random;
import java.lang.Math;

/**
 * Class that manages the game map and the characters within it.
 * Provides methods for managing game models.
 * @author DaveDeDave
 * @author LukeAz
 * @see Observable
 */
public class GameMap extends Observable {

	private Player player;
	private Wumpus wumpus;
	private Pit[] pit;
	private MiniWumpus[] miniWumpus;
	private Survivor[] survivor;
	private HashSet<String> warnings;
	private CharacterSymbol[][] grid;
	private int dim;
	private boolean win;
	private boolean isPlaying;
	
	/**
	 * Initialize model resources.
	 * @param dim game grid size
	 */
	public GameMap(int dim) {
		int numSecondaryCharacter;
		if(dim < 10)
			this.dim = 10;
		else if(dim > 15)
			this.dim = 15;
		else
			this.dim = dim;
		this.grid = new CharacterSymbol[this.dim][this.dim];
		this.player = new Player();
		this.wumpus = new Wumpus();
		numSecondaryCharacter = Math.round(this.dim/2);
		this.pit = new Pit[numSecondaryCharacter];
		this.miniWumpus = new MiniWumpus[numSecondaryCharacter];
		this.survivor = new Survivor[numSecondaryCharacter];
		for(int i = 0; i < numSecondaryCharacter; i++) {
			this.pit[i] = new Pit();
			this.miniWumpus[i] = new MiniWumpus();
			this.survivor[i] = new Survivor();
		}
		this.warnings = new HashSet<String>();
		this.win = false;
		this.isPlaying = false;
	}
	
	/**
	 * Returns the player.
	 * @return Returns the player
	 */
	public Player getPlayer() {
		return this.player;
	}
	
	/**
	 * Returns the wumpus.
	 * @return Returns the wumpus
	 */
	public Wumpus getWumpus() {
		return this.wumpus;
	}

	/**
	 * Returns the miniwumpus.
	 * @param i miniwumpus index
	 * @return Returns the miniwumpus
	 */
	public MiniWumpus getMiniWumpus(int i) {
		MiniWumpus mw;
		try {
			mw = this.miniWumpus[i];
		} catch(ArrayIndexOutOfBoundsException e) {
			mw = null;
		}
		
		return mw;
	} 
	
	/**
	 * Returns the survivor.
	 * @param i survivor index
	 * @return Returns the survivor
	 */
	public Survivor getSurvivor(int i) {
		Survivor s;
		try {
			s = this.survivor[i];
		} catch(ArrayIndexOutOfBoundsException e) {
			s = null;
		}
		
		return s;
	} 

	/**
	 * Returns the warnings.
	 * @return Returns the warnings
	 */
	public HashSet<String> getWarnings() {
		return this.warnings;
	}
	
	/**
	 * Returns the grid size.
	 * @return Returns the grid size
	 */
	public int getDim() {
		return this.dim;
	}
	
	/**
	 * Returns the boolean representing whether you are playing.
	 * @return Returns the boolean representing whether you are playing.
	 */
	public boolean getIsPlaying() {
		return this.isPlaying;
	}
	
	/**
	 * Returns the boolean that represents if you won the game.
	 * @return Returns the boolean that represents if you won the game
	 */
	public boolean getWin() {
		return this.win;
	}
	
	/**
	 * Returns the game grid.
	 * @return Returns the game grid
	 */
	public CharacterSymbol[][] getGrid() {
		return this.grid;
	}
	
	/**
	 * Starts the game, initializing the players' positions.
	 */
	public void startGame() {
		this.setupGame();
		this.setupMap();
		this.updateMap("Start");
	}
	
	/**
	 * Allows you to move the player around the map.
	 * @param d direction in which to move the player.
	 */
	public void movePlayer(Direction d) {
		this.move(this.player, CharacterSymbol.PLAYER, d);
		this.updateMap("Move");
	}
	
	/**
	 * Allows you to shoot in a direction.
	 * @param d direction to shoot.
	 */
	public void shoot(Direction d) {
		boolean canIShoot = player.shoot();
		
		switch(d) {
			case UP:
				if(canIShoot) {
					if(look(this.player.getPosition(), Direction.UP) == CharacterSymbol.WUMPUS) {
						this.win = true;
						this.isPlaying = false;
					}
				}
				break;
			case LEFT:
				if(canIShoot) {
					if(look(this.player.getPosition(), Direction.LEFT) == CharacterSymbol.WUMPUS) {
						this.win = true;
						this.isPlaying = false;
					}
				}
				break;
			case DOWN:
				if(canIShoot) {
					if(look(this.player.getPosition(), Direction.DOWN) == CharacterSymbol.WUMPUS) {
						this.win = true;
						this.isPlaying = false;
					}
				}
				break;
			case RIGHT:
				if(canIShoot) {
					if(look(this.player.getPosition(), Direction.RIGHT) == CharacterSymbol.WUMPUS) {
						this.win = true;
						this.isPlaying = false;
					}
				}
				break;
			default:
				break;
		}
			
		if(canIShoot)
			this.updateMap("");
	}
	
	/**
	 * Initialize game resources, player status and game state.
	 */
	private void setupGame() {
		this.player.setArrow(3);
		this.player.setGold(0);
		for(int i = 0; i < this.miniWumpus.length; i++) {
			this.miniWumpus[i].setIsPlaying(true);
			this.survivor[i].setIsPlaying(true);
		}
		this.win = false;
		this.isPlaying = true;
	}
	
	/**
	 * Checks all map information and notifies observers with the current status.
	 * @param short description of the notification for observers
	 */
	private void updateMap(String description) {
		this.checkGameWarnings();
		this.moveMiniWumpus();
		this.checkPlayerWarnings();
		this.setChanged();
		this.notifyObservers(description);
	}
	
	/**
	 * Initializes the game grid and the position of the characters.
	 */
	private void setupMap() {
		Point p = new Point();
		
		for(int i = 0; i < this.dim; i++) {
			for(int j = 0; j < this.dim; j++) {
				this.grid[i][j] = CharacterSymbol.EMPTY;
			}
		}
		
		p = this.findEmptyCell();
		this.player.setPosition(p.x, p.y);
		this.grid[p.y][p.x] = CharacterSymbol.PLAYER;
		
		p = this.findEmptyCell();
		this.wumpus.setPosition(p.x, p.y);
		this.grid[p.y][p.x] = CharacterSymbol.WUMPUS;
		
		for(int i = 0; i < this.pit.length; i++) {
			p = this.findEmptyCell();
			this.pit[i].setPosition(p.x, p.y);
			this.grid[p.y][p.x] = CharacterSymbol.PIT;
			p = this.findEmptyCell();
			this.miniWumpus[i].setPosition(p.x, p.y);
			this.grid[p.y][p.x] = CharacterSymbol.MINIWUMPUS;
			p = this.findEmptyCell();
			this.survivor[i].setPosition(p.x, p.y);
			this.grid[p.y][p.x] = CharacterSymbol.SURVIVOR;
		}
	}

	/**
	 * Check warnings about determining factors in the game (such as winning the game, losing materials, etc.).
	 */
	private void checkGameWarnings() {
		this.warnings.clear();
		
		if(this.win)
			this.warnings.add("You win!\n");
		else if(this.player.comparePosition(this.wumpus)) {
			this.warnings.add("The wumpus has caught you! You lost.\n");
			this.isPlaying = false;
		} else if(hitPit()) {
			this.warnings.add("You fell into a pit! You lost.\n");
			this.isPlaying = false;
		} else if(hitMiniWumpus()) {
			if(this.isPlaying)
				this.warnings.add("A mini wumpus robbed you.\n");
			else {
				this.warnings.add("A mini wumpus tried to rob you but you had nothing. You lost!\n");
				return;
			}
		} else if(hitSurvivor()) {
			this.warnings.add("You have freed a survivor! You have received a gift.\n");
		}
	}
	
	/**
	 * Check warnings about hunches the player has (such as a hunch of being near a pit).
	 */
	private void checkPlayerWarnings() {
		if(this.isPlaying)
			for(Direction d : Direction.values()) {
				switch(look(this.player.getPosition(), d)) {
					case WUMPUS:
						this.warnings.add("There is a strange smell.\n");
						break;
					case PIT:
						this.warnings.add("You can feel a breeze of wind.\n");
						break;
					case MINIWUMPUS:
						this.warnings.add("Watch your pockets.\n");
						break;
					case SURVIVOR:
						this.warnings.add("Cries for help are heard.\n");
						break;
					default:
						break;
				}
			}
	}
	
	/**
	 * Allows you to move a character in a certain direction.
	 * If you try to go outside the grid, the character remains stationary for one turn.
	 * @param c character to move
	 * @param cs symbol in the grid that represents the character to move
	 * @param d direction in which to move the character
	 */
	private void move(Character c, CharacterSymbol cs, Direction d) {
		try {
			switch(d) {
				case UP:
					this.grid[c.getPosition().y][c.getPosition().x] = CharacterSymbol.EMPTY;
					this.grid[c.getPosition().y - 1][c.getPosition().x] = cs; 
					c.setPosition(c.getPosition().x, c.getPosition().y - 1);
					break;
				case LEFT:
					this.grid[c.getPosition().y][c.getPosition().x] = CharacterSymbol.EMPTY;
					this.grid[c.getPosition().y][c.getPosition().x - 1] = cs;
					c.setPosition(c.getPosition().x - 1, c.getPosition().y); 
					break;
				case DOWN:
					this.grid[c.getPosition().y][c.getPosition().x] = CharacterSymbol.EMPTY;
					this.grid[c.getPosition().y + 1][c.getPosition().x] = cs;
					c.setPosition(c.getPosition().x, c.getPosition().y + 1);
					break;
				case RIGHT:
					this.grid[c.getPosition().y][c.getPosition().x] = CharacterSymbol.EMPTY;
					this.grid[c.getPosition().y][c.getPosition().x + 1] = cs;
					c.setPosition(c.getPosition().x + 1, c.getPosition().y);
					break;
				default:
					break;
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			this.grid[c.getPosition().y][c.getPosition().x] = cs;
		}
	}
	
	/**
	 * It allows you to look at who is in the adiactive cells.
	 * This is used for warnings and to keep two characters from moving in the same cell.
	 * @param p character position
	 * @param d direction in which to look
	 * @return character symbol present in the corresponding grid cell. It can be empty.
	 */
	private CharacterSymbol look(Point p, Direction d) {
		CharacterSymbol cs = null;
		
		try {
			switch(d) {
				case UP:
					cs = this.grid[p.y - 1][p.x];
					break;
				case UP_RIGHT:
					cs = this.grid[p.y - 1][p.x + 1];
					break;
				case RIGHT:
					cs = this.grid[p.y][p.x + 1];
					break;
				case DOWN_RIGHT:
					cs = this.grid[p.y + 1][p.x + 1];
					break;
				case DOWN:
					cs = this.grid[p.y + 1][p.x];
					break;
				case DOWN_LEFT:
					cs = this.grid[p.y + 1][p.x - 1];
					break;
				case LEFT:
					cs = this.grid[p.y][p.x - 1];
					break;
				case UP_LEFT:
					cs = this.grid[p.y - 1][p.x - 1];
					break;
				default:
					break;
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			cs = CharacterSymbol.OUT_OF_GRID;
		}
		
		return cs;
	}
	
	/**
	 * Randomly moves miniwumpus within the map every turn
	 */
	private void moveMiniWumpus() {
		Random rand = new Random();
		Direction d = null;
		
		for(int i = 0; i < this.miniWumpus.length; i++) {
			if(this.miniWumpus[i].getIsPlaying()) {
				d = Direction.values()[rand.nextInt(4)];
				if(look(this.miniWumpus[i].getPosition(), d) == CharacterSymbol.EMPTY)
						this.move(this.miniWumpus[i], CharacterSymbol.MINIWUMPUS, d);
			}
		}
	}
	
	/**
	 * Determines whether the player has ended up in a pit.
	 * @return boolean that determines if the player ended up in a pit
	 */
	private boolean hitPit() {
		boolean hit = false;
		
		for(int i = 0; i < this.pit.length && hit == false; i++)
			if(this.player.comparePosition(this.pit[i]))
				hit = true;
		
		return hit;
	}
	
	/**
	 * Determines if the player has encountered a miniwumpus.
	 * @return boolean that determines if the player has encountered a miniwumpus
	 */
	private boolean hitMiniWumpus() {
		boolean hit = false, loseItem;
		int item;
		
		for(int i = 0; i < this.miniWumpus.length && hit == false; i++)
			if(this.miniWumpus[i].getIsPlaying())
				if(this.player.comparePosition(this.miniWumpus[i])) {
					item = this.miniWumpus[i].steal();
					loseItem = player.loseItem(item);
					if(!loseItem)
						this.isPlaying = false;
					hit = true;
				}
		
		return hit;
	}
	
	/**
	 * Determines if the player has encountered a survivor.
	 * @return Boolean that determines if the player has encountered a survivor
	 */
	private boolean hitSurvivor() {
		boolean hit = false;
		int item;
		
		for(int i = 0; i < this.survivor.length && hit == false; i++)
			if(this.survivor[i].getIsPlaying())
				if(this.player.comparePosition(this.survivor[i])) {
					item = this.survivor[i].found();
					player.gainItem(item);
					hit = true;
		}
		
		return hit;
	}
	
	/**
	 * Utility method to find an empty cell in the grid where to place the characters during the game initialization.
	 * @return free position inside the grid
	 */
	private Point findEmptyCell() {
		Random rand = new Random();
		int column, row;
		
		do {
			column = rand.nextInt(this.dim);
			row = rand.nextInt(this.dim);
		} while(this.grid[row][column] != CharacterSymbol.EMPTY);
		
		return new Point(column, row);
	}
}
