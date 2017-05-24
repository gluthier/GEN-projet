package ch.heigvd.server;

import ch.heigvd.server.bdd.ILog;
import ch.heigvd.server.bdd.BDD;
import ch.heigvd.protocol.Protocol;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.UID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tony Clavien
 * @author Maxime Guillod
 *
 */
public class App implements ILog {

    private ServerSocket server;
    private final BDD bdd;
    private final UID uid;

    public App() {
        this.bdd = BDD.getInstance();
        this.uid = new UID();
        launch();
    }

    public void launch() {
        try {
            bdd.logInfo(this, "START NEW MAIN_APP");
            server = new ServerSocket(Protocol.PORT);
            Socket socket;
            while ((socket = server.accept()) != null) {
                /*
                Start a new thread for the communication. 
                We will have a thread (this) which listen for new communication, and one thread for every game
                 */
                new Game(socket).start();
            }
        } catch (IOException ex) {
            bdd.logError(this, ex);
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            bdd.disconnect();
        }
    }

    public UID getUid() {
        return uid;
    }

    public static void main(String[] args) {
        App app = new App();
    }

}
