package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GeneralDaoImpl {
    private DaoFactory dao;
    private String table;

    public GeneralDaoImpl(DaoFactory dao, String table) {
        this.dao = dao;
        this.table = table;
    }

    public ResultSet getAll() {
        ResultSet res = null;
        try {
            Connection connexion = this.dao.getConnection();
            Statement statement = connexion.createStatement();

            // récupération des produits de la base de données avec la requete SELECT
            res = statement.executeQuery("select * from " + this.table);        
            if(res == null) {
                System.out.println("No values");
            }    
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
