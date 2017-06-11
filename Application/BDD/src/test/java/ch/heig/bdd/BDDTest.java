package ch.heig.bdd;

import java.rmi.server.UID;
import junit.framework.TestCase;

/**
 *
 * @author Maxime Guillod
 */
public class BDDTest extends TestCase implements ILog {

    private final BDD bdd;
    private final UID uid;
    private final ILog ilog;

    public BDDTest(String testName) {
        super(testName);
        bdd = BDD.getInstance();
        uid = new UID();
        ilog = new ILog() {
            @Override
            public UID getUid() {
                return new UID();
            }
        };
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
     * LOGIN ********************************
     */
    public void testCorrectLogin() {
        assertTrue(bdd.testLogin("test", "81dc9bdb52d04dc20036dbd8313ed055", ilog));
    }

    public void testCorrectLoginWronPassword() {
        assertFalse(bdd.testLogin("test", "1212", ilog));
    }

    public void testIncorrectLogin() {
        assertFalse(bdd.testLogin("nope", "1234", ilog));
    }
    
    public void testgetLog() {
        String retour = bdd.getLogString();
        System.out.println(retour);
        assertFalse(retour.equals(""));
    }

    public UID getUid() {
        return uid;
    }

}
