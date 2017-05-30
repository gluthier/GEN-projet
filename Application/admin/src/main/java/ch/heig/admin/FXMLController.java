package ch.heig.admin;

import ch.heigvd.server.bdd.BDD;
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
        String retour = "";
        for (int i = 0; i < 10; i++) {
            retour += bdd.getLog().get(i);
            retour += "\n";
        }
        label.setText(retour);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
