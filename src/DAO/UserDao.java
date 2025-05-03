package DAO;

public interface UserDao {
    public void returnProfilPatient(int id_patient);
    public void returnAllProfiles();
    public void editProfileUser(int id_patient, String newName);
}
