package ch.heig.admin;

import ch.heig.bdd.BDD;
import ch.heig.bdd.Config;
import ch.heig.bdd.Log;
import ch.heig.bdd.User;
import ch.heigvd.protocol.MapSize;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Maxime Guillod
 */
public class FXMLController implements Initializable {

    private static final int NB_LOG = 100;
    private static final int NB_USER = 100;
    private static final String SPACE = "      ";

    private final BDD bdd;
    private int idMapSizeEdited;
    @FXML
    private GridPane logGrid;
    @FXML
    private ProgressIndicator logProgress;
    @FXML
    private GridPane userGrid;
    @FXML
    private TextField numRaws;
    @FXML
    private TextField numCols;
    @FXML
    private TextField playerSpeed;
    @FXML
    private TextField initialPlayerX;
    @FXML
    private TextField initialPlayerY;
    @FXML
    private Label mapSizeNom;
    @FXML
    private JFXTextField mapSizeHeight;
    @FXML
    private JFXTextField mapSizeWidth;

    public FXMLController() {
        bdd = BDD.getInstance();
    }

    @FXML
    private void logUpdate(ActionEvent event) {
        logProgress.setProgress(0.0f);

        // clean grid
        logGrid.getChildren().clear();

        // update log content
        List<Log> list = bdd.getLog(NB_LOG);
        for (int i = 0; i < list.size(); i++) {
            logGrid.add(new Text(list.get(i).getClasse() + SPACE), 0, i);
            logGrid.add(new Text(list.get(i).getUid() + SPACE), 1, i);
            logGrid.add(new Text(list.get(i).getType() + SPACE), 2, i);
            logGrid.add(new Text(list.get(i).getContent() + SPACE), 3, i);
            logGrid.add(new Text(list.get(i).getDate() + SPACE), 4, i);

            logProgress.setProgress(i / list.size());
        }
        logProgress.setProgress(1);
    }

    @FXML
    private void userUpdate(ActionEvent event) {
        userGrid.getChildren().clear();

        List<User> list = bdd.getUsers(NB_USER);
        for (int i = 0; i < list.size(); i++) {
            userGrid.add(new Text(list.get(i).getUsername() + SPACE), 0, i);
            userGrid.add(new Text(list.get(i).getLastLogin() + SPACE), 1, i);
        }
    }

    @FXML
    private void updateConfigDiff(ActionEvent event) {
        Config config = bdd.getConfig();
        numRaws.setText(Integer.toString(config.getNumRows()));
        numCols.setText(Integer.toString(config.getNumCols()));
        playerSpeed.setText(Integer.toString(config.getPlayerSpeed()));
        initialPlayerX.setText(Integer.toString(config.getIntitialPlayerX()));
        initialPlayerY.setText(Integer.toString(config.getIntitialPlayerY()));
    }

    @FXML
    private void applyConfig(ActionEvent event) throws NoSuchMethodException {
        Config config = new Config(
                Integer.valueOf(numCols.getText()),
                Integer.valueOf(numRaws.getText()),
                Integer.valueOf(playerSpeed.getText()),
                Integer.valueOf(initialPlayerX.getText()),
                Integer.valueOf(initialPlayerY.getText())
        );
        bdd.setConfig(config);
    }

    @FXML
    private void updateMapSizeSmall() {
        updateMapSize(2);
    }

    @FXML
    private void updateMapSizeMedium() {
        updateMapSize(1);
    }

    @FXML
    private void updateMapSizeMarge() {
        updateMapSize(3);
    }

    private void updateMapSize(int id) {
        idMapSizeEdited = id;

        MapSize map = bdd.getMapSizeById(idMapSizeEdited);
        mapSizeNom.setText(map.getName());
        mapSizeHeight.setText(String.valueOf(map.getHeight()));
        mapSizeWidth.setText(String.valueOf(map.getWidth()));

        System.out.println(idMapSizeEdited);
    }

    @FXML
    private void setMapSize() {
        bdd.setMapSizeById(
                idMapSizeEdited,
                mapSizeWidth.getText(),
                mapSizeHeight.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Nothing
    }
}
