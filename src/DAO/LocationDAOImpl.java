package DAO;

import Models.Location;


import java.util.HashMap;
import java.util.Map;
import java.sql.*;
import java.util.ArrayList;

public class LocationDAOImpl extends GeneralDaoImpl implements LocationDAO{
    public LocationDAOImpl() {
        super("lieu"); //?? pas sur
    }

    public void ajouterLocation(Location location){
        Map<String, Object> lieu_ajoute = new HashMap<>();
        lieu_ajoute.put("id_lieu", location.getLocationId());
        lieu_ajoute.put("adresse", location.getAdress());
        lieu_ajoute.put("ville", location.getCity());
        lieu_ajoute.put("code_postal", location.getPostalCode());
        insert(lieu_ajoute);
    }

    public void supprimerLocation(Location location) {
        delete("id_lieu", location.getLocationId());
    }

    public void modifierLocation(Location location, String column, Object value) {
        setById("id_lieu",location.getLocationId(),column, location);
    }

    public ResultSet returnLocation(int locationId) {
        return getSpecific("id_lieu", locationId);
    }

}
