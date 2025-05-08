package DAO;

import java.sql.ResultSet;

public interface UserDao {

    public void returnProfilPatient(int idPatient);
    public ResultSet returnAllProfiles();
    public void editProfileUser(int idPatient, String newName);

    public void supprimerUser(int idPatient);
}
