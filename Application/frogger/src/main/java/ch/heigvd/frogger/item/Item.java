package ch.heigvd.frogger.item;

import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.Grid;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author Maxime Guillod
 */
public abstract class Item {

    private int posX;
    private int posY;
    private boolean visible = true;
    private final Grid grid = Grid.getInstance();

    public Item(int getPosX, int getPosY) {
        this.posX = getPosX;
        this.posY = getPosY;
    }

    /**
     * Update the item. For exemple, move obstacle every t secondes.
     */
    public abstract void update();

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
            // set img
            gc.drawImage(null,
                    posX * grid.getCellHeigt(),
                    posY * grid.getCellWidth(),
                    grid.getCellHeigt(),
                    grid.getCellWidth());
        }
    }

    private boolean isTopFree() {
        throw new UnsupportedOperationException();
    }

    private boolean isRightFree() {
        throw new UnsupportedOperationException();
    }

    private boolean isBottomFree() {
        throw new UnsupportedOperationException();
    }

    private boolean isLeftFree() {
        throw new UnsupportedOperationException();
    }

    public void moveTop() {
        if (posY > 0 && isTopFree()) {
            throw new UnsupportedOperationException();
        }
    }

    public void moveRight() {
        if (posX < Constants.NUM_COLS && isRightFree()) {
            throw new UnsupportedOperationException();
        }
    }

    public void moveBottom() {
        if (posY < Constants.NUM_COLS && isBottomFree()) {
            throw new UnsupportedOperationException();
        }
    }

    public void moveLeft() {
        if (posX > 0 && isLeftFree()) {
            throw new UnsupportedOperationException();
        }
    }

}
