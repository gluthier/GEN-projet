package ch.heigvd.frogger.item;

import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;

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
}
