package DAO;

import java.sql.ResultSet;

public interface SpecialistDao { //il manque le suppr ou on le laisse a user ?
    public void returnProfilSpecialist(int id_specialist);
    
    public ResultSet returnAllProfiles();
    
    public void editProfileSpecialist(int id_specialist, String newName);
}