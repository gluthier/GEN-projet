package ch.heigvd.frogger.item;

import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;
import javafx.scene.Group;

/**
 *
 * @author Maxime Guillod
 */
public class Obstacle extends Item {

    public Obstacle(int getPosX, int getPosY, Constants.ItemType type, Group parent) throws CellAlreadyOccupiedException {
        super(getPosX, getPosY, type, parent);
    }
}
