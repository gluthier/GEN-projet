package ch.heigvd.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ch.heigvd.protocol.Difficulty;
import ch.heigvd.protocol.Obstacle;
import ch.heigvd.protocol.Protocol;
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
	 private BufferedWriter out1;
	 private Socket client2 = null;
	 private BufferedWriter out2;
	 private final int id;
	 private Difficulty difficulty;
	//TODO put clients here
	
	public LaunchedGame(int id,int mapWidth, int mapHeight, List<Obstacle> obstacles, int initialX, int initialY, Difficulty difficulty, Socket client1) {
		fixedObstacle = obstacles;
		this.difficulty = difficulty;
		dynamicObstacle = new ArrayList<Obstacle>();
		this.mapHeight = mapHeight;
		this.mapWidth = mapWidth;
		skierX = initialX;
		skierY = initialY;
		this.client1 = client1;
		this.id = id;
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
	
	// start the game in s seconds
	public void start(int s) {
		if(client2 != null) {
			// send that the party start in s seconds
			LocalTime time = LocalTime.now();
			time.plusSeconds(s);
			try {
				out1.write(Protocol.formatStartGame(String.valueOf(id), time));
				out1.flush();
				out2.write(Protocol.formatStartGame(String.valueOf(id), time));
				out2.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// wait these seconds
			// start the game
			Timer timer = new Timer();
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
		if(checkCollision())
		{
			// TODO send skier loose
		}
		//TODO check skier has read the end
		lastMove= null;
	}

}
