package Models;

public class SpecialisationDoc {
    private int id_specialisation;
    private int id_specialist;

    public SpecialisationDoc(int id_specialisation, int id_specialist) {
        this.id_specialisation = id_specialisation;
        this.id_specialist = id_specialist;
    }
    public int getIdSpecialisation() {
        return id_specialisation;
    }
    public int getIdSpecialist() {
        return id_specialist;
    }
    public void setIdSpecialisationDoc(int id_specialisation, int id_specialist) {
        this.id_specialisation = id_specialisation;
        this.id_specialist = id_specialist;
    }
}
