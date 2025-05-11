package DAO;

import Models.Location;
import Models.LocationDoc;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class LocationDocDAOImpl extends GeneralDaoImpl implements LocationDocDAO{
    public LocationDocDAOImpl() {
        super("lieu_specialiste"); //?? pas sur
    }
    public void ajouterLocationDoc(LocationDoc locationdoc){
        Map<String, Object> lieu_doc_ajoute = new HashMap<>();
        lieu_doc_ajoute.put("id_lieu",locationdoc.getIdLocation());
        lieu_doc_ajoute.put("id_specialist",locationdoc.getIdSpecialist());
        insert(lieu_doc_ajoute);
    }
    public void supprimerLocationDoc(int idLocationDoc){
        delete("lieu_specialiste",idLocationDoc);
    }

    public void modifierLieuLocationDoc(LocationDoc locationdoc, int idLocation) {
        setById("idSpecialiste",locationdoc.getIdSpecialist(),"id_lieu",idLocation);
    }

    public void modifierSpecialistLocationDoc(LocationDoc locationdoc, int idSpecialist) {
        setById("idLieu",locationdoc.getIdLocation(),"id_specialiste",idSpecialist);
    }

    public ResultSet returnLocationDoc(int idSpe) {
        return getSpecific("idSpecialiste", idSpe); 
    }
}
