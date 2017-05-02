package ch.heigvd.frogger;

import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;
import ch.heigvd.frogger.item.Obstacle;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

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
            grid.draw(canvas);

            AnchorPane.setTopAnchor(canvas, 0.);
            anchorPane.getChildren().add(canvas);

        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
