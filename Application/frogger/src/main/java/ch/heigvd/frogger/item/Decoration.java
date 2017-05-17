package ch.heigvd.frogger.item;

import java.util.Observable;

import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.Constants.ItemType;

/**
 * Display Decorations != obstacle on the game
 * @author Tony Clavien
 *
 */
public class Decoration extends Item{

	public Decoration(int initPosX, int initPosY, ItemType type) {
		super(initPosX, initPosY, type);
	}
	

    @Override
    public void changeImage() {
        changeImage(Constants.DECORATiON_FOLDER);
    }


	@Override
	public void update(Observable o, Object arg) {
		// do nothing
	}

}
