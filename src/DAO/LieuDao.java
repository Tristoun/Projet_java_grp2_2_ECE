package DAO;

import Models.Lieu;

import java.sql.ResultSet;

public interface LieuDao {
    public void ajouterLieu(Lieu lieu);

    public void supprimerLieu(Lieu lieu);

    public void modifierLieu(Lieu lieu, String column, Object value);

    public ResultSet returnLieu(int lieuId);
}
