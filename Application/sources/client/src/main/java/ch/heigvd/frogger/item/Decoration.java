package ch.heigvd.frogger.item;

import ch.heigvd.frogger.ClientConstants;
import ch.heigvd.protocol.Constants;
import ch.heigvd.protocol.Constants.ItemType;

/**
 * @author Tony Clavien
 * @author Gabriel Luthier
 */
public class Decoration extends Obstacle {

    public Decoration(int initPosX, int initPosY, ItemType type) {
        super(initPosX, initPosY, type);
    }

    @Override
    public void changeImage() {
        changeImage(ClientConstants.DECORATiON_FOLDER);
    }
}
