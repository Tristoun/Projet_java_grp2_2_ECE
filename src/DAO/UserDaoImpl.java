package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

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

            String query = "SELECT id_user FROM user WHERE name=? AND password=?";
            PreparedStatement statement = connexion.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet res = statement.executeQuery();

            if (!res.next()) {
                System.out.println("No existing user found. Creating new one...");
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("name", username);
                userInfo.put("password", password);
                this.insert(userInfo);

                statement = connexion.prepareStatement(query);
                statement.setString(1, username);
                statement.setString(2, password);
                res = statement.executeQuery();

                // Check again after insertion
                if (res.next()) {
                    id = res.getInt("id_user");
                }
            } else {
                id = res.getInt("id_user");
            }

            res.close();
            statement.close();
            connexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }
}
