package ch.heigvd.frogger;

import javafx.application.Application;
import javafx.event.EventHandler;

import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import ch.heigvd.frogger.Constants;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Game.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        // Key handler Example
        // TODO get the eventHandler Out
        //TODO Find a way to link it with GameFXMLController
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(Constants.ACTION_ATTACK.containsKey(event.getCode())) {
					System.out.println("Attacker's action : " + Constants.ACTION_ATTACK.get(event.getCode()) + " on " + event.getCode());
				} else if(Constants.ACTION_DEFEND.containsKey(event.getCode())) {
					System.out.println("Defender's action : " + Constants.ACTION_DEFEND.get(event.getCode()) + " on " + event.getCode());
				}
			}
        	
		});
        
        stage.setTitle("Walliser Frogger");
        stage.setScene(scene);
        stage.show();
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
