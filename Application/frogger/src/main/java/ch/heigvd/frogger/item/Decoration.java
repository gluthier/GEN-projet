package ch.heigvd.frogger.item;

import java.util.Observable;

import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.Constants.ItemType;
import ch.heigvd.frogger.GameFXMLController;
import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;

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
        changeImage(Constants.DECORATiON_FOLDER);
    }
}
