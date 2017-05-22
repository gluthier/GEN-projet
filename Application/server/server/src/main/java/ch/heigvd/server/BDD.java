package ch.heigvd.server;

/**
 * Communicate with the database for loginInformation, stats and more
 *
 * @author Maxime Guillod
 */
public class BDD {

    private static BDD instance = null;

    private BDD() {

    }

    public BDD getInstance() {
        if (instance == null) {
            instance = new BDD();
        }
        return instance;
    }

    public boolean testLogin(String login, String pwd) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
