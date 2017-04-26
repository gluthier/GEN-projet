package ch.heigvd.frogger;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class FXMLController implements Initializable {

    final int width = 1368;
    final int height = 1026;
    final int numCols = 36;
    final int numRows = 27;
    final double cellWidth = width / numCols;
    final double cellHeight = height / numRows;

    @FXML
    private Label label;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @FXML
    private void handleBackgroundAction(ActionEvent e) {
        System.out.println("opening background");
//    	Parent root;
        try {
            GridPane root = new GridPane();
            root.setId("pane");
            root.setAlignment(Pos.CENTER);
            root.setPrefSize(width, height);
            root.setMinSize(width, height);
            root.setGridLinesVisible(true);

            BackgroundImage myBI = new BackgroundImage(
                    new Image(getClass().getResource("/images/background/fond.jpg").toString(), 3648, 2736, false, true),
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT
            );
            
            root.setBackground(new Background(myBI));

            //TODO use controller
            System.out.println("cell w/h :" + cellWidth + "/" + cellHeight);
            for (int i = 0; i < numCols; i++) {
                ColumnConstraints colConst = new ColumnConstraints();
                colConst.setPercentWidth(100.0 / numCols);
                root.getColumnConstraints().add(colConst);
            }
            for (int i = 0; i < numRows; i++) {
                RowConstraints rowConst = new RowConstraints();
                rowConst.setPercentHeight(100.0 / numRows);
                root.getRowConstraints().add(rowConst);
            }

            for (int i = 0; i < numRows; i++) {

                root.add(getObstacle("chalet"), 0, i);
                root.add(getObstacle("chalet"), 1, i);
                root.add(getObstacle("chaletVs"), numCols - 1, i);
                root.add(getObstacle("chaletVs"), numCols, i);

                Random r = new Random();
                root.add(getObstacle("sapin"), r.nextInt(numCols - 4) + 2, r.nextInt(numRows - 10) + 10);
                //root.add(label1, numCols, i);
            }

            Stage stage = new Stage();
            stage.setTitle("Background Test");

            Scene scene = new Scene(root, 1368, 1026);
            scene.getStylesheets().add("/styles/Styles.css");

            stage.setScene(scene);
            stage.show();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private Label getObstacle(String id) {
        Label label = new Label("");
        label.setPrefSize(cellWidth, cellHeight);
        label.setMinSize(cellWidth, cellHeight);
        label.setId(id);

        return label;
    }
}
