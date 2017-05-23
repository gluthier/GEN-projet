package ch.heigvd.frogger.item;

import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;
import java.util.Observable;

/**
 * Represent an Obstacle of the frogger
 *
 * @author Maxime Guillod
 * @author Gabriel Luthier
 * @date 25/04/17
 */
public class Obstacle extends Item {

    public Obstacle(int getPosX, int getPosY, Constants.ItemType type) throws CellAlreadyOccupiedException {
        super(getPosX, getPosY, type);
    }

    @Override
    public void changeImage() {
        changeImage(Constants.OBSTACLE_FOLDER);
    }

    protected void move(int diffX, int diffY) {
        setXGridCoordinate(getXGridCoordinate() + diffX);
        setYGridCoordinate(getYGridCoordinate() + diffY);
    }

    public void moveTop() {
    }

    public void moveTop(int diff) {
    }

    public void moveRight() {
    }

    public void moveRight(int diff) {
    }

    public void moveDown() {
    }

    public void moveDown(int diff) {
    }

    public void moveLeft() {
    }

    public void moveLeft(int diff) {
    }
}
