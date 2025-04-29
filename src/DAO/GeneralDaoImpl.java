package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Set;

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
            if (res == null) {
                System.out.println("No values");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res; //Close the connection after using the data in controller
    }

    /**
     * Insérer un dictionnaire dans la bdd, Map est un type permettant de créer un "dictionnaire" comme en python
     *
     * @params : On passe en type de Map le type de "key" et le type de la valeur
     * Le type Object représente toutes les types on peut donc mettre String, int, Date() ....
     */
    public void insert(Map<String, Object> dictionnary) {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        Set<String> keys = dictionnary.keySet();
        int index = 0;

        for (String keysColumn : keys) {
            columns.append(keysColumn);
            values.append("?");

            if (index < keys.size() - 1) {
                columns.append(", ");
                values.append(", ");
                index++;
            }
        }

        String query = "INSERT INTO " + this.table + "(" + columns.toString() + ") VALUES (" + values.toString() + ")";
        System.out.println(query); //Affiche le résultat de la query  !!! A RETIRER

        try {
            Connection connexion = this.dao.getConnection();
            PreparedStatement statement = connexion.prepareStatement(query);

            int pos = 1;
            for (String keyString : keys) {
                statement.setObject(pos, dictionnary.get(keyString)); //On utilise setObject car on se sait pas qu'elle type de valeur on rentre, on s'adapte
                pos++;
            }

            int res = statement.executeUpdate();
            if (res != 1) {
                System.out.println("Error while insert values");
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    /**
     * Delete by id a column
     * Warning : Caution with foreign key in case of link
     *
     * @param columnName nom de la colonne
     * @param value      valeur
     */
    public void delete(String columnName, int value) {
        try {
            Connection connextion = this.dao.getConnection();
            String query = "DELETE FROM " + this.table + "WHERE " + columnName + "=?";
            PreparedStatement statement = connextion.prepareStatement(query);
            statement.setInt(1, value);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    /**
     * Set an element in the table (must be change to enable multiple set with Map)
     *
     * @param columnName name of the column
     * @param value
     */

    public void setByID(Map<String, Object> dictionnary, int idValue) {
        StringBuilder requestString = new StringBuilder();

        Set<String> keys = dictionnary.keySet();
        int index = 0;

        for (String keyColumn : keys) {
            requestString.append(keyColumn);
            requestString.append(" = ?");


            if (index < keys.size() - 1) {
                requestString.append(", ");
            }
            index++;
        }


        String query = "UPDATE " + this.table + " SET " + requestString.toString() + " WHERE id = ?";
        System.out.println(query);

        try {
            Connection connexion = this.dao.getConnection();
            PreparedStatement statement = connexion.prepareStatement(query);


            int pos = 1;
            for (String keyString : keys) {
                statement.setObject(pos, dictionnary.get(keyString));
                pos++;
            }


            statement.setObject(pos, idValue);


            int res = statement.executeUpdate();
            if (res != 1) {
                System.out.println("Error while updating values");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}