package ch.heigvd.frogger.tcp;

import ch.heigvd.frogger.GameSettings;
import ch.heigvd.frogger.item.FixedObstacle;
import ch.heigvd.protocol.Difficulty;
import ch.heigvd.protocol.MapSize;
import ch.heigvd.protocol.Party;
import ch.heigvd.protocol.Protocol;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * Created by lognaume on 5/24/17.
 */
public class TCPClient {
    private String serverAddress;
    private int serverPort;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    private String token;
    private List<Difficulty> difficultyList;
    private List<MapSize> mapSizeList;

    public TCPClient(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void connect() throws IOException {
        connect(new Socket(serverAddress, serverPort));

    }

    /**
     * Used mainly for testing
     * @param socket
     * @throws IOException
     */
    public void connect(Socket socket) throws IOException {
        this.socket = socket;
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public boolean login(String user, String password) throws IOException {
        String command = Protocol.formatLoginSend(user, password);

        writer.write(command);
        writer.flush();

        String line = reader.readLine();
        // Read response

        token = Protocol.getFormatLoginToken(line);
        if (token.isEmpty()) {
            return false;
        }
        difficultyList = Protocol.getFormatLoginDifficulty(line);
        mapSizeList = Protocol.getFormatLoginMapSize(line);

        return true;
    }

    public List<Party> connectToLobby() throws IOException {
        String command = Protocol.formatLobbySend(token);

        writer.write(command);
        writer.flush();

        String line = reader.readLine();
        return Protocol.getFormatLobbyParties(line);
    }
/*
    public List<FixedObstacle> joinParty(Party party) {
        // TODO
    }
*/
    public void disconnect() throws IOException {
        socket.close();
        writer.close();
        reader.close();
    }

    public void populateSettings(GameSettings settings) {
        settings.setDifficulties(difficultyList);
        settings.setMapSizes(mapSizeList);
    }
}
