package DAO;
import Models.RDV;

import java.sql.ResultSet;
import java.util.*;

public class RDVDaoImpl extends GeneralDaoImpl implements RDVDao{

    public RDVDaoImpl() {
        super("rdv");
    }

    public ResultSet getRdvUser(int idUser) {
        return getSpecific("idUser", idUser);
    }

    public void supprimerRDV(RDV rdv) {
        delete("idRdv", rdv.getId_rdv());
    }

    public void modifierRDV(RDV rdv, String column, Object value) {
        setById("idRdv",rdv.getId_rdv(),column, rdv);
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

<<<<<<< HEAD
    public ResultSet returnRDV(int rdvId) {
        return getSpecific("id_rdv", rdvId);
    }

}
=======
    //public void rechercheRDVParDate()
}
>>>>>>> dev
