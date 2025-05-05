package DAO;

import Models.LocationDoc;

import java.sql.ResultSet;

public interface LocationDocDAO {
    public void ajouterLocationDoc(LocationDoc locationdoc);
    public void supprimerLocationDoc(int idLocationDoc);
    public void modifierLieuLocationDoc(LocationDoc locationdoc, int idLocation);
    public void modifierSpecialistLocationDoc(LocationDoc locationdoc, int idSpecialist);
    public ResultSet returnLocationDoc(int locationDocId);
}
