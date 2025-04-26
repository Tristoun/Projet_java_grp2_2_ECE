package Models;

public class User{
    private int userId;
    private String username;
    private String password;
    private int status; //0=user lambda, 1=specialiste, 2 = admin
    public SpecialistDao(int userId, String username, String password, int status) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.status = status;
    }
    public int getUserId() {
        return userId;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public int getStatus() {
        return status;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
