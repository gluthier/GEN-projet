package ch.heigvd.server;

import ch.heig.bdd.BDD;
import ch.heig.bdd.ILog;
import ch.heigvd.protocol.Obstacle;
import ch.heigvd.protocol.Protocol;

import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.rmi.server.UID;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maxime Guillod
 * @author Tony Clavien
 */
public class Game extends Thread implements ILog {

    private final Socket socket;
    private final UID uid;
    private final BDD bdd;

    public Game(Socket socket) {
        this.socket = socket;
        this.bdd = BDD.getInstance();
        this.uid = new UID();
    }

    @Override
    @SuppressWarnings("empty-statement")
    public void run() {
        try {
            bdd.logInfo(this, "START NEW GAME THREAD");
            // Try to login
            Login login = new Login(socket, uid);
            while (!login.connect()) ;
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
    private void receiveCommand(String message) {
    	// call the right function depending on the message
    }
    
    private void login(String user, String password) {
    	// check with BDD if it's ok
    	if(bdd.testLogin(user, password, this)) {
    		// return connection successful
    		// generate token and store it
    		String token = generateToken();
    		App.CONNECTED_USER.put(user, token);
    		   // getDifficulty and map sized from the bdd		
    		//String message = Protocol.formatLoginAnswer(token, difficulty, map)
    	} else {
    		// return connection failed
    		String message = "{\"token\":null}";
    	}
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
    
    	//TODO link that with App.Current_GAMES
    	String message = Protocol.formatJoinAnswer(ls)
    }
    
    private static String generateToken() {
    	SecureRandom random = new SecureRandom();
    	
    	return new BigInteger(130, random).toString(32);
    }

}
