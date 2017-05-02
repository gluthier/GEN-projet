package ch.heigvd.frogger.item;

import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.Grid;
import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private ImageView imageView = null;

    public Item(int posX, int posY, Constants.ItemType type) throws CellAlreadyOccupiedException {
    	if (posX < 0 || posY < 0) {
    		throw new IndexOutOfBoundsException("Initial position must be positive");
    	}
    	
        this.posX = posX;
        this.posY = posY;
        this.type = type;
        grid.addItem(this);
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
    public void draw(Node parent) {
        if (isVisible()) {
        	if (imageView == null) {
                Image image = new Image(getClass().getResource(Constants.IMG_FOLDER + Constants.OBSTACLE_FOLDER + type + ".png").toString(), grid.getCellWidth(), grid.getCellHeight(), true, true);

        		imageView = new ImageView(image);
        		
        		// Negative value -> image intrinseque width and height
        		imageView.setFitHeight(-1);
        		imageView.setFitWidth(-1);
        	}
        	imageView.setX(posX * grid.getCellWidth() + (grid.getCellWidth()-imageView.getImage().getWidth()) / 2);
    		imageView.setY(posY * grid.getCellHeight() + (grid.getCellHeight()-imageView.getImage().getHeight()) / 2);
        }
    }

    private boolean isTopAccessible() {
        if (posY > 1) {
            return grid.isFree(posX, posY - 1);
        }
        return false;
    }

    private boolean isRightAccessible() {
        if (posX < Constants.NUM_COLS-1) {
            return grid.isFree(posX + 1, posY);
        }
        return false;
    }

    private boolean isBottomAccessible() {
        if (posY < Constants.NUM_ROWS-1) {
            return grid.isFree(posX, posY + 1);
        }
        return false;
    }

    private boolean isLeftAccessible() {
        if (posX > 1) {
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
