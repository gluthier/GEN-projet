package ch.heigvd.protocol;

import org.json.JSONObject;

import ch.heigvd.protocol.Protocol.Direction;

public class Skier implements Sendable{

	private int x;
	private int y;

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

	public void move(Direction move) {
		// check bounds
			switch (move) {
			case right:
				x++;
				break;
			case left:
				x--;
				break;
			case bottom:
				y++;
				break;
			default:
				break;
			}
		}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Skier skier = (Skier) o;

		if (getX() != skier.getX()) return false;
		return getY() == skier.getY();
	}
}
