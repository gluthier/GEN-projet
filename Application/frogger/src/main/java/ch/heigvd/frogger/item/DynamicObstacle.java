package ch.heigvd.frogger.item;

import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.Constants.ItemType;
import ch.heigvd.frogger.GameFXMLController;
import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Define a Dynamic Obstacle ( for now)
 *
 * @author Tony Clavien
 * @author Gabriel Luthier
 */
public class DynamicObstacle extends Obstacle {

    //TODO add a local Timer or a global one who update the position of the obstacle
    // TODO remove magic number
    public DynamicObstacle(int row, ItemType type) {
        this(2, row, type);
    }

    public DynamicObstacle(int column, int row, ItemType type) {
        super(column, row, type);
    }

    @Override
    public void changeImage() {
        changeImage(Constants.OBSTACLE_FOLDER);
    }

    public void removeObstacleFromView(GameFXMLController view) {
        view.removeItem(this);
    }

    public void moveTop() {
        moveTop(1);
    }

    public void moveTop(int diff) {
        move(0, -diff);
    }

    public void moveRight() {
        moveRight(1);
    }

    public void moveRight(int diff) {
        move(diff, 0);
    }

    public void moveDown() {
        moveDown(1);
    }

    public void moveDown(int diff) {
        move(0, diff);
    }

    public void moveLeft() {
        moveLeft(1);
    }

    public void moveLeft(int diff) {
        move(-diff, 0);
    }
}
