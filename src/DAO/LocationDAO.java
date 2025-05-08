package DAO;

import Models.Location;

import java.sql.ResultSet;

public interface LocationDAO {
    public void ajouterLocation(Location location);

    public void supprimerLocation(Location location);

    public void modifierLocation(Location location, String column, Object value);

    public ResultSet returnLocation(int locationId);
}
