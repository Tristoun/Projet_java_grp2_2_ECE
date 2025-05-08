package DAO;

import Models.Specialisation;


import java.util.HashMap;
import java.util.Map;
import java.sql.*;

public class SpecialisationDaoImpl extends GeneralDaoImpl implements SpecialisationDao {
    public SpecialisationDaoImpl() {
        super("specialisation"); //?? pas sur
    }

    public void ajouterSpecialisation(Specialisation specialisation){
        Map<String, Object> specialisation_ajoute = new HashMap<>();
        specialisation_ajoute.put("idSpecialisation", specialisation.getIdSpecialisation());
        specialisation_ajoute.put("nom", specialisation.getSpecialisationNom());
        
        insert(specialisation_ajoute);
    }

    public void supprimerSpecialisation(Specialisation specialisation) {
        delete("idSpecialisation", specialisation.getIdSpecialisation());
    }

    public void modifierSpecialisation(Specialisation specialisation, String column, Object value) {
        setById("idSpecialisation",specialisation.getIdSpecialisation(),column, value);
    }

    public ResultSet returnSpecialisation(int specialisationId) {
        return getSpecific("idSpecialisation", specialisationId);
    }

}
