package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import ku.cs.form.models.Report;
import ku.cs.form.models.User;
import ku.cs.form.services.ReportFileDataSource;

import java.io.File;
import java.io.IOException;

public class ReportController {
    private User user;
    private Report report;
    @FXML private TextField topicTextField;
    @FXML private TextArea detailTextArea;
    @FXML private ChoiceBox<String> categoryChoiceBox;
    @FXML private Label warningLabel;
    @FXML public void initialize() {
        user = (User) com.github.saacsos.FXRouter.getData();
        categoryChoiceBox.getItems().add("มีคาวบอยแอบตุ๋ยเด็กครับ");
    }
    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("nisitPage", user);
        } catch (IOException e) {
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleSendButton(ActionEvent actionEvent){
        report = new Report(topicTextField.getText(),categoryChoiceBox.getValue(),detailTextArea.getText());
        System.out.println(report.toString());
        if(report.getTopic().trim().equals("") || report.getDetail().trim().equals("") || report.getCategory()==null){
            warningLabel.setText("กรอกให้ครบทุกช่องโว้ยยยยยยยยยยยยยยยยยย");
        } else{
            report.changeData();
            try {
                com.github.saacsos.FXRouter.goTo("nisitPage", user);
            } catch (IOException e) {
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }
}
