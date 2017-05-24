package ch.heigvd.frogger;

import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;
import ch.heigvd.frogger.exception.ViewNotSetException;
import ch.heigvd.frogger.item.*;
import javafx.scene.control.Cell;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;

/**
 * Created by lognaume on 5/17/17.
 *
 * @author Gabriel Luthier
 */
public class GameController implements Observer {

    private class Cell {

        private List<Item> content = new LinkedList<>();

        public void add(Item item) {
            content.add(item);
        }

        public void remove(Item item) {
            content.remove(item);
        }

        public boolean isEmpty() {
            return content.isEmpty();
        }

        public boolean containsMoreThanPlayer() {
            for (Item i : content) {
                if (!i.equals(player)) {
                    return true;
                }
            }
            return false;
        }
    }

    private List<Obstacle> obstacles;
    private Player player;
    private Cell[][] grid;

    private static GameFXMLController view;
    private static GameController instance;

    public static GameController getInstance() throws ViewNotSetException {
        if (view == null) {
            throw new ViewNotSetException();
        } else if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public static void setView(GameFXMLController view) {
        GameController.view = view;
    }

    private GameController() {
        // Initialize the game
        initializeGame();
    }

    private void initializeGame() {
        obstacles = new LinkedList<>();
        grid = new Cell[Constants.NUM_COLS][Constants.NUM_ROWS];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = new Cell();
            }
        }

