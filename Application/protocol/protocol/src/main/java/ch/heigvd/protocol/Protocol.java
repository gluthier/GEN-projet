package ch.heigvd.protocol;

import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.common.hash.Hashing;

/**
 * Protocol
 *
 * @author Tony Clavien
 * @author Maxime Guillod Define the message exchanged between clients and
 * server
 */
public class Protocol {

    public static final int PORT = 1234;

    //TODO constants for inside JSON i.e. param, difficulties, etc...
    public static enum Direction {
        right,
        left,
        bottom;
    }

    // TODO enum of all commands
    public static enum command implements Sendable {
        login("login"),
        getlobby("get-lobby"),
        join("join"),
        moveSkier("move-skier"),
        addObstacle("add-obstacle"),
        skierWon("skier-won"),
        vaudoisWon("vaudois-won"),
        skierPosition("skier-position"),
        obstaclesPositions("obstacles-positions"),
        createParty("create-party"),
        startParty("start-party");

        private String value;

        private command(String val) {
            value = val;
        }

        @Override
        public String toString() {
            return value;
        }

        public JSONObject toJson() {
            return null;
        }

        public static command fromString(String string) {
            for (command c : values()) {
                if (c.value.equals(string)) {
                    return c;
                }
            }
            return null;
        }
    }

    public static <T extends Sendable> String formatArraytoJson(Collection<T> ls) {
        JSONArray array = new JSONArray();
        for (Sendable s : ls) {
            array.put(s.toJson());
        }

        return array.toString() + "\n";
    }

    public static String getJsonParam(String message, String object, String param) {
        JSONObject json = new JSONObject(message);
        if (object != null) {
            JSONObject ret = json.getJSONObject(object);
            return ret.getString(param);
        } else {
            return json.getString(param);
        }
    }

    /*
    {"command":"login","param":{"user":"maxime","password":"coucou"}}
     */
    public static String formatLoginSend(String user, String password) {
        JSONObject json = new JSONObject();
        json.put("command", command.login);
        JSONObject param = new JSONObject();
        param.put("user", user);
        param.put("password", Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString());
        json.put("param", param);
        return json.toString() + "\n";
    }

    public static String getFormatLoginUser(String message) {
        return getJsonParam(message, "param", "user");
    }

    public static String getFormatLoginPassword(String message) {
        return getJsonParam(message, "param", "password");
    }

    public static String formatWrongLoginAnswer() {
        JSONObject json = new JSONObject();
        json.put("token", "");
        return json.toString() + "\n";
    }

    public static String formatLoginAnswer(String token, List<Difficulty> difficulty, List<MapSize> map) {
        JSONObject json = new JSONObject();
        json.put("token", token);
        JSONArray difficultyArray = new JSONArray();
        for (Difficulty diff : difficulty) {
            difficultyArray.put(diff.toJson());
        }
        json.put("difficulties", difficultyArray);
        JSONArray mapArray = new JSONArray();
        for (MapSize m : map) {
            mapArray.put(m.toJson());
        }
        json.put("mapSizes", mapArray);

        return json.toString() + "\n";
    }

    public static String getFormatLoginToken(String message) {
        return getJsonParam(message, null, "token");
    }

    public static List<Difficulty> getFormatLoginDifficulty(String message) {
        JSONObject json = new JSONObject(message);
        JSONArray difficulties = json.getJSONArray("difficulties");
        List<Difficulty> diff = new LinkedList<Difficulty>();
        for (int i = 0; i < difficulties.length(); i++) {
            diff.add(new Difficulty(difficulties.getJSONObject(i)));
        }
        return diff;
    }

    public static List<MapSize> getFormatLoginMapSize(String message) {
        JSONObject json = new JSONObject(message);
        JSONArray mapSizes = json.getJSONArray("mapSizes");
        List<MapSize> sizes = new LinkedList<MapSize>();
        for (int i = 0; i < mapSizes.length(); i++) {
            sizes.add(new MapSize(mapSizes.getJSONObject(i)));
        }
        return sizes;
    }

    public static String formatLobbySend(String token) {
        JSONObject json = new JSONObject();
        json.put("command", command.getlobby);
        JSONObject param = new JSONObject();
        param.put("token", token);
        json.put("param", param);
        return json.toString() + "\n";
    }

    public static String formatCreateParty(String token, Party party) {
        JSONObject json = new JSONObject();
        json.put("command", command.createParty);
        JSONObject param = new JSONObject();
        param.put("token", token);
        param.put("party", party.toJson());
        json.put("param", param);
        return json.toString() + "\n";
    }

