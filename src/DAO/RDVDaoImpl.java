package DAO;
import Models.RDV;

import java.sql.ResultSet;
import java.util.*;

public class RDVDaoImpl extends GeneralDaoImpl implements RDVDao{
    GeneralDaoImpl dao = new GeneralDaoImpl("rdv");

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
    public void chercherRDV(int userID){
        dao.search("id_user", String.valueOf(userID));
    }

    public void afficherDispos(int specialistID){
        dao.getSpecific("id_specialiste", specialistID,"heure");
    }



    public ResultSet returnRDV(int rdvId) {
        return getSpecific("idRdv", rdvId);
    }

    //public void rechercheRDVParDate()
}