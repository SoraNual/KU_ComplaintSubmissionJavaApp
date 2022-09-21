package ku.cs.form.models;

import javafx.scene.paint.Color;

public class Nisit extends User{
    Color color;

    public Nisit(String name, String username, String password,String colorWeb) {
        super(name, username, password);
        color = Color.web(colorWeb);
    }

    @Override
    public String toString() {
        return getUsername()+","+getPassword()+",nisit,"+getName()+","+getUserStatus()+","+getLoginAttempt()+","+getAgency()+","+color;
    }

    @Override
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
