package ch.heigvd.server;

import ch.heig.bdd.BDD;
import ch.heig.bdd.ILog;
import ch.heigvd.protocol.Protocol;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.UID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maxime Guillod
 */
public class ListenClient implements Runnable, ILog {

    private ServerSocket server;
    private final UID uid;
    private final BDD bdd;

    public ListenClient() {
        this.uid = new UID();
        this.bdd = BDD.getInstance();
        bdd.logInfo(this, "NEW LISTEN_CLIENT");
    }

    public void run() {
        try {
            bdd.logInfo(this, "RUN LISTEN_CLIENT");
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

        try {
            server.close();
        } catch (IOException ex) {
            bdd.logError(this, ex);
            Logger.getLogger(ListenClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public UID getUid() {
        return uid;
    }

}
