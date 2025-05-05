package Models;

public class LocationDoc {
    private int id_location;
    private int id_specialist;

    public LocationDoc(int id_location, int id_specialist) {
        this.id_location = id_location;
        this.id_specialist = id_specialist;
    }
    public int getId_location() {
        return id_location;
    }
    public int getId_specialist() {
        return id_specialist;
    }
    public void setId_location_doc(int id_location, int id_specialist) {
        this.id_location = id_location;
        this.id_specialist = id_specialist;
    }

}
