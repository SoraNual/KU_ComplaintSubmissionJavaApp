package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class RegisterController {

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
}

