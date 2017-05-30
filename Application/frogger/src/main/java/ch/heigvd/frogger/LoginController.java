package ch.heigvd.frogger;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Gabriel Luthier
 */
public class LoginController implements Initializable {

    @FXML
    private TextField username;
    
    @FXML
    private PasswordField password;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        username.requestFocus();
    }
    
    public void loginUser() {
        System.out.println(username.getText());
        System.out.println(password.getText());
    }
    
    public void createUser() {
        
    }
    
    public void openGame() {
        
    }

}
