package DAO;

import Models.Specialisation;

import java.sql.ResultSet;

public interface SpecialisationDAO {
    public void ajouterSpecialisation(Specialisation specialisation);

    public void supprimerSpecialisation(Specialisation specialisation);

    public void modifierSpecialisation(Specialisation specialisation);

    public ResultSet returnSpecialisation(int specialisationId);
}
