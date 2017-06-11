package ch.heigvd.frogger.controllers;

import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.GameFXMLController;
import ch.heigvd.frogger.ItemClock;
import ch.heigvd.frogger.item.*;
import ch.heigvd.frogger.tcp.TCPClient;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel Luthier
 * @author Guillaume Milani
 */
public class ClientController implements IController {

    private List<DynamicObstacle> dynamicObstacles;
    private Player player;

    private GameFXMLController view;
    private TCPClient client;

    public ClientController(GameFXMLController view, TCPClient client, List<FixedObstacle> fixedObstacles) {
        this.view = view;
        this.client = client;
        initializeGame(fixedObstacles);
    }

    private void initializeGame(List<FixedObstacle> fixedObstacles) {
        dynamicObstacles = new LinkedList<>();

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
                addObstacle(new FixedObstacle(0, i, Constants.ItemType.getRow(Constants.OBSTACLE_ROW.inverse().get(i))));
            } else {
                addObstacle(new FixedObstacle(0, i, Constants.ItemType.Chalet));
            }

            addObstacle(new FixedObstacle(1, i, Constants.ItemType.Chalet));
            addObstacle(new FixedObstacle(Constants.NUM_COLS - 2, i, Constants.ItemType.ChaletVS));
            addObstacle(new FixedObstacle(Constants.NUM_COLS - 1, i, Constants.ItemType.ChaletVS));
        }

        // Create the static obstacles
        for (FixedObstacle o : fixedObstacles) {
            addObstacle(o);
        }

        // Observe the clock (tick)
        ItemClock.getInstance().addObserver(this);
    }

    @Override
    public void setView(GameFXMLController gameFXMLController) {
        this.view = gameFXMLController;
    }

    public void restartGame() {
        view.reset();
        initializeGame(new LinkedList<>());
        ItemClock.getInstance().resume();
    }

    public void addDynamicObstacle(int row) {
        client.addDynamicObstacle(row);
    }

    public void addObstacle(DynamicObstacle o) {
        dynamicObstacles.add(o);
        addObstacle((Obstacle)o);
    }

    public void removeObstacle(DynamicObstacle o) {
        dynamicObstacles.remove(o);
        removeObstacle((Obstacle)o);
    }

    public void addObstacle(Obstacle o) {
        view.addItem(o);
    }

    public void removeObstacle(Obstacle o) {
        view.removeItem(o);
    }

    private void addPlayer() {
        player = new Player(Constants.INITIAL_PLAYER_X, Constants.INITIAL_PLAYER_Y, Constants.ItemType.Skier);
        view.addPlayer(player);
    }

    @Override
    public void update(Observable observable, Object o) {
        // Fetch informations from the server
        try {
            client.fetchServerInfos();
        } catch (IOException e) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, e);
        }

        // Move the skier
        if (client.getSkierX() < player.getXGridCoordinate()) {
            player.setType(Constants.ItemType.SkierLeft);
        } else if (client.getSkierX() > player.getXGridCoordinate()) {
            player.setType(Constants.ItemType.SkierRight);
        } else if (client.getSkierY() > player.getYGridCoordinate()) {
            player.setType(Constants.ItemType.Skier);;
        }

        player.setXGridCoordinate(client.getSkierX());
        player.setYGridCoordinate(client.getSkierY());

        // Remove the old dynamic obstacles
        dynamicObstacles.forEach(this::removeObstacle);

        // Add the new dynamic obstacles
        client.getDynamicObstacles().forEach(this::addObstacle);

        if (client.isSkierWon()) {
            view.showWinnerMessage();
            ItemClock.getInstance().pause();
        } else if (client.isVaudoisWon()) {
            view.showLooserMessage();
            ItemClock.getInstance().pause();
        }
    }

    public void movePlayerLeft() {
        client.moveLeft();
    }

    public void movePlayerRight() {
        client.moveRight();
    }

    public void movePlayerDown() {
        client.moveBottom();
    }
}
