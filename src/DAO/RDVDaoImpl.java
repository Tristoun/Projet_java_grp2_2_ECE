package DAO;
import Models.RDV;

import java.sql.ResultSet;
import java.util.*;

public class RDVDaoImpl extends GeneralDaoImpl implements RDVDao{

    public RDVDaoImpl() {
        super("rdv");
    }

    public void supprimerRDV(RDV rdv) {
        delete("idRdv", rdv.getIdRdv());
    }

    public void modifierRDV(RDV rdv, String column, Object value) {
        setById("idRdv",rdv.getIdRdv(),column, rdv);
    }

    public void ajouterRDV(RDV rdv) {
        Map<String, Object> rdv_ajoute = new HashMap<>();
        rdv_ajoute.put("idRdv", rdv.getIdRdv());
        rdv_ajoute.put("idUser", rdv.getIdPatient());
        rdv_ajoute.put("idSpecialiste", rdv.getIdSpecialiste());
        rdv_ajoute.put("heure", rdv.getdateRdv());
        rdv_ajoute.put("description", rdv.getComment());
        rdv_ajoute.put("note", rdv.getRating());

        insert(rdv_ajoute);
    }

    public ResultSet returnRDV(int rdvId) {
        return getSpecific("idRdv", rdvId);
    }

    //public void rechercheRDVParDate()
}