        try {
            // Skier on top of the mountain
            addPlayer();

            // Start Flag
            addObstacle(new Decoration(Constants.INITIAL_PLAYER_X + 1, Constants.INITIAL_PLAYER_Y, Constants.ItemType.StartLeft));
            addObstacle(new Decoration(Constants.INITIAL_PLAYER_X - 1, Constants.INITIAL_PLAYER_Y, Constants.ItemType.StartRight));

            // Finish Flags
            addObstacle(new Decoration(Constants.FIRST_FINISH_TO_RIGHT, Constants.NUM_ROWS - 1, Constants.ItemType.FinishRight));
            addObstacle(new Decoration(Constants.FIRST_FINISH_TO_LEFT, Constants.NUM_ROWS - 1, Constants.ItemType.FinishLeft));
            addObstacle(new Decoration(Constants.SECOND_FINISH_TO_RIGHT, Constants.NUM_ROWS - 1, Constants.ItemType.FinishRight));
            addObstacle(new Decoration(Constants.SECOND_FINISH_TO_LEFT, Constants.NUM_ROWS - 1, Constants.ItemType.FinishLeft));
            addObstacle(new Decoration(Constants.THIRD_FINISH_TO_RIGHT, Constants.NUM_ROWS - 1, Constants.ItemType.FinishRight));
            addObstacle(new Decoration(Constants.THIRD_FINISH_TO_LEFT, Constants.NUM_ROWS - 1, Constants.ItemType.FinishLeft));
            addObstacle(new Decoration(Constants.FOURTH_FINISH_TO_RIGHT, Constants.NUM_ROWS - 1, Constants.ItemType.FinishRight));
            addObstacle(new Decoration(Constants.FOURTH_FINISH_TO_LEFT, Constants.NUM_ROWS - 1, Constants.ItemType.FinishLeft));

            // Create the two obstacles borders (chalets)
            for (int i = 0; i < Constants.NUM_ROWS; i++) {
                if (Constants.OBSTACLE_ROW.inverse().containsKey(i)) {
                    addObstacle(new Obstacle(0, i, Constants.ItemType.getRow(Constants.OBSTACLE_ROW.inverse().get(i))));
                } else {
                    addObstacle(new Obstacle(0, i, Constants.ItemType.Chalet));
                }

                addObstacle(new Obstacle(1, i, Constants.ItemType.Chalet));
                addObstacle(new Obstacle(Constants.NUM_COLS - 2, i, Constants.ItemType.ChaletVS));
                addObstacle(new Obstacle(Constants.NUM_COLS - 1, i, Constants.ItemType.ChaletVS));
            }

            // Create the static obstacles
            for (int i = 0; i < Constants.NUM_OBSTACLES; i++) {
                Random r = new Random();
                int x = 0;
                int y = 0;

                Obstacle sapin = new Obstacle(x, y, Constants.ItemType.Sapin); // sapin
                addObstacle(sapin);

                do {
                    sapin.setXGridCoordinate(r.nextInt(Constants.NUM_COLS - 4) + 2);
                    sapin.setYGridCoordinate(r.nextInt(Constants.NUM_ROWS - Constants.INITIAL_PLAYER_Y) + Constants.INITIAL_PLAYER_Y);
                } while (!grid[sapin.getXGridCoordinate()][sapin.getYGridCoordinate()].isEmpty());
            }
        } catch (CellAlreadyOccupiedException e) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, e);
        }

        // Observe the clock (tick)
        ItemClock.getInstance().addObserver(this);
    }

    public void restartGame() {
        view.reset();
        initializeGame();
        ItemClock.getInstance().resume();
    }

    public void addDynamicObstacle(int row) {
        try {
            DynamicObstacle o = new DynamicObstacle(Constants.ItemType.Saucisson);
            o.setYGridCoordinate(row);
            view.addItem(o);
            obstacles.add(o);
            grid[o.getXGridCoordinate()][o.getYGridCoordinate()].add(o);
        } catch (CellAlreadyOccupiedException e) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void addObstacle(Obstacle o) {
        view.addItem(o);
        obstacles.add(o);
        grid[o.getXGridCoordinate()][o.getYGridCoordinate()].add(o);
    }

    private void addPlayer() throws CellAlreadyOccupiedException {
        player = new Player(Constants.INITIAL_PLAYER_X, Constants.INITIAL_PLAYER_Y, Constants.ItemType.Skier);
        view.addPlayer(player);
        grid[Constants.INITIAL_PLAYER_X][Constants.INITIAL_PLAYER_Y].add(player);
    }

    @Override
    public void update(Observable observable, Object o) {
        // We use an iterator to avoid an error when removing an Obstacle while iterating over the list
        Iterator<Obstacle> iter = obstacles.iterator();
        while (iter.hasNext()) {
            Obstacle ob = iter.next();
            
            if (!isItemAtRightEdge(ob)) {
                grid[ob.getXGridCoordinate()][ob.getYGridCoordinate()].remove(ob);
                ob.moveRight();
                grid[ob.getXGridCoordinate()][ob.getYGridCoordinate()].add(ob);
            } else {
                grid[ob.getXGridCoordinate()][ob.getYGridCoordinate()].remove(ob);
                ob.removeObstacleFromView(view);
                iter.remove();
            }
        }
        if (checkCollision()) {
            collisionDetected();
        }
    }

    public void movePlayerLeft() {
        player.setType(Constants.ItemType.SkierLeft);
        grid[player.getXGridCoordinate()][player.getYGridCoordinate()].remove(player);
        player.moveLeft();
        if (checkCollision()) {
            collisionDetected();
        }
        grid[player.getXGridCoordinate()][player.getYGridCoordinate()].add(player);
    }

    public void movePlayerRight() {
        player.setType(Constants.ItemType.SkierRight);
        grid[player.getXGridCoordinate()][player.getYGridCoordinate()].remove(player);
        player.moveRight();
        if (checkCollision()) {
            collisionDetected();
        }
        grid[player.getXGridCoordinate()][player.getYGridCoordinate()].add(player);
    }

    public void movePlayerDown() {
        player.setType(Constants.ItemType.Skier);
        grid[player.getXGridCoordinate()][player.getYGridCoordinate()].remove(player);
        player.moveDown();
        if (checkCollision()) {
            collisionDetected();
        }
        if (hasReachedTheEnd()) {
            checkIfWon();
        }
        grid[player.getXGridCoordinate()][player.getYGridCoordinate()].add(player);
    }

    /**
     * Check if player has reached the end
     *
     * @return
     */
    private boolean hasReachedTheEnd() {
        return player.getYGridCoordinate() == Constants.NUM_ROWS - 1;
    }

    private void checkIfWon() {
        int x = player.getXGridCoordinate();

        if ((x > Constants.FIRST_FINISH_TO_RIGHT && x < Constants.FIRST_FINISH_TO_LEFT)
                || (x > Constants.SECOND_FINISH_TO_RIGHT && x < Constants.SECOND_FINISH_TO_LEFT)
                || (x > Constants.THIRD_FINISH_TO_RIGHT && x < Constants.THIRD_FINISH_TO_LEFT)
                || (x > Constants.FOURTH_FINISH_TO_RIGHT && x < Constants.FOURTH_FINISH_TO_LEFT)) {
            gameIsWon();
        } else {
            gameIsLost();
        }
    }

    /**
     * Check collision between player and all obstacles
     *
     * @return
     */
    private boolean checkCollision() {
        return grid[player.getXGridCoordinate()][player.getYGridCoordinate()].containsMoreThanPlayer();
    }

    /**
     * Check collision only with player
     *
     * @param o
     * @return
     */
    private boolean checkCollision(Obstacle o) {
        return grid[o.getXGridCoordinate()][o.getYGridCoordinate()].containsMoreThanPlayer();
    }

    /**
     * Check collision between two items
     *
     * @param i
     * @param j
     * @return
     */
    private boolean checkCollision(Item i, Item j) {
        return i.getXGridCoordinate() == j.getXGridCoordinate() && i.getYGridCoordinate() == j.getYGridCoordinate();
    }

    /**
     * Check if there is a collision with the game corners
     *
     * @return if there is a collision with the game corners
     */
    private boolean checkCollisionWithEdge(Item i) {
        return (i.getXGridCoordinate() < 0 || i.getXGridCoordinate() >= Constants.NUM_COLS - 1 || i.getYGridCoordinate() < 0 || i.getYGridCoordinate() >= Constants.NUM_ROWS - 1);
    }

    private boolean isItemAtRightEdge(Item i) {
        return i.getXGridCoordinate() >= Constants.NUM_COLS - 1;
    }

    private void collisionDetected() {
        gameIsLost();
    }

    private void gameIsWon() {
        ItemClock.getInstance().pause();
        view.showWinnerMessage();
    }

    private void gameIsLost() {
        ItemClock.getInstance().pause();
        view.showLooserMessage();
    }
}
