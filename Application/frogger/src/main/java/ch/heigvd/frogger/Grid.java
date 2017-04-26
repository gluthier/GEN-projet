package ch.heigvd.frogger;

import java.util.Arrays;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author lognaume
 */
public class Grid {

    // TODO: change Object to Item
    private final Object[][] items;

    final double cellWidth = Constants.GAME_WIDTH / Constants.NUM_COLS;
    final double cellHeight = Constants.GAME_HEIGHT / Constants.NUM_ROWS;

    public Grid() {
        this.items = new Object[Constants.NUM_ROWS][Constants.NUM_COLS];
        Arrays.fill(items, null);
    }

    // TODO: change Object to Item
    public Object getItem(int x, int y) throws IllegalArgumentException {
        checkIndex(x, y);
        return items[x][y];
    }

    // TODO: change Object to Item
    public void addItem(Object item, int x, int y) throws CellAlreadyOccupiedException {
        checkIndex(x, y);
        if (items[x][y] != null) {
            throw new CellAlreadyOccupiedException();
        }
        items[x][y] = item;
    }

    // TODO: change Object to Item
    public Object removeItem(int x, int y) throws IllegalArgumentException {
        checkIndex(x, y);

        if (items[x][y] == null) {
            throw new IllegalArgumentException("Can't remove element from empty cell");
        }
        Object item = items[x][y];
        items[x][y] = null;
        return item;

    }
    
    public double getCellHeigt() {
        return cellHeight;
    }
    
    public double getCellWidth() {
        return cellWidth;
    }

    public boolean isFree(int x, int y) throws IllegalArgumentException {
        checkIndex(x, y);
        return items[x][y] == null;
    }

    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(1);
        for (int i = 0; i < Constants.NUM_ROWS; i++) {
            gc.strokeLine(0, i * cellHeight, Constants.GAME_WIDTH, i * cellHeight);
        }

        for (int i = 0; i < Constants.NUM_COLS; i++) {
            gc.strokeLine(i * cellWidth, 0, i * cellWidth, Constants.GAME_HEIGHT);
        }
        
        for (int x = 0; x < items.length; x++) {
            for (int y = 0; y < items[0].length; y++) {
                items[x][y].draw(gc);
            }
        }
    }

    private void checkIndex(int x, int y) throws IllegalArgumentException {
        if (x < 0 || x >= Constants.NUM_ROWS || y < 0 || y >= Constants.NUM_COLS) {
            throw new IllegalArgumentException("Incorrect element index");
        }
    }
}
