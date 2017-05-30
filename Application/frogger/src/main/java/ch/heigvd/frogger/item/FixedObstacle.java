package ch.heigvd.frogger.item;

import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.GameFXMLController;
import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;

/**
 *
 * @author Gabriel Luthier
 */
public class FixedObstacle extends Obstacle {

    public FixedObstacle(int getPosX, int getPosY, Constants.ItemType type) throws CellAlreadyOccupiedException {
        super(getPosX, getPosY, type);
    }

    @Override
    public void changeImage() {
        changeImage(Constants.OBSTACLE_FOLDER);
    }
}
