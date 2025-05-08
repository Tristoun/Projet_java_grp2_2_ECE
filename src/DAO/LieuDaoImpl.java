package DAO;

import Models.Lieu;


import java.util.HashMap;
import java.util.Map;
import java.sql.*;

public class LieuDaoImpl extends GeneralDaoImpl implements LieuDao {
    public LieuDaoImpl() {
        super("lieu"); //?? pas sur
    }

    public void ajouterLieu(Lieu lieu){
        Map<String, Object> lieu_ajoute = new HashMap<>();
        lieu_ajoute.put("idLieu", lieu.getIdLieu());
        lieu_ajoute.put("adresse", lieu.getAdress());
        lieu_ajoute.put("ville", lieu.getCity());
        lieu_ajoute.put("code_postal", lieu.getPostalCode());
        insert(lieu_ajoute);
    }

    public void supprimerLieu(Lieu lieu) {
        delete("idLieu", lieu.getIdLieu());
    }

    public void modifierLieu(Lieu lieu, String column, Object value) {
        setById("idLieu",lieu.getIdLieu(),column, value);
    }

    public ResultSet returnLieu(int lieuId) {
        return getSpecific("idLieu", lieuId);
    }

}
