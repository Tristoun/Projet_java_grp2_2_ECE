package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Set;
import java.lang.String;


public class GeneralDaoImpl {
    private String table;

    public GeneralDaoImpl(String table) {
        this.table = table;
    }

    public ResultSet getAll() {
        ResultSet res = null;
        try {
            Connection connexion = DaoFactory.getConnection();
            Statement statement = connexion.createStatement();

            res = statement.executeQuery("select * from " + this.table);        
            if(res == null) {
                System.out.println("No values");
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }

        return res; 
    }

    public ResultSet getSpecific(String columnName, Object value, String ColumnToGet) {
        ResultSet res = null;
        try {
            Connection connexion = DaoFactory.getConnection();

            String query = "SELECT * FROM " + this.table + " WHERE " + columnName + " = ?";
            System.out.println(query);

            PreparedStatement statement = connexion.prepareStatement(query);
            statement.setObject(1, value);

            res = statement.executeQuery();
            if(res == null) {
                System.out.println("No values");

            }else {
                while (res.next()) {
                    Object result = res.getObject(ColumnToGet);
                    System.out.println(ColumnToGet + ":" + result);
                }

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return res; 
    }

    public ResultSet getSpecificFromTable(String columnName, Object value, String ColumnToGet, String tableName) {
        ResultSet res = null;
        Object result = null;
        try {
            Connection connexion = DaoFactory.getConnection();
            String query = "SELECT " + ColumnToGet +" FROM " + tableName + " WHERE " + columnName + " = ?";
            System.out.println(query);
            PreparedStatement statement = connexion.prepareStatement(query);
            statement.setObject(1, value);

            res = statement.executeQuery();  // Initialize the ResultSet with the query result


            if (res.next()) {
                result = res.getObject(ColumnToGet);
                System.out.println("FOUND : " + result);
            } else {
                System.out.println("Nothing found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }


    /**
     * Insérer un dictionnaire dans la bdd, Map est un type permettant de créer un "dictionnaire" comme en python
     * @params : On passe en type de Map le type de "key" et le type de la valeur
     * Le type Object représente toutes les types on peut donc mettre String, int, Date() ....
     */
    public void insert(Map<String, Object> dictionnary) { 
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        Set<String> keys = dictionnary.keySet();
        int index = 0;

        for(String keysColumn : keys) {
            columns.append(keysColumn);
            values.append("?");

            if(index < keys.size()-1) {
                columns.append(", ");
                values.append(", ");
                index++;
            }
        } 

        String query = "INSERT INTO "+this.table+"("+columns.toString()+") VALUES ("+values.toString()+")";
        System.out.println(query); //Affiche le résultat de la query  !!! A RETIRER

        try {
            Connection connexion = DaoFactory.getConnection();
            PreparedStatement statement = connexion.prepareStatement(query);

            int pos = 1;
            for(String keyString : keys) {
                statement.setObject(pos, dictionnary.get(keyString)); //On utilise setObject car on se sait pas qu'elle type de valeur on rentre, on s'adapte
                pos++;
            }

            int res = statement.executeUpdate();
            if(res != 1) {
                System.out.println("Error while insert values");
            }
        } catch(SQLException e) {
            e.getStackTrace();
        }
    }

    public void insertInOtherTable(Map<String, Object> dictionnary, String table) {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        Set<String> keys = dictionnary.keySet();
        int index = 0;

        for(String keysColumn : keys) {
            columns.append(keysColumn);
            values.append("?");

            if(index < keys.size()-1) {
                columns.append(", ");
                values.append(", ");
                index++;
            }
        }

        String query = "INSERT INTO "+ table +" ( "+columns.toString()+") VALUES ("+values.toString()+")";
        System.out.println(query); //Affiche le résultat de la query  !!! A RETIRER

        try {
            Connection connexion = DaoFactory.getConnection();
            PreparedStatement statement = connexion.prepareStatement(query);

            int pos = 1;
            for(String keyString : keys) {
                statement.setObject(pos, dictionnary.get(keyString)); //On utilise setObject car on se sait pas qu'elle type de valeur on rentre, on s'adapte
                pos++;
            }

            int res = statement.executeUpdate();
            if(res != 1) {
                System.out.println("Error while insert values");
            }
        } catch(SQLException e) {
            e.getStackTrace();
        }
    }

    /**
     * Delete by id a column 
     * Warning : Caution with foreign key in case of link
     * @param columnName nom de la colonne 
     * @param value valeur 
     */
    public void delete(String columnName, int value) {
        try {
            Connection connextion = DaoFactory.getConnection(); //relisez cette ligne et quand vous trouvez le probleme c win
            String query = "DELETE FROM "+this.table+"WHERE "+columnName+"=?";
            PreparedStatement statement = connextion.prepareStatement(query);
            statement.setInt(1, value);
            statement.executeUpdate();

        } catch(SQLException e) {
            e.getStackTrace();
        }
    }

    public void deleteFromTable(String columnName, int value, String tableName) {
        try {
            Connection connextion = DaoFactory.getConnection();
            String query = "DELETE FROM " + tableName + " WHERE " + columnName + " = ? ";
            PreparedStatement statement = connextion.prepareStatement(query);
            statement.setInt(1, value);
            statement.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set an element in the table (must be change to enable multiple set with Map)
     * @param columnName name of the column
     * @param value
     */
    public void setById(String idName, int idValue, String columnName, Object value) {
        try {
            Connection connexion = DaoFactory.getConnection();
            String query = "UPDATE "+this.table+" SET "+columnName+"=? WHERE "+idName+"=?";
            PreparedStatement statement = connexion.prepareStatement(query);
            statement.setObject(1, value);
            statement.setInt(2, idValue);
            statement.executeUpdate();
        } catch(SQLException e) {
            e.getStackTrace();
        }
    }


    public void setByIdOtherTable(String idName, Object idValue, String columnName, Object value, Object tableName) {
        try {
            Connection connexion = DaoFactory.getConnection();
            String query = "UPDATE " + tableName + " SET "+columnName + " = ? WHERE " + idName + " = ? ";
            PreparedStatement statement = connexion.prepareStatement(query);
            statement.setObject(1, value);
            statement.setInt(2, (Integer) idValue);
            statement.executeUpdate();
        } catch(SQLException e) {
            e.getStackTrace();
        }
    }


/* Fonction search écrite, mais non utilisée dans une branche
    public Object search(String columnName, String value) {
        Object return_object = null;
        try {
            Connection connexion = DaoFactory.getConnection();
            String query = "SELECT * FROM "+ this.table + " WHERE " + columnName + " LIKE " + value;
            System.out.println(query);
            PreparedStatement statement = connexion.prepareStatement(query);
            //statement.setObject(1, columnName); //celui la c etat pour remplacer * et avoir que le blaze mais c pas fou
            //statement.setInt(1, value); ca c pour preparer la requete mais il veut un int et pas un string
            ResultSet res = statement.executeQuery();
            return_object = res;

            while (res.next()) {
                int nombreColonne = res.getMetaData().getColumnCount();
                for (int i = 1; i <= nombreColonne; i++) {
                    String nomColonne = res.getMetaData().getColumnName(i);
                    Object valeurColonne = res.getObject(i);
                    System.out.print(nomColonne + ": " + valeurColonne + "/");
                }
                System.out.println();
            }
            */

    public ResultSet search(String columnName, String value) {
        ResultSet return_object = null;
        try {
            Connection connexion = DaoFactory.getConnection();
            String query = "SELECT * FROM " + this.table + " WHERE " + columnName + " LIKE '" + value + "%'";
            PreparedStatement statement = connexion.prepareStatement(query);
            //statement.setObject(1, columnName); //celui la c etat pour remplacer * et avoir que le blaze mais c pas fou
            //statement.setInt(1, value); ca c pour preparer la requete mais il veut un int et pas un string
            return_object = statement.executeQuery();

        } catch(SQLException e) {
            e.getStackTrace();
        }
        return return_object;
    }

    public int insertSomething(String ColumnName, Object value, String ColumnToGet, String TableName) {
        ResultSet res = null;
        int res2 = -10;
        ResultSet res3 = null;
        int res4 = 0;
        try {
            Connection connexion = DaoFactory.getConnection();
            String query = "SELECT * FROM " + TableName + " WHERE " + ColumnName + " = ?";
            PreparedStatement query_prepared = connexion.prepareStatement(query);

            System.out.println(query);
            query_prepared.setObject(1, value);

            res = query_prepared.executeQuery();
            if(!res.next()) {
                System.out.println("No value found - need to insert new one and retrieve id");
                String query2 = "INSERT INTO " + TableName + " ( " + ColumnName + " ) VALUES (?)";
                System.out.println(query2);
                PreparedStatement query_prepared2 = connexion.prepareStatement(query2);
                query_prepared2.setObject(1, value);

                res2 = query_prepared2.executeUpdate();
            }

            String query3 = " SELECT " + ColumnToGet + " FROM " + TableName + " WHERE " + ColumnName + " = ?" ;
            System.out.println(query3);
            PreparedStatement query_prepared3 = connexion.prepareStatement(query3);
            query_prepared3.setObject(1, value);
            res3 = query_prepared3.executeQuery();

            while (res3.next()) {
                res4 = (int) res3.getObject(ColumnToGet);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res4;
    }
}
