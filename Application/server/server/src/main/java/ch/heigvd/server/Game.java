package ch.heigvd.server;

import ch.heig.bdd.BDD;
import ch.heig.bdd.ILog;
import ch.heigvd.protocol.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.rmi.server.UID;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

/**
 *
 * @author Maxime Guillod
 * @author Tony Clavien
 */
public class Game extends Thread implements ILog {

    private final Socket client;
    private final UID uid;
    private final BDD bdd;
    private BufferedWriter out;
    private BufferedReader in;
    private int connectNbTry;
    private boolean logged;
    private String userName;
    private List<Obstacle> fixedObstacles;

    public Game(Socket socket) {
        this.client = socket;
        this.bdd = BDD.getInstance();
        this.uid = new UID();
        this.connectNbTry = 0;
        this.logged = false;
        fixedObstacles = new ArrayList<Obstacle>();
    }

    @Override
    public void run() {
        bdd.logInfo(this, "START NEW GAME THREAD");
        try {
            // we wait on the client if he send us the right command to log in
            in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
            out = new BufferedWriter(
                    new OutputStreamWriter(client.getOutputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                JSONObject json = new JSONObject(line);
                Protocol.command command = Protocol.command.fromString((String) json.get("command"));
                receiveCommand(command, line);
            }
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public UID getUid() {
        return uid;
    }

    /**
     * Receive a command from the client
     */
    private void receiveCommand(Protocol.command command, String args) throws IOException {
        // call the right function depending on the message
        switch (command) {
            case login:
                logged = login(args);
                break;
            case getlobby:
                if (logged) {
                    getLobbies();
                }
                break;
            case join:
                if (logged) {
                    startGame(args);
                }
                break;
            case createParty: {
                if (logged) {
                    createParty(args);
                }
                break;
            }
            case moveSkier:
                if (logged) {
                    //TODO
                }
                break;
            case addObstacle:
                if (logged) {
                    //TODO 
                    // should it be done here ?
                }
            default:
                break;
        }
    }

    private void createParty(String args) {
        bdd.logInfo(this, "Creating new party");
        // create new party key
        Random rand = new Random();
        int id;
        do {
            // TODO Remove hardcoded max ID
            id = rand.nextInt(100);
        } while (App.CURRENT_LOBBIES.containsKey(id));

        Party party = Protocol.getFormatCreateParty(args);
        party.setId(id);
        MapSize map = bdd.getMapSizeById(party.getMapSize().getId());
        Difficulty diff = bdd.getDifficultyById(party.getDifficulty().getId());

        // generate all fixedObstacle
        for (int i = 0; i < Constants.NUM_OBSTACLES; i++) {
            fixedObstacles.add(new Obstacle(rand.nextInt(Constants.NUM_COLS - 4) + 2, rand.nextInt(Constants.NUM_ROWS - Constants.INITIAL_PLAYER_Y) + Constants.INITIAL_PLAYER_Y));
        }
        // add the 2 rows of obstacle on the edge
        for (int i = 0; i < map.getHeight(); i++) {
            
            fixedObstacles.add(new Obstacle(0, i));
            fixedObstacles.add(new Obstacle(1, i));
            fixedObstacles.add(new Obstacle(Constants.NUM_COLS - 2, i));
            fixedObstacles.add(new Obstacle(Constants.NUM_COLS - 1, i));
        }
        
        App.CURRENT_LOBBIES.put(id, party);
        App.CURRENT_GAMES.put(id, new LaunchedGame(id, map.getWidth(), map.getHeight(), fixedObstacles, new Skier(Constants.INITIAL_PLAYER_X, Constants.INITIAL_PLAYER_Y), diff, client));
    }

    private boolean login(String message) throws IOException {
        connectNbTry++;

        String user = Protocol.getFormatLoginUser(message);
        String password = Protocol.getFormatLoginPassword(message);

        /*
        TODO
        test login information with the bdd
         */
        boolean logged = bdd.testLogin(user, password, this);
        if (logged) {
            bdd.logInfo(this, "Login OK");
            String token = generateToken();

            userName = user;
            App.CONNECTED_USER.put(user, token);

            out.write(Protocol.formatLoginAnswer(token, bdd.getDifficulties(), bdd.getMapSizes()));
            out.flush();
            return true;
        }
        bdd.logWarning(this, "Login Error");
        out.write(Protocol.formatWrongLoginAnswer());
        out.flush();
        return false;
    }

    private void getLobbies() throws IOException {
        out.write(Protocol.formatLobbyAnswer(App.CURRENT_LOBBIES.values()));
        out.flush();
    }

    private void startGame(String message) throws IOException {
        String id = Protocol.getFormatJoinId(message);
        String token = Protocol.getFormatJoinToken(message);
        // check token
        if (App.CONNECTED_USER.get(userName).equals(token) && App.CURRENT_GAMES.containsKey(id)) {
            out.write(Protocol.formatJoinAnswer(App.CURRENT_GAMES.get(id).getFixedObstacle()));
            out.flush();
            App.CURRENT_GAMES.get(id).addAnotherPlayer(client);
            App.CURRENT_GAMES.get(id).start(3);
        }
        //TODO if wrong
    }

    private static String generateToken() {
        SecureRandom random = new SecureRandom();

        return new BigInteger(130, random).toString(32);
    }

}
