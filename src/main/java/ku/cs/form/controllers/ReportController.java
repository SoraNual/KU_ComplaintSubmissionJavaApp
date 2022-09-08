package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class ReportController {
    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("nisitPage");
        } catch (IOException e) {
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
