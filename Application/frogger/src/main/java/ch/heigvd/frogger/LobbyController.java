package ch.heigvd.frogger;

import ch.heigvd.frogger.controllers.ClientController;
import ch.heigvd.frogger.item.FixedObstacle;
import ch.heigvd.frogger.tcp.TCPClient;
import ch.heigvd.protocol.Party;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author Gabriel Luthier
 */
public class LobbyController implements Initializable {

    @FXML
    GridPane partiesGrid;

    List<Party> parties;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            parties = MainApp.getTcpClient().connectToLobby();
        } catch (IOException ex) {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }

        populateGrid(parties);
    }

    private void populateGrid(Collection<Party> parties) {
        int i = 0;
        for (Party party: parties) {
            VBox vBox = new VBox();
            Text text = new Text("Game of " + party.getPlayerName() +
                    "\nDifficulty : " + party.getDifficulty().getName() +
                    "\nMap size : " + party.getMapSize().getName() +
                    "\nFree slot : " + party.getFreeRole()
            );
            Button button = new Button("Join");
            button.getStyleClass().add("buttonJoin");

            button.setOnAction(e -> {
                joinParty(party);
            });

            vBox.getChildren().addAll(text, button);

            int row = i / 3;
            int column = i % 3;

            if (row > partiesGrid.getRowConstraints().size()) {
                partiesGrid.getRowConstraints().add(new RowConstraints());
            }

            partiesGrid.add(vBox, column, row);
            i++;
        }
    }

    private void emptyGrid() {
        partiesGrid.getChildren().clear();
    }

    private void joinParty(Party party) {
        System.out.println("Joining party of " + party.getPlayerName());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Game.fxml"));
            Parent root = loader.load();

            GameFXMLController view = loader.getController();


            // TODO in lobby
            List<Party> parties = MainApp.getTcpClient().connectToLobby();
            List<FixedObstacle> obstacles = MainApp.getTcpClient().joinParty(parties.get(0));
            MainApp.setController(new ClientController(view, MainApp.getTcpClient(), obstacles));

            Scene scene = new Scene(root);
            MainApp.getStage().setScene(scene);

            // Center stage
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            MainApp.getStage().setX((primScreenBounds.getWidth() -  MainApp.getStage().getWidth()) / 2);
            MainApp.getStage().setY((primScreenBounds.getHeight() -  MainApp.getStage().getHeight()) / 2);

        } catch (IOException ex) {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void createParty(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateParty.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            MainApp.getStage().setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void refreshLobby(ActionEvent event) {
        emptyGrid();
        try {
            List<Party> parties = MainApp.getTcpClient().connectToLobby();
            populateGrid(parties);
        } catch (IOException e) {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
