package Models;

public class SpecialisationDoc {
    private int idSpecialisation;
    private int idSpecialist;

    public SpecialisationDoc(int idSpecialisation, int idSpecialist) {
        this.idSpecialisation = idSpecialisation;
        this.idSpecialist = idSpecialist;
    }
    public int getIdSpecialist() {
        return idSpecialist;
    }
    public int getIdSpecialisation() {
        return idSpecialisation;
    }

    public void setIdSpecialisationDoc(int idSpecialisation, int idSpecialist) {
        this.idSpecialisation = idSpecialisation;
        this.idSpecialist = idSpecialist;
    }
}
