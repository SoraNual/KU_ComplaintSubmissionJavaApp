package ku.cs.form.models;

public class Nisit extends User{
    String userStatus;
    int loginAttempt;
    public Nisit(String name, String username, String password) {
        super(name, username, password);
        this.userStatus = "active";
        this.loginAttempt = 0;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public int getLoginAttempt() {
        return loginAttempt;
    }
}
