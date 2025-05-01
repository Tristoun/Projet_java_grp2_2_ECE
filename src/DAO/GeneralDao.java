package DAO;

import java.sql.ResultSet;
import java.util.Map;

public interface GeneralDao {
    public void setByID(Map<String, Object> dictionnary, int idValue);
    public ResultSet getSpecific(String columnName, Object value);
}
