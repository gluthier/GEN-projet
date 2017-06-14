package ch.heigvd.frogger;

import ch.heigvd.frogger.item.Item;
import ch.heigvd.frogger.item.Player;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.heigvd.protocol.Constants;
import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GameFXMLController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private VBox vbox;

    private Canvas canvas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUpCanvas();
    }

    public void setUpCanvas() {
        // Create the canvas
        canvas = new Canvas(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        borderPane.setPrefSize(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        borderPane.getChildren().add(canvas);

        vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        borderPane.setCenter(vbox);

        try {
            // Load the background
            Image background = new Image(
                    getClass().getResource(ClientConstants.BACKGROUND_PATH).toString(),
                    Constants.GAME_WIDTH,
                    Constants.GAME_HEIGHT,
                    false, true);

            // Draw the background
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.drawImage(background, 0, 0);

            /*
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

            */

            // make the canvas focusable
            canvas.setFocusTraversable(true);

            // keyboard handler
            canvas.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
                if (ClientConstants.ACTION_ATTACK.containsKey(event.getCode())) {
                    ClientConstants.ACTION_ATTACK.get(event.getCode()).act();
                } else if (ClientConstants.ACTION_DEFEND.containsKey(event.getCode())) {
                    ClientConstants.ACTION_DEFEND.get(event.getCode()).act();
                } else if (ClientConstants.ACTION_GAME.containsKey(event.getCode())) {
                    this.clearMessages();
                    ClientConstants.ACTION_GAME.get(event.getCode()).act();
                }
            });
        } catch (Exception e) {
            Logger.getLogger(GameFXMLController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void addItem(Item i) {
        borderPane.getChildren().add(i);
    }

    public void removeItem(Item i) {
        // Avoid throwing IllegalStateException by running from a non-JavaFX thread.
        Platform.runLater(
                () -> {
                    borderPane.getChildren().remove(i);
                }
        );
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

        // Avoid throwing IllegalStateException by running from a non-JavaFX thread.
        Platform.runLater(
                () -> {
                    vbox.getChildren().clear();
                    vbox.getChildren().addAll(tLost, tRestart);
                }
        );
    }

    public void showWinnerMessage() {
        Text tWon = new Text();
        tWon.setText("YOU WON !");
        tWon.setId("wonText");

        Text tRestart = new Text();
        tRestart.setText("Press 'r' to restart.");
        tRestart.setId("restartText");

        // Avoid throwing IllegalStateException by running from a non-JavaFX thread.
        Platform.runLater(
                () -> {
                    vbox.getChildren().clear();
                    vbox.getChildren().addAll(tWon, tRestart);
                }
        );
    }

    public void clearMessages() {
        vbox.getChildren().clear();
    }

    public void reset() {
        borderPane.getChildren().clear();
        setUpCanvas();
    }
}
