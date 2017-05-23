package ch.heigvd.server;

import junit.framework.TestCase;

/**
 *
 * @author Maxime Guillod
 */
public class BDDTest extends TestCase {

    public BDDTest(String testName) {
        super(testName);
    }

    /**
     * Test of getInstance method, of class BDD.
     */
    public void testGetInstance() {
        BDD bdd = BDD.getInstance();
        if (bdd.isClosed()) {
            fail("La connexion est fermée");
        }
    }

    public void testInsertLog() {
        BDD.getInstance().insertLog("TEST", "Du contenut");
    }

}
