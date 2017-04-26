package ch.heigvd.frogger;

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

            Grid grid = new Grid();

            // Create the game grid
            GridPane gameGridPane = new GridPane();
            gameGridPane.setAlignment(Pos.CENTER);
            gameGridPane.setPrefSize(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
            gameGridPane.setMinSize(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
            gameGridPane.setGridLinesVisible(true);

            Image background = new Image(getClass().getResource("/images/background/fond.jpg").toString(), Constants.GAME_WIDTH, Constants.GAME_HEIGHT, false, true);

            // Load the background
            BackgroundImage myBI = new BackgroundImage(
                    background,
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT
            );

            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.drawImage(background, 0, 0);

            gameGridPane.setBackground(new Background(myBI));

            // Create the columns
            for (int i = 0; i < Constants.NUM_COLS; i++) {
                ColumnConstraints colConst = new ColumnConstraints();
                colConst.setPercentWidth(100.0 / Constants.NUM_COLS);
                gameGridPane.getColumnConstraints().add(colConst);
            }

            // Create the rows
            for (int i = 0; i < Constants.NUM_ROWS; i++) {
                RowConstraints rowConst = new RowConstraints();
                rowConst.setPercentHeight(100.0 / Constants.NUM_ROWS);
                gameGridPane.getRowConstraints().add(rowConst);
            }

            // Create the two obstacles borders (chalets)
            for (int i = 0; i < Constants.NUM_ROWS; i++) {
                gameGridPane.add(getObstacle(Constants.Obstacles.Chalet), 0, i);
                gameGridPane.add(getObstacle(Constants.Obstacles.Chalet), 1, i);
                gameGridPane.add(getObstacle(Constants.Obstacles.ChaletVS), Constants.NUM_COLS - 1, i);
                gameGridPane.add(getObstacle(Constants.Obstacles.ChaletVS), Constants.NUM_COLS, i);
            }

            // Create the static obstacles
            for (int i = 0; i < Constants.NUM_OBSTACLES; i++) {
                Random r = new Random();
                int x = 0;
                int y = 0;

                gameGridPane.add(getObstacle(Constants.Obstacles.Sapin), r.nextInt(Constants.NUM_COLS - 4) + 2, r.nextInt(Constants.NUM_ROWS - 10) + 10);
            }

            AnchorPane.setTopAnchor(gameGridPane, 0.);

            grid.draw(gc);

            // anchorPane.getChildren().addAll(gameGridPane);
            AnchorPane.setTopAnchor(canvas, 0.);
            anchorPane.getChildren().add(canvas);

        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

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
