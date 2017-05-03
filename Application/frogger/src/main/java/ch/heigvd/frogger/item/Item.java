package ch.heigvd.frogger.item;

import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;
import javafx.scene.Node;
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

        String folder;
        if (type == Constants.ItemType.SkierLeft
                || type == Constants.ItemType.Skier
                || type == Constants.ItemType.SkierRight) {
            folder = Constants.PLAYER_FOLDER;
        } else {
            folder = Constants.OBSTACLE_FOLDER;
        }

        Image image = new Image(getClass().getResource(Constants.IMG_FOLDER + folder + type + ".png").toString(), Constants.CELL_WIDTH, Constants.CELL_HEIGHT, true, true);

        setImage(image);
 
        setXGridCoordinate(initPosX);
        setYGridCoordinate(initPosY);

        // Negative value -> image intrinsic width and height
        setFitHeight(-1);
        setFitWidth(-1);
    }

    /**
     * Set the item's position from it's grid coordinate
     * @param x item's position in the grid coordinates
     */
    public final void setXGridCoordinate(int x) {
        setX(x * Constants.CELL_WIDTH + (Constants.CELL_WIDTH - getImage().getWidth()) / 2);
    }

    /**
     * Set the item's position from it's grid coordinate
     * @param y item's position in the grid coordinates
     */
    public final void setYGridCoordinate(int y) {
        setY(y * Constants.CELL_HEIGHT + (Constants.CELL_HEIGHT - getImage().getHeight()) / 2);
    }

    public void setType(Constants.ItemType newType) {
        this.type = newType;
        changeImage();
    }

    public void changeImage() {
        String folder;
        if (type == Constants.ItemType.SkierLeft
                || type == Constants.ItemType.Skier
                || type == Constants.ItemType.SkierRight) {
            folder = Constants.PLAYER_FOLDER;
        } else {
            folder = Constants.OBSTACLE_FOLDER;
        }

        Image image = new Image(getClass().getResource(Constants.IMG_FOLDER + folder + type + ".png").toString(), Constants.CELL_WIDTH, Constants.CELL_HEIGHT, true, true);

        setImage(image);
    }

    /**
     * Get the item's position in grid coordinate
     * @return the item's position in grid coordinate
     */
    public final int getXGridCoordinate() {
        return (int) ((getX() - (Constants.CELL_WIDTH - getImage().getWidth()) / 2) / Constants.CELL_WIDTH);
    }

    /**
     * Get the item's position in grid coordinate
     * @return the item's position in grid coordinate
     */
    public final int getYGridCoordinate() {
        return (int) ((getY() - (Constants.CELL_HEIGHT - getImage().getHeight()) / 2) / Constants.CELL_HEIGHT);
    }

    /**
     * Check if there is a collision with an other Node in the Group
     * @return  if there is a collision
     */
    public boolean collisionWithOtherNode() {
        for (Node n : getParent().getChildrenUnmodifiable()) {
            if (n != this && this.getBoundsInParent().intersects(n.getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if there is a collision with the game corners
     * @return if there is a collision with the game corners
     */
    private boolean collisionWithEdge() {
        return this.getBoundsInParent().intersects(getParent().getBoundsInLocal());
    }

    /**
     * Move the item diffX and diffY (grid coordinates).
     * @param diffX the number of cells to move horizontally (in grid coordinates)
     * @param diffY the number of cells to move vertically (in grid coordinates)
     * @throws CellAlreadyOccupiedException if the cell is already occupied
     */
    private void move(int diffX, int diffY) throws CellAlreadyOccupiedException {
        double oldX = getX();
        double oldY = getY();

        setX(getX() + diffX * Constants.CELL_WIDTH);
        setY(getY() + diffY * Constants.CELL_HEIGHT);
        /* TODO
        if (collisionWithObstacle() || collisionWithEdge()) {
            setX(oldX);
            setY(oldY);
            throw new CellAlreadyOccupiedException();
        }*/
    }

    /**
     * Move from one cell to the top
     * @throws CellAlreadyOccupiedException
     */
    public void moveTop() throws CellAlreadyOccupiedException {
        move(0, -1);
    }

    /**
     * Move from one cell to the right
     * @throws CellAlreadyOccupiedException
     */
    public void moveRight() throws CellAlreadyOccupiedException {
        move(1, 0);
    }

    /**
     * Move from one cell to the bottom
     * @throws CellAlreadyOccupiedException
     */
    public void moveBottom() throws CellAlreadyOccupiedException {
        move(0, 1);
    }

    /**
     * Move from one cell to the left
     * @throws CellAlreadyOccupiedException
     */
    public void moveLeft() throws CellAlreadyOccupiedException {
        move(-1, 0);
    }
}