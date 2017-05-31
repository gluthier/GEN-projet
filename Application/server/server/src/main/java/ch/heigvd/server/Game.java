package ch.heigvd.server;

import ch.heig.bdd.BDD;
import ch.heig.bdd.ILog;
import java.io.IOException;
import java.net.Socket;
import java.rmi.server.UID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maxime Guillod
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

}
