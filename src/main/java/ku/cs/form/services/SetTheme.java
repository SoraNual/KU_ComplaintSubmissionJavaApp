package ku.cs.form.services;

import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import ku.cs.form.models.User;


public class SetTheme {
    private User user;

    public SetTheme(User user) {
        this.user = user;
    }

    public void setObject(Rectangle rectangle){
        rectangle.setStyle("-fx-fill: "+user.getRectangleColor()+";"
                            +"-fx-stroke: none;");
    }

    public void setObject(VBox rectangle) { // similar to Rectangle but VBox
        rectangle.setStyle("-fx-background-color: "+user.getRectangleColor()+";");
    }

    public void setObject(Pane pane){
        pane.setStyle("-fx-background-color: "+user.getBackgroundColor()+";");
    }
    public void setObject(Button button){
        button.setStyle("-fx-background-color: "+user.getButtonColor()+";"
                        +"-fx-border-color: none;"
                        +"-fx-background-radius : 13;"
                        +"-fx-border-radius : 13;"
                        +"-fx-text-fill :"+user.getTextColor());
        button.hoverProperty().addListener((observable, oldValue, newValue) ->{
            if(newValue){
                button.setStyle("-fx-background-color: "+hoverColor(user.getButtonColor())+";"
                                +"-fx-border-color: none;"
                                +"-fx-background-radius : 13;"
                                +"-fx-border-radius : 13;"
                                +"-fx-text-fill :"+user.getTextColor()
                                );
                button.setCursor(Cursor.HAND);
            } else{
                button.setStyle("-fx-background-color: "+user.getButtonColor()+";"
                        +"-fx-border-color: none;"
                        +"-fx-background-radius : 13;"
                        +"-fx-border-radius : 13;"
                        +"-fx-text-fill :"+user.getTextColor());
            }
        });
    }
    public void setObject(ColorPicker colorPicker){
        colorPicker.setStyle("-fx-background-color: "+user.getButtonColor()+";"
                +"-fx-border-color: none;"
                +"-fx-background-radius : 13;"
                +"-fx-border-radius : 13;"
        );
        colorPicker.hoverProperty().addListener((observable, oldValue, newValue) ->{
            if(newValue){
                colorPicker.setStyle("-fx-background-color: "+hoverColor(user.getButtonColor())+";"
                        +"-fx-border-color: none;"
                        +"-fx-background-radius : 13;"
                        +"-fx-border-radius : 13;"
                );
                colorPicker.setCursor(Cursor.HAND);
            } else{
                colorPicker.setStyle("-fx-background-color: "+user.getButtonColor()+";"
                        +"-fx-border-color: none;"
                        +"-fx-background-radius : 13;"
                        +"-fx-border-radius : 13;"
                );
            }
        });
    }

    public void setObject(Label label){
        label.setStyle("-fx-text-fill :"+user.getTextColor()+";");
    }


    public void setObject(TextField textField){
        textField.setStyle("-fx-background-color: "+user.getButtonColor()+";"
                            +"-fx-border-color: none;"
                            +"-fx-background-radius : 13;"
                            +"-fx-border-radius : 13;");
    }
    public void setObject(TextArea textArea){
        textArea.setStyle("-fx-background-color: "+user.getButtonColor()+";"
                +"-fx-border-color: none;"
                +"-fx-background-radius : 13;"
                +"-fx-border-radius : 13;");
    }
    public void setObject(ListView listView){
        listView.setStyle("-fx-background-color: "+user.getRectangleColor()+";"
                +"-fx-border-color: none;"
                +"-fx-selection-bar:"+hoverColor(user.getRectangleColor())+";"
                +"-fx-background-radius: 13;"
                +"-fx-border-radius: 13;"
                +"-fx-control-inner-background: " + user.getRectangleColor() + ";"
        );
    }
    public void setObject(ComboBox comboBox){
        comboBox.setStyle("-fx-background-color: "+user.getButtonColor()+";"
                +"-fx-border-color: none;"
                +"-fx-background-radius : 13;"
                +"-fx-border-radius : 13;"
                +"-fx-text-fill: "+user.getTextColor()+";"
        );
    }
    public void setObject(ChoiceBox choiceBox){
        choiceBox.setStyle("-fx-background-color: "+user.getButtonColor()+";"
                +"-fx-border-color: none;"
                +"-fx-background-radius: 13;"
                +"-fx-border-radius: 13;"
        );
    }

    public void setInvisibleBackgroundButton(Button button){
        button.setStyle("-fx-background-color: none;"
                +"-fx-border-color: none;"
                +"-fx-background-radius : 13;"
                +"-fx-border-radius : 13;"
                +"-fx-text-fill :"+user.getTextColor()+";");
        button.hoverProperty().addListener((observable, oldValue, newValue) ->{
            if(newValue){
                button.setStyle("-fx-background-color: none;"
                        +"-fx-border-color: none;"
                        +"-fx-background-radius : 13;"
                        +"-fx-border-radius : 13;"
                        +"-fx-underline: true;"
                        +"-fx-text-fill :"+user.getTextColor()
                );
                button.setCursor(Cursor.HAND);
            } else{
                button.setStyle("-fx-background-color: none;"
                        +"-fx-border-color: none;"
                        +"-fx-background-radius : 13;"
                        +"-fx-border-radius : 13;"
                        +"-fx-text-fill :"+user.getTextColor());
            }
        });
    }
    public void setObject(PasswordField passwordField){
        passwordField.setStyle("-fx-background-color: "+user.getButtonColor()+";"
                +"-fx-border-color: none;"
                +"-fx-background-radius : 13;"
                +"-fx-border-radius : 13;");
    }
    private String hoverColor(String color){
        color = ("#"+((Color.web(color)).brighter())).replace("0x","").toUpperCase();
        return color;
    }

}
