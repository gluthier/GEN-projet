package ch.heigvd.protocol;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import com.google.common.hash.Hashing;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void testFormatLoginSend() {
		String message = Protocol.formatLoginSend("test", "1234");
		System.out.println(message);
		JSONObject test = new JSONObject(message);
		assertEquals(test.getString("command"), "login");
		JSONObject param = test.getJSONObject("param");
		assertEquals(param.getString("user"), "test");
		assertEquals(param.getString("password"), Hashing.sha256().hashString("1234", StandardCharsets.UTF_8).toString());
	}
    
    public void testFormatLoginName() {
    	String message = Protocol.formatLoginSend("test", "1234");
    	assertEquals("test", Protocol.getFormatLoginUser(message));
    }
    
    public void testFormatLoginPassword() {
    	String message = Protocol.formatLoginSend("test", "1234");
    	assertEquals(Hashing.sha256().hashString("1234", StandardCharsets.UTF_8).toString(), Protocol.getFormatLoginPassword(message));
    }
    
    //TODO more tests
    
    public void testLoginAnswer() {
    	List<Difficulty> difficulty = new LinkedList<Difficulty>();
    	List<MapSize> map = new LinkedList<MapSize>();
    	
    	difficulty.add(new Difficulty(1, "medium", 5, 4, 3, 20));
    	map.add(new MapSize(1, "medium", 200, 100));
    	System.out.println(Protocol.formatLoginAnswer("1234", difficulty, map));
    }
    
    public void testGetLobbySend() {
    	System.out.println(Protocol.formatLobbySend("asd"));
    }
    
   
}
