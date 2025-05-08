package DAO;

import Models.SpecialisationDoc;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class SpecialisationDocDAOImpl extends GeneralDaoImpl implements SpecialisationDocDAO{
    public SpecialisationDocDAOImpl() {
        super("specialisation_doc"); //?? pas sur
    }
    public void ajouterSpecialisationDoc(SpecialisationDoc specialisationdoc){
        Map<String, Object> lieu_doc_ajoute = new HashMap<>();
        lieu_doc_ajoute.put("id_specialisation",specialisationdoc.getIdSpecialisation());
        lieu_doc_ajoute.put("id_specialist",specialisationdoc.getIdSpecialist());
        insert(lieu_doc_ajoute);
    }
    public void supprimerSpecialisationDoc(int idSpecialisationDoc) { //, int idSpecialistDoc
        delete("specialisation_doc",idSpecialisationDoc); //faut changer ca pour que ca prenne en parametre 2 id mais laisser une fonction pour tt effacer qd un specialiste est supprime par exemple
    } //?? donc 3 cas : le lien est suppr mais les 2 existent, le specialiste est suppr, la specialisation est suppr (chacun en solo)

    public void modifierSpecialisationSpecialisationDoc(SpecialisationDoc specialisationdoc, int idSpecialisation) { //le blaze est normal c juste que c Specialisation, ou ca : dans SpecialisationDoc
        setById("id_specialiste",specialisationdoc.getIdSpecialist(),"id_specialisation",idSpecialisation);
    }

    public void modifierSpecialistSpecialisationDoc(SpecialisationDoc specialisationdoc, int idSpecialist) {
        setById("id_specialisation",specialisationdoc.getIdSpecialisation(),"id_specialiste",idSpecialist);
    }

    public ResultSet returnSpecialisationDoc(int specialisationDocId) {
        return getSpecific("id_specialisation", specialisationDocId); //?? modifier pour faire a 2 entrees
    }
}
