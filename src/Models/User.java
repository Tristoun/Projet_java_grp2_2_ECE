package Models;

public class User{
    private int userId;
    private String username;
    private String password;
    private int admin; //0=user lambda, 1=specialiste, 2 = admin
    public User(int userId, String username, String password, int admin) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.admin = admin;
    }
    public int getIdUser() {
        return userId;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public int getAdmin() {
        return admin;
    }
    public void setIdUser(int userId) {
        this.userId = userId;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
