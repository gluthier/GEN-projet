package ch.heigvd.server;

import ch.heigvd.server.bdd.ILog;
import ch.heigvd.server.bdd.BDD;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.rmi.server.UID;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Maxime Guillod
 */
public class Login implements ILog {

    private final Socket socket;
    private boolean logged;
    private final UID uid;
    private final BDD bdd;
    private int nbTry;
    private BufferedWriter out;
    private BufferedReader in;

    public Login(Socket socket, UID uid) throws IOException {
        this.socket = socket;
        out = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream()));
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        this.logged = false;
        this.uid = uid;
        this.bdd = BDD.getInstance();
        this.nbTry = 0;
    }

    public UID getUid() {
        return uid;
    }

    public boolean connect() throws IOException {
        nbTry++;
        /*
        TODO
        Add a timer with the nbTry to avoid brute force
         */
        {
            JSONObject json = new JSONObject();
            json.put("ack", "login");
            out.write(json.toString());
            out.flush();
        }
        {
            /*
            TODO
            test is in.readLine() is a well formated json
             */
            JSONObject json = null;
            try {
                json = new JSONObject(in.readLine());
            } catch (JSONException e) {
                bdd.logError(this, e);
                return false;
            }

        }

        return false;
    }

}
