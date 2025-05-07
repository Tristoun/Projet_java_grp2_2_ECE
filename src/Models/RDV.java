package Models;
import java.time.LocalDateTime;


public class RDV {
    protected int id_rdv;
    protected int id_patient;
    protected int id_specialiste;
    protected LocalDateTime date_rdv;
    protected double rating;
    protected String comment;

    public RDV (int id_rdv, int id_patient, int id_specialiste, LocalDateTime date_rdv, double rating, String comment) {
        this.id_rdv = id_rdv;
        this.id_patient = id_patient;
        this.id_specialiste = id_specialiste;
        this.date_rdv = date_rdv;
        this.rating = rating;
        this.comment = comment;
    }
    public int getIdRdv() {
        return id_rdv;
    }
    public void setIdRdv(int id_rdv) {
        this.id_rdv = id_rdv;
    }
    public int getIdPatient() {
        return id_patient;
    }
    public void setIdPatient(int id_patient) {
        this.id_patient = id_patient;
    }
    public int getIdSpecialiste() {
        return id_specialiste;
    }
    public void setIdSpecialiste(int id_specialiste) {
        this.id_specialiste = id_specialiste;
    }
    public LocalDateTime getDate_rdv() {
        return date_rdv;
    }
    public void setDate_rdv(LocalDateTime date_rdv) {
        this.date_rdv = date_rdv;
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
