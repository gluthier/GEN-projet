package ch.heigvd.frogger;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import javafx.scene.input.KeyCode;

/**
 *
 * @author lognaume
 */
public class Constants {

    public static final int GAME_WIDTH = 1368;
    public static final int GAME_HEIGHT = 1026;
    public static final int NUM_COLS = 36;
    public static final int NUM_ROWS = 27;
    public static final double CELL_WIDTH = (double) Constants.GAME_WIDTH / (double) Constants.NUM_COLS;
    public static final double CELL_HEIGHT = (double) Constants.GAME_HEIGHT / (double) Constants.NUM_ROWS;

    public static final String IMG_FOLDER = "/images/";
    public static final String PLAYER_FOLDER = "ski-personnages/";
    public static final String OBSTACLE_FOLDER = "background/";
    public static final String BACKGROUND_FOLDER = "background/";
    public static final String BACKGROUND_PATH = IMG_FOLDER + BACKGROUND_FOLDER + "fond.jpg";
    
    public static final int ITEM_CLOCK_DELAY = 200;

    public static final int NUM_OBSTACLES = 20;

    /**
     * Map between key pressed and action related for the attacker
     */
    public static final Map<KeyCode, ActionType> ACTION_ATTACK
            = Collections.unmodifiableMap(
                    new EnumMap<KeyCode, ActionType>(KeyCode.class) {
                {
                    put(KeyCode.DOWN, ActionType.MovePlayer);
                    put(KeyCode.LEFT, ActionType.MovePlayer);
                    put(KeyCode.RIGHT, ActionType.MovePlayer);
                }
            });

    /**
     * Map between key pressed and action related for the defender
     */
    public static final Map<KeyCode, ActionType> ACTION_DEFEND
            = Collections.unmodifiableMap(
                    new EnumMap<KeyCode, ActionType>(KeyCode.class) {
                {
                    put(KeyCode.DIGIT0, ActionType.SendObstacle);
                    put(KeyCode.DIGIT1, ActionType.SendObstacle);
                    put(KeyCode.DIGIT2, ActionType.SendObstacle);
                    put(KeyCode.DIGIT3, ActionType.SendObstacle);
                    put(KeyCode.DIGIT4, ActionType.SendObstacle);
                    put(KeyCode.DIGIT5, ActionType.SendObstacle);
                    put(KeyCode.DIGIT6, ActionType.SendObstacle);
                    put(KeyCode.DIGIT7, ActionType.SendObstacle);
                    put(KeyCode.DIGIT8, ActionType.SendObstacle);
                    put(KeyCode.DIGIT9, ActionType.SendObstacle);
                }
            });

    public static enum ItemType {
        Chalet("chalet"),
        ChaletVS("chalet_vs"),
        SkierLeft("skier-left1"),
        Skier("skier-down"),
        SkierRight("skier-right1"),
        Sapin("sapin");

        private String filename = "";

        ItemType(String filename) {
            this.filename = filename;
        }

        public String toString() {
            return filename;
        }
    };

    public static enum ActionType {
        SendObstacle,
        MovePlayer;
    };
}
