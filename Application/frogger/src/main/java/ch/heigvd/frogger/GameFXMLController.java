package ch.heigvd.frogger;

import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;
import ch.heigvd.frogger.item.Item;
import ch.heigvd.frogger.item.Obstacle;
import ch.heigvd.frogger.item.Decoration;
import ch.heigvd.frogger.item.DynamicObstacle;
import ch.heigvd.frogger.item.Player;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GameFXMLController implements Initializable {

    @FXML
    private BorderPane borderPane;
    
    private Canvas canvas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUpCanvas();
    }

    public void setUpCanvas() {
        // Create the canvas
        canvas = new Canvas(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        AnchorPane.setTopAnchor(canvas, 0.);
        borderPane.setPrefSize(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        borderPane.getChildren().add(canvas);
        
        
        try {
            // Load the background
            Image background = new Image(
                    getClass().getResource(Constants.BACKGROUND_PATH).toString(),
                    Constants.GAME_WIDTH,
                    Constants.GAME_HEIGHT,
                    false, true);

            // Draw the background
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.drawImage(background, 0, 0);
           
            // ----- ONLY FOR DEBUG : draw lines -----
            // TODO : Remove
            // Draw the lines directly on the graphicContext
            gc.setStroke(Color.GRAY);
            gc.setLineWidth(1);
            for (int i = 0; i < Constants.NUM_ROWS; i++) {
                gc.strokeLine(0, i * Constants.CELL_HEIGHT, Constants.GAME_WIDTH, i * Constants.CELL_HEIGHT);
            }

            for (int i = 0; i < Constants.NUM_COLS; i++) {
                gc.strokeLine(i * Constants.CELL_WIDTH, 0, i * Constants.CELL_WIDTH, Constants.GAME_HEIGHT);
            }
            // ---------------------------------------

            // make the canvas focusable
            canvas.setFocusTraversable(true);

            // keyboard handler
            canvas.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
                if (Constants.ACTION_ATTACK.containsKey(event.getCode())) {
                    Constants.ACTION_ATTACK.get(event.getCode()).act();
                } else if (Constants.ACTION_DEFEND.containsKey(event.getCode())) {
                    Constants.ACTION_DEFEND.get(event.getCode()).act();
                } else if (Constants.ACTION_GAME.containsKey(event.getCode())) {
                    Constants.ACTION_GAME.get(event.getCode()).act();
                }
            });
        } catch (Exception e) {
            Logger.getLogger(GameFXMLController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void addItem(Item i) {
        borderPane.getChildren().add(i);
    }

    public void addPlayer(Player p) {
        borderPane.getChildren().add(p);
    }
    
    public void showLooserMessage() {
        Text tLost = new Text();
        tLost.setText("YOU LOST !");
        tLost.setId("lostText");
        
        Text tRestart = new Text();
        tRestart.setText("Press 'r' to restart.");
        tRestart.setId("restartText");
        
        VBox vbox = new VBox();
        vbox.getChildren().addAll(tLost, tRestart);
        vbox.setAlignment(Pos.CENTER);
        
        borderPane.setCenter(null);
        borderPane.setCenter(vbox);
    }

    public void reset() {
        borderPane.getChildren().clear();
        setUpCanvas();
    }
}
