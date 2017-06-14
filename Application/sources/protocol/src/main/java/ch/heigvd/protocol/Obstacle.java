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

	private int id;
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {this.id = id; }
	
	public void moveRight() {
		x++;
	}
	public Obstacle(int x, int y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}

	public Obstacle(int x, int y) {
		this(x, y, -1);
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
		this.id = json.getInt("id");
	}
	
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("x", x);
		json.put("y", y);
		json.put("id", id);
		return json;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Obstacle obstacle = (Obstacle) o;

		return id == obstacle.id;
	}
}
