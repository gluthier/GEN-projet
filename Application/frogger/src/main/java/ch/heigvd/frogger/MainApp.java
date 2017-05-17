package ch.heigvd.frogger;

import ch.heigvd.frogger.item.ItemClock;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {
	
	public static GameFXMLController controller = null;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Game.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Walliser Frogger");
        stage.setScene(scene);
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
    
    public static void setController(GameFXMLController con) {
    	if(controller == null) {
    		controller = con;
    	}
    }
    
    public static GameFXMLController getController() {
    	return controller;
    }

}
