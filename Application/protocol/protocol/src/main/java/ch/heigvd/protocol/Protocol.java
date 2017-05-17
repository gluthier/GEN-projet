package ch.heigvd.protocol;

/**
 * Hello world!
 *
 */
public class Protocol 
{
	
	public static enum FreeRole {
		skier,
		defender
	}
	
    public static void main( String[] args )
    {
        System.out.println( "Hello World! I'm the protocol" );
    }
    
    public static String formatLoginSend(String user, String password) {
    	return "not implemented yet";
    }
    
    public static String getFormatLoginUser(String message) {
    	return "not implemented yet";
    }
    
    public static String getFormatLoginPassword(String message) {
    	return "not implemented yet";
    }
    
    public static String formatLoginAnswer(String token,Difficulty difficulty, MapSize map) {
    	return "not implemented yet";
    }
    
    public static String getFormatLoginToken(String message) {
    	return "not implemented yet";
    }
    
    public static Difficulty getFormatLoginDifficulty(String message) {
    	return null;
    }
    
    public static MapSize getFormatLoginMapSize(String message) {
    	return null;
    }
    
    public static String formatLobbySend(String token) {
    	return "not implemented yet";
    }
    
    public static String getFormatLobbyToken(String message) {
    	return "not implemented yet";
    }
    
    public static String formatLobbyAnswer(int id, String name, String difficulty, String mapSize, FreeRole free) {
    	return "not implemented yet";
    }
}
