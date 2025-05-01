package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory {
    private static String url;
    private static String username;
    private static String password;

    public static void init(String database, String user, String pass) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            url = "jdbc:mysql://localhost:3306/" + database;
            username = user;
            password = pass;
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur de connexion à la base de données");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}