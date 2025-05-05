import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import DAO.DaoFactory;
import DAO.GeneralDaoImpl;
import DAO.UserDaoImpl;
import DAO.RDVDaoImpl;
import java.sql.ResultSet;


import Models.RDV;
import Models.Specialist;
import Models.User;
import Vue.*;


public class Main {
    public static void main(String args[]) {
        DaoFactory.init("info_doctolib", "root", "patapouf");
        UserDaoImpl userDao = new UserDaoImpl();

        ResultSet res = userDao.getAll();
        GeneralVue.showOutput(res);
        User user = new User(2,"Bernard","1234",0);
        Specialist specialiste = new Specialist(3,"Dr Michmich","1234","Sympatoche",15.2,3.5);
        RDV rdv = new RDV(1,2,3,null,2,"pas ouf"); //Le rating on l ajoutera apres


        userDao.setById("id_user", 2, "name", "bob2");
        userDao.delete("id_user", 1);

        try {
            DaoFactory.getConnection().close(); //Close the connection if used
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
