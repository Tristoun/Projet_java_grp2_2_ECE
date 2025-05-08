package DAO;

import Models.LieuDoc;

import java.sql.ResultSet;

public interface LieuDocDAO {
    public void ajouterLieuDoc(LieuDoc lieudoc);
    public void supprimerLieuDoc(int idLieuDoc);
    public void modifierLieuDoc(LieuDoc lieudoc, int idLieu);
    public void modifierSpecialistLieuDoc(LieuDoc lieudoc, int idSpecialist);
    public ResultSet returnLieuDoc(int lieuDocId);
}
