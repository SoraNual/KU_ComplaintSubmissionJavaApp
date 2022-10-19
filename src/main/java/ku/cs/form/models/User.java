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

    protected String rectangleColor;
    protected String textColor;
    protected String backgroundColor;
    protected String buttonColor;

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.loginTime = null; //default time = recently time
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



    public String getProfileImageFilePath() {
        return profileImageFilePath;
    }

    @Override
    public String toString() {
        return getLoginTime() + "," + getUsername() + "," + getPassword() + "," + this.getClass().getSimpleName().toLowerCase() + "," + getName() + "," + "active,0";
    }

    public DateTimeFormatter getFormat() {
        return format;
    }

    public void setPassword(String password) {
        this.password = password;
    }




    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public void setProfileImageFilePath(String profileImageFilePath) {
        this.profileImageFilePath = profileImageFilePath;
    }



}
