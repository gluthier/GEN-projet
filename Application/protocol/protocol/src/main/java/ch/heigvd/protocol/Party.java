package ch.heigvd.protocol;

import org.json.JSONObject;

public class Party implements Sendable{
	
	public static enum FreeRole {
		skier,
		defender;
		
		public static FreeRole fromString(String in) {
			if(in.equals("skier")) {
				return skier;
			}
			else if(in .equals("defender")) {
				return FreeRole.defender;
			} else {
				//TODO throw exception ?
				return null;
			}
				
		}
	}
	
	private final int id;
	private final String playerName;
	private final String difficultyName;
	private final int mapSizeId;
	private final FreeRole freeRole;
	
	
	public Party(int id, String playerName, String difficultyName, int mapSizeId, FreeRole freeRole) {
		this.id = id;
		this.playerName = playerName;
		this.difficultyName = difficultyName;
		this.mapSizeId = mapSizeId;
		this.freeRole = freeRole;
	}
	
	public Party(JSONObject json) {
		this.id = json.getInt("id");
		this.playerName = json.getString("playerName");
		this.difficultyName = json.getString("difficulty");
		this.mapSizeId = json.getInt("mapSize");
		this.freeRole = FreeRole.fromString(json.getString("freeRole"));
	}
	
	public int getId() {
		return id;
	}
	public String getPlayerName() {
		return playerName;
	}
	public String getDifficultyName() {
		return difficultyName;
	}
	public int getMapSizeId() {
		return mapSizeId;
	}
	public FreeRole getFreeRole() {
		return freeRole;
	}

	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("playerName", playerName);
		json.put("difficulty", difficultyName);
		json.put("mapSize", mapSizeId);
		json.put("freeRole", freeRole);
		
		return json;
	}
	
	
	

}
