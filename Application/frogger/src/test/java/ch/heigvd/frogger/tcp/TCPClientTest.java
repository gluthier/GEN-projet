package ch.heigvd.frogger.tcp;

import ch.heigvd.frogger.GameSettings;
import ch.heigvd.protocol.Difficulty;
import ch.heigvd.protocol.MapSize;
import ch.heigvd.protocol.Protocol;
import com.google.common.hash.Hashing;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by lognaume on 5/30/17.
 */
public class TCPClientTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void connect() throws Exception {
    }

    @Test
    public void loginSendCorrectJSon() throws IOException {
        // Using Mockito
        final Socket socket = Mockito.mock(Socket.class);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);

        final InputStream inputStream = new ByteArrayInputStream("".getBytes());
        Mockito.when(socket.getInputStream()).thenReturn(inputStream);

        TCPClient client = new TCPClient("address", 1234);
        client.connect(socket);

        try {
            client.login("toto", "1234");
        } catch (Exception e) {
        }

        Assert.assertEquals(Protocol.formatLoginSend("toto","1234"), byteArrayOutputStream.toString());
    }

    @Test
    public void wrongLogin() throws IOException {
        // Using Mockito
        final Socket socket = Mockito.mock(Socket.class);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);

        final InputStream inputStream = new ByteArrayInputStream(Protocol.formatWrongLoginAnswer().getBytes());
        Mockito.when(socket.getInputStream()).thenReturn(inputStream);

        TCPClient client = new TCPClient("address", 1234);
        client.connect(socket);

        Assert.assertFalse(client.login("toto", "1234"));
    }

    @Test
    public void correctLogin() throws IOException {
        // Using Mockito
        final Socket socket = Mockito.mock(Socket.class);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);

        final List<Difficulty> difficultyList = new LinkedList<>();
        final List<MapSize> mapSizes = new LinkedList<>();

        final InputStream inputStream = new ByteArrayInputStream(Protocol.formatLoginAnswer("tok", difficultyList, mapSizes).getBytes());
        Mockito.when(socket.getInputStream()).thenReturn(inputStream);

        TCPClient client = new TCPClient("address", 1234);
        client.connect(socket);

        Assert.assertTrue(client.login("toto", "1234"));
    }

    @Test
    public void correctLoginPopulatesSettings() throws IOException {
        // To receive the settings
        GameSettings settings = new GameSettings();

        // Using Mockito
        final Socket socket = Mockito.mock(Socket.class);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);

        final List<Difficulty> difficultyList = new LinkedList<>();
        final List<MapSize> mapSizes = new LinkedList<>();

        final InputStream inputStream = new ByteArrayInputStream(Protocol.formatLoginAnswer("tok", difficultyList, mapSizes).getBytes());
        Mockito.when(socket.getInputStream()).thenReturn(inputStream);

        TCPClient client = new TCPClient("address", 1234);
        client.connect(socket);

        Assert.assertTrue(client.login("toto", "1234"));
        client.populateSettings(settings);
        Assert.assertEquals(difficultyList, settings.getDifficulties());
        Assert.assertEquals(mapSizes, settings.getMapSizes());
    }
}