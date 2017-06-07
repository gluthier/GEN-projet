package ch.heigvd.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import ch.heigvd.protocol.Obstacle;
import ch.heigvd.protocol.Protocol.Direction;

/**
 * Handle a game that has been launched
 * @author Tony
 *
 */
public class LaunchedGame {
	private List<Obstacle> fixedObstacle;
	private List<Obstacle> dynamicObstacle;
	private final int mapWidth;
	private final int mapHeight;
	private int skierX;
	private int skierY;
	private Direction lastMove;
	 private final Socket client1;
	 private Socket client2;
	//TODO put clients here
	
	public LaunchedGame(int mapWidth, int mapHeight, List<Obstacle> obstacles, int initialX, int initialY,Socket client1) {
		fixedObstacle = obstacles;
		dynamicObstacle = new ArrayList<Obstacle>();
		this.mapHeight = mapHeight;
		this.mapWidth = mapWidth;
		skierX = initialX;
		skierY = initialY;
		this.client1 = client1;
		
	}
	
	public void move(Direction d) {
		lastMove = d;
	}
	
	public void addAnotherPlayer(Socket client2) {
		this.client2 = client2;
	}
	
	// start the game in s seconds
	public void start(int s) {
		
	}
	
	private void moveSkier() {
		// check bounds
		if(lastMove != null) {
			switch (lastMove) {
			case right:
				skierX++;
				break;
			case left:
				skierX--;
				break;
			case bottom:
				skierY++;
				break;
			default:
				break;
			}
		}
	}
	
	public void addDynamicObstacle(Obstacle o) {
		dynamicObstacle.add(o);
	}
	
	private boolean checkCollision() {
		// check if the player is in collision
		return false;
	}
	
	private void tick() {
		// Move everything accordingly to the Timer tick
		moveSkier();
		
		lastMove= null;
	}

}
