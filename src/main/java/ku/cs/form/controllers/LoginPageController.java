package ku.cs.form.controllers;

import com.github.saacsos.FXRouter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.form.models.Admin;
import ku.cs.form.models.Nisit;
import ku.cs.form.models.Staff;
import ku.cs.form.models.User;
import ku.cs.form.services.UserData;

import java.io.*;

public class LoginPageController {
    @FXML private ImageView img;
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
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
        UserData unclarifiedUser = new UserData("data","users.csv");
        user = unclarifiedUser.usernamePasswordCheck(username,password);
        try {
            if (user instanceof Admin) {
                FXRouter.goTo("admin",user);
            } else if (user instanceof Staff) {
                FXRouter.goTo("newStaff",user);
            } else if (user instanceof Nisit) {
                FXRouter.goTo("nisitPage",user);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
