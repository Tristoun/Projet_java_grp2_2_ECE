package Models;

public class Lieu{
    private int idLieu;
    private String adress;
    private String city;
    private String postalCode;

    public Lieu(int idLieu, String adress, String city, String postalCode) {
        this.idLieu = idLieu;
        this.adress = adress;
        this.city = city;
        this.postalCode = postalCode;
    }
    public int getIdLieu() {
        return idLieu;
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
    public void setIdLieu(int idLieu) {
        this.idLieu = idLieu;
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