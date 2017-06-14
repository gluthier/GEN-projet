package ch.heigvd.frogger;

import ch.heigvd.frogger.action.ActionAttack;
import ch.heigvd.frogger.action.ActionDefend;
import ch.heigvd.frogger.action.ActionGame;
import ch.heigvd.frogger.action.Actions;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import javafx.scene.input.KeyCode;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author lognaume
 * @author Maxime Guillod
 * @author Tony Clavien
 * @author Gabriel Luthier
 */
public class ClientConstants {
    public static final String IMG_FOLDER = "/images/";
    public static final String PLAYER_FOLDER = "ski-personnages/";
    public static final String OBSTACLE_FOLDER = "background/";
    public static final String BACKGROUND_FOLDER = "background/";
    public static final String ICON_FOLDER = "icon/";
    public static final String DECORATiON_FOLDER = "ski-obstacles/";
    public static final String BACKGROUND_PATH = IMG_FOLDER + BACKGROUND_FOLDER + "fond.jpg";
    public static final String ICON_PATH = IMG_FOLDER + ICON_FOLDER + "favicon.jpg";

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
                    put(KeyCode.DIGIT1, new ActionDefend(1));
                    put(KeyCode.DIGIT2, new ActionDefend(2));
                    put(KeyCode.DIGIT3, new ActionDefend(3));
                    put(KeyCode.DIGIT4, new ActionDefend(4));
                    put(KeyCode.DIGIT5, new ActionDefend(5));
                    put(KeyCode.DIGIT6, new ActionDefend(6));
                    put(KeyCode.DIGIT7, new ActionDefend(7));
                    put(KeyCode.DIGIT8, new ActionDefend(8));
                    put(KeyCode.DIGIT9, new ActionDefend(9));
                    put(KeyCode.DIGIT0, new ActionDefend(0));
                }
            });

    public static final Map<KeyCode, Actions> ACTION_GAME
            = Collections.unmodifiableMap(
                    new EnumMap<KeyCode, Actions>(KeyCode.class) {
                {
                    put(KeyCode.R, new ActionGame(ActionGame.ActionGameType.RESTART));
                }
            });
    
    /**
     * Map between obstacle row number and grid row number
     */
    public static final BiMap<Integer, Integer> OBSTACLE_ROW
            = new ImmutableBiMap.Builder<Integer, Integer>().put(1, 7)
                    .put(2, 9)
                    .put(3, 11)
                    .put(4, 13)
                    .put(5, 15)
                    .put(6, 17)
                    .put(7, 19)
                    .put(8, 21)
                    .put(9, 23)
                    .put(0, 25)
                    .build();

    public static String SERVER_ADDRESS = "localhost";
    public static int SERVER_PORT = 1234;
}
