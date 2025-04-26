package Models;

public class Location{
    private int locationId;
    private String adress;
    private String city;
    private String postalCode;

    public Location(int locationId, adress, String city, String postalCode) {
        this.locationId = locationId;
        this.adress = adress;
        this.city = city;
        this.postalCode = postalCode;
    }
    public int getLocationId() {
        return locationId;
    }
    public String getAdress() {
        return adress;
    }
    public String getCity() {
        return city;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
    public void setAdress(String adress) {
        this.adress = adress;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}