package ch.heigvd.frogger;

import ch.heigvd.frogger.controllers.ClientController;
import ch.heigvd.frogger.item.FixedObstacle;
import ch.heigvd.protocol.Difficulty;
import ch.heigvd.protocol.MapSize;
import ch.heigvd.protocol.Party;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author Gabriel Luthier
 */
public class CreatePartyController implements Initializable {

    @FXML
    ChoiceBox choiceDifficulty;
    @FXML
    ChoiceBox choiceMapSize;
    @FXML
    ChoiceBox choiceRole;

    private GameSettings settings;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        settings = MainApp.getGameSettings();
        settings.getDifficulties().forEach(d -> choiceDifficulty.getItems().add(d));
        settings.getMapSizes().forEach(d -> choiceMapSize.getItems().add(d));
        for (int i = 0; i < Party.FreeRole.values().length; i++) {
            choiceRole.getItems().add(Party.FreeRole.values()[i]);
        }
    }

    @FXML
    private void createParty(ActionEvent event) {
        Difficulty diff = settings.getDifficulties().get(choiceDifficulty.getSelectionModel().selectedIndexProperty().get());
        MapSize mapSize = settings.getMapSizes().get(choiceMapSize.getSelectionModel().selectedIndexProperty().get());
        Party.FreeRole freeRole = Party.FreeRole.values()[1 - choiceRole.getSelectionModel().selectedIndexProperty().get()];

        Party party = new Party(-1, settings.getUsername(), diff, mapSize, freeRole);
        try {
            MainApp.getTcpClient().createParty(party);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Game.fxml"));
            Parent root = loader.load();

            GameFXMLController view = loader.getController();

            List<FixedObstacle> obstacles = MainApp.getTcpClient().joinParty(party);
            MainApp.setController(new ClientController(view, MainApp.getTcpClient(), obstacles));

            Scene scene = new Scene(root);
            MainApp.getStage().setScene(scene);

            // Center stage
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            MainApp.getStage().setX((primScreenBounds.getWidth() -  MainApp.getStage().getWidth()) / 2);
            MainApp.getStage().setY((primScreenBounds.getHeight() -  MainApp.getStage().getHeight()) / 2);
        } catch (IOException ex) {
            Logger.getLogger(CreatePartyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
