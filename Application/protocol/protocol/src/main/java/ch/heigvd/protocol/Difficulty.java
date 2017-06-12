package ch.heigvd.protocol;

import org.json.JSONObject;

public class Difficulty implements Sendable{
	
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
	
	public Difficulty(JSONObject json) {
		this.id = json.getInt("id");
		this.name = json.getString("name");
		this.manaRegenerationSpeed = json.getInt("manaRegenerationSpeed");
		this.playerMoveSpeed = json.getInt("playerMoveSpeed");
		this.obstacleMoveSpeed = json.getInt("obstacleMoveSpeed");
		this.obstacleWidth = json.getInt("obstacleWidth");
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
	
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("name", name);
		json.put("manaRegenerationSpeed", manaRegenerationSpeed);
		json.put("playerMoveSpeed", playerMoveSpeed);
		json.put("obstacleMoveSpeed", obstacleMoveSpeed);
		json.put("obstacleWidth", obstacleWidth);
		
		return json;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Difficulty that = (Difficulty) o;

		if (getId() != that.getId()) return false;
		if (getManaRegenerationSpeed() != that.getManaRegenerationSpeed()) return false;
		if (getPlayerMoveSpeed() != that.getPlayerMoveSpeed()) return false;
		if (getObstacleMoveSpeed() != that.getObstacleMoveSpeed()) return false;
		if (getObstacleWidth() != that.getObstacleWidth()) return false;
		return getName().equals(that.getName());
	}
}
