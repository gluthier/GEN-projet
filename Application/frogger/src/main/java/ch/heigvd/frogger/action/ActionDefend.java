package ch.heigvd.frogger.action;

import ch.heigvd.frogger.ClientConstants;
import ch.heigvd.protocol.Constants;
import ch.heigvd.frogger.ItemClock;
import ch.heigvd.frogger.MainApp;
import ch.heigvd.protocol.Party;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Define a Defensive Action for Player 2
 *
 * @author Tony Clavien
 *
 */
public class ActionDefend implements Actions {

    private final int row;

    public ActionDefend(int row) {
        if (row < 0 || row > 9) {
            throw new IllegalArgumentException("wrong row number");
        }

        this.row = row;
    }

    @Override
    public void act() {
        if (MainApp.getGameSettings().getRole() == Party.FreeRole.defender) {
            try {
                MainApp.getController().addDynamicObstacle(ClientConstants.OBSTACLE_ROW.get(row));
            } catch (Exception e) {
                Logger.getLogger(ActionDefend.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

}
