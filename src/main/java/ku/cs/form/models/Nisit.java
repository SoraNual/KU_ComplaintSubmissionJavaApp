package ku.cs.form.models;

import javafx.scene.paint.Color;

public class Nisit extends User{
    private Color rectangleColor;
    private Color textColor;
    private Color backgroundColor;
    private Color buttonColor;
    private Color borderColor;

    public Nisit(String name, String username, String password,String rectangleColorWeb,String textColorWeb,String backgroundColorWeb,String buttonColorWeb,String borderColorWeb) {
        super(name, username, password);
        rectangleColor = Color.web(rectangleColorWeb);
        textColor = Color.web(textColorWeb);
        backgroundColor = Color.web(backgroundColorWeb);
        buttonColor = Color.web(buttonColorWeb);
        borderColor = Color.web(borderColorWeb);
    }

    @Override
    public Color getButtonColor() {
        return buttonColor;
    }

    @Override
    public void setButtonColor(Color buttonColor) {
        this.buttonColor = buttonColor;
    }

    @Override
    public Color getBorderColor() {
        return borderColor;
    }

    @Override
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    @Override
    public String toString() {
        return getUsername()+","+getPassword()+",nisit,"+getName()+","+getUserStatus()+","+getLoginAttempt()+","+getAgency()+","+ rectangleColor +","+textColor+","+backgroundColor+","+buttonColor+","+borderColor;
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
