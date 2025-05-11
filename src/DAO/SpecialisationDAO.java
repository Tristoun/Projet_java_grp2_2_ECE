package DAO;

import Models.Specialisation;

import java.sql.ResultSet;
import java.util.Map;

public interface SpecialisationDAO {
    public void ajouterSpecialisation(Map<String, Object> dict);

    public void supprimerSpecialisation(Specialisation specialisation);

    public void modifierSpecialisation(Specialisation specialisation);

    public ResultSet returnSpecialisation(int specialisationId);
}
