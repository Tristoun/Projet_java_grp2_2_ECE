package DAO;

import Models.Specialisation;

import java.sql.ResultSet;

public interface SpecialisationDao {
    public void ajouterSpecialisation(Specialisation specialisation);

    public void supprimerSpecialisation(Specialisation specialisation);

    public void modifierSpecialisation(Specialisation specialisation, String column, Object value);

    public ResultSet returnSpecialisation(int specialisationId);
}


