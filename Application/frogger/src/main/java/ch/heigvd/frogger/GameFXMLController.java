package ch.heigvd.frogger;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
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
        System.out.println("opening background");

        try {

            GridPane gameGridPane = new GridPane();
            gameGridPane.setAlignment(Pos.CENTER);
            gameGridPane.setPrefSize(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
            gameGridPane.setMinSize(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
            gameGridPane.setGridLinesVisible(true);

            BackgroundImage myBI = new BackgroundImage(
                    new Image(getClass().getResource("/images/background/fond.jpg").toString(), 3648, 2736, false, true),
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT
            );

            gameGridPane.setBackground(new Background(myBI));

            for (int i = 0; i < Constants.NUM_COLS; i++) {
                ColumnConstraints colConst = new ColumnConstraints();
                colConst.setPercentWidth(100.0 / Constants.NUM_COLS);
                gameGridPane.getColumnConstraints().add(colConst);
            }

            for (int i = 0; i < Constants.NUM_ROWS; i++) {
                RowConstraints rowConst = new RowConstraints();
                rowConst.setPercentHeight(100.0 / Constants.NUM_ROWS);
                gameGridPane.getRowConstraints().add(rowConst);
            }

            for (int i = 0; i < Constants.NUM_ROWS; i++) {

                gameGridPane.add(getObstacle(Constants.Obstacles.Chalet), 0, i);
                gameGridPane.add(getObstacle(Constants.Obstacles.Chalet), 1, i);
                gameGridPane.add(getObstacle(Constants.Obstacles.ChaletVS), Constants.NUM_COLS - 1, i);
                gameGridPane.add(getObstacle(Constants.Obstacles.ChaletVS), Constants.NUM_COLS, i);

                Random r = new Random();
                gameGridPane.add(getObstacle(Constants.Obstacles.Sapin), r.nextInt(Constants.NUM_COLS - 4) + 2, r.nextInt(Constants.NUM_ROWS - 10) + 10);
                //root.add(label1, Constants.NUM_COLS, i);
            }

            AnchorPane.setTopAnchor(gameGridPane, 0.);

            anchorPane.getChildren().addAll(gameGridPane);

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
