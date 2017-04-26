package ch.heigvd.frogger.item;

import ch.heigvd.frogger.Constants;

/**
 *
 * @author Maxime Guillod
 */
public class Obstacle extends Item {

    public Obstacle(int getPosX, int getPosY, Constants.ItemType type) {
        super(getPosX, getPosY, type);
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    

}
