package ch.heigvd.frogger;

import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;
import ch.heigvd.frogger.item.Obstacle;
import ch.heigvd.frogger.item.Decoration;
import ch.heigvd.frogger.item.DynamicObstacle;
import ch.heigvd.frogger.item.Player;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameFXMLController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    private Canvas canvas;
    private Group itemsGroup;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Create the canvas
        canvas = new Canvas(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        itemsGroup = new Group();
        Group staticObstacleGroup = new Group();
        Group dynamicObstacleGroup = new Group();
        itemsGroup.getChildren().add(staticObstacleGroup);
        itemsGroup.getChildren().add(dynamicObstacleGroup);

        AnchorPane.setTopAnchor(canvas, 0.);
        anchorPane.getChildren().add(canvas);
        anchorPane.getChildren().add(itemsGroup);

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
           

            // Skier on top of the mountain
            Player player = new Player(Constants.INITIAL_PLAYER_X, Constants.INITIAL_PLAYER_Y, Constants.ItemType.Skier);
            itemsGroup.getChildren().add(player);
            
            // Start Flag
            Decoration leftStart = new Decoration(Constants.INITIAL_PLAYER_X+1, Constants.INITIAL_PLAYER_Y, Constants.ItemType.StartLeft);
            Decoration rightStart = new Decoration(Constants.INITIAL_PLAYER_X-1, Constants.INITIAL_PLAYER_Y, Constants.ItemType.StartRight);
            itemsGroup.getChildren().add(leftStart);
            itemsGroup.getChildren().add(rightStart);
            
            // add Finish Flags
            // TODO add rocks between finish ?
            for(int i = 0; i < 3; i++) {
            	itemsGroup.getChildren().add(new Decoration(7 + i * 10, Constants.NUM_ROWS-1, Constants.ItemType.FinishLeft));
                itemsGroup.getChildren().add(new Decoration(5 + i * 10, Constants.NUM_ROWS-1, Constants.ItemType.FinishRight));
            }

            // Create the two obstacles borders (chalets)
            for (int i = 0; i < Constants.NUM_ROWS; i++) {
                if (Constants.OBSTACLE_ROW.inverse().containsKey(i)) {
                    staticObstacleGroup.getChildren().add(new Obstacle(0, i, Constants.ItemType.getRow(Constants.OBSTACLE_ROW.inverse().get(i))));
                } else {
                    staticObstacleGroup.getChildren().add(new Obstacle(0, i, Constants.ItemType.Chalet));
                }
                staticObstacleGroup.getChildren().add(new Obstacle(1, i, Constants.ItemType.Chalet));
                staticObstacleGroup.getChildren().add(new Obstacle(Constants.NUM_COLS - 2, i, Constants.ItemType.ChaletVS));
                staticObstacleGroup.getChildren().add(new Obstacle(Constants.NUM_COLS - 1, i, Constants.ItemType.ChaletVS));
            }

            // Create the static obstacles
            // TODO improve the randomization so that
            // 		it exist a path between the player and the finish line
            //		there is no tree in the air
            for (int i = 0; i < Constants.NUM_OBSTACLES; i++) {
                Random r = new Random();
                int x = 0;
                int y = 0;

                Obstacle sapin = new Obstacle(x, y, Constants.ItemType.Sapin); // sapin
                staticObstacleGroup.getChildren().add(sapin);

                // TODO: Avoid infinite loop
                do {
                    sapin.setXGridCoordinate(r.nextInt(Constants.NUM_COLS - 4) + 2);
                    sapin.setYGridCoordinate(r.nextInt(Constants.NUM_ROWS - Constants.INITIAL_PLAYER_Y) + Constants.INITIAL_PLAYER_Y);
                } while (sapin.collisionWithOtherNode());
            }

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

            // keyboard handler
            canvas.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
                if (Constants.ACTION_ATTACK.containsKey(event.getCode())) {
                    System.out.println("Attacker's action : " + Constants.ACTION_ATTACK.get(event.getCode()) + " on " + event.getCode());
                    Constants.ACTION_ATTACK.get(event.getCode()).act(player);
                } else if (Constants.ACTION_DEFEND.containsKey(event.getCode())) {
                    System.out.println("Defender's action : " + Constants.ACTION_DEFEND.get(event.getCode()) + " on " + event.getCode());
                    try {
                        DynamicObstacle OD = new DynamicObstacle(Constants.ItemType.Saucisson);
                        Constants.ACTION_DEFEND.get(event.getCode()).act(OD);
                        dynamicObstacleGroup.getChildren().add(OD);
                    } catch (CellAlreadyOccupiedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("Exception catch !!");
        }
    }
    
    // TODO add a somewhat timer when starting the game ( small movie ? )
    public void start() {
        // make the canvas focusable
    	Text startText = new Text("START");
        startText.setX(Constants.GAME_WIDTH / 2 - startText.getBoundsInLocal().getWidth() / 2);
        startText.setY(Constants.GAME_HEIGHT / 2 - startText.getBoundsInLocal().getHeight() / 2);
        startText.setFill(Color.BLUE);
        startText.setFont(new Font(50));
        itemsGroup.getChildren().add(startText);
        
        canvas.setFocusTraversable(true);
    }
    
    // TODO detect if the player has reach a finish part
    public void stop() {
        // make the canvas unfocusable
        canvas.setFocusTraversable(false);
        Text stopText = new Text("END");
        stopText.setX(Constants.GAME_WIDTH / 2 - stopText.getBoundsInLocal().getWidth() / 2);
        stopText.setY(Constants.GAME_HEIGHT / 2 - stopText.getBoundsInLocal().getHeight() / 2);
        stopText.setFill(Color.RED);
        stopText.setFont(new Font(50));
        itemsGroup.getChildren().add(stopText);
    }
}
