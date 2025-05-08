import java.sql.SQLException;

import DAO.*;

import java.sql.ResultSet;


import Models.*;
import Vue.*;


public class Main {
    public static void main(String args[]) {
        DaoFactory.init("info_doctolib", "root", "");
        UserDaoImpl userDao = new UserDaoImpl();
        SpecialistDaoImpl specialistDao = new SpecialistDaoImpl();
        RDVDaoImpl rdvDao = new RDVDaoImpl();
        LieuDaoImpl lieuDao = new LieuDaoImpl();
        LieuDocDaoImpl lieuDocDao = new LieuDocDaoImpl();
        SpecialisationDaoImpl specialisationDao = new SpecialisationDaoImpl();
        SpecialisationDocDaoImpl specialisationDocDao = new SpecialisationDocDaoImpl();

        ResultSet res = userDao.getAll();
        GeneralVue.showOutput(res);
        //User user = new User(11,"Bernard","1234",0);
        //Specialist specialiste = new Specialist(3,"Dr Michmich","1234","Sympatoche",15.2,3.5);
        //specialistDao. //pas encore fait faut discuter avec Matteo Matteo
        RDV rdv = new RDV(4,7,1,null,2,"pas ouf");
        rdvDao.ajouterRDV(rdv);//pb la
        //Lieu lieu = new Lieu(4,"12 Rue de moi","Atonavisfrere","69005");
        //lieuDao.ajouterLieu(lieu);
        //LieuDoc lieuDoc = new LieuDoc(4,1);
        //lieuDocDao.ajouterLieuDoc(lieuDoc);
        //Specialisation dentiste = new Specialisation(1,"dentiste");
        //specialisationDao.ajouterSpecialisation(dentiste);

        SpecialisationDoc specialisationDoc = new SpecialisationDoc(4,32);
        specialisationDocDao.ajouterSpecialisationDoc(specialisationDoc);




        /*int newId = userDao.logIn("bob","1234");
        boolean estcequilestadmin = userDao.verifierSiAdmin(3);
        System.out.println("Admin: "+estcequilestadmin);
        User nouveau_user = new User(2,"jacques","789",0,"non");
        boolean etla = userDao.verifierSiAdmin(nouveau_user);
        System.out.println("Admin 2: "+etla);*/





        //userDao.setById("id_user", 2, "name", "bob2");
        //userDao.delete("id_user", 1);

        try {
            DaoFactory.getConnection().close(); //Close the connection if used
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
