package ch.heigvd.frogger;

import ch.heigvd.frogger.controllers.ClientController;
import ch.heigvd.frogger.controllers.IController;
import ch.heigvd.frogger.exception.ControllerNotSetException;
import ch.heigvd.frogger.item.FixedObstacle;
import ch.heigvd.frogger.tcp.TCPClient;
import ch.heigvd.protocol.Party;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sun.applet.Main;

import java.util.List;

public class MainApp extends Application {

    private static IController controller;
    private static TCPClient tcpClient;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        MainApp.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        LoginController loginController = loader.getController();
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Walliser Frogger");
        stage.setScene(scene);
        stage.getIcons().add(new Image(Constants.ICON_PATH));
        stage.show();
        
        // Fermeture de l'application
        stage.setOnCloseRequest((WindowEvent event) -> {
            // Stop ItemClock timer Thread
            ItemClock.getInstance().stop();
        });
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static IController getController() throws ControllerNotSetException {
        if (controller == null) {
            throw new ControllerNotSetException();
        }
        return controller;
    }

    public static void setController(IController controller) {
        MainApp.controller = controller;
    }

    public static TCPClient getTcpClient() {
        if (tcpClient == null) {
            tcpClient = new TCPClient(Constants.SERVER_ADDRESS, Constants.SERVER_PORT);
        }
        return tcpClient;
    }

    public static Stage getStage() {
        return stage;
    }
}
