package Models;

public class Specialisation {
    private int id_specialisation;
    private String nom;

    public Specialisation(int id_specialisation, String nom) {
        this.id_specialisation = id_specialisation;
        this.nom = nom;
    }

    public int getId_specialisation() {
        return id_specialisation;
    }

    public String getNom() {
        return nom;
    }

    public void setId_specialisation(int id_specialisation) {
        this.id_specialisation = id_specialisation;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
