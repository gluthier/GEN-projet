package ch.heigvd.frogger.action;

import ch.heigvd.frogger.controllers.ClientController;
import ch.heigvd.frogger.MainApp;
import ch.heigvd.frogger.controllers.IController;

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
            IController controller = null;
            try {
                controller = MainApp.getController();
            } catch (Exception e) {
                Logger.getLogger(ActionAttack.class.getName()).log(Level.SEVERE, null, e);
            }
            switch (action) {
                case RESTART:
                    controller.restartGame();
                    break;
                default:
                    break;
            }
    }

}
