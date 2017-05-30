package ch.heigvd.frogger.action;

import ch.heigvd.frogger.GameController;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel Luthier
 */
public class ActionGame implements Actions {

    public static enum ActionGameType {
        RESTART
    };
    
    private final ActionGameType action;
    
    public ActionGame(ActionGameType action) {
        this.action = action;
    }
    
    @Override
    public void act() {
            GameController gc = null;
            try {
                gc = GameController.getInstance();
            } catch (Exception e) {
                Logger.getLogger(ActionAttack.class.getName()).log(Level.SEVERE, null, e);
            }
            switch (action) {
                case RESTART:
                    gc.restartGame();
                    break;
                default:
                    break;
            }
    }

}
