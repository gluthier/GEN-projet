package ch.heigvd.protocol;

import org.json.JSONObject;

public class Obstacle implements Sendable{
	/**
	 * Column
	 */
	private int x;
	/**
	 * Rows
	 */
	private int y;
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public void moveRight() {
		x++;
	}
	public Obstacle(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Compare the position of an obstacle with a point
	 * @param x
	 * @param y
	 * @return true if same position
	 */
	public boolean compareToCoordinate(int x, int y){
		return this.x == x && this.y == y;
	}
	
	public Obstacle(JSONObject json) {
		this.x = json.getInt("x");
		this.y = json.getInt("y");
	}
	
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("x", x);
		json.put("y", y);
		return json;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Obstacle obstacle = (Obstacle) o;

		if (x != obstacle.x) return false;
		return y == obstacle.y;
	}
}
