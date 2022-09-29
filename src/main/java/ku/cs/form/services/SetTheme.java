package ku.cs.form.services;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ku.cs.form.models.User;


public class SetTheme {
    private User user;

    public SetTheme(User user) {
        this.user = user;
    }

    public void setObject(Rectangle rectangle){
        rectangle.setFill(user.getRectangleColor());
    }
    public void setObject(Pane pane){
        pane.setBackground(new Background(new BackgroundFill(user.getBackgroundColor(),null,null)));
    }
    public void setObject(Button button,Boolean bool){
        button.setBackground(new Background(new BackgroundFill(user.getButtonColor(),null,null)));
        button.setBorder(new Border(new BorderStroke(user.getBorderColor(), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        button.setTextFill(user.getTextColor());

    }
    public void setObject(ColorPicker colorPicker){
        colorPicker.setBackground(new Background(new BackgroundFill(user.getButtonColor(),null,null)));
        colorPicker.setBorder(new Border(new BorderStroke(user.getBorderColor(), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    public void setObject(Label label){
        label.setTextFill(user.getTextColor());
    }


    public void setObject(TextField textField){
        textField.setBackground(new Background(new BackgroundFill(user.getButtonColor(),null,null)));
        textField.setBorder(new Border(new BorderStroke(user.getBorderColor(),BorderStrokeStyle.SOLID,CornerRadii.EMPTY,BorderWidths.DEFAULT)));
    }
    public void setObject(TextArea textArea){
        textArea.setBackground(new Background(new BackgroundFill(user.getButtonColor(),null,null)));
        textArea.setBorder(new Border(new BorderStroke(user.getBorderColor(),BorderStrokeStyle.SOLID,CornerRadii.EMPTY,BorderWidths.DEFAULT)));
    }
    public void setObject(ListView listView){
        listView.setBackground(new Background(new BackgroundFill(user.getRectangleColor(),null,null)));
        listView.setBorder(new Border(new BorderStroke(user.getBorderColor(),BorderStrokeStyle.SOLID,CornerRadii.EMPTY,BorderWidths.DEFAULT)));
    }
    public void setObject(ComboBox comboBox){
        comboBox.setBorder(new Border(new BorderStroke(user.getBorderColor(),BorderStrokeStyle.SOLID,CornerRadii.EMPTY,BorderWidths.DEFAULT)));
        comboBox.setBackground(new Background(new BackgroundFill(user.getButtonColor(),null,null)));
    }

}
