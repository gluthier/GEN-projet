package ch.heigvd.frogger.item;

import ch.heigvd.frogger.ClientConstants;
import ch.heigvd.protocol.Constants;
import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represent a game item. It's mainly an ImageView node implementing collision detection.
 *
 * @author Maxime Guillod, Guillaume Milani, Gabriel Luthier
 * @date 02/05/17
 */
public abstract class Item extends ImageView {

    private Constants.ItemType type;

    public Item(int initPosX, int initPosY, Constants.ItemType type) {
        super();

        this.type = type;

        if (initPosX < 0 || initPosY < 0) {
            throw new IndexOutOfBoundsException("Initial position must be positive");
        }
        changeImage();

        setXGridCoordinate(initPosX);
        setYGridCoordinate(initPosY);

        // Negative value -> image intrinsic width and height
        setFitHeight(-1);
        setFitWidth(-1);
    }

    /**
     * Set the item's position from it's grid coordinate
     *
     * @param x item's position in the grid coordinates
     */
    public final void setXGridCoordinate(int x) {
        setX(x * Constants.CELL_WIDTH + (Constants.CELL_WIDTH - getImage().getWidth()) / 2);
    }

    /**
     * Set the item's position from it's grid coordinate
     *
     * @param y item's position in the grid coordinates
     */
    public final void setYGridCoordinate(int y) {
        setY(y * Constants.CELL_HEIGHT + (Constants.CELL_HEIGHT - getImage().getHeight()) / 2);
    }

    public void setType(Constants.ItemType newType) {
        this.type = newType;
        changeImage();
    }

    /**
     * Type of the item
     *
     * @return
     */
    public Constants.ItemType getType() {
        return type;
    }

    /**
     * Update the image
     */
    public abstract void changeImage();

    /**
     * Update the image with specific folder
     *
     * @param folder
     */
    protected void changeImage(String folder) {
        Image image = new Image(getClass().getResource(ClientConstants.IMG_FOLDER + folder + type + ".png").toString(), Constants.CELL_WIDTH, Constants.CELL_HEIGHT, true, true);

        setImage(image);
    }

    /**
     * Get the item's position in grid coordinate
     *
     * @return the item's position in grid coordinate
     */
    public final int getXGridCoordinate() {
        return (int)Math.round((getX() - (Constants.CELL_WIDTH - getImage().getWidth()) / 2.) / Constants.CELL_WIDTH);
    }

    /**
     * Get the item's position in grid coordinate
     *
     * @return the item's position in grid coordinate
     */
    public final int getYGridCoordinate() {
        return (int)Math.round((getY() - (Constants.CELL_HEIGHT - getImage().getHeight()) / 2.) / Constants.CELL_HEIGHT);
    }

    /**
     * Move the item diffX and diffY (grid coordinates).
     *
     * @param diffX the number of cells to move horizontally (in grid coordinates)
     * @param diffY the number of cells to move vertically (in grid coordinates)
     * @throws CellAlreadyOccupiedException if the cell is already occupied
     */
    protected void move(int diffX, int diffY) {
        setXGridCoordinate(getXGridCoordinate() + diffX);
        setYGridCoordinate(getYGridCoordinate() + diffY);
    }

    /**
     * Move from one cell to the top
     */
    public void moveTop() {
        moveTop(1);
    }

    public void moveTop(int diff) {
        move(0, -diff);
    }

    /**
     * Move from one cell to the right
     */
    public void moveRight() {
        moveRight(1);
    }

    public void moveRight(int diff) {
        move(diff, 0);
    }

    /**
     * Move from one cell to the bottom
     */
    public void moveDown() {
        moveDown(1);
    }

    public void moveDown(int diff) {
        move(0, diff);
    }

    /**
     * Move from one cell to the left
     */
    public void moveLeft(){
        moveLeft(1);
    }

    public void moveLeft(int diff) {
        move(-diff, 0);
    }

    public double getWidth() {
        return getImage().getWidth();
    }

    public double getHeight() {
        return getImage().getHeight();
    }
}
