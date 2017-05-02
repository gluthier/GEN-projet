package ch.heigvd.frogger.item;

import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;

/**
 *
 * @author Maxime Guillod
 */
public class Player extends Item {

    public Player(int getPosX, int getPosY, Constants.ItemType type) throws CellAlreadyOccupiedException {
        super(getPosX, getPosY, type);
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
