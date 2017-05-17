package ch.heigvd.frogger.item;

import ch.heigvd.frogger.Constants.ItemType;
import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Define a Dynamic Obstacle ( for now)
 *
 * @author Tony Clavien
 */
public class DynamicObstacle extends Obstacle {

    //TODO add a local Timer or a global one who update the position of the obstacle
    // TODO remove magic number
    public DynamicObstacle(ItemType type) throws CellAlreadyOccupiedException {
        super(2, 5, type);
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            // TODO Implement moveRight(int);
            moveRight();
            moveRight();
            moveRight();
        } catch (CellAlreadyOccupiedException ex) {
            Logger.getLogger(DynamicObstacle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
