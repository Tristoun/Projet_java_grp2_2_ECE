package Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ResourceBundle.Control;

import javax.swing.Action;

import DAO.DaoFactory;
import DAO.UserDaoImpl;

import Application.DrawApp;

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

    public AnchorPane switchScene(String path, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        AnchorPane root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        return root;
    }

    public AnchorPane switchProfil(ActionEvent event) {
        AnchorPane root = null;
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        try {
            root = switchScene("../SceneDesign/profil.fxml", event);
            DrawApp.drawProfil(root, userDaoImpl, idUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    public AnchorPane switchSearch(ActionEvent event) {
        AnchorPane root = null;
        try {
            root = switchScene("../SceneDesign/search.fxml", event);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }
    

    public void submitLogIn(ActionEvent event) {
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        try {
            username = usernameInput.getText();
            password = passwordInput.getText();
    
            System.out.println(username + " " + password);
            idUser = userDaoImpl.logIn(username, password);
            
            if(idUser != -1 && idUser != -2) {
                switchProfil(event);
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
                switchProfil(event);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
