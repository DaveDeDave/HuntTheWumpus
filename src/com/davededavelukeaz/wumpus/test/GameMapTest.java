package com.davededavelukeaz.wumpus.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.davededavelukeaz.wumpus.model.CharacterSymbol;
import com.davededavelukeaz.wumpus.model.Direction;
import com.davededavelukeaz.wumpus.model.GameMap;

import java.awt.Point;

/**
 * This class is dedicated to test driven development.
 * @author DaveDeDave
 * @author LukeAz
 */
class GameMapTest {

	GameMap gameMap;
	
	/**
	 * Before each test, I instantiate and initialize the game map.
	 */
	@BeforeEach
	void setup() {
		this.gameMap = new GameMap(10);
		this.gameMap.startGame();
	}
	
	/**
	 * Checking that the map is created the right size.
	 */
	@Test
	void checkDim() {
		Assertions.assertEquals(10, this.gameMap.getDim());
	}
	
	/**
	 * Check that the shooting method is working correctly.
	 */
	@Test
	void checkShoot() {
		this.gameMap.getPlayer().setArrow(1);
		this.gameMap.shoot(Direction.UP);
		Assertions.assertEquals(0, this.gameMap.getPlayer().getArrow());
	}
	
	/**
	 * Check that the player cannot move outside the map.
	 */
	@Test
	void checkMoveOutsideMap() {
		this.gameMap.getPlayer().setPosition(0, 0);
		this.gameMap.getGrid()[0][0] = CharacterSymbol.PLAYER;
		this.gameMap.movePlayer(Direction.LEFT);
		Assertions.assertEquals(new Point(0, 0), this.gameMap.getPlayer().getPosition());
	}	
	
	/**
	 * Check that as a result of finding a survivor my materials have increased.
	 */
	@Test
	void checkFindSurvivor() {
		int numItems;
		this.gameMap.getPlayer().setArrow(3);
		this.gameMap.getPlayer().setGold(1);
		numItems = this.gameMap.getPlayer().getArrow() + this.gameMap.getPlayer().getGold();
		this.gameMap.getPlayer().setPosition(0, 0);
		this.gameMap.getGrid()[0][0] = CharacterSymbol.PLAYER;
		this.gameMap.getSurvivor(0).setPosition(1, 0);
		this.gameMap.getGrid()[0][1] = CharacterSymbol.SURVIVOR;
		this.gameMap.movePlayer(Direction.RIGHT);
		Assertions.assertFalse(this.gameMap.getSurvivor(0).getIsPlaying());
		Assertions.assertEquals(numItems + 1, this.gameMap.getPlayer().getArrow() + this.gameMap.getPlayer().getGold());
	}
	
	/**
	 * Check that following an encounter with a miniwumpus my materials are scaled.
	 */
	@Test
	void checkFindMiniWumpus() {
		int numItems;
		this.gameMap.getPlayer().setArrow(3);
		this.gameMap.getPlayer().setGold(1);
		numItems = this.gameMap.getPlayer().getArrow() + this.gameMap.getPlayer().getGold();
		this.gameMap.getPlayer().setPosition(0, 0);
		this.gameMap.getGrid()[0][0] = CharacterSymbol.PLAYER;
		this.gameMap.getMiniWumpus(0).setPosition(1, 0);
		this.gameMap.getGrid()[0][1] = CharacterSymbol.MINIWUMPUS;
		this.gameMap.movePlayer(Direction.RIGHT);
		Assertions.assertFalse(this.gameMap.getMiniWumpus(0).getIsPlaying());
		Assertions.assertEquals(numItems - 1, this.gameMap.getPlayer().getArrow() + this.gameMap.getPlayer().getGold());
	}
	
	/**
	 * Check that as a result of an encounter with a wumpus I lost.
	 */
	@Test
	void checkLoseWumpus() {	
		this.gameMap.getPlayer().setArrow(1);
		this.gameMap.getPlayer().setPosition(0, 0);
		this.gameMap.getGrid()[0][0] = CharacterSymbol.PLAYER;
		this.gameMap.getWumpus().setPosition(1, 0);
		this.gameMap.getGrid()[0][1] = CharacterSymbol.WUMPUS;
		this.gameMap.movePlayer(Direction.RIGHT);
		Assertions.assertFalse(this.gameMap.getWin());
		Assertions.assertFalse(this.gameMap.getIsPlaying());
	}
	
	/**
	 * Check that following an encounter with a miniwumpus, if I have no materials I lose.
	 */
	@Test
	void checkLoseMiniWumpus() {
		this.gameMap.getPlayer().setArrow(0);
		this.gameMap.getPlayer().setGold(0);
		this.gameMap.getPlayer().setPosition(0, 0);
		this.gameMap.getGrid()[0][0] = CharacterSymbol.PLAYER;
		this.gameMap.getMiniWumpus(0).setPosition(1, 0);
		this.gameMap.getGrid()[0][1] = CharacterSymbol.MINIWUMPUS;
		this.gameMap.movePlayer(Direction.RIGHT);
		Assertions.assertFalse(this.gameMap.getWin());
		Assertions.assertFalse(this.gameMap.getIsPlaying());
	}
	
	/**
	 * Check that after shooting the wumpus I have won.
	 */
	@Test 
	void checkWin() {
		this.gameMap.getPlayer().setPosition(0, 0);
		this.gameMap.getGrid()[0][0] = CharacterSymbol.PLAYER;
		this.gameMap.getWumpus().setPosition(1, 0);
		this.gameMap.getGrid()[0][1] = CharacterSymbol.WUMPUS;
		this.gameMap.shoot(Direction.RIGHT);
		Assertions.assertTrue(this.gameMap.getWin());
		Assertions.assertFalse(this.gameMap.getIsPlaying());
	}
	
	/**
	 * Check that the method for warnings works.
	 */
	@Test
	void checkWarningWorks() {
		this.gameMap.getPlayer().setPosition(0, 0);
		this.gameMap.getGrid()[0][0] = CharacterSymbol.PLAYER;
		this.gameMap.getWumpus().setPosition(2, 0);
		this.gameMap.getGrid()[0][2] = CharacterSymbol.WUMPUS;
		this.gameMap.movePlayer(Direction.RIGHT);
		Assertions.assertFalse(this.gameMap.getWarnings().isEmpty());
	}

}
