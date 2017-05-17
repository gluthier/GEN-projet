package ch.heigvd.frogger;

import ch.heigvd.frogger.item.ItemClock;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Game.fxml"));
        Parent root = loader.load();

        GameController controller = new GameController(loader.getController());

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

}
