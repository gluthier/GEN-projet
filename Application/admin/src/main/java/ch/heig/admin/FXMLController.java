package ch.heig.admin;

import ch.heig.bdd.BDD;
import ch.heig.bdd.Config;
import ch.heig.bdd.Log;
import ch.heig.bdd.User;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author Maxime Guillod
 */
public class FXMLController implements Initializable {
    
    private static final int NB_LOG = 100;
    private static final int NB_USER = 100;
    private static final String SPACE = "      ";
    
    private BDD bdd;
    @FXML
    private GridPane logGrid;
    @FXML
    private ProgressIndicator logProgress;
    @FXML
    private GridPane userGrid;
    @FXML
    private TextField carteHeight;
    @FXML
    private TextField carteWidth;
    
    public FXMLController() {
        bdd = BDD.getInstance();
    }
    
    @FXML
    private void logUpdate(ActionEvent event) {
        logProgress.setProgress(0.0f);

        // clean grid
        logGrid.getChildren().clear();

        // update log content
        List<Log> list = bdd.getLog(NB_LOG);
        for (int i = 0; i < list.size(); i++) {
            logGrid.add(new Text(list.get(i).getClasse() + SPACE), 0, i);
            logGrid.add(new Text(list.get(i).getUid() + SPACE), 1, i);
            logGrid.add(new Text(list.get(i).getType() + SPACE), 2, i);
            logGrid.add(new Text(list.get(i).getContent() + SPACE), 3, i);
            logGrid.add(new Text(list.get(i).getDate() + SPACE), 4, i);
            
            logProgress.setProgress(i / list.size());
        }
        logProgress.setProgress(1);
    }
    
    @FXML
    private void userUpdate(ActionEvent event) {
        userGrid.getChildren().clear();
        
        List<User> list = bdd.getUsers(NB_USER);
        for (int i = 0; i < list.size(); i++) {
            userGrid.add(new Text(list.get(i).getUsername() + SPACE), 0, i);
            userGrid.add(new Text(list.get(i).getPassword() + SPACE), 1, i);
            userGrid.add(new Text(list.get(i).getLastLogin() + SPACE), 2, i);
        }
    }
    
    @FXML
    private void updateConfigDiff(ActionEvent event) {
        Config config = bdd.getConfig();
        carteHeight.setText(Integer.toString(config.getCarteHeight()));
        carteWidth.setText(Integer.toString(config.getCarteWidth()));
    }
    
    @FXML
    private void applyConfig(ActionEvent event) throws NoSuchMethodException {
        Config config = new Config(
                Integer.valueOf(carteHeight.getText()),
                Integer.valueOf(carteWidth.getText()));
        bdd.setConfig(config);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
