package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import com.github.saacsos.FXRouter;
import javafx.scene.layout.AnchorPane;
import ku.cs.form.models.User;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.form.services.SetTheme;


public class AdminController {

    @FXML private Label adminGreetingLabel;
    @FXML private ImageView adminProfileImageView;
    @FXML private AnchorPane adminAnchorPane;
    private User admin;

    @FXML
    public void initialize() {
        admin = (User) FXRouter.getData();
        adminAnchorPane.getStylesheets().add("file:src/main/resources/ku/cs/styles/styles.css");
        SetTheme setTheme = new SetTheme(admin.getUsername());
        setTheme.setting();
        showAdminProfile();

    }
    public void handleLoginTimeMenu(ActionEvent actionEvent){
        try {
            FXRouter.goTo("loginTime");
        } catch (IOException e) {
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    public void handleStaffRegisterMenu(ActionEvent actionEvent) throws IOException {
        try {
            FXRouter.goTo("staffRegister",admin);
        } catch (IOException e) {
            throw new IOException(e);
        }

    }

    public void showAdminProfile() {
        adminGreetingLabel.setText(admin.getUsername());
        adminProfileImageView.setImage(new Image("file:" + admin.getProfileImageFilePath()));
    }

    @FXML
    private void handleManageAgencyBtn(ActionEvent actionEvent) {
        try {
           FXRouter.goTo("manageAgency");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleManageCategoryBtn(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("manageCategory");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleUserComplaintBtn(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("userComplaint",admin);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML private void handleThemeBtn(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("editProfile",admin);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
