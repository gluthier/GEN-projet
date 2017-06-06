package ch.heigvd.server;

import ch.heig.bdd.BDD;
import ch.heig.bdd.ILog;
import java.net.ServerSocket;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ch.heigvd.protocol.*;

/**
 *
 * @author Tony Clavien
 * @author Maxime Guillod
 *
 */
public class App implements ILog {

    private ServerSocket server;
    private final BDD bdd;
    private final Thread threadListenClient;
    private final UID uid;
    public static Map<String, String> CONNECTED_USER;
    public static List<Party> CURRENT_LOBBIES;

    public App() {
        this.bdd = BDD.getInstance();
        this.uid = new UID();
        bdd.logInfo(this, "START MAIN APP");
        
        /*
        We start the thread which listen for new connection
        */
        threadListenClient = new Thread(new ListenClient());
        threadListenClient.start();
    }

    public UID getUid() {
        return uid;
    }

    public static void main(String[] args) {
    	CONNECTED_USER = new HashMap<String, String>();
    	CURRENT_LOBBIES = new ArrayList<Party>();
        App app = new App();   
    }

}
