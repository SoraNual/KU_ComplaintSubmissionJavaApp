package ku.cs.form.models;

import javafx.scene.paint.Color;

public class Nisit extends User{

    private String userStatus;
    private int loginAttempt;
    public Nisit(String name, String username, String password) {
        super(name, username, password);
    }
    public void bannedLoginAttempt() {
        loginAttempt++;
    }
    public String getUserStatus() {
        return userStatus;
    }

    public int getLoginAttempt() {
        return loginAttempt;
    }
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
    public void setLoginAttempt(int loginAttempt) {
        this.loginAttempt = loginAttempt;
    }

    @Override
    public String toString() {
        return getLoginTime() + "," + getUsername() + "," + getPassword() + "," + this.getClass().getSimpleName().toLowerCase() + "," + getName() + "," + getUserStatus() + "," + getLoginAttempt();
    }
}
