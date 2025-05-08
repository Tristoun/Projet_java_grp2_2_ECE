package Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle.Control;
import javax.swing.Action;
import DAO.DaoFactory;
import DAO.RDVDaoImpl;
import DAO.SpecialisationDAOImpl;
import DAO.SpecialisationDocDAOImpl;
import DAO.SpecialistDaoImpl;
import DAO.UserDaoImpl;
import Models.RDV;
import Application.DrawApp;
import DAO.RDVDao;
import DAO.RDVDaoImpl;

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
    private ChoiceBox<String> choiceTalent = new ChoiceBox<>();
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
        newController.choiceTalent = this.choiceTalent; //Could be update to update only on search page
        
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

    public ChoiceBox<String> setTalentBox() {
        SpecialisationDAOImpl talentDao = new SpecialisationDAOImpl();

        ResultSet res = talentDao.getAll();
        ObservableList<String> lstTalent = FXCollections.observableArrayList();
        String nameTalent = "";
        try {
            while (res.next()) {
                nameTalent = res.getString("nom");
                lstTalent.add(nameTalent);
            }
            this.choiceTalent.setItems(lstTalent);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return choiceTalent;

    }

    public ArrayList<Integer> getListSpecialistByTalent(String valueTalent, SpecialisationDAOImpl specialisationDao, SpecialisationDocDAOImpl speDocDao) {
        ArrayList<Integer> lstDocSpe = new ArrayList<>();

        this.choiceTalent.setValue(valueTalent);
        ResultSet resSpecialisation = specialisationDao.getSpecific("nom", valueTalent);
        int idSpecialisation = -1;
        try {
            if(resSpecialisation.next()) {
                idSpecialisation = resSpecialisation.getInt("idSpecialisation");
                ResultSet resDocSpe = speDocDao.getSpecific("idSpecialisation", idSpecialisation);
                while(resDocSpe.next()) {   
                    lstDocSpe.add(resDocSpe.getInt("idSpecialiste"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(lstDocSpe);        
        return lstDocSpe;
    }

    public void UpdateSearch(ActionEvent event) throws IOException {
        if(this.root == null) {
            switchScene("../SceneDesign/search.fxml", event);
        }

        SpecialistDaoImpl speDao = new SpecialistDaoImpl();
        UserDaoImpl userDao = new UserDaoImpl();
        SpecialisationDAOImpl specialisationDao = new SpecialisationDAOImpl();
        SpecialisationDocDAOImpl speDocDao = new SpecialisationDocDAOImpl();
        
        String search = "";
        if(searchInput != null) {
            search = searchInput.getText();
        }
        System.out.println(search);
        ResultSet res;
        int state = 0;
        
        String valueTalent = this.choiceTalent.getValue();
        boolean searchTalent = false;

        ArrayList<Integer> lstDocSpe = getListSpecialistByTalent(valueTalent, specialisationDao, speDocDao);
        if(valueTalent == null) { //Use if no value is used 
            this.choiceTalent = setTalentBox(); //Set specialisation in choice box 
        }
        if(valueTalent != null) {
            searchTalent = true;
        }
        
        DrawApp.drawChoiceBox(root, this.choiceTalent, 233, 227, 185, 31);
        
        
        if(search != "") {
            res = userDao.search("name", search); //Looking for a specialist with a specific name
            state = 1;
        }
        else {
            res = speDao.returnAllProfiles();
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
                    int idSpe = -1;
                    if(state != 1) {
                        idSpe  = res.getInt("idSpecialiste");
                    }


                    //Be aware of specialisation
                    if(state == 1) { //Specific in case of search with an input
                        name = res.getString("name");
                        ResultSet resSpe = speDao.getSpecific("idUser", idUser);
                        if(resSpe.next()) {
                            if(lstDocSpe.contains(resSpe.getInt("idSpecialiste"))  || searchTalent == false ) {
                                description = resSpe.getString("description");
                                note = resSpe.getDouble("moyenneNote");
                                tarif = resSpe.getDouble("tarif");
                                DrawApp.drawSpecialistSearch(root, name, description, note, tarif, x, y);
                                y += 158.0;
                            }
                        }
                    }
                    else {
                        if(lstDocSpe.contains(idSpe) || searchTalent == false ) {
                            name = userDao.getName(idUser);
                            description = res.getString("description");
                            note = res.getDouble("moyenneNote");
                            tarif = res.getDouble("tarif"); 
                            
                            DrawApp.drawSpecialistSearch(root, name, description, note, tarif, x, y);
                            y += 158.0;   
                        }   
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

    public void getHistoric(ActionEvent event) throws SQLException {
        RDVDaoImpl rdvdao = new RDVDaoImpl();
        SpecialistDaoImpl specialistDao = new SpecialistDaoImpl();
        UserDaoImpl userdao = new UserDaoImpl();
        
        ResultSet res =  rdvdao.getRdvUser(idUser);
        String nameUser = userdao.getName(idUser);
        double x = 21.0;
        double y = 108.0;
        while (res.next()) {   

            int idSpe = res.getInt("idSpecialiste");
            String nameSpe = specialistDao.getName(idSpe);
            int note = res.getInt("note");
            String description = res.getString("description");
            Timestamp timestamp = res.getTimestamp("heure");
            Date date = new Date(timestamp.getTime());

            DrawApp.drawHistoric(root, nameUser, nameSpe, date, note, description, x, y);
            y += 158.0;
        }
    }


    public void switchHistoric(ActionEvent event) {
        try {
            switchScene("../SceneDesign/historic.fxml", event);
            try {
                getHistoric(event);
            } catch(SQLException e) {
                e.getStackTrace();
            }
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

    

    

    private RDVDaoImpl rdvDao;

    public void RDVinit() {
        rdvDao = new RDVDaoImpl();
    }

    public boolean patientIsAvailable(int idPatient, LocalDateTime date_rdv) { // cette fonction verif si le user a deja un creneau à cette heure là
        ResultSet resultSet = rdvDao.getRdvUser(idPatient); 
        try {
            while (resultSet.next()) {
                LocalDateTime existingDate = resultSet.getTimestamp("heure").toLocalDateTime();
                if (existingDate.equals(date_rdv)) {return false; }
            }
        } 
        catch (SQLException e) {e.printStackTrace();}
        return true;
    }

    public List<RDV> getAllRDV(int IdSpecialiste){ //recup les rdv du specialiste => besoin sous forme liste pour exploiter res ou pas ?
        ResultSet res = rdvDao.getSpecific("IdSpecialiste", IdSpecialiste);
        List<RDV> listerdv = new ArrayList<>();

        try {
            while (res.next()) {
                RDV rdv = new RDV(res.getInt("idRdv"), //pb avec nom des colonnes dans db ?
                res.getInt("idUser"), 
                res.getInt("idSpecialiste"),
                res.getTimestamp("heure").toLocalDateTime(),
                res.getDouble("note"),
                res.getString("description"));
                
                listerdv.add(rdv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listerdv; 
    }

    public String addRDV(RDV rdv) { 
        if (patientIsAvailable(rdv.getId_patient(), rdv.getDate_rdv())) {
            rdvDao.ajouterRDV(rdv);  
            return "ok c bon";
        } else {
            return "t'as déjà un rdv à cette heure là mon pote";
        }
    }
}