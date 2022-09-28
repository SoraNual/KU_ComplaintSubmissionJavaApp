package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ku.cs.form.models.Staff;

import java.io.IOException;

public class NewStaffPageController {
    @FXML
    private Label agencyLabel;

    @FXML
    private Label nameLabel;

    @FXML
    public void initialize(Staff staff) {
        nameLabel.setText(staff.getName());
        agencyLabel.setText(staff.getAgency());
    }

    @FXML
    public void handleChangePasswordButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("newStaffChangePassword");
        } catch (IOException e) {
            System.out.println("ไม่สามารถไปหน้า Change Password ได้");
        }
    }
}
