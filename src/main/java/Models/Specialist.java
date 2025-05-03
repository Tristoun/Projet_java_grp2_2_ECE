package Models;

import java.util.HashMap;
import java.util.Map;

public class Specialist extends User {
    private int specialistId;
    private String specialistUsername;
    private String specialistPassword;
    private Map<String, Object> schedule;
    private String description;
    public Specialist(int specialistId, String specialistUsername, String specialistPassword) {
        super(specialistId,specialistUsername,specialistPassword,1); //1 car tjrs specialiste
        this.schedule = new HashMap<>();
        this.description = "";
    }

    public void setSchedule(Map<String, Object> schedule) {
        this.schedule = schedule;
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
}
