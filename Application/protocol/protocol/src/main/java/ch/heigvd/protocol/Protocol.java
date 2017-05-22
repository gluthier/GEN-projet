package ch.heigvd.protocol;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.common.hash.Hashing;

/**
 * Protocol
 * @author Tony Clavien
 * @author Maxime Guillod
 * Define the message exchanged between clients and server
 */
public class Protocol {
    
    public static final int PORT = 1234;
	
	public static enum FreeRole {
		skier,
		defender
	}
	
	public static enum Direction {
		right,
		left,
		bottom
	}
	
    public static String formatLoginSend(String user, String password) {
    	JSONObject json = new JSONObject();
    	json.put("command", "login");
    	JSONObject param = new JSONObject();
    	param.put("user", user);
    	param.put("password", Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString());
    	json.put("param", param);
    	return json.toString();
    }
    
    public static String getFormatLoginUser(String message) {
    	JSONObject json = new JSONObject(message);
		JSONObject param = json.getJSONObject("param");
		return param.getString("user");
    }
    
    public static String getFormatLoginPassword(String message) {
    	JSONObject json = new JSONObject(message);
		JSONObject param = json.getJSONObject("param");
		return param.getString("password");
    }
    
    public static String formatLoginAnswer(String token,List<Difficulty> difficulty, List<MapSize> map) {
    	JSONObject json = new JSONObject();
    	json.put("token", token);
    	JSONArray difficultyArray = new JSONArray();
    	for (Difficulty diff : difficulty) {
			difficultyArray.put(diff.toJson());
		}
    	json.put("difficulties", difficultyArray);
    	JSONArray mapArray = new JSONArray();
    	for (MapSize m : map) {
			difficultyArray.put(m.toJson());
		}
    	json.put("mapSizes", mapArray);
    	
    	return json.toString();
    }
    
    public static String getFormatLoginToken(String message) {
    	return "not implemented yet";
    }
    
    public static List<Difficulty> getFormatLoginDifficulty(String message) {
    	return null;
    }
    
    public static List<MapSize> getFormatLoginMapSize(String message) {
    	return null;
    }
    
    public static String formatLobbySend(String token) {
    	return "not implemented yet";
    }
    
    public static String getFormatLobbyToken(String message) {
    	return "not implemented yet";
    }
    
    public static String formatLobbyAnswer(int id, String name, String difficulty, String mapSize, FreeRole free) {
    	return "not implemented yet";
    }
    
    public static String getFormatLobbyId(String message) {
    	return "not implemented yet";
    }
    
    public static String getFormatLobbyName(String message) {
    	return "not implemented yet";
    }
    
    public static String getFormatLobbyDifficulty(String message) {
    	return "not implemented yet";
    }
    
    public static String getFormatLobbyMapSize(String message) {
    	return "not implemented yet";
    }
    
    public static FreeRole getFormatLobbyFreeRole(String message) {
    	return null;
    }
    
    public static String formatJoinSend(String token, int id) {
    	return "not implemented yet";
    }
    
    public static String getFormatJoinToken(String message) {
    	return "not implemented yet";
    }
    
    public static String getFormatJoinId(String message) {
    	return "not implemented yet";
    }
    
    public static String formatJoinAnswer(List<FixedObstacle> ls) {
    	return "not implemented yet";
    }
    
    public static List<FixedObstacle> getFormatJoinObstacle(String message) {
    	return null;
    }
    
    public static String formatMoveSend(Direction dir) {
    	return "not implemented yet";
    }
    
    public static Direction getFormatMove(String message) {
    	return null;
    }
    
    public static String formatNewDynamicObstacle(int row) {
    	return "not implemented yet";
    }
    
    // TODO send skier coordinate
    // TODO send new Dynamic obstacle coordinate
}
