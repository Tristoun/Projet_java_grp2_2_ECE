package DAO;

import Models.LieuDoc;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class LieuDocDaoImpl extends GeneralDaoImpl implements LieuDocDao {
    public LieuDocDaoImpl() {
        super("lieu_specialiste"); //?? pas sur
    }
    public void ajouterLieuDoc(LieuDoc lieudoc){
        Map<String, Object> lieu_doc_ajoute = new HashMap<>();
        lieu_doc_ajoute.put("idLieu",lieudoc.getIdLieu());
        lieu_doc_ajoute.put("idSpecialiste",lieudoc.getIdSpecialist());
        insert(lieu_doc_ajoute);
    }
    public void supprimerLieuDoc(int idLieuDoc){
        delete("lieu_specialiste",idLieuDoc);
    }

    public void modifierLieuLieuDoc(LieuDoc lieudoc, int idLieu) {
        setById("idSpecialiste",lieudoc.getIdSpecialist(),"idLieu",idLieu);
    }

    public void modifierSpecialistLieuDoc(LieuDoc lieudoc, int idSpecialist) {
        setById("idLieu",lieudoc.getIdLieu(),"idSpecialiste",idSpecialist);
    }

    public ResultSet returnLieuDoc(int lieuDocId) {
        return getSpecific("idLieu", lieuDocId); //?? est ce que le this va marcher aussi si c le meme nom "idLieu" ? Je pense que oui mais go check
    }
}
