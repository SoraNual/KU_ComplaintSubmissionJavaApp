package ku.cs.form.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ku.cs.form.models.Report;
import ku.cs.form.models.ReportList;
import ku.cs.form.models.User;
import ku.cs.form.services.ReportCategoryDataSource;
import ku.cs.form.services.ReportFileDataSource;
import ku.cs.form.services.UserDataSource;

import java.io.IOException;
import java.util.ArrayList;

public class ReportController {
    private User user;
    private Report report;
    @FXML private TextField topicTextField;
    @FXML private TextArea detailTextArea;
    @FXML private ChoiceBox<String> categoryChoiceBox;
    private ReportCategoryDataSource reportCategoryDataSource;
    private ArrayList<String> reportCategory;
    private ReportList reportList;
    private ReportFileDataSource reportFileDataSource;
    @FXML private Label warningLabel;
    @FXML public void initialize() {
        reportCategoryDataSource = new ReportCategoryDataSource("data","reportCategory.csv");
        reportCategory = reportCategoryDataSource.readData();

        reportFileDataSource = new ReportFileDataSource("data","reports.csv");
        reportList = reportFileDataSource.readData();

        user = (User) com.github.saacsos.FXRouter.getData();
        categoryChoiceBox.getItems().addAll(reportCategory);
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("nisitPage");
        } catch (IOException e) {
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleSubmitButton(ActionEvent actionEvent) {
        report = new Report(topicTextField.getText(), user.getUsername(), categoryChoiceBox.getValue(), detailTextArea.getText());
        System.out.println(report);
        if (report.getTopic().trim().isBlank() || report.getDetail().trim().isBlank() || report.getCategory() == null) {
            warningLabel.setText("โปรดกรอกให้ครบทุกช่อง");
        } else {
            reportList.addReport(report);
            reportFileDataSource.writeData(reportList);
            try {

                com.github.saacsos.FXRouter.goTo("nisitPage");
            } catch (IOException e) {
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }
}
