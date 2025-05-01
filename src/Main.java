import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import DAO.DaoFactory;
import DAO.GeneralDaoImpl;
import DAO.UserDaoImpl;
import java.sql.ResultSet;


import Vue.*;


public class Main {
    public static void main(String args[]) {
        DaoFactory.init("info_doctolib", "root", "");
        UserDaoImpl userDao = new UserDaoImpl();

        ResultSet res = userDao.getAll();
        //GeneralVue.showOutput(res);

        Map<String, Object> userbdd = new HashMap<>();
        userbdd.put("name", "michel");
        userbdd.put("password", "4567");
        //userDao.insert(userbdd);

        userDao.returnProfilPatient(5);

        //UserDAO.SetByID("id_user", 2, "name", "bob2");
        userDao.delete("id_user", 7);
        userDao.returnAllProfiles();
        userDao.editProfileUser(6,"neweditedname");

        try {
            DaoFactory.getConnection().close(); //Close the connection if used
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