    public static String formatStartGame(Party party, long time) {
        JSONObject json = new JSONObject();
        json.put("command", command.startParty);
        JSONObject param = new JSONObject();
        param.put("party", party.toJson());
        param.put("time", time);
        json.put("param", param);
        return json.toString()  + "\n";
    }

    public static Party getParamParty(String message) {
        JSONObject object = new JSONObject(message);
        return new Party(object.getJSONObject("param").getJSONObject("party"));
    }

    public static long getFormatStartGameTime(String message) {
        return new JSONObject(message).getJSONObject("param").getLong("time");
    }

    public static String getFormatCreatePartyToken(String message) {
        return getJsonParam(message, "param", "token");
    }

    public static Difficulty getFormatCreatePartyDifficulty(String message) {
        return getParamParty(message).getDifficulty();
    }

    public static MapSize getFormatCreatePartyMapSize(String message) {
        return getParamParty(message).getMapSize();
    }

    public static String getFormatLobbyToken(String message) {
        return getJsonParam(message, "param", "token");
    }

    public static String formatLobbyAnswer(Collection<Party> parties) {
        return formatArraytoJson(parties);
    }

    public static List<Party> getFormatLobbyParties(String message) {
        JSONArray array = new JSONArray(message);
        List<Party> parties = new LinkedList();
        for (int i = 0; i < array.length(); i++) {
            parties.add(new Party(array.getJSONObject(i)));
        }
        return parties;

    }

    public static String formatJoinSend(String token, Party party) {
        JSONObject json = new JSONObject();
        json.put("command", command.join);
        JSONObject param = new JSONObject();
        param.put("token", token);
        param.put("party", party.toJson());
        json.put("param", param);
        return json.toString() + "\n";
    }

    public static String getFormatJoinToken(String message) {
        return getJsonParam(message, "param", "token");
    }

    public static String formatJoinAnswer(Collection<Obstacle> ls) {
        JSONObject json = new JSONObject();
        json.put("command",command.obstaclesPositions);
        json.put("fixedObstacles",ls);
        return json.toString() + "\n";
    }

    public static List<Obstacle> getFormatJoinObstacle(String message) {
        JSONArray array = new JSONObject(message).getJSONArray("fixedObstacles");
        return getObstacles(array);
    }

    public static List<Obstacle> getFormatDynamicObstacles(String message) {
        JSONArray array = new JSONObject(message).getJSONArray("dynamicObstacles");
        return getObstacles(array);
    }

    private static List<Obstacle> getObstacles(JSONArray array) {
        List<Obstacle> obstacles = new LinkedList<Obstacle>();
        for (int i = 0; i < array.length(); i++) {
            obstacles.add(new Obstacle(array.getJSONObject(i)));
        }
        return obstacles;
    }

    public static command getFormatCommand(String message) {
        JSONObject json = new JSONObject(message);
        return command.fromString(json.getString("command"));
    }

    public static Skier getFormatSkier(String message) {
        JSONObject json = new JSONObject(message);
        return (Skier) json.get("skier");
    }

    public static String formatMoveSend(Direction dir) {
        JSONObject json = new JSONObject();
        json.put("command", command.moveSkier);
        JSONObject param = new JSONObject();
        param.put("direction", dir);
        json.put("param", param);
        return json.toString() + "\n";
    }

    public static Direction getFormatMove(String message) {
        return Direction.valueOf(getJsonParam(message, "param", "direction"));
    }

    public static String formatNewDynamicObstacle(int row) {
        JSONObject json = new JSONObject();
        json.put("command", command.addObstacle);
        JSONObject param = new JSONObject();
        param.put("row", row);
        json.put("param", param);
        return json.toString() + "\n";
    }
    
    public static String formatSkierWon() {
    	JSONObject json = new JSONObject();
        json.put("command", command.skierWon);
        return json.toString() + "\n";
    }
    
    public static String formatVaudoisWon() {
    	JSONObject json = new JSONObject();
        json.put("command", command.vaudoisWon);
        return json.toString() + "\n";
    }

    public static String formatSendSkierPosition(Skier skier) {
    	JSONObject json = new JSONObject();
        json.put("command", command.skierPosition);
        json.put("skier", skier);
        return json.toString() + "\n";
    }
    
    public static String formatSendDynamicObstacle(List<Obstacle> obstacles) {
    	JSONObject json = new JSONObject();
        json.put("command", command.skierPosition);
        JSONArray obstacleArray = new JSONArray();
        for (Obstacle obstacle : obstacles) {
        	obstacleArray.put(obstacle.toJson());
        }
        json.put("dynamicObstacles", obstacleArray);

        return json.toString() + "\n";
    }
}
