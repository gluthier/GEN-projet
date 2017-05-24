package ch.heigvd.server;

import ch.heigvd.server.bdd.ILog;
import ch.heigvd.server.bdd.BDD;
import java.rmi.server.UID;
import junit.framework.TestCase;

/**
 *
 * @author Maxime Guillod
 */
public class BDDTest extends TestCase implements ILog {

    private final BDD bdd;
    private final UID uid;
    private final Game game;

    public BDDTest(String testName) {
        super(testName);
        bdd = BDD.getInstance();
        uid = new UID();
        game = new Game(null);
    }

    /**
     * Test of getInstance method, of class BDD.
     */
    public void testGetInstance() {
        if (bdd.isClosed()) {
            fail("La connexion est fermée");
        }
    }

    public void testLogInfo() {
        String content = "Plein de contenu";
        bdd.logInfo(this, content);
        assertEquals(content, bdd.getLastLogContent());
    }

    public void testLogError() {
        Exception e = new UnsupportedOperationException("Test");
        bdd.logError(this, e);
        assertEquals("Test", bdd.getLastLogContent());
    }

    /**
     * *****************************
     * LOGIN
     *********************************
     */
    public void testCorrectLogin() {
        assertTrue(bdd.testLogin("test", "1234", game));
    }
    
    public void testCorrectLoginWronPassword() {
        assertFalse(bdd.testLogin("test", "1212", game));
    }
    
    public void testIncorrectLogin() {
        assertFalse(bdd.testLogin("nope", "1234", game));
    }

    public UID getUid() {
        return uid;
    }

}
