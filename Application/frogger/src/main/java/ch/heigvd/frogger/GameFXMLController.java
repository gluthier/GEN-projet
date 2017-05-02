package ch.heigvd.frogger;

import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;
import ch.heigvd.frogger.item.Obstacle;
import ch.heigvd.frogger.item.Player;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class GameFXMLController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Create the canvas
            Canvas canvas = new Canvas(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
            Group elementsGroup = new Group();

            // Load the background
            Image background = new Image(
                    getClass().getResource(Constants.BACKGROUND_PATH).toString(),
                    Constants.GAME_WIDTH,
                    Constants.GAME_HEIGHT,
                    false, true);

            // Draw the background
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.drawImage(background, 0, 0);

            // Create the two obstacles borders (chalets)
            for (int i = 0; i < Constants.NUM_ROWS; i++) {
                new Obstacle(0, i, Constants.ItemType.Chalet, elementsGroup); // chalet
                new Obstacle(1, i, Constants.ItemType.Chalet, elementsGroup); // chalet
                new Obstacle(Constants.NUM_COLS - 2, i, Constants.ItemType.ChaletVS, elementsGroup); // chaletVS
                new Obstacle(Constants.NUM_COLS - 1, i, Constants.ItemType.ChaletVS, elementsGroup);      // chaletVS
            }

            // Create the static obstacles
            for (int i = 0; i < Constants.NUM_OBSTACLES; i++) {
                Random r = new Random();
                int x = 0;
                int y = 0;

                Obstacle sapin = new Obstacle(x, y, Constants.ItemType.Sapin, elementsGroup); // sapin

                // TODO: Avoid infinite loop
                do {
                    sapin.setXFromGrid(r.nextInt(Constants.NUM_COLS - 4) + 2);
                    sapin.setYFromGrid(r.nextInt(Constants.NUM_ROWS - 2) + 2);
                } while (sapin.collisionWithObstacle());
            }

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
            
            // Skier on top of the mountain
            Group playerGroup = new Group();
            final Player player = new Player(14, 5, Constants.ItemType.Skier, playerGroup);

            // ---------------------------------------

            // make the canvas focusable
            canvas.setFocusTraversable(true);

            // keyboard handler
            canvas.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent event) {
                    if (Constants.ACTION_ATTACK.containsKey(event.getCode())) {
                        System.out.println("Attacker's action : " + Constants.ACTION_ATTACK.get(event.getCode()) + " on " + event.getCode());

                        try {
                            if (event.getCode() == KeyCode.LEFT) {
                                player.setType(Constants.ItemType.SkierLeft);
                                player.moveLeft();
                            } else if (event.getCode() == KeyCode.DOWN) {
                                player.setType(Constants.ItemType.Skier);
                                player.moveBottom();
                            } else if (event.getCode() == KeyCode.RIGHT) {
                                player.setType(Constants.ItemType.SkierRight);
                                player.moveRight();
                            }
                        } catch(CellAlreadyOccupiedException e) {
                            e.printStackTrace();
                        }
                    } else if (Constants.ACTION_DEFEND.containsKey(event.getCode())) {
                        System.out.println("Defender's action : " + Constants.ACTION_DEFEND.get(event.getCode()) + " on " + event.getCode());
                    }
                }
            });

            AnchorPane.setTopAnchor(canvas, 0.);
            anchorPane.getChildren().add(canvas);
            anchorPane.getChildren().add(elementsGroup);
            anchorPane.getChildren().add(playerGroup);

        } catch (CellAlreadyOccupiedException e2) {
            e2.printStackTrace();
        }
    }
}
