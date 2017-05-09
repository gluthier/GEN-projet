package ch.heigvd.frogger.item;

import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represent an Obstacle of the frogger
 *
 * @author Maxime Guillod
 * @date 25/04/17
 */
public class Obstacle extends Item {

    public Obstacle(int getPosX, int getPosY, Constants.ItemType type) throws CellAlreadyOccupiedException {
        super(getPosX, getPosY, type);
    }

    @Override
    public void changeImage() {
        changeImage(Constants.OBSTACLE_FOLDER);
    }

    @Override
    public void update(Observable o, Object arg) {
        // Do Nothing
    }
}
