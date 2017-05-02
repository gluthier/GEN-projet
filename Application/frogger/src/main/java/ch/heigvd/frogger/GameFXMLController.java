package ch.heigvd.frogger;

import ch.heigvd.frogger.item.Item;
import ch.heigvd.frogger.item.Obstacle;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class GameFXMLController implements Initializable {

    final double cellWidth = Constants.GAME_WIDTH / Constants.NUM_COLS;
    final double cellHeight = Constants.GAME_HEIGHT / Constants.NUM_ROWS;

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Create the canvas
            Canvas canvas = new Canvas(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
            Grid grid = Grid.getInstance();

            // Load the background
            Image background = new Image(getClass().getResource(Constants.BACKGROUND_PATH).toString(), Constants.GAME_WIDTH, Constants.GAME_HEIGHT, false, true);

            BackgroundImage myBI = new BackgroundImage(
                    background,
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT
            );

            // Draw the background
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.drawImage(background, 0, 0);

            // Create the two obstacles borders (chalets)
            for (int i = 0; i < Constants.NUM_ROWS; i++) {
                grid.addItem(new Obstacle(0, i, Constants.ItemType.Chalet)); // chalet
                grid.addItem(new Obstacle(1, i, Constants.ItemType.Chalet)); // chalet
                grid.addItem(new Obstacle(Constants.NUM_COLS - 2, i, Constants.ItemType.ChaletVS)); // chaletVS
                grid.addItem(new Obstacle(Constants.NUM_COLS - 1, i, Constants.ItemType.ChaletVS));      // chaletVS
            }

            // Create the static obstacles
            for (int i = 0; i < Constants.NUM_OBSTACLES; i++) {
                Random r = new Random();
                int x = 0;
                int y = 0;

                // TODO: Avoid infinite loop
                do {
                    x = r.nextInt(Constants.NUM_COLS - 4) + 2;
                    y = r.nextInt(Constants.NUM_ROWS - 2) + 2;
                } while (!grid.isFree(x, y));
                
                grid.addItem(new Obstacle(x, y, Constants.ItemType.Sapin)); // sapin
            }

            // Draw the grid on the Canvas
            grid.draw(gc);
            
            // make the canvas focusable
            canvas.setFocusTraversable(true);
            
            // keyboard handler
            canvas.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

    			@Override
    			public void handle(KeyEvent event) {
    				if(Constants.ACTION_ATTACK.containsKey(event.getCode())) {
    					System.out.println("Attacker's action : " + Constants.ACTION_ATTACK.get(event.getCode()) + " on " + event.getCode());
    				} else if(Constants.ACTION_DEFEND.containsKey(event.getCode())) {
    					System.out.println("Defender's action : " + Constants.ACTION_DEFEND.get(event.getCode()) + " on " + event.getCode());
    				}
    			}
            });

            AnchorPane.setTopAnchor(canvas, 0.);
            anchorPane.getChildren().add(canvas);

        } catch (CellAlreadyOccupiedException | IllegalArgumentException e2) {
            e2.printStackTrace();
        }
    }
}
