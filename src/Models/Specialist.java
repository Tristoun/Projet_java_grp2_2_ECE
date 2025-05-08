package Models;

import java.util.HashMap;
import java.util.Map;

public class Specialist extends User {
    private int specialistId;
    private String specialistUsername;
    private String specialistPassword;
    private Map<String, Object> schedule;
    private String description;
    private double tarif;
    private double moyenne_note;
    public Specialist(int specialistId, String specialistUsername, String specialistPassword,String description, double tarif, double moyenne_note) {
        super(specialistId,specialistUsername,specialistPassword,1); //1 car tjrs specialiste
        this.schedule = new HashMap<>();
        this.description = description;
        this.tarif = tarif;
        this.moyenne_note = moyenne_note;
    }

    public void setSchedule(Map<String, Object> schedule) {
        this.schedule = schedule;
    }
    public int getIdSpecialist() {
        return specialistId;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Map<String, Object> getSchedule() {
        return schedule;
    }
    public String getDescription() {
        return description;
    }
    public double getTarif() {
        return tarif;
    }
    public double getMoyenne_note() {
        return moyenne_note;
    }
    public void setTarif(double tarif) {
        this.tarif = tarif;
    }
    public void setMoyenne_note(double moyenne_note) {
        this.moyenne_note = moyenne_note;
    }
    public String getSpecialistUsername() {
        return specialistUsername;
    }
}
