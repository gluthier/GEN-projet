package ch.heigvd.protocol;

/**
 * @author lognaume
 * @author Maxime Guillod
 * @author Tony Clavien
 * @author Gabriel Luthier
 */
public class Constants {

    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 960;
    public static int NUM_COLS = 36;
    public static int NUM_ROWS = 27;
    public static double CELL_WIDTH = (double) Constants.GAME_WIDTH / (double) Constants.NUM_COLS;
    public static double CELL_HEIGHT = (double) Constants.GAME_HEIGHT / (double) Constants.NUM_ROWS;

    public static final int INITIAL_PLAYER_X = 14;
    public static final int INITIAL_PLAYER_Y = 5;
    
    public static final int FIRST_FINISH_TO_RIGHT = 4;
    public static final int FIRST_FINISH_TO_LEFT = 8;
    public static final int SECOND_FINISH_TO_RIGHT = 12;
    public static final int SECOND_FINISH_TO_LEFT = 16;
    public static final int THIRD_FINISH_TO_RIGHT= 20;
    public static final int THIRD_FINISH_TO_LEFT = 24;
    public static final int FOURTH_FINISH_TO_RIGHT = 28;
    public static final int FOURTH_FINISH_TO_LEFT = 32;

    public static final int ITEM_CLOCK_DELAY = 200;

    public static final int NUM_OBSTACLES = 20;

    public static enum ItemType {
        Chalet("chalet"),
        ChaletVS("chalet_vs"),
        SkierLeft("skier-left1"),
        Skier("skier-down"),
        SkierRight("skier-right1"),
        SkierDownFall("skier-down-fall1"),
        Sapin("sapin"),
    	Saucisson("saucisson_vaudois"),
    	StartLeft("sign-start-left"),
    	StartRight("sign-start-right"),
    	FinishLeft("sign-finish-left"),
    	FinishRight("sign-finish-right"),
    	Row_0("ROW_0"),
    	Row_1("ROW_1"),
    	Row_2("ROW_2"),
    	Row_3("ROW_3"),
    	Row_4("ROW_4"),
    	Row_5("ROW_5"),
    	Row_6("ROW_6"),
    	Row_7("ROW_7"),
    	Row_8("ROW_8"),
    	Row_9("ROW_9");

        private String filename = "";

        ItemType(String filename) {
            this.filename = filename;
        }

        public static ItemType getRow(int i) {
            switch (i) {
                case 0:
                    return ItemType.Row_0;
                case 1:
                    return ItemType.Row_1;
                case 2:
                    return ItemType.Row_2;
                case 3:
                    return ItemType.Row_3;
                case 4:
                    return ItemType.Row_4;
                case 5:
                    return ItemType.Row_5;
                case 6:
                    return ItemType.Row_6;
                case 7:
                    return ItemType.Row_7;
                case 8:
                    return ItemType.Row_8;
                case 9:
                    return ItemType.Row_9;
                default:
                    throw new IllegalArgumentException("Wrong row number");
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
