package ch.heigvd.frogger.item;

import ch.heigvd.frogger.Constants;

/**
 *
 * @author Maxime Guillod
 */
public class Player extends Item {

    public Player(int getPosX, int getPosY, Constants.ItemType type) {
        super(getPosX, getPosY, type);
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
