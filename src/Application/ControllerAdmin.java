package Application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.Button;

public class ControllerAdmin extends Controller{

    public void updateUser(User user) {
        UserDaoImpl userDao = new UserDaoImpl();
        System.out.println("I'm updating user : " + user.getUserId());
        userDao.editProfileUser(user);
    }

    public void updateSpecialist(Specialist specialist) {
        SpecialistDaoImpl speDao = new SpecialistDaoImpl();
        speDao.editProfileSpecialist(specialist);
    }

    public void updateSpecialisation(Specialisation specialisation) {
        SpecialisationDAOImpl specialisationDAOImpl = new SpecialisationDAOImpl();
        specialisationDAOImpl.modifierSpecialisation(specialisation);
    }

    public void updateLocation(Location location) {
        LocationDAOImpl locationDAOImpl = new LocationDAOImpl();
        locationDAOImpl.modifierLocation(location);
    }

    public void updateRdv(RDV rdv) {
        RDVDaoImpl rdvDaoImpl = new RDVDaoImpl();
        rdvDaoImpl.modifierRDV(rdv);
    }

    public void deleteUser(User user) {
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        userDaoImpl.supprimerUser(user.getUserId());
    }

    public void deleteSpecialist(Specialist specialist) {
        SpecialistDaoImpl speDao = new SpecialistDaoImpl();
        speDao.deleteSpecialist(specialist);
    }

    public void deleteSpecialisation(Specialisation specialisation) {
        SpecialisationDAOImpl specialisationDAOImpl = new SpecialisationDAOImpl();
        specialisationDAOImpl.deleteSpecialisation(specialisation);
    }

    public void deleteLocation(Location location) {
        LocationDAOImpl locationDAOImpl = new LocationDAOImpl();
        locationDAOImpl.deleteLocation(location);
    }

    public void deleteRdv(RDV rdv) {
        RDVDaoImpl rdvDaoImpl = new RDVDaoImpl();
        rdvDaoImpl.deleteRdv(rdv);
    }


    public void modifUser(ActionEvent event) {
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        TableView<User> tableData = new TableView<>();
        tableData.setEditable(true); // Enable editing on the TableView
        try {
            switchScene("../SceneDesign/admin.fxml", event);
    
            ResultSet res = userDaoImpl.getAll();
    
            TableColumn<User, Integer> idCol = new TableColumn<>("ID");
            TableColumn<User, String> nameCol = new TableColumn<>("Username");
            TableColumn<User, String> passCol = new TableColumn<>("Password");
            TableColumn<User, Integer> adminCol = new TableColumn<>("Admin");
            TableColumn<User, Void> actionCol = new TableColumn<>("Action"); // Column for buttons
    
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
            nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
            nameCol.setOnEditCommit(e -> {
                User user = e.getRowValue();
                user.setUsername(e.getNewValue());
            });
    
            passCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
            passCol.setCellFactory(TextFieldTableCell.forTableColumn());
            passCol.setOnEditCommit(e -> {
                User user = e.getRowValue();
                user.setPassword(e.getNewValue()); 
            });

            // Make the Admin column editable with a TextField (Integer)
            adminCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStatus()).asObject());
            adminCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            adminCol.setOnEditCommit(e -> {
                User user = e.getRowValue();
                user.setStatus(e.getNewValue());
            });
            
