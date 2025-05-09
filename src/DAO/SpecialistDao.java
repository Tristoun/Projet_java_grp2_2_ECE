package DAO;

import java.sql.ResultSet;

import Models.Specialist;


public interface SpecialistDao {
    public void returnProfilSpecialist(int id_specialist);
    
    public ResultSet returnAllProfiles();
    
    public void editProfileSpecialist(Specialist specialist);
}