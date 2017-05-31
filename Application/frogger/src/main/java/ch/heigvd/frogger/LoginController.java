package ch.heigvd.frogger;

import ch.heigvd.frogger.exception.ViewNotSetException;
import ch.heigvd.frogger.tcp.TCPClient;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        username.requestFocus();
    }

    public void loginUser() {
        try {
            while (!tcpClient.login(username.getText(), password.getText())) {
                System.out.println("not logged");
            }
            openGame();
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

            GameController.setView(loader.getController());
            // Force GameController to load
            GameController.getInstance();

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
        } catch (ViewNotSetException | IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
