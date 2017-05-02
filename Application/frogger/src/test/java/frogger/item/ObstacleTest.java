package frogger.item;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.Grid;
import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;
import ch.heigvd.frogger.item.Obstacle;

public class ObstacleTest {

	private Obstacle obs;
	private final int initPosX = 0;
	private final int initPosY = 0;

	@Before
	public void setUp() throws CellAlreadyOccupiedException {
		obs = new Obstacle(initPosX, initPosY, Constants.ItemType.Chalet);
	}

	@After
	public void tearDown() {
		Grid.getInstance().removeItem(obs.getPosX(), obs.getPosY());
	}

	@Test
	public final void testUpdate() {
		// TODO Test update
	}

	@Test
	public final void testGetPosX() {
		assertEquals(initPosX, obs.getPosX());
	}

	@Test
	public final void testGetPosY() {
		assertEquals(initPosY, obs.getPosY());
	}

	@Test
	public final void testSetVisible() {
		obs.setVisible(false);
		assertEquals(false, obs.isVisible());
	}

	@Test
	public final void testIsVisibleByDefault() {
		// Must be visible by default
		assertEquals(true, obs.isVisible());
	}

	@Test
	public final void testDraw() {
		// fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testMoveTopAlreadyTopShouldntMove() throws CellAlreadyOccupiedException {
		// Already on top, shouldn't move
		obs.moveTop();
		assertEquals(initPosY, obs.getPosY());

	}

	@Test
	public final void testMoveTop() throws CellAlreadyOccupiedException {
		Obstacle myObs = new Obstacle(0, 2, Constants.ItemType.Chalet);
		myObs.moveTop();
		Grid.getInstance().removeItem(myObs.getPosX(), myObs.getPosY());
		assertEquals(1, myObs.getPosY());
	}

	@Test
	public final void testMoveRightAlreadyAtTheEdgeShouldntMove() throws CellAlreadyOccupiedException {
		Obstacle myObs = new Obstacle(Constants.NUM_COLS-1, 0, Constants.ItemType.Chalet);
		myObs.moveRight();
		Grid.getInstance().removeItem(myObs.getPosX(), myObs.getPosY());
		assertEquals(Constants.NUM_COLS-1, myObs.getPosX());
	}

	@Test
	public final void testMoveRight() throws CellAlreadyOccupiedException {
		obs.moveRight();
		assertEquals(initPosX+1, obs.getPosX());
	}

	@Test
	public final void testMoveBottomAlreadyBottomShouldntMove() throws CellAlreadyOccupiedException {
		Obstacle myObs = new Obstacle(Constants.NUM_ROWS-1, 0, Constants.ItemType.Chalet);
		myObs.moveBottom();
		Grid.getInstance().removeItem(myObs.getPosX(), myObs.getPosY());
		assertEquals(Constants.NUM_ROWS-1, myObs.getPosX());
	}

	@Test
	public final void testMoveBottom() throws CellAlreadyOccupiedException {
		obs.moveBottom();
		assertEquals(initPosY+1, obs.getPosY());
	}

	@Test
	public final void testMoveLeftAlreadyAtTheEdgeShouldntMove() throws CellAlreadyOccupiedException {
		obs.moveLeft();
		assertEquals(initPosX, obs.getPosX());
	}

	@Test
	public final void testMoveLeft() throws CellAlreadyOccupiedException {
		Obstacle myObs = new Obstacle(1, 0, Constants.ItemType.Chalet);
		myObs.moveLeft();
		Grid.getInstance().removeItem(myObs.getPosX(), myObs.getPosY());
		assertEquals(0, obs.getPosY());
	}
}
