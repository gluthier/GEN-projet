package ch.heigvd.server;

import ch.heig.bdd.BDD;
import ch.heig.bdd.ILog;
import ch.heigvd.protocol.Obstacle;
import ch.heigvd.protocol.Protocol;

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

    public Game(Socket socket) {
        this.client = socket;
        this.bdd = BDD.getInstance();
        this.uid = new UID();
        this.connectNbTry = 0;
    }

    @Override
    @SuppressWarnings("empty-statement")
    public void run() {
        try {
            bdd.logInfo(this, "START NEW GAME THREAD");
            // Try to login => change in that implementation
            //Login login = new Login(client, uid);
            //while (!login.connect()) ;
            // we wait on the client if he send us the right command to log in
           in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
           out = new BufferedWriter(
                   new OutputStreamWriter(socket.getOutputStream()));
           
           String line;
           while ((line = in.readLine()) != null) {
             System.out.println("Line: " + line);
             JSONObject json = new JSONObject(line);
            
             Protocol.command command = Protocol.command.valueOf((String) json.get("command"));
             receiveCommand(command,line);
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
    private void receiveCommand(Protocol.command command,String args) {
    	// call the right function depending on the message
    	switch (command) {
		case login:
			login(args);
			break;

		default:
			break;
		}
    }
    
    private boolean login(String args) {
    	connectNbTry++;

        String user = Protocol.getFormatLoginUser(answer);
        String password = Protocol.getFormatLoginPassword(answer);

        /*
        TODO
        test login information with the bdd
         */
        boolean logged = bdd.testLogin(user, password, this);
        if (logged) {
            bdd.logInfo(this, "Login OK");
            String token = generateToken();
            
    		App.CONNECTED_USER.put(user, token);
    		//TODO need difficulty and mapSized from the DB
            out.write(Protocol.formatLoginAnswer(token, difficulty, map));
            out.flush();
            return true;
        }
        bdd.logWarning(this, "Login Error");
        String message = "{\"token\":null}";
        out.write(Protocol.formatWrongLoginAnswer());
        return false;
    }
    
    private void getLobbies() {
    	String message = Protocol.formatLobbyAnswer(App.CURRENT_LOBBIES.values());
    }
    
    private void startGame(int id) {
    	// generate all fixedObstacle
    	List<Obstacle> ls = new ArrayList<Obstacle>();
    	Random rand = new Random();
    	// TODO Do global variable
    	for (int i = 0; i < 10; i++) {
    		//TODO get width and height via MapSizeId
    		ls.add(new Obstacle(rand.nextInt(15), rand.nextInt(15)));
		}
    
    	//TODO link that with App.Current_GAMES and launch one
    	String message = Protocol.formatJoinAnswer(ls)
    }
    
    private static String generateToken() {
    	SecureRandom random = new SecureRandom();
    	
    	return new BigInteger(130, random).toString(32);
    }

}
