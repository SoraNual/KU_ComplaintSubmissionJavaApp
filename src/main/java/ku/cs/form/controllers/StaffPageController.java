package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class StaffPageController {
    @FXML String agencyLabel;
//    @FXML private ListView<> reportListView;

    @FXML
    void handleChangePasswordButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("changePassword");
        } catch (IOException e) {
            System.out.println("ไม่สามารถไปหน้า Change Password ได้");
        }
    }

    @FXML
    void getReportsListView() {
        // TODO
    }

}
