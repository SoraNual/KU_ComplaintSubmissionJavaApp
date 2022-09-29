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
    private String Agency;
    private Color rectangleColor;
    private Color textColor;
    private Color backgroundColor;
    private Color buttonColor;
    private Color borderColor;
    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.loginTime = LocalDateTime.now(); //default time = recently time
        this.userStatus = "active";
        this.loginAttempt = 0;
        setProfileImage();
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
         return loginTime.format(format);
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setLoginTime(String s) {
        //change string to LocalDateTime
        this.loginTime = LocalDateTime.parse(s,format);
    }
    private void setProfileImage() {
            String fileName = username + ".jpg";
            String directoryName = "data" + File.separator + "img";
            String filePath = directoryName + File.separator + fileName;
            File file = new File(filePath);

            if(!file.exists()) {
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
        return username + "," + password + "," + this.getClass() + "," + getName() + "," + userStatus + "," + loginAttempt + "," + Agency;
    }

    public String toStringLoginTime() {
        return loginTime.format(format);
    }

    public DateTimeFormatter getFormat() {
        return format;
    }

    public String getAgency() {
        return Agency;
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

    public void setAgency(String agency) {
        Agency = agency;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    public Color getRectangleColor() {
        return rectangleColor;
    }

    public void setRectangleColor(Color rectangleColor) {
        this.rectangleColor = rectangleColor;
    }

    public Color getButtonColor() {
        return buttonColor;
    }


    public void setButtonColor(Color buttonColor) {
        this.buttonColor = buttonColor;
    }
}
