package ch.heigvd.server;

import junit.framework.TestCase;

/**
 *
 * @author Maxime Guillod
 */
public class BDDTest extends TestCase {
    
    private final BDD bdd ;

    public BDDTest(String testName) {
        super(testName);
        bdd = BDD.getInstance();
    }

    /**
     * Test of getInstance method, of class BDD.
     */
    public void testGetInstance() {
        if (bdd.isClosed()) {
            fail("La connexion est fermée");
        }
    }

    public void testInsertLog() {
        String content = "Plein de contenu";
        bdd.insertLog("TEST", content);
        assertEquals(content, bdd.getLastLogContent());
    }

}
