import java.sql.SQLException;

import DAO.DaoFactory;
import DAO.GeneralDaoImpl;
import DAO.UserDao;
import java.sql.ResultSet;


import Vue.*;


public class Main {
    public static void main(String args[]) {
        DaoFactory dao = DaoFactory.getInstance("info_doctolib", "root", "patapouf");
        UserDao userDao = new UserDao(dao);

        ResultSet res = userDao.getAll();
        GeneralVue.showOutput(res);
    }
}
