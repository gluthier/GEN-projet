package ch.heigvd.server;

import java.net.Socket;
import java.rmi.server.UID;

/**
 *
 * @author Maxime Guillod
 */
public class Login implements ILog {

    private final Socket socket;
    private boolean logged;
    private final UID uid;
    private final BDD bdd;

    public Login(Socket socket, UID uid) {
        this.socket = socket;
        this.logged = false;
        this.uid = uid;
        this.bdd = BDD.getInstance();
    }

    boolean isLogged() {
        return logged;
    }

    public void tryConnect() {
        /*
        Wait login information from client
        */
        
        /*
        Test client information
        */
    }

    public UID getUid() {
        return uid;
    }

}
