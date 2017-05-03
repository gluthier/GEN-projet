package ch.heigvd.frogger.item;

import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;

/**
 * Represent a Player of the frogger
 *
 * @author Maxime Guillod
 * @date 25/04/17
 */
public class Player extends Item {

    public Player(int getPosX, int getPosY, Constants.ItemType type) throws CellAlreadyOccupiedException {
        super(getPosX, getPosY, type);
    }
}
