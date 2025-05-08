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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle.Control;
import javax.swing.Action;
import DAO.DaoFactory;
import DAO.SpecialistDaoImpl;
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
    @FXML
    private Label errorLabel;
    @FXML 
    private TextField searchInput;
    
    private FXMLLoader loader;
    private AnchorPane root;
    private String username;
    private String password;
    private int idUser;
    
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    
    public int getIdUser() {
        return idUser;
    }

    public void switchScene(String path, ActionEvent event) throws IOException {
        this.loader = new FXMLLoader(getClass().getResource(path));
        this.root = loader.load();
        
        Controller newController = loader.getController();
        newController.setIdUser(this.idUser);
        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchProfil(ActionEvent event) {
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        try {
            switchScene("../SceneDesign/profil.fxml", event);
            Controller profilController = loader.getController();
            DrawApp.drawProfil(root, userDaoImpl, profilController.getIdUser());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void UpdateSearch(ActionEvent event) throws IOException {
        if(this.root == null) {
            switchScene("../SceneDesign/search.fxml", event);
        }

        SpecialistDaoImpl speDao = new SpecialistDaoImpl();
        UserDaoImpl userDao = new UserDaoImpl();
        String search = "";
        if(searchInput != null) {
            search = searchInput.getText();
        }
        System.out.println(search);
        ResultSet res;
        int state = 0;
        if(search != "") {
            res = userDao.search("name", search);
            state = 1;
        }
        else {
            res = speDao.returnAllProfiles(); //?? G un pb la (Vianney)
        }
        try {
            double x = 21.0;
            double y = 478.0; //+158 each block
            if(res != null) {
                while (res.next()) {
                    String name = "";
                    int idUser = res.getInt("idUser");
                    System.out.println(idUser);
                    String description = "";
                    double note = 0.0; 
                    double tarif = 0.0;
                    if(state == 1) {
                        name = res.getString("name");
                        ResultSet resSpe = speDao.getSpecific("idUser", idUser);
                        if(resSpe.next()) {
                            description = resSpe.getString("description");
                            note = resSpe.getDouble("moyenneNote");
                            tarif = resSpe.getDouble("tarif");
                            DrawApp.drawSpecialistSearch(root, name, description, note, tarif, x, y);
                            y += 158.0;
                        }
                    }
                    else {
                        ResultSet resUser = userDao.getSpecific("idUser", idUser);
                        if(resUser.next()) {
                            name = resUser.getString("name");
                        } 
                        description = res.getString("description");
                        note = res.getDouble("moyenneNote");
                        tarif = res.getDouble("tarif"); 
                        
                        DrawApp.drawSpecialistSearch(root, name, description, note, tarif, x, y);
                        y += 158.0;   
                    }
    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void switchSearch(ActionEvent event) {
        try {
            switchScene("../SceneDesign/search.fxml", event);
            UpdateSearch(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logOut(ActionEvent event) {
        try {
            this.idUser = -1; //Remove id from user
            switchScene("../SceneDesign/login.fxml", event);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void switchHistoric(ActionEvent event) {
        try {
            switchScene("../SceneDesign/historic.fxml", event);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            else {
                root = (AnchorPane) ((Node)event.getSource()).getScene().getRoot();
                if(errorLabel != null) {
                    errorLabel.setText("");
                }
                errorLabel = DrawApp.drawLabel(root, 250, 480, "Wrong username or password", 20, Color.RED);
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
            else {
                root = (AnchorPane) ((Node)event.getSource()).getScene().getRoot();
                if(errorLabel != null) {
                    errorLabel.setText("");
                }
                errorLabel = DrawApp.drawLabel(root, 200, 480, "Please specify correct username or password", 20, Color.RED);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}