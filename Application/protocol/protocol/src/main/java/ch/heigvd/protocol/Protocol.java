package ch.heigvd.protocol;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.common.hash.Hashing;

/**
 * Protocol
 *
 * @author Tony Clavien
 * @author Maxime Guillod Define the message exchanged between clients and server
 */
public class Protocol {

    public static final int PORT = 1234;

    //TODO constants for inside JSON i.e. param, difficulties, etc...
    public static enum Direction {
        right,
        left,
        bottom;

        public static Direction fromString(String in) {
            if (in.equals("right")) {
                return Direction.right;
            } else if (in.equals("left")) {
                return Direction.left;
            } else if (in.equals("bottom")) {
                return Direction.bottom;
            } else {
                // throw exepction ?
                return null;
            }
        }
    }

    // TODO enum of all commands
    public static enum command {
        login("login"),
        getlobby("get-lobby"),
        join("join"),
        moveSkier("move-skier"),
        addObstacle("add-obstacle");

        private String value;

        private command(String val) {
            value = val;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    /**
     * Prepare the request to ask the client to login
     *
     * @param isLogged
     * @return
     */
    public static String formatRequestLogin(boolean isLogged) {
        JSONObject json = new JSONObject();
        json.put("logged", isLogged);
        return json.toString();
    }

    /**
     * Get the server response if the login is correct
     *
     * @param message
     * @return
     */
    public static boolean getRequestLogin(String message) {
        JSONObject json = new JSONObject(message);
        if ("true".equals(getJsonParam(message, "param", "logged"))) {
            return true;
        }
        return false;
    }

    public static String formatArraytoJson(List<Sendable> ls) {
        JSONArray array = new JSONArray();
        for (Sendable s : ls) {
            array.put(s.toJson());
        }

        return array.toString();
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

    public static String formatLoginSend(String user, String password) {
        JSONObject json = new JSONObject();
        json.put("command", command.login);
        JSONObject param = new JSONObject();
        param.put("user", user);
        param.put("password", Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString());
        json.put("param", param);
        return json.toString();
    }

    public static String getFormatLoginUser(String message) {
        return getJsonParam(message, "param", "user");
    }

    public static String getFormatLoginPassword(String message) {
        return getJsonParam(message, "param", "password");
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
            difficultyArray.put(m.toJson());
        }
        json.put("mapSizes", mapArray);

        return json.toString();
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
        return json.toString();
    }

    public static String getFormatLobbyToken(String message) {
        return getJsonParam(message, "param", "token");
    }

    public static String formatLobbyAnswer(List<Sendable> parties) {
        return formatArraytoJson(parties);
    }

    public static List<Party> getFormatLobbyParties(String message) {
        JSONArray array = new JSONArray(message);
        List<Party> parties = new LinkedList<Party>();
        for (int i = 0; i < array.length(); i++) {
            parties.add(new Party(array.getJSONObject(i)));
        }
        return parties;

    }

    public static String formatJoinSend(String token, int id) {
        JSONObject json = new JSONObject();
        json.put("command", "join");
        JSONObject param = new JSONObject();
        param.put("token", token);
        param.put("id", id);
        json.put("param", param);
        return json.toString();
    }

    public static String getFormatJoinToken(String message) {
        return getJsonParam(message, "param", "token");
    }

    public static String getFormatJoinId(String message) {
        return getJsonParam(message, "param", "id");
    }

    public static String formatJoinAnswer(List<Sendable> ls) {
        return formatArraytoJson(ls);
    }

    public static List<Obstacle> getFormatJoinObstacle(String message) {
        JSONArray array = new JSONObject(message).getJSONArray("fixedObstacles");
        List<Obstacle> obstacles = new LinkedList<Obstacle>();
        for (int i = 0; i < array.length(); i++) {
            obstacles.add(new Obstacle(array.getJSONObject(i)));
        }
        return obstacles;
    }

    public static String formatMoveSend(Direction dir) {
        JSONObject json = new JSONObject();
        json.put("command", command.moveSkier);
        JSONObject param = new JSONObject();
        param.put("direction", dir);
        json.put("param", param);
        return json.toString();
    }

    public static Direction getFormatMove(String message) {
        return Direction.fromString(getJsonParam(message, "param", "direction"));
    }

    public static String formatNewDynamicObstacle(int row) {
        JSONObject json = new JSONObject();
        json.put("command", command.addObstacle);
        JSONObject param = new JSONObject();
        param.put("row", row);
        json.put("param", param);
        return json.toString();
    }

    // TODO send skier coordinate
    // TODO send new Dynamic obstacle coordinate
}
