package Models;

public class Specialist extends User {
    private int specialistId;
    private String specialistUsername;
    private String specialistPassword;
    private Map<String, Object> schedule;
    private String description;
    public Specialist(int specialistId, String specialistUsername, String specialistPassword) {
        super(specialistId,specialistUsername,specialistPassword,1); //1 car tjrs specialiste
        this.schedule = = new HashMap<>();
        this.description = "";
    }

    public void setSchedule(ArrayList<String> schedule) {
        this.schedule = schedule;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public ArrayList<String> getSchedule() {
        return schedule;
    }
    public String getDescription() {
        return description;
    }
}
