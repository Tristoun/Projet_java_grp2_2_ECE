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

    public void ajouterSpecialisation(Map<String, Object> dict){
        insert(dict);
    }

    public void supprimerSpecialisation(Specialisation specialisation) {
        delete("id_specialisation", specialisation.getIdSpecialisation());
    }

    public void modifierSpecialisation(Specialisation specialisation) {
        setById("idSpecialisation",specialisation.getIdSpecialisation(), "nom", specialisation.getSpecialisationNom());
    }

    public ResultSet returnSpecialisation(int specialisationId) {
        return getSpecific("id_specialisation", specialisationId);
    }

    public void deleteSpecialisation(Specialisation specialisation) {
        int id = specialisation.getIdSpecialisation();
        SpecialisationDocDAOImpl specialisationDocDAO = new SpecialisationDocDAOImpl();
        specialisationDocDAO.delete("idSpecialisation", id);
        delete("idSpecialisation", id);
    }
}
