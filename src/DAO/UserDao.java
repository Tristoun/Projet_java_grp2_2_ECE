package DAO;

import java.sql.ResultSet;

public interface UserDao {

    public void returnProfilPatient(int id_patient);
    public ResultSet returnAllProfiles();

    public void editProfileUser(int id_patient, String newName);

    public void supprimerUser(int id_patient);

}

