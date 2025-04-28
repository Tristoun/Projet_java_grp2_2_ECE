package DAO;

import Models.Specialist;

public interface SpecialistDao {
    public void ModifierSpecialiste(Specialist personne, String description, String schedule, float tarif);

}