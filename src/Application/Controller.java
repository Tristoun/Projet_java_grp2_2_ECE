package Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import javax.swing.Action;

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

    String username;
    String password;
    int idUser;

    public void switchScene(String path, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../SceneDesign/profil.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void submitLogIn(ActionEvent event) {
        UserDaoImpl userdaoimpl = new UserDaoImpl();
        try {
            username = usernameInput.getText();
            password = passwordInput.getText();
    
            System.out.println(username + " " + password);
            idUser = userdaoimpl.logIn(username, password);
            
            if(idUser != -1 && idUser != -2) {
                switchScene("../SceneDesign/main.fxml", event);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void register(ActionEvent event) {
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        try {
            username = usernameInput.getText();
            password = passwordInput.getText();
    
            System.out.println(username + " " + password);
            idUser = userDaoImpl.registerUser(username, password);
            if(idUser != -1 && idUser != -2) {
                switchScene("../SceneDesign/main.fxml", event);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
