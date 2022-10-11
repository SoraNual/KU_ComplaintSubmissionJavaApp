package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.github.saacsos.FXRouter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class HomeController {
    @FXML private ImageView cat;


    @FXML
    public void initialize() {
        String url = getClass().getResource("/ku/cs/images/catSayHi.gif").toExternalForm();
        cat.setImage(new Image(url));
    }

    @FXML
    public void handleLoginButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า Login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
            e.printStackTrace();
        }
    }

    public void handleRegisterButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("register");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า register ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    public void handleInstructionButton(ActionEvent actionEvent) {
        /*try {
            //FXRouter.goTo("instruction");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า instruction ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }*/
    }

    public void handleCreatorButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("creatorTeam");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า creator ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
