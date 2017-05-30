package ch.heigvd.server;

import ch.heigvd.protocol.Protocol;
import ch.heigvd.server.bdd.ILog;
import ch.heigvd.server.bdd.BDD;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.rmi.server.UID;

/**
 *
 * @author Maxime Guillod
 */
public class Login implements ILog {

    private final Socket socket;
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
        out.write(Protocol.formatRequestLogin(false));
        out.flush();

        String answer = in.readLine();

        String user = Protocol.getFormatLoginUser(answer);
        String password = Protocol.getFormatLoginPassword(answer);

        /*
        TODO
        test login information with the bdd
         */
        boolean logged = bdd.testLogin(user, password, this);
        if (logged) {
            bdd.logInfo(this, "Login OK");
            out.write(Protocol.formatRequestLogin(true));
            out.flush();
            return true;
        }
        bdd.logWarning(this, "Login Error");
        return false;
    }

}
