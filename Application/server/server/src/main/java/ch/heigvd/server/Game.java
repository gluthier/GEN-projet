package ch.heigvd.server;

import ch.heigvd.server.bdd.ILog;
import ch.heigvd.server.bdd.BDD;
import java.net.Socket;
import java.rmi.server.UID;

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
    public void run() {
        bdd.logInfo(this, "START NEW GAME THREAD");
        // Try to login
        Login login = new Login(socket, uid);
        do {
            login.tryConnect();
        } while (!login.isLogged());

    }

    public UID getUid() {
        return uid;
    }

}
