package Models;

public class LieuDoc {
    private int idLieu;
    private int idSpecialist;

    public LieuDoc(int idLieu, int idSpecialist) {
        this.idLieu = idLieu;
        this.idSpecialist = idSpecialist;
    }
    public int getIdLieu() {
        return idLieu;
    }
    public int getIdSpecialist() {
        return idSpecialist;
    }
    public void setIdLieuDoc(int idLieu, int idSpecialist) {
        this.idLieu = idLieu;
        this.idSpecialist = idSpecialist;
    }

}
