package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import com.github.saacsos.FXRouter;
import ku.cs.form.models.Admin;


public class AdminController {

    @FXML private Label adminGreetingLabel;
    private Admin admin;

    @FXML
    public void initialize() {
        admin = new Admin("admin","AdminInwza007","123456");
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


}
