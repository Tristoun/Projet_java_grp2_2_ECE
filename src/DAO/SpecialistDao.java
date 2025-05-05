package DAO;


public interface SpecialistDao { //il manque le suppr ou on le laisse a user ?
    public void returnProfilSpecialist(int id_specialist);
    
    public void returnAllProfiles();
    
    public void editProfileSpecialist(int id_specialist, String newName);
}