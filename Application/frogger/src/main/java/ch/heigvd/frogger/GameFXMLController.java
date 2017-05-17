package ch.heigvd.frogger;

import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;
import ch.heigvd.frogger.item.Item;
import ch.heigvd.frogger.item.Obstacle;
import ch.heigvd.frogger.item.DynamicObstacle;
import ch.heigvd.frogger.item.Player;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameFXMLController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    private Player player;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Create the canvas
        Canvas canvas = new Canvas(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        Group itemsGroup = new Group();
        Group staticObstacleGroup = new Group();
        Group dynamicObstacleGroup = new Group();
        itemsGroup.getChildren().add(staticObstacleGroup);
        itemsGroup.getChildren().add(dynamicObstacleGroup);

        AnchorPane.setTopAnchor(canvas, 0.);
        anchorPane.getChildren().add(canvas);
        anchorPane.getChildren().add(itemsGroup);

        try {
            // Load the background
            Image background = new Image(
                    getClass().getResource(Constants.BACKGROUND_PATH).toString(),
                    Constants.GAME_WIDTH,
                    Constants.GAME_HEIGHT,
                    false, true);

            // Draw the background
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.drawImage(background, 0, 0);

            // ----- ONLY FOR DEBUG : draw lines -----
            // TODO : Remove
            // Draw the lines directly on the graphicContext
            gc.setStroke(Color.GRAY);
            gc.setLineWidth(1);
            for (int i = 0; i < Constants.NUM_ROWS; i++) {
                gc.strokeLine(0, i * Constants.CELL_HEIGHT, Constants.GAME_WIDTH, i * Constants.CELL_HEIGHT);
            }

            for (int i = 0; i < Constants.NUM_COLS; i++) {
                gc.strokeLine(i * Constants.CELL_WIDTH, 0, i * Constants.CELL_WIDTH, Constants.GAME_HEIGHT);
            }
            // ---------------------------------------

            // make the canvas focusable
            canvas.setFocusTraversable(true);

            // keyboard handler
            canvas.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
                if (Constants.ACTION_ATTACK.containsKey(event.getCode())) {
                    // System.out.println("Attacker's action : " + Constants.ACTION_ATTACK.get(event.getCode()) + " on " + event.getCode());
                    Constants.ACTION_ATTACK.get(event.getCode()).act();
                } else if (Constants.ACTION_DEFEND.containsKey(event.getCode())) {
                    // System.out.println("Defender's action : " + Constants.ACTION_DEFEND.get(event.getCode()) + " on " + event.getCode());
                    Constants.ACTION_DEFEND.get(event.getCode()).act();
                }
            });

        } catch (Exception e) {
            System.out.println("Exception catch !!" + e.getMessage());
        }
    }

    public void addItem(Item i) {
        anchorPane.getChildren().add(i);
    }

    public void addPlayer(Player p) {
        anchorPane.getChildren().add(p);
        player = p;
    }
}
