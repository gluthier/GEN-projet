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
	
	public MapSize(JSONObject json) {
		this.id = json.getInt("id");
		this.name = json.getString("name");
		this.width = json.getInt("width");
		this.height = json.getInt("height");	
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
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("name", name);
		json.put("width", width);
		json.put("height", height);
		return json;
	}
}
