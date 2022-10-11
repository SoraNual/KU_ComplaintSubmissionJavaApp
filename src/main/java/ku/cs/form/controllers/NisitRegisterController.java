package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.form.models.Nisit;
import ku.cs.form.services.NisitRegistration;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.*;

public class NisitRegisterController {

    @FXML private TextField nameTextField;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;

    @FXML
    private ImageView regis_pic;
    @FXML public void initialize() {
        String url = getClass().getResource("/ku/cs/images/register_pic.png").toExternalForm();
        regis_pic.setImage(new Image(url));
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("home");
        } catch (IOException e) {
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleRegisterButton(ActionEvent actionEvent) {

        String name = nameTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        NisitRegistration reg = new NisitRegistration("data","users.csv");
        String error = reg.registrationCheck(name,username,password,confirmPassword);

        if(error.isBlank()){
            Nisit newNisit = new Nisit(name, username, password,"#666666FF","#FFFFFFFF","#333333FF","#1A1A1AFF");
            reg.addNisit(newNisit);
            showPopUp("Registration successful!","Hello Welcome!",null);
            try {
                com.github.saacsos.FXRouter.goTo("login");
            } catch (IOException e) {
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        } else{
            showPopUp(error,"ERROR",null);
        }
    }

    public static void showPopUp(String infoMessage, String titleBar, String headerMessage)
    {
        Alert alert;
        if(titleBar != null && titleBar.equals("ERROR")) alert = new Alert(AlertType.ERROR);
        else alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }


}

