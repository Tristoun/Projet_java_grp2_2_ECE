import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import DAO.DaoFactory;
import DAO.RDVDaoImpl;
import DAO.SpecialistDaoImpl;
import DAO.UserDaoImpl;
import java.sql.ResultSet;


import Vue.*;


public class Main {
    public static void main(String args[]) {
        DaoFactory.init("info_doctolib", "root", "");
        UserDaoImpl userDao = new UserDaoImpl();
        RDVDaoImpl rdvdao = new RDVDaoImpl("rdv");
        SpecialistDaoImpl spcdao  = new SpecialistDaoImpl();

        ResultSet res = userDao.getAll();
        GeneralVue.showOutput(res);

        Map<String, Object> userbdd = new HashMap<>();
        userbdd.put("name", "michel");
        userbdd.put("password", "4567");
        userDao.insert(userbdd);


        userDao.setById("id_user", 2, "name", "bob2");
        userDao.delete("id_user", 1);
        userDao.editProfileUser(6,"neweditedname");

        spcdao.deleteSpecialist(1);
        userDao.deleteUser(13);

        rdvdao.chercherRDV(1);
        rdvdao.afficherDispos(2);

        try {
            DaoFactory.getConnection().close(); //Close the connection if used
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
