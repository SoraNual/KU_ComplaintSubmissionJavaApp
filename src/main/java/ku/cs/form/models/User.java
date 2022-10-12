package ku.cs.form.models;

import javafx.scene.paint.Color;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {
    private final String name;
    private String username;
    private String password;
    private LocalDateTime loginTime;
    private String profileImageFilePath;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); //for change date format
    private String userStatus;
    private int loginAttempt;
    protected String rectangleColor;
    protected String textColor;
    protected String backgroundColor;
    protected String buttonColor;

    public User(String name, String username, String password, String rectangleColor, String textColor, String backgroundColor, String buttonColor) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.loginTime = null; //default time = recently time
        this.userStatus = "active";
        this.loginAttempt = 0;
        this.rectangleColor = rectangleColor;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
        this.buttonColor = buttonColor;
        setProfileImage();
    }

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getLoginTime() {
        if (loginTime == null) return "ยังไม่มีการใช้งาน";
        return loginTime.format(format);
    }


    public void setLoginTime(String s) {
        //change string to LocalDateTime
        if(s.equals("ยังไม่มีการใช้งาน")) this.loginTime = null;
        else this.loginTime = LocalDateTime.parse(s,format);
    }

    public void setProfileImage() {
        String fileName = username + ".jpg";
        String directoryName = "data" + File.separator + "img";
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        if (!file.exists()) {
            fileName = "default.jpg";
            filePath = directoryName + File.separator + fileName;
        }
        profileImageFilePath = filePath;
    }

    public void bannedLoginAttempt() {
        loginAttempt++;
    }

    public String getProfileImageFilePath() {
        return profileImageFilePath;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public int getLoginAttempt() {
        return loginAttempt;
    }

    @Override
    public String toString() {
        //loginTime.format(format) => change LocalDateTime to string
        return getLoginTime() + "," + getUsername() + "," + getPassword() + "," + this.getClass().getSimpleName().toLowerCase() + "," + getName() + "," + getUserStatus() + "," + getLoginAttempt() + "," + "," + rectangleColor + "," + textColor + "," + backgroundColor + "," + buttonColor;
    }

    public String toStringLoginTime() {
        return loginTime.format(format);
    }

    public DateTimeFormatter getFormat() {
        return format;
    }



    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }



    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public void setProfileImageFilePath(String profileImageFilePath) {
        this.profileImageFilePath = profileImageFilePath;
    }

    public void setLoginAttempt(int loginAttempt) {
        this.loginAttempt = loginAttempt;
    }

    public String getRectangleColor() {
        return rectangleColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public String getButtonColor() {
        return buttonColor;
    }

    public void setRectangleColor(String rectangleColor) {
        this.rectangleColor = rectangleColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setButtonColor(String buttonColor) {
        this.buttonColor = buttonColor;
    }
}
