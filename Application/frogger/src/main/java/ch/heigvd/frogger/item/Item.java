package ch.heigvd.frogger.item;

import ch.heigvd.frogger.CellAlreadyOccupiedException;
import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.Grid;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

/**
 *
 * @author Maxime Guillod
 */
public abstract class Item {

    private int posX;
    private int posY;
    private boolean visible = true;
    private final Grid grid = Grid.getInstance();
    private final Constants.ItemType type;

    public Item(int getPosX, int getPosY, Constants.ItemType type) {
        this.posX = getPosX;
        this.posY = getPosY;
        this.type = type;
    }

    /**
     * Update the item. For exemple, move obstacle every t secondes.
     */
    public abstract void update();

    /**
     * position X
     *
     * @return
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Position Y
     *
     * @return
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Set the visibility of the item on the grid
     *
     * @param visible
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Is the item visible on the grid
     *
     * @return
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Draw the item on the UI if it's visible
     *
     * @param gc
     */
    public void draw(GraphicsContext gc) {
        if (isVisible()) {
            Image image = new Image(getClass().getResource(Constants.IMG_FOLDER + Constants.OBSTACLE_FOLDER + type + ".png").toString(), grid.getCellWidth(), grid.getCellHeight(), true, true);
            // set img
            // dans le img, attention a la fin de la méthode pour éviter d'étirer le modèle
            gc.drawImage(image,
                    posX * grid.getCellWidth() + (grid.getCellWidth()-image.getWidth()) / 2,
                    posY * grid.getCellHeight() + (grid.getCellHeight()-image.getHeight()) / 2,
                    image.getWidth(),
                    image.getHeight()
            );
        }
    }

    private boolean isTopAccessible() {
        if (posY > 0) {
            return grid.isFree(posX, posY - 1);
        }
        return false;
    }

    private boolean isRightAccessible() {
        if (posX < Constants.NUM_COLS) {
            return grid.isFree(posX + 1, posY);
        }
        return false;
    }

    private boolean isBottomAccessible() {
        if (posY < Constants.NUM_ROWS) {
            return grid.isFree(posX, posY + 1);
        }
        return false;
    }

    private boolean isLeftAccessible() {
        if (posX > 0) {
            return grid.isFree(posX - 1, posY);
        }
        return false;
    }

    public void moveTop() throws CellAlreadyOccupiedException {
        synchronized (grid) {
            if (isTopAccessible()) {
                grid.removeItem(posX, posY);
                posY--;
                grid.addItem(this);
            }
        }
    }

    public void moveRight() throws CellAlreadyOccupiedException {
        synchronized (grid) {
            if (isRightAccessible()) {
                grid.removeItem(posX, posY);
                posX++;
                grid.addItem(this);
            }
        }
    }

    public void moveBottom() throws CellAlreadyOccupiedException {
        synchronized (grid) {
            if (isBottomAccessible()) {
                grid.removeItem(posX, posY);
                posY++;
                grid.addItem(this);
            }
        }
    }

    public void moveLeft() throws CellAlreadyOccupiedException {
        synchronized (grid) {
            if (isLeftAccessible()) {
                grid.removeItem(posX, posY);
                posX--;
                grid.addItem(this);
            }
        }
    }

}
