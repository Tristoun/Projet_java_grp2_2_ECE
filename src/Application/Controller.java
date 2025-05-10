package Application;

import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle.Control;
import javax.swing.Action;
import DAO.DaoFactory;
import DAO.LocationDAOImpl;
import DAO.LocationDocDAOImpl;
import DAO.RDVDaoImpl;
import DAO.SpecialisationDAOImpl;
import DAO.SpecialisationDocDAOImpl;
import DAO.SpecialistDaoImpl;
import DAO.UserDaoImpl;
import Models.RDV;
import Models.User;
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
    private DatePicker datePicker = new DatePicker();
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

    public AnchorPane getRoot() {
        return this.root;
    }

    public void switchScene(String path, ActionEvent event) throws IOException {
        this.loader = new FXMLLoader(getClass().getResource(path));
        this.root = loader.load();
        
        Controller newController = loader.getController();
        newController.setIdUser(this.idUser);
        newController.choiceTalent = this.choiceTalent; //Could be update to update only on search page
        newController.datePicker = this.datePicker;
        
        if (event.getSource() instanceof Node) {
            Node sourceNode = (Node) event.getSource();
            
            Stage stage = (Stage) sourceNode.getScene().getWindow();
            
            if (stage != null) {
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                System.out.println("Stage is null!");
            }
        } else {
            System.out.println("Event source is not a Node!");
        }

        // code d'avant
        /*Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/
    }
    
    public void switchProfil(ActionEvent event) {
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        try {
            switchScene("../SceneDesign/profil.fxml", event);
            Controller profilController = loader.getController();
            DrawApp.drawProfil(root, userDaoImpl, profilController.getIdUser());
            DrawApp.drawImage(root, "../image/client.png", 60, 136, 138, 133);
            try {
                getNextRdv();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

    public String getAddrDoc(LocationDocDAOImpl locDocDao, LocationDAOImpl locDao, int idSpe) throws SQLException {
        String addr = "";
        ResultSet resLocDoc = locDocDao.returnLocationDoc(idSpe);
        if(resLocDoc.next()) {
            int idAddr = resLocDoc.getInt("idLieu");
            System.out.println(idAddr);
            ResultSet resLoc = locDao.getSpecific("idLieu", idAddr);
            if(resLoc.next()) {
                addr = resLoc.getString("adresse") + " " + resLoc.getString("ville") + " " + resLoc.getString("code_postal");
            }
        }
        return addr;
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

    public ArrayList<String> getTalentSpecialist(int idSpe) {
        ArrayList<String> lstTalent = new ArrayList<>();
        SpecialisationDocDAOImpl speDocDao = new SpecialisationDocDAOImpl();
        SpecialisationDAOImpl specialisationDao = new SpecialisationDAOImpl();
        ResultSet res = speDocDao.getSpecific("idSpecialiste", idSpe);
        try {
            while (res.next()) {
                int idSpecialisation = res.getInt("idSpecialisation");
                
                ResultSet resSpe = specialisationDao.getSpecific("idSpecialisation", idSpecialisation);
                if(resSpe.next()) {
                    String nom = resSpe.getString("nom");
                    lstTalent.add(nom);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lstTalent;

        
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

        LocalDate selectedDate = datePicker.getValue();
        boolean searchDate = false;

        if(selectedDate != null) {
            searchDate = true;
        }

        ArrayList<Integer> lstDocSpe = getListSpecialistByTalent(valueTalent, specialisationDao, speDocDao);
        if(valueTalent == null) { //Use if no value is used 
            this.choiceTalent = setTalentBox(); //Set specialisation in choice box 
        }
        if(valueTalent != null) {
            searchTalent = true;
        }
        
        DrawApp.drawChoiceBox(root, this.choiceTalent, 233, 227, 185, 31);
        DrawApp.drawDatePicker(root, datePicker, 461, 227, 185, 31);
        
        if(search != "") {
            res = userDao.search("name", search); //Looking for a specialist with a specific name
            state = 1;
        }
        else {
            res = speDao.returnAllProfiles();
        }

        try {
            double x = 21.0;
            double y = 295.0; //+158 each block
            if(res != null) {
                while (res.next()) {
                    String name = "";
                    int idUser = res.getInt("idUser");
                    System.out.println(idUser);
                    String description = "";
                    double note = 0.0; 
                    double tarif = 0.0;
                    int idSpe = -1;
                    List<RDV> rdvlist;
                    boolean available = true;
                    Button button = null;
                    if(state != 1) {
                        idSpe  = res.getInt("idSpecialiste");
                    }
                    //Be aware of specialisation
                    if(state == 1) { //Specific in case of search with an input
                        name = res.getString("name");
                        ResultSet resSpe = speDao.getSpecific("idUser", idUser);
                        if(resSpe.next()) {
                            idSpe = resSpe.getInt("idSpecialiste");
                            if(searchDate == true) {
                                rdvlist = getAllRDV(idSpe);
                                available = specialisteIsAvailable(rdvlist, selectedDate);
                            }
                            if(available == true) {
                                if(lstDocSpe.contains(idSpe)  || searchTalent == false ) {
                                    description = resSpe.getString("description");
                                    note = resSpe.getDouble("moyenneNote");
                                    tarif = resSpe.getDouble("tarif");
                                    button = DrawApp.drawSpecialistSearch(root, name, description, note, tarif, x, y);
                                    y += 158.0;
                                }
                            }
                        }
                    }
                    else {
                        if(searchDate == true) {
                            rdvlist = getAllRDV(idSpe);
                            available = specialisteIsAvailable(rdvlist, selectedDate);
                        }
                        if(available == true) {
                            if(lstDocSpe.contains(idSpe) || searchTalent == false ) {
                                name = userDao.getName(idUser);
                                description = res.getString("description");
                                note = res.getDouble("moyenneNote");
                                tarif = res.getDouble("tarif"); 
                                
                                button = DrawApp.drawSpecialistSearch(root, name, description, note, tarif, x, y);
                                y += 158.0;   
                            }   
                        }
                    }
                    if(button != null) {
                        int finalIdSpe = idSpe; //Create new to assign for each button
                        button.setOnAction(e -> {
                            switchTakeRdv(e, finalIdSpe);
                        });
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

    public void switchTakeRdv(ActionEvent event, int idSpe) {
        SpecialistDaoImpl speDao = new SpecialistDaoImpl();
        LocationDAOImpl locDao = new LocationDAOImpl();
        LocationDocDAOImpl locDocDao = new LocationDocDAOImpl();
        try {
            switchScene("../SceneDesign/priserdv.fxml", event);
            ArrayList<String> lstSpecialisation = getTalentSpecialist(idSpe);
            String name = speDao.getName(idSpe);
            String addr = "";
            addr = getAddrDoc(locDocDao, locDao, idSpe);
            DrawApp.drawTakeRdv(root, name, lstSpecialisation, addr);
        
        }catch(IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableRDV(event, idSpe);
        // fonction antoine appellée ici
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
            double note = res.getDouble("note");
            String description = res.getString("description");
            Timestamp timestamp = res.getTimestamp("heure");
            Date date = new Date(timestamp.getTime());

            DrawApp.drawHistoric(root, nameUser, nameSpe, date, note, description, x, y, 759, 135, "");
            y += 158.0;
        }
    }

    public void getNextRdv() throws SQLException {
        RDVDaoImpl rdvdao = new RDVDaoImpl();
        SpecialistDaoImpl specialistDao = new SpecialistDaoImpl();
        UserDaoImpl userdao = new UserDaoImpl();
        LocationDAOImpl locDao = new LocationDAOImpl();
        LocationDocDAOImpl locDocDao = new LocationDocDAOImpl();
        
        ResultSet res =  rdvdao.getNextRdv(idUser);
        String nameUser = userdao.getName(idUser);
        double x = 41.0;
        double y = 445.0;
        if(res != null) {
            if (res.next()) {   

                int idSpe = res.getInt("idSpecialiste");
                String nameSpe = specialistDao.getName(idSpe);
                String description = res.getString("description");
                Timestamp timestamp = res.getTimestamp("heure");
                Date date = new Date(timestamp.getTime());
                String addr = "";
                
                try {
                    addr = getAddrDoc(locDocDao, locDao, idSpe);
                }
                catch(SQLException e) {
                    e.getStackTrace();
                }
                DrawApp.drawHistoric(root, nameUser, nameSpe, date, -1, description, x, y, 723, 148, addr);
            }
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
            User patient = null;
            patient = userDaoImpl.logIn(username, password);
            idUser = patient.getUserId();

            if(idUser != -1 && idUser != -2) {
                if(userDaoImpl.verifierSiAdmin(patient) == 1) {
                    System.out.println("GO ADMIN");
                    ControllerAdmin controlAdmin = new ControllerAdmin();
                    controlAdmin.modifUser(event);
                    return;
                }
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

    public boolean patientIsAvailable(int idPatient, LocalDateTime date_rdv, RDVDaoImpl rdvDao ) { // cette fonction verif si le user a deja un creneau à cette heure là
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

    public boolean specialisteIsAvailable(List<RDV> lstRdv, LocalDate dateRdv) {
        for (RDV rdv : lstRdv) {
            LocalDate date = rdv.getDate_rdv().toLocalDate();
            if (dateRdv.equals(date)) {
                return false; 
            }
        }
        return true;
    }

    public List<RDV> getAllRDV(int IdSpecialiste){ //recup les rdv du specialiste => besoin sous forme liste pour exploiter res ou pas ?
        RDVDaoImpl rdvDao = new RDVDaoImpl();

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

    public String addRDV(RDV rdv, RDVDaoImpl rdvDao) { 
        if (patientIsAvailable(rdv.getId_patient(), rdv.getDate_rdv(), rdvDao)) {
            rdvDao.ajouterRDV(rdv);  
            return "ok c bon";
        } else {
            return "t'as déjà un rdv à cette heure là mon pote";
        }
    }

    public void tableRDV(ActionEvent event, int IdSpecialiste) {
        RDVDaoImpl rdvDaoImpl = new RDVDaoImpl();
        // UserDaoImpl userDao = new UserDaoImpl();                 Je pense pas en avoir besoin sauf peut-être pour modif données bdd après ?
        TableView<LocalDateTime> table = new TableView<>(); 
        TableColumn<LocalDateTime, String> slotColonne = new TableColumn<>("Créneaux disponibles");
        
        slotColonne.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().toString()));
        
        ObservableList<LocalDateTime> creneauxDisposObs = FXCollections.observableArrayList(); // liste des creneaux disponibles OBSERVABLE

        List<RDV> listrdvspe = getAllRDV(IdSpecialiste); // liste des rdvs du specialiste
        List<LocalDateTime> allPossibleSlots = genereSlots(LocalDate.now()); // peut-être pas localdate.now ??

        // enlève créneaux déjà réservés
        List<LocalDateTime> dispos = new ArrayList<>(allPossibleSlots);

        for (RDV rdv : listrdvspe){
            LocalDateTime dejareserve = rdv.getDate_rdv(); // getDate retourne bien une heure et pas un jour
            dispos.removeIf(slot -> slot.equals(dejareserve));
        }
        
        creneauxDisposObs.setAll(dispos);

        table.getColumns().add(slotColonne);
        table.setItems(creneauxDisposObs);
        
        int idPatient = getIdUser();

        table.setOnMouseClicked(event1 -> { // faire un bouton confirmer nn ?
            LocalDateTime selectedSlot = table.getSelectionModel().getSelectedItem();
            if (selectedSlot != null) {
                // vérif patient dispo
                if (patientIsAvailable(idPatient, selectedSlot, rdvDaoImpl)) {
                    

                    RDV newRdv = new RDV(0, idPatient, IdSpecialiste, selectedSlot, 0, "RDV réservé par " + idPatient);
                    rdvDaoImpl.ajouterRDV(newRdv);

                    System.out.println("RDV réservé pour : " + idPatient + " à " + selectedSlot);

                    // met à jour table pour montrer
                } else {
                    System.out.println("Le patient a déjà un RDV réservé pour cette heure-là.");
                }
            }});

        DrawApp.drawTableView(root, table, 50, 365, 700, 390);
    }

    public List<LocalDateTime> genereSlots(LocalDate date) { // j'ai pas trouve comment faire d'autre qu'en generant une liste de creneaux possibles
        List<LocalDateTime> slots = new ArrayList<>();
        LocalDateTime startOfDay = LocalDateTime.of(date, LocalTime.of(9, 0)); // heure de début
        for (int i = 0; i < 14; i++) { // nb créneaux
            slots.add(startOfDay.plusMinutes(i*30)); // pour créneau de une heure mettre plusHours(i)
        }
        return slots;
    }
}