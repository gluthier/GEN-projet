package ch.heig.admin;

import ch.heig.bdd.BDD;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class FXMLController implements Initializable {
    
    @FXML
    private Label label;
    private BDD bdd;

    public FXMLController() {
        bdd = BDD.getInstance();
    }
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        label.setText(bdd.getLog());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
