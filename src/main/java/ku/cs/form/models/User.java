package ku.cs.form.models;

import javafx.scene.image.Image;

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

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public void setLoginAttempt(int loginAttempt) {
        this.loginAttempt = loginAttempt;
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

    public String toStringLoginTime() {
        return loginTime.format(format);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", loginTime=" + loginTime +
                ", profileImageFilePath='" + profileImageFilePath + '\'' +
                ", format=" + format +
                ", userStatus='" + userStatus + '\'' +
                ", loginAttempt=" + loginAttempt +
                '}';
    }
}
