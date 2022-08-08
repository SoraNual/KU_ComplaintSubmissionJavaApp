package ku.cs.form.controllers;

import com.github.saacsos.FXRouter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class LoginPageController {
    @FXML private ImageView img;
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
}
