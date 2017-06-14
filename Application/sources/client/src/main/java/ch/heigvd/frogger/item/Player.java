package ch.heigvd.frogger.item;

import ch.heigvd.frogger.ClientConstants;
import ch.heigvd.protocol.Constants;

/**
 * Represent a Player of the frogger
 *
 * @author Maxime Guillod
 */
public class Player extends Item {

    public Player(int getPosX, int getPosY, Constants.ItemType type) {
        super(getPosX, getPosY, type);
    }

    @Override
    public void changeImage(String imagePath) {
        super.changeImage(imagePath); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changeImage() {
        String folder;
        if (getType() == Constants.ItemType.SkierLeft
                || getType() == Constants.ItemType.Skier
                || getType() == Constants.ItemType.SkierRight
                || getType() == Constants.ItemType.SkierDownFall) {
            folder = ClientConstants.PLAYER_FOLDER;
        } else {
            folder = ClientConstants.OBSTACLE_FOLDER;
        }

        changeImage(folder);
    }
}
