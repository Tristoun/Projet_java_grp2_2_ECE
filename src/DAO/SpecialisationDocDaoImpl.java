package DAO;

import Models.SpecialisationDoc;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class SpecialisationDocDaoImpl extends GeneralDaoImpl implements SpecialisationDocDao {
    public SpecialisationDocDaoImpl() {
        super("specialisation_doc"); //?? pas sur
    }
    public void ajouterSpecialisationDoc(SpecialisationDoc specialisation_doc){
        Map<String, Object> specialisation_doc_ajoute = new HashMap<>();
        specialisation_doc_ajoute.put("idSpecialiste",specialisation_doc.getIdSpecialist());
        specialisation_doc_ajoute.put("idSpecialisation",specialisation_doc.getIdSpecialisation());
        System.out.println("Insertion : " + specialisation_doc_ajoute);

        insert(specialisation_doc_ajoute);
    }

    public void supprimerSpecialisationDoc(int idSpecialisationDoc) { //, int idSpecialistDoc
        delete("specialisation_doc",idSpecialisationDoc); //faut changer ca pour que ca prenne en parametre 2 id mais laisser une fonction pour tt effacer qd un specialiste est supprime par exemple
    } //?? donc 3 cas : le lien est suppr mais les 2 existent, le specialiste est suppr, la specialisation est suppr (chacun en solo)

    public void modifierSpecialisationSpecialisationDoc(SpecialisationDoc specialisationdoc, int idSpecialisation) { //le blaze est normal c juste que c Specialisation, ou ca : dans SpecialisationDoc
        setById("idSpecialiste",specialisationdoc.getIdSpecialist(),"idSpecialisation",idSpecialisation);
    }

    public void modifierSpecialistSpecialisationDoc(SpecialisationDoc specialisationdoc, int idSpecialist) {
        setById("idSpecialisation",specialisationdoc.getIdSpecialisation(),"idSpecialiste",idSpecialist);
    }

    public ResultSet returnSpecialisationDoc(int specialisationDocId) {
        return getSpecific("idSpecialisation", specialisationDocId); //?? modifier pour faire a 2 entrees
    }
}
