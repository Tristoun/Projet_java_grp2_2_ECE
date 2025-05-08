package Models;

public class Specialisation {
    private int idSpecialisation;
    private String nom;

    public Specialisation(int idSpecialisation, String nom) {
        this.idSpecialisation = idSpecialisation;
        this.nom = nom;
    }

    public int getIdSpecialisation() {
        return idSpecialisation;
    }

    public String getSpecialisationNom() {
        return nom;
    }

    public void setIdSpecialisation(int idSpecialisation) {
        this.idSpecialisation = idSpecialisation;
    }

    public void setSpecialisationNom(String nom) {
        this.nom = nom;
    }
}
