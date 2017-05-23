package ch.heigvd.server;

import java.rmi.server.UID;
import junit.framework.TestCase;

/**
 *
 * @author Maxime Guillod
 */
public class BDDTest extends TestCase implements ILog {

    private final BDD bdd;
    private final UID uid;

    public BDDTest(String testName) {
        super(testName);
        bdd = BDD.getInstance();
        uid = new UID();
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

    public UID getUid() {
        return uid;
    }

}
