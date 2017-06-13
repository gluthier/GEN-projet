package ch.heigvd.protocol;

import com.google.common.hash.Hashing;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Created by lognaume on 6/7/17.
 *
 * @author Gabriel Luthier
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

    @Test
    public void testGetFormatLoginDifficulty() throws Exception {
    }

    @Test
    public void testGetFormatLoginMapSize() throws Exception {
    }

    @Test
    public void testFormatLobbySend() throws Exception {
    }

    @Test
    public void testFormatCreateParty() throws Exception {
    }

    @Test
    public void testFormatStartGame() throws Exception {
    }

    @Test
    public void testGetFormatStartGameId() throws Exception {
    }

    @Test
    public void testGetFormatStartGameTime() throws Exception {
    }

    @Test
    public void testGetFormatCreatePartyToken() throws Exception {
    }

    @Test
    public void testGetFormatCreatePartyDifficultyId() throws Exception {
    }

    @Test
    public void testGetFormatCreatePartyMapSizeId() throws Exception {
    }

    @Test
    public void testGetFormatLobbyToken() throws Exception {
    }

    @Test
    public void testFormatLobbyAnswer() throws Exception {
    }

    @Test
    public void testGetFormatLobbyParties() throws Exception {
    }

    @Test
    public void testFormatJoinSend() throws Exception {
    }

    @Test
    public void testGetFormatJoinToken() throws Exception {
    }

    @Test
    public void testGetFormatJoinId() throws Exception {
    }

    @Test
    public void testFormatJoinAnswer() throws Exception {
    }

    @Test
    public void testGetFormatJoinObstacle() throws Exception {
    }

    @Test
    public void testGetFormatDynamicObstacles() throws Exception {
    }

    @Test
    public void testGetObstacles() throws Exception {
    }

    @Test
    public void testGetFormatCommand() throws Exception {
    }

    @Test
    public void testGetFormatSkier() throws Exception {
    }

    @Test
    public void testFormatMoveSend() throws Exception {
    }

    @Test
    public void testGetFormatMove() throws Exception {
    }

    @Test
    public void testFormatNewDynamicObstacle() throws Exception {
    }
}
