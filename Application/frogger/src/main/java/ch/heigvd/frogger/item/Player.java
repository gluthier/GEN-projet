package ch.heigvd.frogger.item;

import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    @Override
    public void changeImage(String imagePath) {
        super.changeImage(imagePath); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changeImage() {
        String folder;
        if (getType() == Constants.ItemType.SkierLeft
                || getType() == Constants.ItemType.Skier
                || getType() == Constants.ItemType.SkierRight) {
            folder = Constants.PLAYER_FOLDER;
        } else {
            folder = Constants.OBSTACLE_FOLDER;
        }

        changeImage(folder);
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            moveBottom();
        } catch (CellAlreadyOccupiedException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
