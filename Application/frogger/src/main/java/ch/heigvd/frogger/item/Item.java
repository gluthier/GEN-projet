package ch.heigvd.frogger.item;

import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Maxime Guillod
 */
public abstract class Item extends ImageView {

    private final Group parent;
    private final Constants.ItemType type;

    public Item(int posX, int posY, Constants.ItemType type, Group parent) throws CellAlreadyOccupiedException {
        super();

        if (posX < 0 || posY < 0) {
            throw new IndexOutOfBoundsException("Initial position must be positive");
        }

        Image image = new Image(getClass().getResource(Constants.IMG_FOLDER + Constants.OBSTACLE_FOLDER + type + ".png").toString(), Constants.CELL_WIDTH, Constants.CELL_HEIGHT, true, true);

        setImage(image);
        
        setXFromGrid(posX);
        setYFromGrid(posY);

        // Negative value -> image intrinsic width and height
        setFitHeight(-1);
        setFitWidth(-1);

        this.type = type;
        this.parent = parent;
        parent.getChildren().add(this);
    }

    public final void setXFromGrid(int x) {
        setX(x * Constants.CELL_WIDTH + (Constants.CELL_WIDTH - getImage().getWidth()) / 2);
    }

    public final void setYFromGrid(int y) {
        setY(y * Constants.CELL_HEIGHT + (Constants.CELL_HEIGHT - getImage().getHeight()) / 2);

    }

    public boolean collisionWithObstacle() {
        for (Node n : parent.getChildren()) {
            if (n != this && this.getBoundsInParent().intersects(n.getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }

    private boolean collisionWithEdge() {
        return this.getBoundsInParent().intersects(parent.getBoundsInLocal());
    }

    private void move(int diffX, int diffY) {
        double oldX = getX();
        double oldY = getY();

        setX(getX() + diffX * Constants.CELL_WIDTH);
        setY(getY() + diffY * Constants.CELL_HEIGHT);

        if (collisionWithObstacle() || collisionWithEdge()) {
            setX(oldX);
            setY(oldY);
        }
    }

    public void moveTop() throws CellAlreadyOccupiedException {
        move(0, -1);
    }

    public void moveRight() throws CellAlreadyOccupiedException {
        move(1, 0);
    }

    public void moveBottom() throws CellAlreadyOccupiedException {
        move(0, 1);
    }

    public void moveLeft() throws CellAlreadyOccupiedException {
        move(-1, 0);
    }
}
