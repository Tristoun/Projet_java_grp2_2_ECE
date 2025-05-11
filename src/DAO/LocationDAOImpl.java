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

    public void modifierLocation(Location location) {
        int id = location.getLocationId();
        setById("idLieu",id,"adresse", location.getAdress());
        setById("idLieu",id,"ville", location.getCity());
        setById("idLieu",id,"code_postal", location.getPostalCode());
    }

    public ResultSet returnLocation(int locationId) {
        return getSpecific("id_lieu", locationId);
    }

    public void deleteLocation(Location loc) {
        int idLoc = loc.getLocationId();
        LocationDocDAOImpl locationDocDAOImpl = new LocationDocDAOImpl();
        locationDocDAOImpl.delete("idLieu", idLoc);
        delete("idLieu", idLoc);
    }
}
