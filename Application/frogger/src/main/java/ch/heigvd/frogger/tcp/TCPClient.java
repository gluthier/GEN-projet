package ch.heigvd.frogger.tcp;

import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.GameSettings;
import ch.heigvd.frogger.item.DynamicObstacle;
import ch.heigvd.frogger.item.FixedObstacle;
import ch.heigvd.frogger.item.Item;
import ch.heigvd.frogger.item.Player;
import ch.heigvd.protocol.*;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ch.heigvd.protocol.Protocol.getFormatJoinObstacle;

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

    private List<DynamicObstacle> dynamicObstacles;

    private Skier skier;

    private boolean skierWon = false;
    private boolean vaudoisWon = false;

    public TCPClient(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;

        dynamicObstacles = new LinkedList<>();
        skier = new Skier(0, 0);
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

        writer.write(command+"\n");
        writer.flush();

        String line;

        // Read response
        line = reader.readLine();
        Logger.getLogger(TCPClient.class.getName()).log(Level.INFO, "Received" + line);

        token = Protocol.getFormatLoginToken(line);
        if (token.isEmpty()) {
            return false;
        }
        difficultyList = Protocol.getFormatLoginDifficulty(line);
        mapSizeList = Protocol.getFormatLoginMapSize(line);

        Logger.getLogger(TCPClient.class.getName()).log(Level.INFO, "Connected to server "+Constants.SERVER_ADDRESS+":"+Constants.SERVER_PORT);

        return true;
    }

    public List<Party> connectToLobby() throws IOException {
        String command = Protocol.formatLobbySend(token);
        Logger.getLogger(TCPClient.class.getName()).log(Level.INFO, "Connecting to lobby...");
        writer.write(command+"\n");
        writer.flush();

        String line = reader.readLine();
        List<Party> lobby = Protocol.getFormatLobbyParties(line);
        Logger.getLogger(TCPClient.class.getName()).log(Level.INFO, "Connected to lobby");
        return lobby;
    }

    public List<FixedObstacle> joinParty(Party party) throws IOException {
        String command = Protocol.formatJoinSend(token, party.getId());
        Logger.getLogger(TCPClient.class.getName()).log(Level.INFO, "Joining party #"+party.getId());
        writer.write(command+"\n");
        writer.flush();

        String line = reader.readLine();

        List<Obstacle> obstacles = Protocol.getFormatJoinObstacle(line);
        List<FixedObstacle> fixedObstacles = new LinkedList<>();
        obstacles.forEach(o -> fixedObstacles.add(new FixedObstacle(o.getX(), o.getY(), Constants.ItemType.Sapin)));

        Logger.getLogger(TCPClient.class.getName()).log(Level.INFO, "Joined party #"+party.getId());
        return fixedObstacles;
    }

    public void createParty(Party party) {
        String command = Protocol.formatCreateParty(token, party);
    }

    public void moveLeft() {
        move(Protocol.Direction.left);
    }

    public void moveRight() {
        move(Protocol.Direction.right);
    }

    public void moveBottom() {
        move(Protocol.Direction.bottom);
    }

    private void move(Protocol.Direction direction) {
        String command = Protocol.formatMoveSend(direction);
        Logger.getLogger(TCPClient.class.getName()).log(Level.INFO, "Trying to move "+direction);
        writer.write(command+"\n");
        writer.flush();
    }

    public void addDynamicObstacle(int row) {
        String command = Protocol.formatNewDynamicObstacle(row);

        writer.write(command+"\n");
        writer.flush();
    }

    /**
     * Try to fetch info from server
     * @return true if at least one command has been read
     */
    public void fetchServerInfos() throws IOException {
        String line;

        while ((line = reader.readLine()) != null) {
            fetch(Protocol.getFormatCommand(line), line);
        }
    }

    private void fetch(Protocol.command cmd, String message) {
        switch (cmd) {
            case skierPosition:
                skier = Protocol.getFormatSkier(message);
                break;
            case obstaclesPositions:
                List<Obstacle> obs = Protocol.getFormatDynamicObstacles(message);
                dynamicObstacles.clear();
                obs.forEach(o -> dynamicObstacles.add(new DynamicObstacle(o.getX(), o.getY(), Constants.ItemType.Saucisson)));
                break;
            case skierWon:
                skierWon = true;
                break;
            case vaudoisWon:
                vaudoisWon = true;
                break;
        }
    }

    public void disconnect() throws IOException {
        socket.close();
        writer.close();
        reader.close();
    }

    public void populateSettings(GameSettings settings) {
        settings.setDifficulties(difficultyList);
        settings.setMapSizes(mapSizeList);
    }

    public List<DynamicObstacle> getDynamicObstacles() {
        return dynamicObstacles;
    }

    public int getSkierX() {
        return skier.getX();
    }

    public int getSkierY() {
        return skier.getY();
    }

    public boolean isSkierWon() {
        return skierWon;
    }

    public boolean isVaudoisWon() {
        return vaudoisWon;
    }
}
