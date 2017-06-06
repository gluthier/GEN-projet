package ch.heigvd.protocol;

import org.json.JSONObject;

public class Skier implements Sendable{

	private final int x;
	private final int y;

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Skier(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Skier(JSONObject json) {
		this.x = json.getInt("x");
		this.y = json.getInt("y");
	}
	
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("x", x);
		json.put("y", y);
		return json;
	}
}
