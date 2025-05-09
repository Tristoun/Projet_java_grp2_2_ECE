package Application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import DAO.LocationDAOImpl;
import DAO.RDVDaoImpl;
import DAO.SpecialisationDAOImpl;
import DAO.SpecialistDaoImpl;
import DAO.UserDaoImpl;
import Models.Location;
import Models.RDV;
import Models.Specialisation;
import Models.Specialist;
import Models.User;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerAdmin extends Controller{

    public void modifUser(ActionEvent event) {
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        TableView<User> tableData = new TableView<>();
        
        try {
            switchScene("../SceneDesign/admin.fxml", event);
    
            ResultSet res = userDaoImpl.getAll();
    
            TableColumn<User, Integer> idCol = new TableColumn<>("ID");
            TableColumn<User, String> nameCol = new TableColumn<>("Username");
            TableColumn<User, String> passCol = new TableColumn<>("Password");
            TableColumn<User, Integer> adminCol = new TableColumn<>("Admin");
    
            ObservableList<User> data = FXCollections.observableArrayList(); 
            
            while (res.next()) {
                int id = res.getInt("idUser");
                String name = res.getString("name");
                String pass = res.getString("password");
                int admin = res.getInt("admin");
                User currentUser = new User(id, name, pass, admin);
                data.add(currentUser);
            }
    
            idCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUserId()).asObject());
            nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
            passCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
            adminCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStatus()).asObject());
    
            tableData.getColumns().addAll(idCol, nameCol, passCol, adminCol);
            tableData.setItems(data);
    
            DrawApp.drawTableView(getRoot(), tableData, 28, 112, 750, 480);
    
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public void modifSpecialiste(ActionEvent event) {
        SpecialistDaoImpl speDao = new SpecialistDaoImpl();
        TableView<Specialist> tableData = new TableView<>();
        UserDaoImpl userDao = new UserDaoImpl();
        try {
            switchScene("../SceneDesign/admin.fxml", event);
    
            ResultSet res = speDao.getAll();
    
            TableColumn<Specialist, Integer> idCol = new TableColumn<>("ID");
            TableColumn<Specialist, String> nameCol = new TableColumn<>("Username");
            TableColumn<Specialist, String> descCol = new TableColumn<>("Description");
            TableColumn<Specialist, Double> tarifCol = new TableColumn<>("Tarif");
            TableColumn<Specialist, Double> moyennecol = new TableColumn<>("Moyenne");
    
            ObservableList<Specialist> data = FXCollections.observableArrayList(); 
            
            while (res.next()) {
                int id = res.getInt("idSpecialiste");
                int idUser = res.getInt("idUser");
                String description = res.getString("description");
                double tarif = res.getDouble("tarif");
                double moyenneNote = res.getDouble("moyenneNote");
                
                ResultSet userContent = userDao.getSpecific("idUser",  idUser);
                User user;
                if(userContent.next()) {
                    user = new User(idUser, userContent.getString("name"), userContent.getString("password"), userContent.getInt("admin"));
                    Specialist currentSpecialist = new Specialist(id, user, description, tarif, moyenneNote);
                    data.add(currentSpecialist);
                }

            }
    
            idCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdSpecialist()).asObject());
            nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNameUser()));
            descCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
            tarifCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTarif()).asObject());
            moyennecol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getMoyenne_note()).asObject());
    
            tableData.getColumns().addAll(idCol, nameCol, descCol, tarifCol, moyennecol);
            tableData.setItems(data);
    
            DrawApp.drawTableView(getRoot(), tableData, 28, 112, 750, 480);
    
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void modifSpecialisation(ActionEvent event) {
        SpecialisationDAOImpl specialisationDaoImpl = new SpecialisationDAOImpl();
        TableView<Specialisation> tableData = new TableView<>();

        try {
            switchScene("../SceneDesign/admin.fxml", event);

            ResultSet res = specialisationDaoImpl.getAll();

            TableColumn<Specialisation, Integer> idCol = new TableColumn<>("ID Specialisation");
            TableColumn<Specialisation, String> nomCol = new TableColumn<>("Nom");

            ObservableList<Specialisation> data = FXCollections.observableArrayList();

            while (res.next()) {
                int id = res.getInt("idSpecialisation");
                String nom = res.getString("nom");

                Specialisation current = new Specialisation(id, nom);
                data.add(current);
            }

            idCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdSpecialisation()).asObject());
            nomCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSpecialisationNom()));

            tableData.getColumns().addAll(idCol, nomCol);
            tableData.setItems(data);

            DrawApp.drawTableView(getRoot(), tableData, 28, 112, 750, 480);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifRdv(ActionEvent event) {
        RDVDaoImpl rdvDaoImpl = new RDVDaoImpl();
        TableView<RDV> tableData = new TableView<>();
        UserDaoImpl userDao = new UserDaoImpl();
        SpecialistDaoImpl speDao = new SpecialistDaoImpl();

        try {
            switchScene("../SceneDesign/admin.fxml", event);

            ResultSet res = rdvDaoImpl.getAll();

            TableColumn<RDV, Integer> idCol = new TableColumn<>("ID RDV");
            TableColumn<RDV, String> userCol = new TableColumn<>("Name User");
            TableColumn<RDV, String> specCol = new TableColumn<>("Name Specialiste");
            TableColumn<RDV, String> heureCol = new TableColumn<>("Heure");
            TableColumn<RDV, Double> noteCol = new TableColumn<>("Note");
            TableColumn<RDV, String> descCol = new TableColumn<>("Description");

            ObservableList<RDV> data = FXCollections.observableArrayList();

            while (res.next()) {
                int idRdv = res.getInt("idRdv");
                int idUser = res.getInt("idUser");
                int idSpecialiste = res.getInt("idSpecialiste");


                Timestamp timestamp = res.getTimestamp("heure");
                LocalDateTime heure = timestamp.toLocalDateTime();

                int note = res.getInt("note");
                String description = res.getString("description");

                RDV currentRdv = new RDV(idRdv, idUser, idSpecialiste, heure, note, description);
                data.add(currentRdv);
            }

            idCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId_rdv()).asObject());


            userCol.setCellValueFactory(cellData -> {
                int userId = cellData.getValue().getId_patient(); // or getIdUser(), depending on your model
                String userName = "";
                try {
                    userName = userDao.getName(userId); // Replace with your method that returns a String name
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return new SimpleStringProperty(userName);
            });
            
            
            specCol.setCellValueFactory(cellData -> {
                int specId = cellData.getValue().getId_specialiste();
                String specName = "";
                try {
                    specName = speDao.getName(specId); // Replace with your actual method
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return new SimpleStringProperty(specName);
            });
            
            
            heureCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate_rdv().toString()));
            noteCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getRating()).asObject());
            descCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getComment()));

            tableData.getColumns().addAll(idCol, userCol, specCol, heureCol, noteCol, descCol);
            tableData.setItems(data);

            DrawApp.drawTableView(getRoot(), tableData, 28, 112, 750, 480);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifLieu(ActionEvent event) {
        LocationDAOImpl locationDaoImpl = new LocationDAOImpl();
        TableView<Location> tableData = new TableView<>();
    
        try {
            switchScene("../SceneDesign/admin.fxml", event);
    
            ResultSet res = locationDaoImpl.getAll();
    
            TableColumn<Location, Integer> idCol = new TableColumn<>("ID");
            TableColumn<Location, String> adresseCol = new TableColumn<>("Adresse");
            TableColumn<Location, String> villeCol = new TableColumn<>("Ville");
            TableColumn<Location, String> codePostalCol = new TableColumn<>("Code Postal");
    
            ObservableList<Location> data = FXCollections.observableArrayList();
    
            while (res.next()) {
                int idLieu = res.getInt("idLieu");
                String adresse = res.getString("adresse");
                String ville = res.getString("ville");
                String codePostal = res.getString("code_postal");
    
                Location currentLocation = new Location(idLieu, adresse, ville, codePostal);
                data.add(currentLocation);
            }
    
            idCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getLocationId()).asObject());
            adresseCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdress()));
            villeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCity()));
            codePostalCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPostalCode()));
    
            tableData.getColumns().addAll(idCol, adresseCol, villeCol, codePostalCol);
            tableData.setItems(data);
    
            DrawApp.drawTableView(getRoot(), tableData, 28, 112, 750, 480);
    
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void logOut() {
        logOut();
    }



}
