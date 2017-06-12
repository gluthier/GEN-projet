package ch.heigvd.frogger.action;

import ch.heigvd.frogger.MainApp;
import ch.heigvd.frogger.controllers.GameController;
import ch.heigvd.frogger.ItemClock;
import ch.heigvd.frogger.controllers.IController;
import sun.applet.Main;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Define Attack action for Player 1
 *
 * @author Tony Clavien
 *
 */
public class ActionAttack implements Actions {

    /**
     * list of possible move
     *
     */
    public static enum MoveType {
        LEFT,
        RIGHT,
        DOWN;
    };

    private final MoveType move;

    public ActionAttack(MoveType move) {
        this.move = move;
    }

    @Override
    public void act() {
        if (ItemClock.getInstance().isRunning()) {
            IController controller = null;
            try {
                controller = MainApp.getController();
            } catch (Exception e) {
                Logger.getLogger(ActionAttack.class.getName()).log(Level.SEVERE, null, e);
            }
            switch (move) {
                case LEFT:
                    controller.movePlayerLeft();
                    break;
                case DOWN:
                    controller.movePlayerDown();
                    break;
                case RIGHT:
                    controller.movePlayerRight();
                    break;
                default:
                    break;
            }
        }
    }
}
