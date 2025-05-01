package DAO;

import java.util.HashMap;
import java.util.Map;

public class UserDaoImpl extends GeneralDaoImpl{

    public UserDaoImpl() {
        super("user");
    }

    public void returnProfilPatient(int id_patient){
        getSpecific("id_user", id_patient);
    }




   /* public void SetByID(int idUser, String name, String password){
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("id_user", idUser);
        updateData.put("name", name);
        GeneralDaoImpl.setByID(updateData, 5);
     }

    */
}
