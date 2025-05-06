package DAO;

import Models.Specialisation;


import java.util.HashMap;
import java.util.Map;
import java.sql.*;
import java.util.ArrayList;

public class SpecialisationDAOImpl extends GeneralDaoImpl implements SpecialisationDAO{
    public SpecialisationDAOImpl() {
        super("specialisation"); //?? pas sur
    }

    public void ajouterSpecialisation(Specialisation specialisation){
        Map<String, Object> specialisation_ajoute = new HashMap<>();
        specialisation_ajoute.put("id_specialisation", specialisation.getSpecialisationId());
        specialisation_ajoute.put("nom", specialisation.getSpecialisationNom());
        
        insert(specialisation_ajoute);
    }

    public void supprimerSpecialisation(Specialisation specialisation) {
        delete("id_specialisation", specialisation.getSpecialisationId());
    }

    public void modifierSpecialisation(Specialisation specialisation, String column, Object value) {
        setById("id_specialisation",specialisation.getSpecialisationId(),column, value);
    }

    public ResultSet returnSpecialisation(int specialisationId) {
        return getSpecific("id_specialisation", specialisationId);
    }

}
