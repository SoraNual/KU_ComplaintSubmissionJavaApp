package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class StaffPageController {
    @FXML private Label agencyLabel;
//    @FXML private ListView<> reportListView;

    @FXML
    public void handleChangePasswordButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("changePassword");
        } catch (IOException e) {
            System.out.println("ไม่สามารถไปหน้า Change Password ได้");
        }
    }

    @FXML
    public void getReportsListView() {
        // TODO
    }

}
