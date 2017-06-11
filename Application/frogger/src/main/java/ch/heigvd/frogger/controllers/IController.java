package ch.heigvd.frogger.controllers;

import ch.heigvd.frogger.GameFXMLController;
import ch.heigvd.frogger.item.*;

import java.util.*;

/**
 * Created by lognaume on 5/17/17.
 *
 * @author Gabriel Luthier
 */
public interface IController extends Observer {

    void setView(GameFXMLController gameFXMLController);

    void restartGame();

    void addDynamicObstacle(int row);

    void addObstacle(Obstacle o);

    void movePlayerLeft();

    void movePlayerRight();

    void movePlayerDown();
}

interface ICell {

    void add(Item i);

    void remove(Item i);

    boolean isEmpty();

    boolean containsMoreThanPlayer();
}
