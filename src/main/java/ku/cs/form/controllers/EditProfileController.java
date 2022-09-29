package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import ku.cs.form.models.User;
import ku.cs.form.services.SetTheme;
import ku.cs.form.services.UserData;

public class EditProfileController {

    @FXML private TextField newPasswordTextField;
    @FXML private TextField confirmNewPasswordTextField;
    private User user;
    @FXML private Color rectangleColor;
    @FXML private Color textColor;
    @FXML private Color backgroundColor;
    @FXML private Color borderColor;
    @FXML private Color buttonColor;
    @FXML private ColorPicker rectangleColorPicker;
    @FXML private ColorPicker textColorPicker;
    @FXML private ColorPicker backgroundColorPicker;
    @FXML private ColorPicker borderColorPicker;
    @FXML private ColorPicker buttonColorPicker;

    @FXML private Rectangle rightRec;
    @FXML private Rectangle leftRec;
    @FXML private Rectangle sampleRec;
    @FXML private Rectangle sampleBackground;
    @FXML private Rectangle sampleListView;
    @FXML private Rectangle sampleButton;
    @FXML private Button confirmButton;
    @FXML private Button backButton;
    @FXML private Pane pane;
    @FXML private Label sampleLabel;
    @FXML private Label passwordLabel;
    @FXML private Label confirmPasswordLabel;
    @FXML private Label changeRectangleColorLabel;
    @FXML private Label changeTextColorLabel;
    @FXML private Label changeFontLabel;
    @FXML private Label changeButtonColorLabel;
    @FXML private Label changeBackgroundColorLabel;
    @FXML private Label warningLabel;
    @FXML private Label incorrectWarningLabel;
    @FXML private Label changeBorderColorLabel;
    @FXML private ComboBox<Font> changeFontComboBox;
    @FXML public void initialize() {
        user = (User) com.github.saacsos.FXRouter.getData();
        SetTheme theme = new SetTheme(user);
        rectangleColor = user.getRectangleColor();
        backgroundColor = user.getBackgroundColor();
        textColor = user.getTextColor();
        borderColor = user.getBorderColor();
        buttonColor = user.getButtonColor();

        theme.setObject(rightRec);
        theme.setObject(leftRec);
        theme.setObject(sampleRec);

        theme.setObject(warningLabel);
        theme.setObject(changeBackgroundColorLabel);
        theme.setObject(changeFontLabel);
        theme.setObject(changeRectangleColorLabel);
        theme.setObject(changeTextColorLabel);
        theme.setObject(changeButtonColorLabel);
        theme.setObject(passwordLabel);
        theme.setObject(confirmPasswordLabel);
        theme.setObject(changeBorderColorLabel);
        theme.setObject(sampleLabel);

        theme.setObject(pane);

        theme.setObject(confirmButton,Boolean.FALSE);
        theme.setObject(backButton,Boolean.FALSE);

        theme.setObject(textColorPicker);
        theme.setObject(rectangleColorPicker);
        theme.setObject(backgroundColorPicker);
        theme.setObject(borderColorPicker);

        theme.setObject(changeFontComboBox);

        theme.setObject(newPasswordTextField);
        theme.setObject(confirmNewPasswordTextField);

        sampleBackground.setFill(user.getBackgroundColor());
        sampleButton.setFill(user.getButtonColor());
        sampleListView.setFill(user.getRectangleColor());


        sampleListView.setStroke(user.getBorderColor());
        sampleButton.setStroke(user.getBorderColor());
        sampleRec.setStroke(user.getRectangleColor());
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
                user.setBorderColor(borderColor);
                user.setButtonColor(buttonColor);
                file.changeData(user);
                try {
                    com.github.saacsos.FXRouter.goTo("nisitPage", user);
                } catch (Exception e) {
                    System.out.println("Path มึงเหี้ยไอ้ควาย");
                }
        }
        else{
            incorrectWarningLabel.setText("รหัสผ่านไม่ตรงกันนะพ่อหนุ่ม :<");
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

    public void handleBorderColorPickerButton(ActionEvent actionEvent){
        borderColor = borderColorPicker.getValue();
        sampleListView.setStroke(borderColor);
        sampleButton.setStroke(borderColor);
    }

    public void handleButtonColorPickerButton(ActionEvent actionEvent){
        buttonColor = buttonColorPicker.getValue();
        sampleButton.setFill(buttonColor);
    }

    public void handleChangeFontComboBox(ActionEvent actionEvent){

    }

}

