package DAO;
import Models.RDV;

import java.util.*;

public class RDVDaoImpl extends GeneralDaoImpl implements RDVDao{

    public RDVDaoImpl(String table) {
        super(table);
    }

    public void supprimerRDV(RDV rdv) {
        delete("id_rdv", rdv.getId_rdv());
    }
    public void modifierRDV(RDV rdv, String column, Object value) {
        setById("id_rdv",rdv.getId_rdv(),column, rdv);
    }
    public void ajouterRDV(RDV rdv) {
        Map<String, Object> rdv_ajoute = new HashMap<>();
        rdv_ajoute.put("id_rdv", rdv.getId_rdv());
        rdv_ajoute.put("id_patient", rdv.getId_patient());
        rdv_ajoute.put("id_specialiste", rdv.getId_specialiste());
        rdv_ajoute.put("date_rdv", rdv.getDate_rdv());
        rdv_ajoute.put("rating", rdv.getRating());
        rdv_ajoute.put("comment", rdv.getComment());
        insert(rdv_ajoute);
    }


}
