package ch.heigvd.frogger.action;

import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.GameController;
import ch.heigvd.frogger.ItemClock;
import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
            GameController gc = null;
            try {
                gc = GameController.getInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            switch (move) {
                case LEFT:
                    gc.movePlayerLeft();
                    break;
                case DOWN:
                    gc.movePlayerDown();
                    break;
                case RIGHT:
                    gc.movePlayerRight();
                    break;
                default:
                    break;
            }
        }
    }
}
