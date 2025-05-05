package Models;

public class SpecialisationDoc {
    private int id_specialisation;
    private int id_specialist;

    public SpecialisationDoc(int id_specialisation, int id_specialist) {
        this.id_specialisation = id_specialisation;
        this.id_specialist = id_specialist;
    }
    public int getId_specialisation() {
        return id_specialisation;
    }
    public int getId_specialist() {
        return id_specialist;
    }
    public void setId_specialisation_doc(int id_specialisation, int id_specialist) {
        this.id_specialisation = id_specialisation;
        this.id_specialist = id_specialist;
    }
}
