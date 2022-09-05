package ku.cs.form.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {
    private final String name;
    private String username;
    private String password;
    private LocalDateTime loginTime;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); //for change date format


    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.loginTime = LocalDateTime.now(); //default time = recently time
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getLoginTime() {
         return loginTime;
    }

    public void setLoginTime(String s) {
        //change string to LocalDateTime
        this.loginTime = LocalDateTime.parse(s,format);
    }

    @Override
    public String toString() {
        //loginTime.format(format) => change LocalDateTime to string
        return name + " : " + username + " : " + loginTime.format(format);
    }
}
