package ch.heigvd.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.*;

import ch.heigvd.protocol.*;
import ch.heigvd.protocol.Protocol.Direction;

/**
 * Handle a game that has been launched
 * @author Tony
 *
 */
public class LaunchedGame {
	private List<Obstacle> fixedObstacle;
	private HashMap<Integer, Obstacle> dynamicObstacle;
	private int dynamicObstacleCounter;
	//TODO replace by skier
	private Skier skier;
	private final Party party;
	private Direction lastMove;
	 private final Socket client1;
	 private BufferedWriter out1;
	 private Socket client2 = null;
	 private BufferedWriter out2;
	 private Difficulty difficulty;
	 private Timer timer;
	 private long lastObstacleMove;
	//TODO put clients here
	
	public LaunchedGame(Party party, List<Obstacle> obstacles, Skier initialSkier, Socket client1) {
		this.party = party;
		fixedObstacle = obstacles;
		dynamicObstacle = new HashMap();
		dynamicObstacleCounter = 0;
		skier = initialSkier;
		this.client1 = client1;
		timer = new Timer();
		try {
			out1 = new BufferedWriter(
			        new OutputStreamWriter(client1.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lastObstacleMove = System.currentTimeMillis();
	}
	
	public List<Obstacle> getFixedObstacle() {
		return fixedObstacle;
	}
	
	public void move(Direction d) {
		lastMove = d;
	}
	
	public void addAnotherPlayer(Socket client2) {
		this.client2 = client2;
		try {
			out2 = new BufferedWriter(
			        new OutputStreamWriter(client2.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Send the message to both client
	 * @param message
	 */
	private void broadcast(String message) {
		try {
            if(message != null) {
                out1.write(message);
                out1.flush();
                out2.write(message);
                out2.flush();
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// start the game in s seconds
	public void start(int s) {
		if(client2 != null) {
			// Remove party from lobby
			App.CURRENT_LOBBIES.remove(party);
			// send that the party start in s seconds
			long time = System.currentTimeMillis();
			time += s * 1000;
				broadcast(Protocol.formatStartGame(party, time));

			// wait these seconds
			// start the game
			// TODO put timer time in variable
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					tick();
				}
			},s*1000 - (System.currentTimeMillis() - time), ServerConstants.tickRate);
		}
	}
	
	private void moveSkier() {
		// check bounds
		if(lastMove != null) {
			skier.move(lastMove);
		}
	}
	
	public void addDynamicObstacle(Obstacle o) {
		o.setId(dynamicObstacleCounter);
		dynamicObstacle.put(dynamicObstacleCounter++, o);
	}
	
	private boolean checkCollision() {
		// check collision with fixed obstacle
		for (Obstacle obstacle : fixedObstacle) {
			if(obstacle.compareToCoordinate(skier.getX(), skier.getY())){
				return true;
			}
		}
		// check collision with dynamic obstacle
		for (Obstacle obstacle : dynamicObstacle.values()) {
			if(obstacle.compareToCoordinate(skier.getX(), skier.getY())){
				return true;
			}
		}
		return false;
	}
	
	private void tick() {
		// Move everything accordingly to the Timer tick
		moveSkier();
		if (System.currentTimeMillis() - lastObstacleMove > 200 * (5 - (party.getDifficulty().getObstacleMoveSpeed() % 5))) {
			moveDynamicObstacle();
			lastObstacleMove = System.currentTimeMillis();
		}

		if(checkCollision())
		{
			System.out.println("Collision check");
			// TODO Remove launched Game from game
			broadcast(Protocol.formatVaudoisWon());
			timer.cancel();
		}
		
		if(checkSkierWon()) {
			System.out.println("Skier won check");
			broadcast(Protocol.formatSkierWon());
			timer.cancel();
		}
		
		lastMove= null;
		// send new informations
		broadcast(Protocol.formatSendSkierPosition(skier));
		broadcast(Protocol.formatSendDynamicObstacle(dynamicObstacle.values()));
	}

	/**
	 * Check if the skier has reach the last row of the map
	 * @return true if the skier has reach the end
	 */
	private boolean checkSkierWon() {
		return skier.getY() >= Constants.GAME_HEIGHT;
	}

	/**
	 * Move each dynamic obstacle 1 step to the right
	 * if obstacle reach the edge of the map remove
	 */
	private void moveDynamicObstacle() {
		for (Obstacle obstacle : dynamicObstacle.values()) {
			obstacle.moveRight();
			if(obstacle.getX() >= Constants.GAME_WIDTH) {
				dynamicObstacle.remove(obstacle.getId());
			}
		}
	}

}
