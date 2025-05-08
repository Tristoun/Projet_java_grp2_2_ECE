package Models;
import java.time.LocalDateTime;


public class RDV {
    protected int idRdv;
    protected int idPatient;
    protected int idSpecialist;
    protected LocalDateTime dateRdv;
    protected double rating;
    protected String comment;

    public RDV (int idRdv, int idPatient, int idSpecialist, LocalDateTime dateRdv, double rating, String comment) {
        this.idRdv = idRdv;
        this.idPatient = idPatient;
        this.idSpecialist = idSpecialist;
        this.dateRdv = dateRdv;
        this.rating = rating;
        this.comment = comment;
    }
    public int getIdRdv() {
        return idRdv;
    }
    public void setIdRdv(int idRdv) {
        this.idRdv = idRdv;
    }
    public int getIdPatient() {
        return idPatient;
    }
    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }
    public int getIdSpecialiste() {
        return idSpecialist;
    }
    public void setIdSpecialiste(int idSpecialist) {
        this.idSpecialist = idSpecialist;
    }
    public LocalDateTime getdateRdv() {
        return dateRdv;
    }
    public void setdateRdv(LocalDateTime dateRdv) {
        this.dateRdv = dateRdv;
    }
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

}
