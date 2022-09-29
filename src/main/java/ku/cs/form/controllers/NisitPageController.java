package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.form.models.User;
import ku.cs.form.services.LoginTimeFileDataSource;

import java.io.IOException;

public class NisitPageController {

    @FXML private Label nameLabel;
    @FXML private ImageView nisitImage;
    private User user;


    @FXML public void initialize() {
        user = (User) com.github.saacsos.FXRouter.getData();
        nameLabel.setText(user.getName());
        //String url = getClass().getResource(user.getProfileImageFilePath()).toExternalForm();
        //nisitImage.setImage(new Image(url));
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
    public void handleReportButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("report",user);
        } catch (IOException e) {
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}
