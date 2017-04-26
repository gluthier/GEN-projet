package ch.heigvd.frogger;

import ch.heigvd.frogger.item.Item;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
                grid.addItem(null, 0, i); // chalet
                grid.addItem(null, 1, i); // chalet
                grid.addItem(null, Constants.NUM_COLS - 2, i); // chaletVS
                grid.addItem(null, Constants.NUM_COLS - 1, i);      // chaletVS
            }

            // Create the static obstacles
            for (int i = 0; i < Constants.NUM_OBSTACLES; i++) {
                Random r = new Random();
                int x = 0;
                int y = 0;

                // TODO: Avoid infinite loop
                do {
                    x = r.nextInt(Constants.NUM_COLS - 4) + 2;
                    y = r.nextInt(Constants.NUM_ROWS - 10) + 10;
                } while (!grid.isFree(x, y));
                
                grid.addItem(null, x, y); // sapin
            }

            // Draw the grid on the Canvas
            grid.draw(gc);

            AnchorPane.setTopAnchor(canvas, 0.);
            anchorPane.getChildren().add(canvas);

        } catch (CellAlreadyOccupiedException | IllegalArgumentException e2) {
            e2.printStackTrace();
        }
    }

    
    // TODO: Remove
    private Node getObstacle(Constants.Obstacles type) {
        Label label = new Label("");
        label.setPrefSize(cellWidth, cellHeight);
        label.setMinSize(cellWidth, cellHeight);
        Image image = new Image(getClass().getResource(Constants.IMG_FOLDER + Constants.OBSTACLE_FOLDER + type + ".png").toString(), cellWidth, cellHeight, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        label.setBackground(new Background(backgroundImage));

        return label;
    }

}
