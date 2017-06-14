import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;
import ch.heigvd.frogger.item.Obstacle;
import ch.heigvd.frogger.item.Player;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import static junit.framework.TestCase.assertEquals;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

/**
 *
 * @author Gabriel
 */
public class TestFX extends ApplicationTest {

    /*
    private Group elementsGroup;

    private Obstacle chalet;
    private int chaletX = 0;
    private int chaletY = 0;

    private Obstacle chaletVS;
    private int chaletVSX = 1;
    private int chaletVSY = 1;

    private Obstacle sapin;
    private int sapinX = 2;
    private int sapinY = 2;

    private Player player;
     */
    
    @Override
    public void start(Stage stage) throws Exception {
        /*
        elementsGroup = new Group();
        try {
            chalet = new Obstacle(chaletX, chaletY, Constants.ItemType.Chalet);
            chaletVS = new Obstacle(chaletVSX, chaletVSY, Constants.ItemType.ChaletVS);
            sapin = new Obstacle(sapinX, sapinY, Constants.ItemType.Sapin);
        } catch (CellAlreadyOccupiedException ex) {
            Logger.getLogger(TestFXTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        elementsGroup.getChildren().add(chalet);
        elementsGroup.getChildren().add(chaletVS);
        elementsGroup.getChildren().add(sapin);

        player = new Player(Constants.PLAYER_POS_START_X,
                Constants.PLAYER_POS_START_Y,
                Constants.ItemType.Skier);
        elementsGroup.getChildren().add(player);

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Game.fxml"));
        Scene scene = new Scene(root, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        stage.setScene(scene);
        stage.show();
         */
    }

    /*
    @Test
    public void testObstaclesCorrectType() {
        assertEquals(Constants.ItemType.Chalet, chalet.getType());
        assertEquals(Constants.ItemType.ChaletVS, chaletVS.getType());
        assertEquals(Constants.ItemType.Sapin, sapin.getType());
    }

    @Test
    public void testPlayerCorrectType() {
        assertEquals(Constants.ItemType.Skier, player.getType());
    }

    @Test
    public void testPlayerCorrectStartingPosition() {
        assertEquals(Constants.PLAYER_POS_START_X, player.getXGridCoordinate());
        assertEquals(Constants.PLAYER_POS_START_Y, player.getYGridCoordinate());
    }

    @Test
    public void testPlayerLeftMove() {
        push(KeyCode.LEFT); // move left from start position
        assertEquals(Constants.PLAYER_POS_START_X+1, player.getXGridCoordinate());
        assertEquals(Constants.PLAYER_POS_START_Y, player.getYGridCoordinate());
        assertEquals(Constants.ItemType.SkierLeft, player.getType());
    }
    
    @Test
    public void testPlayerRightMove() {
        push(KeyCode.RIGHT); // move back to start position
        assertEquals(Constants.PLAYER_POS_START_X, player.getXGridCoordinate());
        assertEquals(Constants.PLAYER_POS_START_Y, player.getYGridCoordinate());
        assertEquals(Constants.ItemType.SkierRight, player.getType());
    }
    
    @Test
    public void testPlayerDownMove() {
        push(KeyCode.RIGHT); // move dowm from start position
        assertEquals(Constants.PLAYER_POS_START_X, player.getXGridCoordinate());
        assertEquals(Constants.PLAYER_POS_START_Y+1, player.getYGridCoordinate());
        assertEquals(Constants.ItemType.Skier, player.getType());
    }
     */
}
