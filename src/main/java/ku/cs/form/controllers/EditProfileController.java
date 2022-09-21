package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ku.cs.form.models.User;
import ku.cs.form.services.UserData;

public class EditProfileController {

    @FXML private TextField newPasswordTextField;
    @FXML private TextField confirmNewPasswordTextField;
    @FXML private Label incorrectWarningText;
    private User user;
    @FXML private Color color;
    @FXML private ColorPicker colorPickerButton;

    @FXML private Rectangle rightRec;
    @FXML private Rectangle leftRec;
    @FXML private Button confirmButton;
    @FXML public void initialize() {
        user = (User) com.github.saacsos.FXRouter.getData();
        rightRec.setFill(user.getColor());
        leftRec.setFill(user.getColor());
    }



    public void handleBackButton(ActionEvent actionEvent) {
        try{
            com.github.saacsos.FXRouter.goTo("nisitPage");
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
                user.setColor(color);
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

    public void handleColorPickerButton(ActionEvent actionEvent){
        color = colorPickerButton.getValue();
        rightRec.setFill(color);
        leftRec.setFill(color);

    }
}

