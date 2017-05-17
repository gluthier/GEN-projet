package ch.heigvd.protocol;

import java.nio.charset.StandardCharsets;

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

    public void testFormatSendLogin() {
		String message = Protocol.formatLoginSend("test", "1234");
		System.out.println(message);
		JSONObject test = new JSONObject(message);
		assertEquals(test.getString("command"), "login");
		JSONObject param = test.getJSONObject("param");
		assertEquals(param.getString("user"), "test");
		assertEquals(param.getString("password"), Hashing.sha256().hashString("1234", StandardCharsets.UTF_8).toString());
	}
}
