package ch.heigvd.frogger;

import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;
import ch.heigvd.frogger.item.*;
import javafx.scene.control.Cell;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by lognaume on 5/17/17.
 */
public class GameController implements Observer {
    private List<Obstacle> obstacles;
    private List<DynamicObstacle> dynamicObstacles;
    private Player player;
    private static GameFXMLController view;
    private static GameController instance;
    private Item[][] grid;

    public static GameController getInstance() throws Exception {
        if (view == null) {
            throw new Exception("Can't get the game controller before setting the view");
        } else if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public static void setView(GameFXMLController view) {
        GameController.view = view;
    }

    private GameController() {
        obstacles = new LinkedList<>();
        dynamicObstacles = new LinkedList<>();
        grid = new Item[Constants.NUM_COLS][Constants.NUM_ROWS];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = null;
            }
        }

        try {
            // Skier on top of the mountain
            addPlayer();
        } catch (CellAlreadyOccupiedException e) {
            // TODO: manage exceptions
        }
        // Create the two obstacles borders (chalets)
        try {
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
        } catch (CellAlreadyOccupiedException e) {
            // TODO: manage exceptions
        }

        try {
            // Create the static obstacles
            for (int i = 0; i < Constants.NUM_OBSTACLES; i++) {
                Random r = new Random();
                int x = 0;
                int y = 0;

                Obstacle sapin = new Obstacle(x, y, Constants.ItemType.Sapin); // sapin
                addObstacle(sapin);

                // TODO: Avoid infinite loop
                do {
                    sapin.setXGridCoordinate(r.nextInt(Constants.NUM_COLS - 4) + 2);
                    sapin.setYGridCoordinate(r.nextInt(Constants.NUM_ROWS - Constants.INITIAL_PLAYER_Y) + Constants.INITIAL_PLAYER_Y);
                } while (checkCollision(sapin));
            }
        } catch (CellAlreadyOccupiedException e) {
            // TODO: manage exceptions
        }

        // Observe the clock (tick)
        ItemClock.getInstance().addObserver(this);
    }

    public void addDynamicObstacle(int row) {
        try {
            DynamicObstacle o = new DynamicObstacle(Constants.ItemType.Saucisson);
            o.setYGridCoordinate(row);
            view.addItem(o);
            dynamicObstacles.add(o);
            grid[o.getXGridCoordinate()][o.getYGridCoordinate()] = o;
        } catch (CellAlreadyOccupiedException e) {
            //TODO: manage exceptions
        }

    }

    public void addObstacle(Obstacle o) {
        view.addItem(o);
        obstacles.add(o);
        grid[o.getXGridCoordinate()][o.getYGridCoordinate()] = o;
    }

    private void addPlayer() throws CellAlreadyOccupiedException {
        player = new Player(Constants.INITIAL_PLAYER_X, Constants.INITIAL_PLAYER_Y, Constants.ItemType.Skier);
        view.addPlayer(player);
        grid[Constants.INITIAL_PLAYER_X][Constants.INITIAL_PLAYER_Y] = player;
    }

    @Override
    public void update(Observable observable, Object o) {
        for (DynamicObstacle ob : dynamicObstacles) {
            grid[ob.getXGridCoordinate()][ob.getYGridCoordinate()] = null;
            if (!checkCollisionWithEdge(ob)) {
                ob.moveRight();
                if (checkCollision(ob)) {
                    collisionDetected();
                }
                grid[ob.getXGridCoordinate()][ob.getYGridCoordinate()] = ob;
            }
        }
    }

    public void movePlayerLeft() {
        player.setType(Constants.ItemType.SkierLeft);
        grid[player.getXGridCoordinate()][player.getYGridCoordinate()] = null;
        player.moveLeft();
        if (checkCollision(player)) {
            collisionDetected();
        }
        grid[player.getXGridCoordinate()][player.getYGridCoordinate()] = player;
    }

    public void movePlayerRight() {
        player.setType(Constants.ItemType.SkierRight);
        grid[player.getXGridCoordinate()][player.getYGridCoordinate()] = null;
        player.moveRight();
        if (checkCollision(player)) {
            collisionDetected();
        }
        grid[player.getXGridCoordinate()][player.getYGridCoordinate()] = player;
    }

    public void movePlayerDown() {
        player.setType(Constants.ItemType.Skier);
        grid[player.getXGridCoordinate()][player.getYGridCoordinate()] = null;
        player.moveDown();
        if (checkCollision(player)) {
            collisionDetected();
        }
        grid[player.getXGridCoordinate()][player.getYGridCoordinate()] = player;
    }

    /**
     * Check collision between player and all obstacles
     * @param p
     * @return
     */
    private boolean checkCollision(Player p) {
        return grid[p.getXGridCoordinate()][p.getYGridCoordinate()] != null;

        /*
        for (Obstacle o : obstacles) {
            if (checkCollision(p, o)) {
                return true;
            }
        }
        for (DynamicObstacle o : dynamicObstacles) {
            if (checkCollision(p, o)) {
                return true;
            }
        }
        return false; // checkCollisionWithEdge(p);
        */
    }

    /**
     * Check collision only with player
     * @param o
     * @return
     */

    private boolean checkCollision(Obstacle o) {
        // return checkCollision(o, player);
        return grid[o.getXGridCoordinate()][o.getYGridCoordinate()] != null && grid[o.getXGridCoordinate()][o.getYGridCoordinate()] instanceof Player;
    }

    /**
     * Check collision between two items
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
        return (i.getXGridCoordinate() < 0 || i.getXGridCoordinate() >= Constants.NUM_COLS-1 || i.getYGridCoordinate() < 0 || i.getYGridCoordinate() >= Constants.NUM_ROWS-1);
    }

    private void collisionDetected() {
        System.out.println("Collision !!!");
    }
}
