package ch.heigvd.protocol;

public class Difficulty {
	
	private final int id;
	private final String name;
	private final int manaRegenerationSpeed;
	private final int playerMoveSpeed;
	private final int obstacleMoveSpeed;
	private final int obstacleWidth;
	
	public Difficulty(int id, String name, int manaRegenerationSpeed, int playerMoveSpeed, int obstacleMoveSpeed, int obstacleWidth) {
		this.id = id;
		this.name = name;
		this.manaRegenerationSpeed = manaRegenerationSpeed;
		this.playerMoveSpeed = playerMoveSpeed;
		this.obstacleMoveSpeed = obstacleMoveSpeed;
		this.obstacleWidth = obstacleWidth;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getManaRegenerationSpeed() {
		return manaRegenerationSpeed;
	}
	public int getPlayerMoveSpeed() {
		return playerMoveSpeed;
	}
	public int getObstacleMoveSpeed() {
		return obstacleMoveSpeed;
	}
	public int getObstacleWidth() {
		return obstacleWidth;
	}
	
	//TODO to JSON ?
	
}
