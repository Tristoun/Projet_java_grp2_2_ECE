package DAO;
import Models.RDV;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class RDVDaoImpl extends GeneralDaoImpl implements RDVDao{

    public RDVDaoImpl() {
        super("rdv");
    }

    public ResultSet getRdvUser(int idUser) {
        return getSpecific("idUser", idUser);
    }

    public ResultSet getNextRdv(int idUser) {
        String query = "SELECT * FROM rdv WHERE heure > NOW() and idUser = ? ORDER BY heure ASC LIMIT 1";
        ResultSet res = null;
        try {
            Connection conn = DaoFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idUser);
            res = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    
    public void supprimerRDV(RDV rdv) {
        delete("idRdv", rdv.getId_rdv());
    }

    public void modifierRDV(RDV rdv) {
        int id = rdv.getId_rdv();
        setById("idRdv", id, "heure", rdv.getDate_rdv());
        setById("idRdv", id, "note", rdv.getRating());
        setById("idRdv", id, "description", rdv.getComment());
    }

    public void ajouterRDV(RDV rdv) {
        Map<String, Object> rdv_ajoute = new HashMap<>();
        rdv_ajoute.put("idRdv", rdv.getId_rdv());
        rdv_ajoute.put("idUser", rdv.getId_patient());
        rdv_ajoute.put("idSpecialiste", rdv.getId_specialiste());
        rdv_ajoute.put("heure", rdv.getDate_rdv());
        rdv_ajoute.put("rating", rdv.getRating());
        rdv_ajoute.put("comment", rdv.getComment());
        insert(rdv_ajoute);
    }

    public ResultSet returnRDV(int rdvId) {
        return getSpecific("id_rdv", rdvId);
    }

}


