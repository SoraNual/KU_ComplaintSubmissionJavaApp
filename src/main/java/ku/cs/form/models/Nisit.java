package ku.cs.form.models;

import javafx.scene.paint.Color;

public class Nisit extends User{

    private String status;
    public Nisit(String name, String username, String password,String rectangleColorWeb,String textColorWeb,String backgroundColorWeb,String buttonColorWeb) {
        super(name, username, password,rectangleColorWeb,textColorWeb,backgroundColorWeb,buttonColorWeb);
    }

    public Nisit(String name, String username, String password) {
        super(name, username, password);
    }

}
