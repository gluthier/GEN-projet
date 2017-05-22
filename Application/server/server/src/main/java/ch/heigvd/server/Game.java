package ch.heigvd.server;

import java.net.Socket;

/**
 *
 * @author Maxime Guillod
 */
public class Game extends Thread{

    private final Socket socket;

    public Game(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
