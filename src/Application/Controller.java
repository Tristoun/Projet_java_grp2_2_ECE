package Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import DAO.DaoFactory;
import DAO.UserDaoImpl;


public class Controller {

    @FXML 
    private Label labelUsername;
    @FXML
    private Label labelPassword;

    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;

    @FXML
    private Button logiButton;

    String username;
    String password;


    public void submitLogIn(ActionEvent event) {
        UserDaoImpl userdaoimpl = new UserDaoImpl();
        try {
            username = usernameInput.getText();
            password = passwordInput.getText();
    
            System.out.println(username + " " + password);
            int id = userdaoimpl.logIn(username, password);
            System.out.println(id);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
