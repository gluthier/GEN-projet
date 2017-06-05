package ch.heigvd.frogger.item;

import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.GameFXMLController;
import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;
import java.util.Observable;

/**
 * Represent an Obstacle of the frogger
 *
 * @author Maxime Guillod
 * @author Gabriel Luthier
 * @date 25/04/17
 */
public abstract class Obstacle extends Item {

    public Obstacle(int getPosX, int getPosY, Constants.ItemType type) {
        super(getPosX, getPosY, type);
    }

    public void removeObstacleFromView(GameFXMLController view) {
        // Do nothing
    }

    protected void move(int diffX, int diffY) {
        setXGridCoordinate(getXGridCoordinate() + diffX);
        setYGridCoordinate(getYGridCoordinate() + diffY);
    }

    public void moveTop() {
        // Do nothing
    }

    public void moveTop(int diff) {
        // Do nothing
    }

    public void moveRight() {
        // Do nothing
    }

    public void moveRight(int diff) {
        // Do nothing
    }

    public void moveDown() {
        // Do nothing
    }

    public void moveDown(int diff) {
        // Do nothing
    }

    public void moveLeft() {
        // Do nothing
    }

    public void moveLeft(int diff) {
        // Do nothing
    }
}
