package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import ku.cs.form.models.*;
import ku.cs.form.services.SetTheme;
import ku.cs.form.services.UserDataSource;

public class EditProfileController {


    private User user;
    @FXML private Color rectangleColor;
    @FXML private Color textColor;
    @FXML private Color backgroundColor;
    @FXML private Color buttonColor;
    @FXML private ColorPicker rectangleColorPicker;
    @FXML private ColorPicker textColorPicker;
    @FXML private ColorPicker backgroundColorPicker;
    @FXML private ColorPicker buttonColorPicker;
    @FXML private Rectangle sampleRec;
    @FXML private Rectangle sampleBackground;
    @FXML private Rectangle sampleListView;
    @FXML private Rectangle sampleButton;
    @FXML private Label sampleLabel;
    private UserDataSource userDataSource;
    private UserList userList;
    @FXML public void initialize() {
        user = (User) com.github.saacsos.FXRouter.getData();
        userDataSource = new UserDataSource("data","users.csv");
        userList = userDataSource.readData();
    }

    public void handleBackButton(ActionEvent actionEvent) {
        try{
            com.github.saacsos.FXRouter.goTo("nisitPage",user);
        } catch (Exception e){
            System.out.println("Path มีปัญหาละพ่อหนุ่ม");
        }
    }

    public void handleConfirmButton(ActionEvent actionEvent){
        String rectangleColor_toHEX =  ("#" + rectangleColor).replace("0x","").toUpperCase();
        String backgroundColor_toHEX = ("#"+backgroundColor).replace("0x","").toUpperCase();
        String textColor_toHEX = ("#"+textColor).replace("0x","").toUpperCase();
        String buttonColor_toHEX = ("#"+buttonColor).replace("0x","").toUpperCase();

        SetTheme setTheme = new SetTheme(user.getUsername());

        System.out.println(rectangleColor_toHEX);
        System.out.println(backgroundColor_toHEX);
        System.out.println(textColor_toHEX);
        System.out.println(buttonColor_toHEX);

        setTheme.setNewTheme(textColor_toHEX,backgroundColor_toHEX,rectangleColor_toHEX,buttonColor_toHEX);

        userDataSource.changeData(user);
        if(user instanceof Admin) {
            try {
                com.github.saacsos.FXRouter.goTo("admin", user);
            } catch (Exception e) {
                System.out.println("Path มีปัญหาละพ่อหนุ่ม");
            }
        } else if (user instanceof Staff) {
            try {
                com.github.saacsos.FXRouter.goTo("staffPage", user);
            } catch (Exception e) {
                System.out.println("Path มีปัญหาละพ่อหนุ่ม");
            }
        } else if (user instanceof Nisit) {
            try {
                com.github.saacsos.FXRouter.goTo("nisitPage", user);
            } catch (Exception e) {
                System.out.println("Path มีปัญหาละพ่อหนุ่ม");
            }
        }
    }

    public void handleRectangleColorPickerButton(ActionEvent actionEvent){
        rectangleColor = rectangleColorPicker.getValue();
        sampleRec.setFill(rectangleColor);
        sampleRec.setStroke(rectangleColor);
        sampleListView.setFill(rectangleColor);
    }

    public void handleBackgroundColorPickerButton(ActionEvent actionEvent){
        backgroundColor = backgroundColorPicker.getValue();
        sampleBackground.setFill(backgroundColor);
    }
    public void handleTextColorPickerButton(ActionEvent actionEvent){
        textColor = textColorPicker.getValue();
        sampleLabel.setTextFill(textColor);
    }


    public void handleButtonColorPickerButton(ActionEvent actionEvent){
        buttonColor = buttonColorPicker.getValue();
        sampleButton.setFill(buttonColor);
    }

}

