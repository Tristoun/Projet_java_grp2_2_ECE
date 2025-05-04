package DAO;

import java.sql.ResultSet;
import java.util.Map;

public interface GeneralDao {
    public void setByID(Map<String, Object> dictionnary, int idValue);
    public ResultSet getSpecific(String columnName, Object value);
    public ResultSet getAll();

    public void insert(Map<String, Object> dictionnary);

    public void delete(String columnName, int value);

    public void setById(String idName, int idValue, String columnName, Object value);
}
