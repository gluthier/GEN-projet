package ch.heigvd.frogger.item;

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
                    posX * grid.getCellHeigth(),
                    posY * grid.getCellWidth(),
                    grid.getCellHeight(),
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
        if (posX < grid.getWidth() && isRightFree()) {
            throw new UnsupportedOperationException();
        }
    }

    public void moveBottom() {
        if (posY < grid.getHeight() && isBottomFree()) {
            throw new UnsupportedOperationException();
        }
    }

    public void moveLeft() {
        if (posX > 0 && isLeftFree()) {
            throw new UnsupportedOperationException();
        }
    }

}
