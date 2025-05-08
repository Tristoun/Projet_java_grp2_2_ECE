package DAO;

import Models.SpecialisationDoc;

import java.sql.ResultSet;

public interface SpecialisationDocDAO {
    public void ajouterSpecialisationDoc(SpecialisationDoc specialisationdoc);
    public void supprimerSpecialisationDoc(int idSpecialisationDoc);
    public void modifierSpecialisationSpecialisationDoc(SpecialisationDoc specialisationdoc, int idSpecialisation);
    public void modifierSpecialistSpecialisationDoc(SpecialisationDoc specialisationdoc, int idSpecialist);
    public ResultSet returnSpecialisationDoc(int specialisationDocId);
}
