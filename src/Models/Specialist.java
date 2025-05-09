package Models;

import java.util.HashMap;
import java.util.Map;

public class Specialist {
    private int specialistId;
    private User user;
    private Map<String, Object> schedule;
    private String description;
    private double tarif;
    private double moyenne_note;
    public Specialist(int specialistId, User user, String description, double tarif, double moyenne_note) { //Improve with passing a user of a string
        this.specialistId = specialistId;
        this.user = user;
        this.description = description;
        this.tarif = tarif;
        this.moyenne_note = moyenne_note;
    }

    public String getNameUser() {
        return user.getUsername();
    }

    public User getUser() {
        return this.user;
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

    public void setUsername(String username) {
        this.user.setUsername(username);
    }

}
