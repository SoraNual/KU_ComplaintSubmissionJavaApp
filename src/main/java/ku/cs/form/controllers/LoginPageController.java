package ku.cs.form.controllers;

import com.github.saacsos.FXRouter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.form.models.*;
import ku.cs.form.services.LoginTimeFileDataSource;
import ku.cs.form.services.UserDataSource;

import java.io.*;

public class LoginPageController {
    @FXML private ImageView img;
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    @FXML private Label incorrectWarningText;
    private User user;
    @FXML public void initialize() {
        String url = getClass().getResource("/ku/cs/images/catMeow.png").toExternalForm();
        img.setImage(new Image(url));
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("home");
        } catch (IOException e) {
            System.out.println("ไม่สามารถไปหน้า Home ได้");
        }
    }

    @FXML public void handleLoginButton(ActionEvent actionEvent) {

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        UserDataSource unclarifiedUser = new UserDataSource("data","users.csv");
        user = unclarifiedUser.usernamePasswordCheck(username,password);
        try {
            if (user instanceof Admin) {
                addLoginTime();
                FXRouter.goTo("admin",user);
            } else if (user instanceof Staff) {
                addLoginTime();
                FXRouter.goTo("newStaff",user);
            } else if (user instanceof Nisit) {
                addLoginTime();
                FXRouter.goTo("nisitPage",user);
            } else{
                incorrectWarningText.setText("Incorrect username or password");
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void addLoginTime(){
        LoginTimeFileDataSource loginTimeFileDataSource = new LoginTimeFileDataSource("data","loginTime.csv");
        UserList users = loginTimeFileDataSource.readData();
        users.addUser(user);
        loginTimeFileDataSource.writeData(users);
    }
}
