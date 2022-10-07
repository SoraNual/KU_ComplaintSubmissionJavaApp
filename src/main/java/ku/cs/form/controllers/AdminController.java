package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;
import com.github.saacsos.FXRouter;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ku.cs.form.models.Admin;


public class AdminController {

    @FXML private Label adminGreetingLabel;
    @FXML private AnchorPane adminAnchorPane;
    private Admin admin;

    @FXML
    public void initialize() {
        admin = new Admin("admin","AdminInwza007","123456","0x669966ff","0x000000ff","0xffffffff","0x008000ff");
        System.out.println(admin.toString());
        showAdminGreetingLabel();
    }
    public void handleLoginTimeMenu(ActionEvent actionEvent){
        try {
            FXRouter.goTo("loginTime");
        } catch (IOException e) {
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    public void handleStaffRegisterMenu(ActionEvent actionEvent){
        try {
            FXRouter.goTo("staffRegister");
        } catch (IOException e) {
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }

    }

    public void showAdminGreetingLabel() {
        adminGreetingLabel.setText("Welcome "+admin.getUsername()+"!");
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
            FXRouter.goTo("userComplaint");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
