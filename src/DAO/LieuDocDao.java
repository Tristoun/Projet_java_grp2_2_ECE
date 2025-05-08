package DAO;

import Models.LieuDoc;

import java.sql.ResultSet;

public interface LieuDocDao {
    public void ajouterLieuDoc(LieuDoc lieudoc);
    public void supprimerLieuDoc(int idLieuDoc);
    public void modifierLieuLieuDoc(LieuDoc lieudoc, int idLieu); //ici on modif le lieu
    public void modifierSpecialistLieuDoc(LieuDoc lieudoc, int idSpecialist); //et ici le specialiste
    public ResultSet returnLieuDoc(int lieuDocId);
}
