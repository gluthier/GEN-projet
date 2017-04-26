package ch.heigvd.frogger;

/**
 *
 * @author lognaume
 */
public class Constants {
    public static final int GAME_WIDTH = 1368;
    public static final int GAME_HEIGHT = 1026;
    public static final int NUM_COLS = 36;
    public static final int NUM_ROWS = 27;
    
    public static final String IMG_FOLDER = "/images/";
    public static final String OBSTACLE_FOLDER = "background/";
    public static final String BACKGROUND_FOLDER = "background/";
    
    public static enum Obstacles {
        Chalet ("chalet"),
        ChaletVS ("chalet_vs"),
        Sapin ("sapin");
        
        private String filename = "";
        
        Obstacles(String filename) {
            this.filename = filename;
        }
        
        public String toString() {
            return filename;
        }
    };
}
