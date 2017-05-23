package ch.heigvd.server;

import java.net.Socket;
import java.rmi.server.UID;

/**
 *
 * @author Maxime Guillod
 */
public class Game extends Thread {

    private final Socket socket;
    private UID uid;
    private BDD bdd;

    public Game(Socket socket) {
        this.socket = socket;
        this.bdd = BDD.getInstance();
        /*
        Create a unique id to identify the connection and for log
         */
        uid = new UID();
        bdd.insertLog(uid, "NEW GAME");
    }

    @Override
    public void run() {
        // Try to login
        Login login = new Login(socket);
        do {
            login.tryConnect();
        } while (!login.isLogged());

    }

    public int getUid() {
        return uid.hashCode();
    }

}
