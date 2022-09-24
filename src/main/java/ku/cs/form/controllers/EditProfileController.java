package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ku.cs.form.models.User;
import ku.cs.form.services.UserData;

public class EditProfileController {

    @FXML private TextField newPasswordTextField;
    @FXML private TextField confirmNewPasswordTextField;
    @FXML private Label incorrectWarningText;
    private User user;
    @FXML private Color rectangleColor;
    @FXML private Color textColor;
    @FXML private Color backgroundColor;
    @FXML private ColorPicker rectangleColorPicker;
    @FXML private ColorPicker textColorPicker;
    @FXML private ColorPicker backgroundColorPicker;

    @FXML private Rectangle rightRec;
    @FXML private Rectangle leftRec;
    @FXML private Button confirmButton;
    @FXML private Pane pane;
    @FXML private Label passwordLabel;
    @FXML private Label confirmPasswordLabel;
    @FXML private Label changeRectangleColorLabel;
    @FXML private Label changeTextColorLabel;
    @FXML private Label changeFontLabel;
    @FXML private Label changeButtonColorLabel;
    @FXML private Label changeBackgroundColorLabel;
    @FXML private Label warningLabel;
    @FXML public void initialize() {
        user = (User) com.github.saacsos.FXRouter.getData();

        rectangleColor = user.getRectangleColor();
        textColor = user.getTextColor();
        backgroundColor = user.getBackgroundColor();


        rightRec.setFill(rectangleColor);
        leftRec.setFill(rectangleColor);

        //pane.setBackground(new Background(new BackgroundFill(backgroundColor,null,null)));

        warningLabel.setTextFill(textColor);
        passwordLabel.setTextFill(textColor);
        confirmPasswordLabel.setTextFill(textColor);
        changeBackgroundColorLabel.setTextFill(textColor);
        changeButtonColorLabel.setTextFill(textColor);
        changeRectangleColorLabel.setTextFill(textColor);
        changeFontLabel.setTextFill(textColor);
        changeTextColorLabel.setTextFill(textColor);
    }



    public void handleBackButton(ActionEvent actionEvent) {
        try{
            com.github.saacsos.FXRouter.goTo("nisitPage",user);
        } catch (Exception e){
            System.out.println("Path มีปัญหาละพ่อหนุ่ม");
        }
    }

    public void handleConfirmButton(ActionEvent actionEvent){
        UserData file = new UserData("data","users.csv");
        String newPassword = newPasswordTextField.getText();
        String newConfirmPassword = confirmNewPasswordTextField.getText();
        if (newPassword.equals(newConfirmPassword)) {
            if (!newPassword.trim().equals("")){
                user.setPassword(newPassword);}
                user.setRectangleColor(rectangleColor);
                user.setBackgroundColor(backgroundColor);
                user.setTextColor(textColor);
                file.changeData(user);
                try {
                    com.github.saacsos.FXRouter.goTo("nisitPage", user);
                } catch (Exception e) {
                    System.out.println("Path มึงเหี้ยไอ้ควาย");
                }
        }
        else{
            incorrectWarningText.setText("รหัสผ่านไม่ตรงกันนะพ่อหนุ่ม :<");
        }
    }

    public void handleRectangleColorPickerButton(ActionEvent actionEvent){
        rectangleColor = rectangleColorPicker.getValue();
        rightRec.setFill(rectangleColor);
        leftRec.setFill(rectangleColor);
    }

    public void handleBackgroundColorPickerButton(ActionEvent actionEvent){
        backgroundColor = backgroundColorPicker.getValue();
        pane.setBackground(new Background(new BackgroundFill(backgroundColor,null,null)));
    }
    public void handleTextColorPickerButton(ActionEvent actionEvent){
        textColor = textColorPicker.getValue();

        warningLabel.setTextFill(textColor);
        passwordLabel.setTextFill(textColor);
        confirmPasswordLabel.setTextFill(textColor);
        changeBackgroundColorLabel.setTextFill(textColor);
        changeButtonColorLabel.setTextFill(textColor);
        changeRectangleColorLabel.setTextFill(textColor);
        changeFontLabel.setTextFill(textColor);
        changeTextColorLabel.setTextFill(textColor);
        confirmButton.setTextFill(textColor);
    }

}

