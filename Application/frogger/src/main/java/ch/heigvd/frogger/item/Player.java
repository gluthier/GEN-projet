package ch.heigvd.frogger.item;

import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;
import javafx.scene.Group;

/**
 *
 * @author Maxime Guillod
 */
public class Player extends Item {

    public Player(int getPosX, int getPosY, Constants.ItemType type, Group parent) throws CellAlreadyOccupiedException {
        super(getPosX, getPosY, type, parent);
    }
}
