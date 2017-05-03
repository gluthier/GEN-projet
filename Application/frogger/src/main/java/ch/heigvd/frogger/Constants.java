package ch.heigvd.frogger;

import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

import ch.heigvd.frogger.item.ActionAttack;
import ch.heigvd.frogger.item.ActionDefend;
import ch.heigvd.frogger.item.Actions;
import javafx.scene.input.KeyCode;

/**
 *
 * @author lognaume
 * @author Tony Clavien
 */
public class Constants {

    public static final int GAME_WIDTH = 1368;
    public static final int GAME_HEIGHT = 1026;
    public static final int NUM_COLS = 36;
    public static final int NUM_ROWS = 27;
    public static final double CELL_WIDTH = (double) Constants.GAME_WIDTH / (double) Constants.NUM_COLS;
    public static final double CELL_HEIGHT = (double) Constants.GAME_HEIGHT / (double) Constants.NUM_ROWS;
    
    public static final int INITIAL_PLAYER_X = 14;
    public static final int INITIAL_PLAYER_Y = 5;

    public static final String IMG_FOLDER = "/images/";
    public static final String PLAYER_FOLDER = "ski-personnages/";
    public static final String OBSTACLE_FOLDER = "background/";
    public static final String BACKGROUND_FOLDER = "background/";
    public static final String BACKGROUND_PATH = IMG_FOLDER + BACKGROUND_FOLDER + "fond.jpg";

    public static final int NUM_OBSTACLES = 20;

    /**
     * Map between key pressed and action related for the attacker
     */
    @SuppressWarnings("serial")
	public static final Map<KeyCode, Actions> ACTION_ATTACK
            = Collections.unmodifiableMap(
                    new EnumMap<KeyCode, Actions>(KeyCode.class) {
                {
                    put(KeyCode.DOWN, new ActionAttack(ActionAttack.MoveType.DOWN));
                    put(KeyCode.LEFT, new ActionAttack(ActionAttack.MoveType.LEFT));
                    put(KeyCode.RIGHT, new ActionAttack(ActionAttack.MoveType.RIGHT));
                }
            });

    /**
     * Map between key pressed and action related for the defender
     */
    @SuppressWarnings("serial")
	public static final Map<KeyCode, Actions> ACTION_DEFEND
            = Collections.unmodifiableMap(
                    new EnumMap<KeyCode, Actions>(KeyCode.class) {
                {
                    put(KeyCode.DIGIT1, new ActionDefend(0));
                    put(KeyCode.DIGIT2, new ActionDefend(1));
                    put(KeyCode.DIGIT3, new ActionDefend(2));
                    put(KeyCode.DIGIT4, new ActionDefend(3));
                    put(KeyCode.DIGIT5, new ActionDefend(4));
                    put(KeyCode.DIGIT6, new ActionDefend(5));
                    put(KeyCode.DIGIT7, new ActionDefend(6));
                    put(KeyCode.DIGIT8, new ActionDefend(7));
                    put(KeyCode.DIGIT9, new ActionDefend(8));
                    put(KeyCode.DIGIT0, new ActionDefend(9));
                }
            });
    
    @SuppressWarnings("serial")
    /**
     * Map between obstacle row number and grid row number
     */
	public static final Map<Integer,Integer> OBSTACLE_ROW
    	= Collections.unmodifiableMap(
    			new LinkedHashMap<Integer,Integer>() {
    				{
    					put(0,7);
    					put(1,9);
    					put(2,11);
    					put(3,13);
    					put(4,15);
    					put(5,17);
    					put(6,19);
    					put(7,21);
    					put(8,23);
    					put(9,25);
    				}
    			});

    public static enum ItemType {
        Chalet("chalet"),
        ChaletVS("chalet_vs"),
        SkierLeft("skier-left1"),
        Skier("skier-down"),
        SkierRight("skier-right1"),
        Sapin("sapin"),
    	Saucisson("saucisson_vaudois"),
    	Row_0("Row_0"),
    	Row_1("ROW_1"),
    	Row_2("Row_2"),
    	Row_3("ROW_3"),
    	Row_4("Row_4"),
    	Row_5("ROW_5"),
    	Row_6("Row_6"),
    	Row_7("ROW_7"),
    	Row_8("Row_8"),
    	Row_9("ROW_9");

        private String filename = "";

        ItemType(String filename) {
            this.filename = filename;
        }
        
        public static ItemType getRow(int i) {
			switch (i) {
			case 0 : return ItemType.Row_0;
			case 1 : return ItemType.Row_1;
			case 2 : return ItemType.Row_2;
			case 3 : return ItemType.Row_3;
			case 4 : return ItemType.Row_4;
			case 5 : return ItemType.Row_5;
			case 6 : return ItemType.Row_6;
			case 7 : return ItemType.Row_7;
			case 8 : return ItemType.Row_8;
			case 9 : return ItemType.Row_9;
			default : throw new IllegalArgumentException("Wrong row number");
			}
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
