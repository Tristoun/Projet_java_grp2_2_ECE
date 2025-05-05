package DAO;


public interface SpecialistDao {
    public void returnProfilSpecialist(int id_specialist);
    
    public void returnAllProfiles();
    
    public void editProfileSpecialist(int id_specialist, String newName);
}