            actionCol.setCellFactory(col -> {
                return new TableCell<User, Void>() {
                    private final Button updateBtn = new Button("Update");
                    private final Button deleteBtn = new Button("Delete");
                    private final HBox hbox = new HBox(10, updateBtn, deleteBtn);
                    
                    {
                        hbox.setAlignment(Pos.CENTER);
                        
                        updateBtn.setOnAction(e -> {
                            if (getTableRow() != null && getTableRow().getItem() != null) {
                                User userCurrent = getTableRow().getItem();
                                updateUser(userCurrent);
                            }
                        });
                        
                        deleteBtn.setOnAction(e -> {
                            if (getTableRow() != null && getTableRow().getItem() != null) {
                                User userCurrent = getTableRow().getItem();
                                deleteUser(userCurrent);
                            }
                        });
                    }
                    
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(hbox);
                        }
                    }
                };
            });
    
            tableData.getColumns().addAll(idCol, nameCol, passCol, adminCol, actionCol);
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
        tableData.setEditable(true); // Enable editing

        try {
            switchScene("../SceneDesign/admin.fxml", event);

            ResultSet res = speDao.getAll();

            TableColumn<Specialist, Integer> idCol = new TableColumn<>("ID");
            TableColumn<Specialist, String> nameCol = new TableColumn<>("Username");
            TableColumn<Specialist, String> descCol = new TableColumn<>("Description");
            TableColumn<Specialist, Double> tarifCol = new TableColumn<>("Tarif");
            TableColumn<Specialist, Double> moyenneCol = new TableColumn<>("Moyenne");
            TableColumn<Specialist, Void> actionCol = new TableColumn<>("Action");

            ObservableList<Specialist> data = FXCollections.observableArrayList();

            while (res.next()) {
                int id = res.getInt("idSpecialiste");
                int idUser = res.getInt("idUser");
                String description = res.getString("description");
                double tarif = res.getDouble("tarif");
                double moyenneNote = res.getDouble("moyenneNote");

                ResultSet userContent = userDao.getSpecific("idUser", idUser);
                if (userContent.next()) {
                    User user = new User(idUser, userContent.getString("name"),
                                        userContent.getString("password"), userContent.getInt("admin"));
                    Specialist currentSpecialist = new Specialist(id, user, description, tarif, moyenneNote);
                    data.add(currentSpecialist);
                }
            }

            idCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdSpecialist()).asObject());

            nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNameUser()));
            nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
            nameCol.setOnEditCommit(e -> {
                Specialist spe = e.getRowValue();
                spe.setUsername(e.getNewValue());
            });

            descCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
            descCol.setCellFactory(TextFieldTableCell.forTableColumn());
            descCol.setOnEditCommit(e -> {
                Specialist spe = e.getRowValue();
                spe.setDescription(e.getNewValue());
            });

            tarifCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTarif()).asObject());
            tarifCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            tarifCol.setOnEditCommit(e -> {
                Specialist spe = e.getRowValue();
                spe.setTarif(e.getNewValue());
            });

            moyenneCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getMoyenne_note()).asObject());
            moyenneCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            moyenneCol.setOnEditCommit(e -> {
                Specialist spe = e.getRowValue();
                spe.setMoyenne_note(e.getNewValue());
            });

            actionCol.setCellFactory(col -> {
                return new TableCell<Specialist, Void>() {
                    private final Button updateBtn = new Button("Update");
                    private final Button deleteBtn = new Button("Delete");
                    private final HBox hbox = new HBox(10, updateBtn, deleteBtn);

                    {
                        hbox.setAlignment(Pos.CENTER);

                        updateBtn.setOnAction(e -> {
                            if (getTableRow() != null && getTableRow().getItem() != null) {
                                Specialist current = getTableRow().getItem();
                                updateSpecialist(current);
                            }
                        });

                        deleteBtn.setOnAction(e -> {
                            if (getTableRow() != null && getTableRow().getItem() != null) {
                                Specialist current = getTableRow().getItem();
                                deleteSpecialist(current);
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(hbox);
                        }
                    }
                };
            });

            tableData.getColumns().addAll(idCol, nameCol, descCol, tarifCol, moyenneCol, actionCol);
            tableData.setItems(data);

            DrawApp.drawTableView(getRoot(), tableData, 28, 112, 750, 480);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


    public void modifSpecialisation(ActionEvent event) {
        SpecialisationDAOImpl specialisationDaoImpl = new SpecialisationDAOImpl();
        TableView<Specialisation> tableData = new TableView<>();
        tableData.setEditable(true); // Enable editing on the table
    
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
            nomCol.setCellFactory(TextFieldTableCell.forTableColumn());
            nomCol.setOnEditCommit(e -> {
                Specialisation sp = e.getRowValue();
                sp.setSpecialisationNom(e.getNewValue());
            });
    
            TableColumn<Specialisation, Void> actionCol = new TableColumn<>("Actions");
            actionCol.setCellFactory(col -> new TableCell<>() {
                private final Button updateBtn = new Button("Update");
                private final Button deleteBtn = new Button("Delete");
                private final HBox hbox = new HBox(10, updateBtn, deleteBtn);
    
                {
                    updateBtn.setOnAction(e -> {
                        Specialisation current = getTableView().getItems().get(getIndex());
                        updateSpecialisation(current); // Implement this
                    });
    
                    deleteBtn.setOnAction(e -> {
                        Specialisation current = getTableView().getItems().get(getIndex());
                        deleteSpecialisation(current);
                    });
    
                    hbox.setAlignment(Pos.CENTER);
                }
    
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : hbox);
                }
            });
    
            tableData.getColumns().addAll(idCol, nomCol, actionCol);
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
        tableData.setEditable(true);
    
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
            TableColumn<RDV, Void> actionCol = new TableColumn<>("Action");
    
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
                int userId = cellData.getValue().getId_patient();
                String userName = "";
                try {
                    userName = userDao.getName(userId);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return new SimpleStringProperty(userName);
            });
    
            specCol.setCellValueFactory(cellData -> {
                int specId = cellData.getValue().getId_specialiste();
                String specName = "";
                try {
                    specName = speDao.getName(specId);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return new SimpleStringProperty(specName);
            });
    
            DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

            heureCol.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().getDate_rdv().format(displayFormatter))
            );

            heureCol.setCellFactory(TextFieldTableCell.forTableColumn());

            heureCol.setOnEditCommit(e -> {
                RDV rdv = e.getRowValue();
                try {
                    LocalDateTime newDate = LocalDateTime.parse(e.getNewValue(), displayFormatter);
                    rdv.setDate_rdv(newDate);
                } catch (DateTimeParseException ex) {
                    System.out.println("Invalid date format. Please use dd/MM/yy HH:mm");
                    ex.printStackTrace();
                }
            });
    
            noteCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getRating()).asObject());
            noteCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            noteCol.setOnEditCommit(e -> {
                RDV rdv = e.getRowValue();
                rdv.setRating(e.getNewValue().intValue());
            });
    
            descCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getComment()));
            descCol.setCellFactory(TextFieldTableCell.forTableColumn());
            descCol.setOnEditCommit(e -> {
                RDV rdv = e.getRowValue();
                rdv.setComment(e.getNewValue());
            });
    
            actionCol.setCellFactory(col -> new TableCell<RDV, Void>() {
                private final Button updateBtn = new Button("Update");
                private final Button deleteBtn = new Button("Delete");
                private final HBox hbox = new HBox(10, updateBtn, deleteBtn);
    
                {
                    hbox.setAlignment(Pos.CENTER);
    
                    updateBtn.setOnAction(e -> {
                        if (getTableRow() != null && getTableRow().getItem() != null) {
                            RDV current = getTableRow().getItem();
                            updateRdv(current);
                        }
                    });
    
                    deleteBtn.setOnAction(e -> {
                        if (getTableRow() != null && getTableRow().getItem() != null) {
                            RDV current = getTableRow().getItem();
                            deleteRdv(current);
                        }
                    });
                }
    
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : hbox);
                }
            });
    
            tableData.getColumns().addAll(idCol, userCol, specCol, heureCol, noteCol, descCol, actionCol);
            tableData.setItems(data);
    
            DrawApp.drawTableView(getRoot(), tableData, 28, 112, 750, 480);
    
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    

    public void modifLieu(ActionEvent event) {
        LocationDAOImpl locationDaoImpl = new LocationDAOImpl();
        TableView<Location> tableData = new TableView<>();
        tableData.setEditable(true); // Enable editing

        try {
            switchScene("../SceneDesign/admin.fxml", event);

            ResultSet res = locationDaoImpl.getAll();

            TableColumn<Location, Integer> idCol = new TableColumn<>("ID");
            TableColumn<Location, String> adresseCol = new TableColumn<>("Adresse");
            TableColumn<Location, String> villeCol = new TableColumn<>("Ville");
            TableColumn<Location, String> codePostalCol = new TableColumn<>("Code Postal");
            TableColumn<Location, Void> actionCol = new TableColumn<>("Action");

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
            adresseCol.setCellFactory(TextFieldTableCell.forTableColumn());
            adresseCol.setOnEditCommit(e -> {
                Location loc = e.getRowValue();
                loc.setAdress(e.getNewValue());
            });

            villeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCity()));
            villeCol.setCellFactory(TextFieldTableCell.forTableColumn());
            villeCol.setOnEditCommit(e -> {
                Location loc = e.getRowValue();
                loc.setCity(e.getNewValue());
            });

            codePostalCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPostalCode()));
            codePostalCol.setCellFactory(TextFieldTableCell.forTableColumn());
            codePostalCol.setOnEditCommit(e -> {
                Location loc = e.getRowValue();
                loc.setPostalCode(e.getNewValue());
            });

            actionCol.setCellFactory(col -> {
                return new TableCell<Location, Void>() {
                    private final Button updateBtn = new Button("Update");
                    private final Button deleteBtn = new Button("Delete");
                    private final HBox hbox = new HBox(10, updateBtn, deleteBtn);

                    {
                        hbox.setAlignment(Pos.CENTER);

                        updateBtn.setOnAction(e -> {
                            if (getTableRow() != null && getTableRow().getItem() != null) {
                                Location current = getTableRow().getItem();
                                updateLocation(current);
                            }
                        });

                        deleteBtn.setOnAction(e -> {
                            if (getTableRow() != null && getTableRow().getItem() != null) {
                                Location current = getTableRow().getItem();
                                deleteLocation(current);
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(hbox);
                        }
                    }
                };
            });

            tableData.getColumns().addAll(idCol, adresseCol, villeCol, codePostalCol, actionCol);
            tableData.setItems(data);

            DrawApp.drawTableView(getRoot(), tableData, 28, 112, 750, 480);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


    public void logOut() {
        logOut();
    }

}
