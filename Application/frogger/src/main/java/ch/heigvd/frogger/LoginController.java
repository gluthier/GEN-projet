package ch.heigvd.frogger;

import ch.heigvd.frogger.controllers.ClientController;
import ch.heigvd.frogger.controllers.GameController;
import ch.heigvd.frogger.controllers.IController;
import ch.heigvd.frogger.exception.ControllerNotSetException;
import ch.heigvd.frogger.item.FixedObstacle;
import ch.heigvd.frogger.tcp.TCPClient;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.heigvd.protocol.Party;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Gabriel Luthier
 */
public class LoginController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    private TCPClient tcpClient;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcpClient = new TCPClient(Constants.SERVER_ADDRESS, Constants.SERVER_PORT);
        try {
            tcpClient.connect();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        username.requestFocus();
    }

    public void loginUser() {
        try {
            if (!tcpClient.login(username.getText(), password.getText())) {
                System.out.println("Not logged");
                password.getStyleClass().add("textFieldError");
            } else {
                openGame();
            }
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createUser() {
        openGame();
    }

    public void consultStats() {

    }

    private void openGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Game.fxml"));
            Parent root = loader.load();

            GameFXMLController view = loader.getController();


            // TODO in lobby
            List<Party> parties = tcpClient.connectToLobby();
            List<FixedObstacle> obstacles = tcpClient.joinParty(parties.get(0));
            MainApp.setController(new ClientController(view, tcpClient, obstacles));

            Stage stage = (Stage) username.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            scene.getStylesheets().add("/styles/Styles.css");
            stage.setTitle("Walliser Frogger");
            stage.setScene(scene);
            stage.getIcons().add(new Image(Constants.ICON_PATH));

            // Center stage
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);

            stage.show();

            // Fermeture de l'application
            stage.setOnCloseRequest((WindowEvent event) -> {
                // Stop ItemClock timer Thread
                ItemClock.getInstance().stop();
            });
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
