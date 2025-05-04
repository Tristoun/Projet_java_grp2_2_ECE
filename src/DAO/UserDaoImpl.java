package DAO;

import java.util.HashMap;
import java.util.Map;

import Models.User;


public class UserDaoImpl extends GeneralDaoImpl{

    public UserDaoImpl() {
        super("user");
    }

    public void returnProfilPatient(int id_patient){
        getSpecific("id_user", id_patient, "name");
    }

    public void returnAllProfiles() {
        getAll();
    }

    public void editProfileUser(int id_patient, String newName){
        setById("id_user", id_patient, "name", newName);
    }


   /* public void SetByID(int idUser, String name, String password){
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("id_user", idUser);
        updateData.put("name", name);
        GeneralDaoImpl.setByID(updateData, 5);
     }

    */
   public int verifierSiAdmin(User patient){
       int role = patient.getStatus();
       if (role == 2){
           return 1;
       }
       return 0;
   }
}
