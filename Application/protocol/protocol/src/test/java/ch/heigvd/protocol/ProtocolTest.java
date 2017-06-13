package ch.heigvd.protocol;

import com.google.common.hash.Hashing;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * @author Tony Clavien
 * @author Maxime Guillod
 * @author Gabriel Luthier
 * @author Guillaume Milani
 */
public class ProtocolTest {

    @Test
    public void testFormatArraytoJson() throws Exception {
        LinkedList<Sendable> testList = new LinkedList<Sendable>();
        testList.add(new Skier(0, 1));
        testList.add(new Difficulty(1, "medium", 5, 4, 3, 20));

        String message = Protocol.formatArraytoJson(testList);
        JSONArray testArray = new JSONArray(message);

        JSONObject testSkierObject = testArray.getJSONObject(0);
        JSONObject testDifficultyObject = testArray.getJSONObject(1);

        assertEquals(0, testSkierObject.get("x"));
        assertEquals(1, testSkierObject.get("y"));

        assertEquals(1, testDifficultyObject.get("id"));
        assertEquals("medium", testDifficultyObject.get("name"));
        assertEquals(5, testDifficultyObject.get("manaRegenerationSpeed"));
        assertEquals(4, testDifficultyObject.get("playerMoveSpeed"));
        assertEquals(3, testDifficultyObject.get("obstacleMoveSpeed"));
        assertEquals(20, testDifficultyObject.get("obstacleWidth"));
    }

    @Test
    public void testGetJsonParam() throws Exception {
        // {"command":"login","param":{"user":"maxime","password":"coucou"}}
        String message = "{\"command\":\"login\",\"param\":{\"user\":\"maxime\",\"password\":\"coucou\"}}";

        assertEquals("maxime", Protocol.getJsonParam(message, "param", "user"));
        assertEquals("coucou", Protocol.getJsonParam(message, "param", "password"));

        assertEquals("login", Protocol.getJsonParam(message, null, "command"));
    }

    @Test
    public void testFormatLoginSend() throws Exception {
        String message = Protocol.formatLoginSend("test", "1234");

        JSONObject test = new JSONObject(message);
        assertEquals("login", test.getString("command"));

        JSONObject param = test.getJSONObject("param");
        assertEquals("test", param.getString("user"));
        assertEquals(Hashing.sha256().hashString("1234", StandardCharsets.UTF_8).toString(), param.getString("password"));
    }

    @Test
    public void testGetFormatLoginUser() {
        String message = Protocol.formatLoginSend("test", "1234");
        assertEquals("test", Protocol.getFormatLoginUser(message));
    }

    @Test
    public void testGetFormatLoginPassword() {
        String message = Protocol.formatLoginSend("test", "1234");
        assertEquals(Hashing.sha256().hashString("1234", StandardCharsets.UTF_8).toString(), Protocol.getFormatLoginPassword(message));
    }

    @Test
    public void testFormatWrongLoginAnswer() throws Exception {
        String message = Protocol.formatWrongLoginAnswer();
        JSONObject test = new JSONObject(message);
        assertEquals("", test.getString("token"));
    }

    @Test
    public void testFormatLoginAnswer() throws Exception {
        LinkedList<Difficulty> difficulty = new LinkedList<Difficulty>();
        LinkedList<MapSize> map = new LinkedList<MapSize>();

        difficulty.add(new Difficulty(1, "medium", 5, 4, 3, 20));
        map.add(new MapSize(1, "medium", 200, 100));

        String answer = Protocol.formatLoginAnswer("1234", difficulty, map);

        JSONObject test = new JSONObject(answer);
        assertEquals("1234", test.get("token"));
        JSONArray difficulties = test.getJSONArray("difficulties");
        JSONArray mapSizes = test.getJSONArray("mapSizes");

        assertEquals(difficulty.get(0), new Difficulty(difficulties.getJSONObject(0)));
        assertEquals(map.get(0), new MapSize(mapSizes.getJSONObject(0)));
    }

    @Test
    public void testGetFormatLoginToken() throws Exception {
        // {"token":"9164108374"}
        String message = "{\"token\":\"9164108374\"}";
        assertEquals("9164108374", Protocol.getFormatLoginToken(message));
    }
}
