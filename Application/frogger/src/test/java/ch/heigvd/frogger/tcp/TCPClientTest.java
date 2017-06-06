package ch.heigvd.frogger.tcp;

import ch.heigvd.frogger.GameSettings;
import ch.heigvd.frogger.item.Obstacle;
import ch.heigvd.protocol.Difficulty;
import ch.heigvd.protocol.MapSize;
import ch.heigvd.protocol.Party;
import ch.heigvd.protocol.Protocol;
import com.google.common.hash.Hashing;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by lognaume on 5/30/17.
 */
public class TCPClientTest {
    private TCPClient client;
    private Socket socket;
    private ByteArrayOutputStream byteArrayOutputStream;

    @Before
    public void setUp() throws Exception {
        // Using Mockito
        socket = Mockito.mock(Socket.class);
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);

        client = new TCPClient("address", 1234);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void connect() throws Exception {
    }

    @Test
    public void loginSendCorrectJSon() throws IOException {
        // Build server's response
        final InputStream inputStream = new ByteArrayInputStream("".getBytes());
        Mockito.when(socket.getInputStream()).thenReturn(inputStream);

        client.connect(socket);

        try {
            client.login("toto", "1234");
        } catch (Exception e) {
        }

        Assert.assertEquals(Protocol.formatLoginSend("toto","1234"), byteArrayOutputStream.toString());
    }

    @Test
    public void wrongLogin() throws IOException {
        // Build server's response
        final InputStream inputStream = new ByteArrayInputStream(Protocol.formatWrongLoginAnswer().getBytes());
        Mockito.when(socket.getInputStream()).thenReturn(inputStream);

        client.connect(socket);

        Assert.assertFalse(client.login("toto", "1234"));
    }

    @Test
    public void correctLogin() throws IOException {
        final List<Difficulty> difficultyList = new LinkedList<>();
        final List<MapSize> mapSizes = new LinkedList<>();

        // Build server's response
        final InputStream inputStream = new ByteArrayInputStream(Protocol.formatLoginAnswer("tok", difficultyList, mapSizes).getBytes());
        Mockito.when(socket.getInputStream()).thenReturn(inputStream);

        client.connect(socket);

        Assert.assertTrue(client.login("toto", "1234"));
    }

    @Test
    public void correctLoginPopulatesSettings() throws IOException {
        // To receive the settings
        GameSettings settings = new GameSettings();

        final List<Difficulty> difficultyList = new LinkedList<>();
        final List<MapSize> mapSizes = new LinkedList<>();

        // Build server's response
        final InputStream inputStream = new ByteArrayInputStream(Protocol.formatLoginAnswer("tok", difficultyList, mapSizes).getBytes());
        Mockito.when(socket.getInputStream()).thenReturn(inputStream);

        client.connect(socket);

        Assert.assertTrue(client.login("toto", "1234"));
        client.populateSettings(settings);
        Assert.assertEquals(difficultyList, settings.getDifficulties());
        Assert.assertEquals(mapSizes, settings.getMapSizes());
    }

    @Test
    public void connectToLobbyReturnsCorrectPartyList() throws IOException {
        final List<Party> partyList = new LinkedList<>();
        partyList.add(new Party(0, "Player1","Easy", 0, Party.FreeRole.defender));
        partyList.add(new Party(1, "Player2","Medium", 0, Party.FreeRole.defender));
        partyList.add(new Party(2, "Player3","Hard", 0, Party.FreeRole.skier));

        // Build server's response

        final InputStream inputStream = new ByteArrayInputStream(Protocol.formatLobbyAnswer(partyList).getBytes());
        Mockito.when(socket.getInputStream()).thenReturn(inputStream);

        client.connect(socket);

        List<Party> partiesRead = client.connectToLobby();
        Assert.assertEquals(partyList, partiesRead);
    }

    @Test
    public void connectToPartyReturnsCorrectObstacleList() throws IOException {
     
    }
}