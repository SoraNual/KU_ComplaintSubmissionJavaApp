package ku.cs.form.models;

import javafx.scene.paint.Color;

public class Nisit extends User{
    Color rectangleColor;
    Color textColor;
    Color backgroundColor;

    public Nisit(String name, String username, String password,String rectangleColorWeb,String textColorWeb,String backgroundColorWeb) {
        super(name, username, password);
        rectangleColor = Color.web(rectangleColorWeb);
        textColor = Color.web(textColorWeb);
        backgroundColor = Color.web(backgroundColorWeb);
    }

    @Override
    public String toString() {
        return getUsername()+","+getPassword()+",nisit,"+getName()+","+getUserStatus()+","+getLoginAttempt()+","+getAgency()+","+ rectangleColor +","+textColor+","+backgroundColor;
    }

    @Override
    public Color getRectangleColor() {
        return rectangleColor;
    }

    @Override
    public Color getTextColor() {
        return textColor;
    }

    @Override
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public void setRectangleColor(Color rectangleColor) {
        this.rectangleColor = rectangleColor;
    }

    @Override
    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    @Override
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
