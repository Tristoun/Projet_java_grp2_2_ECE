package Models;

public class Specialisation {
    private int id_specialisation;
    private String nom;

    public Specialisation(int id_specialisation, String nom) {
        this.id_specialisation = id_specialisation;
        this.nom = nom;
    }

    public int getIdSpecialisation() {
        return id_specialisation;
    }

    public String getSpecialisationNom() {
        return nom;
    }

    public void setIdSpecialisation(int id_specialisation) {
        this.id_specialisation = id_specialisation;
    }

    public void setSpecialisationNom(String nom) {
        this.nom = nom;
    }
}
