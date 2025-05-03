import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import DAO.DaoFactory;
import DAO.GeneralDaoImpl;
import DAO.UserDaoImpl;
import java.sql.ResultSet;


import Vue.*;


public class Main {
    public static void main(String args[]) {
        DaoFactory.init("info_doctolib", "root", "patapouf");
        UserDaoImpl userDao = new UserDaoImpl();

        ResultSet res = userDao.getAll();
        GeneralVue.showOutput(res);

        Map<String, Object> userbdd = new HashMap<>();
        userbdd.put("name", "michel");
        userbdd.put("password", "4567");
        userDao.insert(userbdd);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("userbdd.json"), userbdd);

        System.out.println("JSON saved!");

        userDao.setById("id_user", 2, "name", "bob2");
        userDao.delete("id_user", 1);

        try {
            DaoFactory.getConnection().close(); //Close the connection if used
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
