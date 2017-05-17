package ch.heigvd.protocol;

import org.json.JSONObject;

public class MapSize implements Sendable{

	private final int id;
	private final String name;
	private final int width;
	private final int height;
	
	public MapSize(int id, String name, int width, int height) {
		this.id = id;
		this.name = name;
		this.width = width;
		this.height = height;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public JSONObject toJson() {
		// TODO Auto-generated method stub
		return null;
	}
}
