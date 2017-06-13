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
import sun.applet.Main;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            MainApp.getTcpClient().connect();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        username.requestFocus();
    }

    public void loginUser() {
        try {
            if (!MainApp.getTcpClient().login(username.getText(), password.getText())) {
                password.getStyleClass().add("textFieldError");
            } else {
                MainApp.getTcpClient().populateSettings(MainApp.getGameSettings());
                MainApp.getGameSettings().setUsername(username.getText());
                openLobby();
            }
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createUser() {

    }

    public void consultStats() {

    }

    private void openLobby() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Lobby.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            MainApp.getStage().setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
