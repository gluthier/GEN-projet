package ch.heig.admin;

import ch.heig.bdd.BDD;
import ch.heig.bdd.Log;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class FXMLController implements Initializable {

    private static final int NB_LOG = 100;
    private static final String SPACE = "      ";

    @FXML
    private GridPane logGrid;
    @FXML
    private ProgressIndicator logProgress;
    private BDD bdd;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
