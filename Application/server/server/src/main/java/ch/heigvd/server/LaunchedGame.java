package ch.heigvd.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ch.heigvd.protocol.Difficulty;
import ch.heigvd.protocol.Obstacle;
import ch.heigvd.protocol.Protocol;
import ch.heigvd.protocol.Skier;
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
	//TODO replace by skier
	private Skier skier;
	private Direction lastMove;
	 private final Socket client1;
	 private BufferedWriter out1;
	 private Socket client2 = null;
	 private BufferedWriter out2;
	 private final int id;
	 private Difficulty difficulty;
	 private Timer timer;
	//TODO put clients here
	
	public LaunchedGame(int id,int mapWidth, int mapHeight, List<Obstacle> obstacles, Skier initialSkier, Difficulty difficulty, Socket client1) {
		fixedObstacle = obstacles;
		this.difficulty = difficulty;
		dynamicObstacle = new ArrayList<Obstacle>();
		this.mapHeight = mapHeight;
		this.mapWidth = mapWidth;
		skier = initialSkier;
		this.client1 = client1;
		this.id = id;
		timer = new Timer();
		try {
			out1 = new BufferedWriter(
			        new OutputStreamWriter(client1.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
			out1.write(message);
			out1.flush();
			out2.write(message);
			out2.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// start the game in s seconds
	public void start(int s) {
		if(client2 != null) {
			// send that the party start in s seconds
			LocalTime time = LocalTime.now();
			time.plusSeconds(s);
			broadcast(Protocol.formatStartGame(String.valueOf(id), time));
			
			// wait these seconds
			// start the game
			// TODO put timer time in variable
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					tick();
				}
			},Duration.between(LocalTime.now(), time).toMillis(),1000) ;
		}
	}
	
	private void moveSkier() {
		// check bounds
		if(lastMove != null) {
			skier.move(lastMove);
		}
	}
	
	public void addDynamicObstacle(Obstacle o) {
		dynamicObstacle.add(o);
	}
	
	private boolean checkCollision() {
		// check collision with fixed obstacle
		for (Obstacle obstacle : fixedObstacle) {
			if(obstacle.compareToCoordinate(skier.getX(), skier.getY())){
				return true;
			}
		}
		// check collision with dynamic obstacle
		for (Obstacle obstacle : dynamicObstacle) {
			if(obstacle.compareToCoordinate(skier.getX(), skier.getY())){
				return true;
			}
		}
		return false;
	}
	
	private void tick() {
		// Move everything accordingly to the Timer tick
		moveSkier();
		moveDynamicObstacle();
		if(checkCollision())
		{
			// TODO Remove launched Game from game
			broadcast(Protocol.formatSkierWon());
			timer.cancel();
		}
		
		if(checkSkierWon()) {
			broadcast(Protocol.formatVaudoisWon());
			timer.cancel();
		}
		
		lastMove= null;
		// send new informations
		broadcast(Protocol.formatSendSkierPosition(skier));
		broadcast(Protocol.formatSendDynamicObstacle(dynamicObstacle));
	}

	/**
	 * Check if the skier has reach the last row of the map
	 * @return true if the skier has reach the end
	 */
	private boolean checkSkierWon() {
		return skier.getY() >= mapHeight;
	}

	/**
	 * Move each dynamic obstacle 1 step to the right
	 * if obstacle reach the edge of the map remove
	 */
	private void moveDynamicObstacle() {
		for (Obstacle obstacle : dynamicObstacle) {
			obstacle.moveRight();
			if(obstacle.getX() >= mapWidth) {
				dynamicObstacle.remove(obstacle);
			}
		}
	}

}
