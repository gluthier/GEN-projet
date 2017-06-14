package ch.heigvd.frogger.item;

import ch.heigvd.frogger.ClientConstants;
import ch.heigvd.protocol.Constants;

/**
 *
 * @author Gabriel Luthier
 */
public class FixedObstacle extends Obstacle {

    public FixedObstacle(int getPosX, int getPosY, Constants.ItemType type) {
        super(getPosX, getPosY, type);
    }

    @Override
    public void changeImage() {
        changeImage(ClientConstants.OBSTACLE_FOLDER);
    }
}
