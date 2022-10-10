package ku.cs.form.controllers;

import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.form.models.User;
import ku.cs.form.models.UserList;
import ku.cs.form.services.NisitRegistration;
import ku.cs.form.services.UserData;

import java.io.IOException;

public class NewStaffChangePasswordPageController {
    @FXML
    private ImageView staffRegisPic;
    @FXML
    private TextField username;
    @FXML
    private PasswordField oldPassword;
    @FXML
    private PasswordField newPassword;
    @FXML
    private PasswordField confirmPassword;
    private User user;

    @FXML
    public void initialize() {
        String url = getClass().getResource("/ku/cs/images/bleh.jpeg").toExternalForm();
        staffRegisPic.setImage(new Image(url));
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("newStaff");
        } catch (IOException e) {
            System.out.println("ไม่สามารถกลับหน้า New Staff Page ได้");
        }
    }

    @FXML
    public void handleChangePasswordButton(ActionEvent actionEvent) {
        // TODO
    }
}

