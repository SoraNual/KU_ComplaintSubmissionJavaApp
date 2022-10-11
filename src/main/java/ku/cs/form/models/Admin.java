package ku.cs.form.models;

public class Admin extends User{

    public Admin(String name, String username, String password,String rectangleColorWeb,String textColorWeb,String backgroundColorWeb,String buttonColorWeb) {
        super(name, username, password,rectangleColorWeb,textColorWeb,backgroundColorWeb,buttonColorWeb);
    }

    public Admin(String name, String username, String password) {
        super(name, username, password);
    }
}
