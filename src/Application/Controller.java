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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle.Control;
import java.util.Set;
import javafx.scene.shape.Rectangle;

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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

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

    int indexSearch = 0;

    
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    
    public int getIdUser() {
        return idUser;
    }

    public AnchorPane getRoot() {
        return this.root;
    }

    public void updateSearchRight(ActionEvent event) {
        this.indexSearch += 3;
        try {
            UpdateSearch(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateSearchLeft(ActionEvent event) {
        this.indexSearch -=3;
        if(this.indexSearch < 0) {
            this.indexSearch = 0;
        }
        try {
            UpdateSearch(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void switchScene(String path, ActionEvent event) throws IOException {
        this.loader = new FXMLLoader(getClass().getResource(path));
        this.root = loader.load();
        
        Controller newController = loader.getController();
        newController.setIdUser(this.idUser);
        newController.choiceTalent = this.choiceTalent; //Could be update to update only on search page
        newController.datePicker = this.datePicker;
        System.out.println("SEARCH : " + indexSearch);
        Scene currentScene = ((Node) event.getSource()).getScene();
        Stage stage = (Stage) currentScene.getWindow();
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
            ArrayList<Map<String, Object>> lstContent = new ArrayList();
            if(res != null) {
                while (res.next()) {
                    Map<String, Object> content = new HashMap<>();
                    String name = "";
                    int idUser = res.getInt("idUser");
                    System.out.println(idUser);
                    String description = "";
                    double note = 0.0; 
                    double tarif = 0.0;
                    int idSpe = -1;
                    List<RDV> rdvlist;
                    boolean available = true;

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

                                    content.put("idSpe", idSpe);
                                    content.put("name", name);
                                    content.put("description", description);
                                    content.put("note", note);
                                    content.put("tarif", tarif);
                                    lstContent.add(content);

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

                                content.put("idSpe", idSpe);
                                content.put("name", name);
                                content.put("description", description);
                                content.put("note", note);
                                content.put("tarif", tarif);
                                lstContent.add(content);
                            }   
                        }
                    }
                    System.out.println(lstContent);
                }

                int index = 0;
                int itemsDrawn = 0;
                Button button = null;
                int total = lstContent.size();
                if (this.indexSearch >= total) {
                    this.indexSearch = (total % 3 == 0) ? total - 3 : total - (total % 3);
                }
                System.out.println("NEW SEARCH : " + indexSearch);
                for (Map<String, Object> rdvList : lstContent) {
                    if (index >= this.indexSearch && itemsDrawn < 3) {
                        button = DrawApp.drawSpecialistSearch(
                            root,
                            rdvList.get("name").toString(),
                            rdvList.get("description").toString(),
                            (double) rdvList.get("note"),
                            (double) rdvList.get("tarif"),
                            x, y
                        );
                        y += 158.0;

                        if (button != null) {
                            int finalIdSpe = (int) rdvList.get("idSpe");
                            button.setOnAction(e -> switchTakeRdv(e, finalIdSpe));
                        }

                        itemsDrawn++;
                    }
                    index++;
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

    public String getNomJourFrancais(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY: return "LUNDI";
            case TUESDAY: return "MARDI";
            case WEDNESDAY: return "MERCREDI";
            case THURSDAY: return "JEUDI";
            case FRIDAY: return "VENDREDI";
            case SATURDAY: return "SAMEDI";
            default: return "";
        }
    }
    
    public void tableRDV(ActionEvent event, int IdSpecialiste) {
        RDVDaoImpl rdvDaoImpl = new RDVDaoImpl();
        TableView<Map<String, LocalDateTime>> table = new TableView<>();
        table.getSelectionModel().setCellSelectionEnabled(true);
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        String[] joursSemaine = {"LUNDI", "MARDI", "MERCREDI", "JEUDI", "VENDREDI", "SAMEDI"};
        Map<String, List<LocalDateTime>> dispoParJour = new HashMap<>();
        for (String jour : joursSemaine) {
            dispoParJour.put(jour, new ArrayList<>());
        }

        //on enleve les creneaux deja reserves
        Set<LocalDateTime> creneauxReserves = new HashSet<>();
        List<RDV> rdvs = getAllRDV(IdSpecialiste);  
        for (RDV rdv : rdvs) {
            creneauxReserves.add(rdv.getDate_rdv());
        }

        // Vérifier les créneaux réservés
        System.out.println("Créneaux réservés : " + creneauxReserves);


        for (String jour : joursSemaine) {
            TableColumn<Map<String, LocalDateTime>, String> dayColumn = new TableColumn<>(jour);
            dayColumn.setCellValueFactory(cellData -> {
                LocalDateTime slot = cellData.getValue().get(jour);
                return new SimpleStringProperty(slot != null ? slot.toLocalTime().toString() : "");
            });


            // cette partie rend les cellules déjà reservées grisées et incliquables + les rends joliiies
            dayColumn.setCellFactory(col -> {
                TableCell<Map<String, LocalDateTime>, String> cell = new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            LocalDateTime slot = (LocalDateTime) getTableRow().getItem().get(jour);
                            if (slot != null && creneauxReserves.contains(slot)) {
                                setStyle("-fx-background-color: lightgray; -fx-text-fill: red; ");
                                setDisable(true); // Rendre la cellule non cliquable
                                setText("Réservé");
                            } else { //pas sur que necessaire mais dans le doute 
                                setStyle("");
                                setDisable(false); 
                                setText(item);
                            }
                        } else {
                            setText(null);
                        }
                    }
                };
                return cell;
            });

            //formattage pour que soit propre dans tableau
            dayColumn.setResizable(false);
            dayColumn.setPrefWidth(116);
            table.getColumns().add(dayColumn);
        }

        ObservableList<Map<String, LocalDateTime>> creneauxDisposObs = FXCollections.observableArrayList();
        
        // ajoute créneaux dans map
        for (int d = 0; d < 7; d++) {
            LocalDate date = LocalDate.now().plusDays(d); // from tomorrow to +6 days
            for (LocalDateTime slot : genereSlots(date)) {
                String jour = getNomJourFrancais(slot.getDayOfWeek());
                if (dispoParJour.containsKey(jour)) {
                    dispoParJour.get(jour).add(slot);
                }
            }
        }

        int nombreCreneauxParJour = 14; 
        for (int i = 0; i < nombreCreneauxParJour; i++) {
            Map<String, LocalDateTime> row = new HashMap<>();
            for (String jour : joursSemaine) {
                List<LocalDateTime> creneaux = dispoParJour.get(jour);
                if (creneaux != null && i < creneaux.size()) {
                    row.put(jour, creneaux.get(i));
                } else {
                    row.put(jour, null);
                }
            }
            creneauxDisposObs.add(row);
        }
        System.out.println(creneauxDisposObs);
    
        table.setItems(creneauxDisposObs);
    
        table.setOnMouseClicked(event1 -> {
            TablePosition<Map<String, LocalDateTime>, ?> pos = table.getSelectionModel().getSelectedCells().get(0);
            String selectedDay = pos.getTableColumn().getText(); // e.g., "LUNDI 13/05"
            Map<String, LocalDateTime> selectedRow = table.getItems().get(pos.getRow());
            LocalDateTime selectedSlot = selectedRow.get(selectedDay);

            if (selectedSlot != null) {
                if (patientIsAvailable(getIdUser(), selectedSlot, rdvDaoImpl)) {
                    RDV newRdv = new RDV(0, getIdUser(), IdSpecialiste, selectedSlot, 0, "RDV réservé par " + getIdUser()); //FIX TO AUTOINCREMENT
                    rdvDaoImpl.ajouterRDV(newRdv);
                    System.out.println("RDV réservé pour : " + getIdUser() + " à " + selectedSlot);
                } else {
                    System.out.println("t'as déjà réservé à cette heure là mon reuf");
                }
            }
        });

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