package ch.heigvd.server;

import java.net.Socket;

/**
 *
 * @author Maxime Guillod
 */
public class Login {

    private final Socket socket;
    private boolean logged;

    public Login(Socket socket) {
        this.socket = socket;
        this.logged = false;
    }

    boolean isLogged() {
        return logged;
    }

    public void tryConnect() {
        /*
        Wait for login information from client, and return spesific message (login OK, login failled, ...)
         */
 /*
        Connection with the database to verifiy login information
         */
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
