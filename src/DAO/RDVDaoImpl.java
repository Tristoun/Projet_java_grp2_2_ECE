package DAO;
import Models.RDV;

import java.sql.ResultSet;
import java.util.*;

public class RDVDaoImpl extends GeneralDaoImpl implements RDVDao{

    public RDVDaoImpl() {
        super("rdv");
    }

    public void supprimerRDV(RDV rdv) {
        delete("id_rdv", rdv.getIdRdv());
    }

    public void modifierRDV(RDV rdv, String column, Object value) {
        setById("id_rdv",rdv.getIdRdv(),column, rdv);
    }

    public void ajouterRDV(RDV rdv) {
        Map<String, Object> rdv_ajoute = new HashMap<>();
        rdv_ajoute.put("id_rdv", rdv.getIdRdv());
        rdv_ajoute.put("id_useer", rdv.getIdPatient());
        rdv_ajoute.put("id_specialiste", rdv.getIdSpecialiste());
        rdv_ajoute.put("heure", rdv.getDate_rdv());
        rdv_ajoute.put("note", rdv.getRating());
        rdv_ajoute.put("description", rdv.getComment());
        insert(rdv_ajoute);
    }

    public ResultSet returnRDV(int rdvId) {
        return getSpecific("id_rdv", rdvId);
    }

    //public void rechercheRDVParDate()
}