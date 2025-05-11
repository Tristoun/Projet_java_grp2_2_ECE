package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import Models.User;


public class UserDaoImpl extends GeneralDaoImpl{
    
    public UserDaoImpl() {
        super("user");
    }

    /**
     * Login function allows the user to log in or create an account with a username and password
     * @param username the user's username
     * @param password the user's password
     * @return idUser the user's ID in the database, or -1 if login failed
     */
    public User logIn(String username, String password) {
        int id = -1;
        User patient = null;
        try {
            Connection connexion = DaoFactory.getConnection();

            String query = "SELECT * FROM user WHERE name=? AND password=?";
            PreparedStatement statement = connexion.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet res = statement.executeQuery();
            System.out.println(res);
            if (res.next()) {
                id = res.getInt("idUser");
                System.out.println("ID : " + id);
                int admin = res.getInt("admin");
                System.out.println("ADMIN : " + admin);
                patient = new User(id, username, password, admin);
            }
            res.close();
            statement.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(patient);
        return patient;
    }

    public int registerUser(String username, String password) {
        int id = -1;
        if(username == "" || password == "") {
            return id;
        }
        try {
            Connection connexion = DaoFactory.getConnection();

            String query = "SELECT idUser FROM user WHERE name=?";
            PreparedStatement statement = connexion.prepareStatement(query);
            statement.setString(1, username);
            ResultSet res = statement.executeQuery();

            if (!res.next()) {
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("name", username);
                userInfo.put("password", password);
                this.insert(userInfo);
                query = query+" and password=?";
                System.out.println(query);
                statement = connexion.prepareStatement(query);
                statement.setString(1, username);
                statement.setString(2, password);
                res = statement.executeQuery();

                if (res.next()) {
                    id = res.getInt("idUser");
                }
            } else {
                id = -2; //L'utilisateur existe déjà 
            }

            res.close();
            statement.close();
            connexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    
    }

    public void returnProfilPatient(int id_patient){
        getSpecific("idUser", id_patient);
    }

    public void returnAllProfiles() {
        getAll();
    }

    public void editProfileUser(User user){
        setById("idUser", user.getUserId(), "name", user.getUsername());
        setById("idUser", user.getUserId(), "password", user.getPassword());
        setById("idUser", user.getUserId(), "admin", user.getStatus());
    }

    public String getName(int idUser) throws SQLException {
        ResultSet res = getSpecific("idUser", idUser);
        String name = "";
        if(res.next()) {
            try {
                name = res.getString("name");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } 
        return name;
    }

    public void supprimerUser(int id_patient){
        LocationDocDAOImpl locdocdao = new LocationDocDAOImpl();
        SpecialisationDocDAOImpl spedocDao = new SpecialisationDocDAOImpl();
        RDVDaoImpl rdvDao = new RDVDaoImpl();
        SpecialistDaoImpl speDao = new SpecialistDaoImpl();

        ResultSet residSpe = speDao.getSpecific("idUser", id_patient);
        try {
            if(residSpe.next()) {
                int idSpe = residSpe.getInt("idSpecialiste");
                locdocdao.delete("idSpecialiste", idSpe);
                spedocDao.delete("idSpecialiste", idSpe);
                rdvDao.delete("idSpecialiste", idSpe);
                speDao.delete("idSpecialiste", idSpe);
            }
            rdvDao.delete("idUser", id_patient);
            delete("idUser", id_patient);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   public int verifierSiAdmin(User patient){
       int role = patient.getStatus();
       if (role == 1){
           return 1;
       }
       return 0;
   }

}
