package ch.heigvd.protocol;

import com.google.common.hash.Hashing;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lognaume on 6/7/17.
 */
public class ProtocolTest {
    @Test
    public void testFormatArraytoJson() throws Exception {

    }

    @Test
    public void testGetJsonParam() throws Exception {
    }

    @Test
    public void testFormatLoginSend() throws Exception {
        String message = Protocol.formatLoginSend("test", "1234");

        JSONObject test = new JSONObject(message);
        assertEquals(test.getString("command"), "login");
        JSONObject param = test.getJSONObject("param");
        assertEquals(param.getString("user"), "test");
        assertEquals(param.getString("password"), Hashing.sha256().hashString("1234", StandardCharsets.UTF_8).toString());
    }

    public void testFormatLoginName() {
        String message = Protocol.formatLoginSend("test", "1234");
        assertEquals("test", Protocol.getFormatLoginUser(message));
    }

    public void testFormatLoginPassword() {
        String message = Protocol.formatLoginSend("test", "1234");
        assertEquals(Hashing.sha256().hashString("1234", StandardCharsets.UTF_8).toString(), Protocol.getFormatLoginPassword(message));
    }

    @Test
    public void testGetFormatLoginUser() throws Exception {
    }

    @Test
    public void testGetFormatLoginPassword() throws Exception {
    }

    @Test
    public void testFormatWrongLoginAnswer() throws Exception {
    }

    @Test
    public void testFormatLoginAnswer() throws Exception {
        List<Difficulty> difficulty = new LinkedList<Difficulty>();
        List<MapSize> map = new LinkedList<MapSize>();

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