package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public int logIn(String username, String password) {
        int id = -1;

        try {
            Connection connexion = DaoFactory.getConnection();

            String query = "SELECT idUser FROM user WHERE name=? AND password=?";
            PreparedStatement statement = connexion.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet res = statement.executeQuery();

            if (res.next()) {
                id = res.getInt("idUser");
            }
            res.close();
            statement.close();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
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

    public void returnProfilPatient(int idPatient){
        getSpecific("idUser", idPatient);
    }

    public void returnAllProfiles() {
        getAll();
    }

    public void editProfileUser(int idPatient, String newName){
        setById("idUser", idPatient, "name", newName);
    }
    public void supprimerUser(int idPatient){
        delete("id_user", idPatient);
    }

   /* public void SetByID(int idUser, String name, String password){
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("idUser", idUser);
        updateData.put("name", name);
        GeneralDaoImpl.setByID(updateData, 5);
     }

    */
   public boolean verifierSiAdmin(int id_user) {
       ResultSet resultset = getSpecific("idUser", id_user);
       int role = 0;
       try {
           if (resultset.next()) {
               role = resultset.getInt("admin");
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return role == 1;
   }

   public boolean verifierSiAdmin(User user){
       int role = user.getAdmin();
       return role==1;
   }

}
