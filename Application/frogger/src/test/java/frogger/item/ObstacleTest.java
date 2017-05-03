package frogger.item;

import static org.junit.Assert.*;

import javafx.scene.Group;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;
import ch.heigvd.frogger.item.Obstacle;

public class ObstacleTest {
//	private Obstacle obs;
//	private Group group;
//	private final int initPosX = 0;
//	private final int initPosY = 0;
//	private double delta = 0.01;
//
//	@Before
//	public void setUp() throws CellAlreadyOccupiedException {
//		obs = new Obstacle(initPosX, initPosY, Constants.ItemType.Chalet);
//		group = new Group();
//	}
//
//	@After
//	public void tearDown() {
//	}
//
//	@Test
//	public final void testMoveTopAlreadyTopShouldntMove() throws CellAlreadyOccupiedException {
//		// Already on top, shouldn't move
//		obs.moveTop();
//		assertEquals(initPosY, obs.getY(), delta);
//	}
//
//	@Test
//	public final void testMoveTop() throws CellAlreadyOccupiedException {
//		Obstacle myObs = new Obstacle(0, 2, Constants.ItemType.Chalet);
//		myObs.moveTop();
//		assertEquals(1, myObs.getY(), delta);
//		assertEquals(0, myObs.getX(), delta);
//	}
//
//	@Test
//	public final void testMoveRightAlreadyAtTheEdgeShouldntMove() throws CellAlreadyOccupiedException {
//		Obstacle myObs = new Obstacle(Constants.NUM_COLS-1, 0, Constants.ItemType.Chalet);
//		myObs.moveRight();
//		assertEquals(Constants.NUM_COLS-1, myObs.getXGridCoordinate());
//	}
//
//	@Test
//	public final void testMoveRight() throws CellAlreadyOccupiedException {
//		obs.moveRight();
//		assertEquals(initPosX+1, obs.getXGridCoordinate());
//	}
//
//	@Test
//	public final void testMoveBottomAlreadyBottomShouldntMove() throws CellAlreadyOccupiedException {
//		Obstacle myObs = new Obstacle(Constants.NUM_ROWS-1, 0, Constants.ItemType.Chalet);
//		myObs.moveBottom();
//		assertEquals(Constants.NUM_ROWS-1, myObs.getXGridCoordinate());
//	}
//
//	@Test
//	public final void testMoveBottom() throws CellAlreadyOccupiedException {
//		obs.moveBottom();
//		assertEquals(initPosY+1, obs.getYGridCoordinate());
//	}
//
//	@Test
//	public final void testMoveLeftAlreadyAtTheEdgeShouldntMove() throws CellAlreadyOccupiedException {
//		obs.moveLeft();
//		assertEquals(initPosX, obs.());
//	}
//
//	@Test
//	public final void testMoveLeft() throws CellAlreadyOccupiedException {
//		Obstacle myObs = new Obstacle(1, 0, Constants.ItemType.Chalet);
//		myObs.moveLeft();
//		Grid.getInstance().removeItem(myObs.getPosX(), myObs.getPosY());
//		assertEquals(0, obs.getPosY());
//	}
}