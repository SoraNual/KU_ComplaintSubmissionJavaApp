package ku.cs.form.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ku.cs.form.models.*;
import ku.cs.form.services.ReportCategoryDataSource;
import ku.cs.form.services.ReportFileDataSource;

import java.io.IOException;

public class ReportController {
    private User user;
    private Report report;
    @FXML private TextField topicTextField;
    @FXML private TextArea detailTextArea;
    @FXML private ChoiceBox<ReportCategory> categoryChoiceBox;
    private ReportCategoryDataSource reportCategoryDataSource;
    private ReportCategoryList reportCategories;
    private ReportList reportList;
    private ReportFileDataSource reportFileDataSource;
    @FXML private Label warningLabel;
    @FXML private Label additionalDetailLabel;
    @FXML private Label additionalImageLabel;
    @FXML private TextArea additionalDetailTextArea;
    @FXML private Button uploadImgButton;
    @FXML public void initialize() {
        reportCategoryDataSource = new ReportCategoryDataSource("data","reportCategory.csv");
        reportCategories = reportCategoryDataSource.readData();

        reportFileDataSource = new ReportFileDataSource("data","reports.csv");
        reportList = reportFileDataSource.readData();

        user = (User) com.github.saacsos.FXRouter.getData();
        categoryChoiceBox.getItems().addAll(reportCategories.getAllCategories());
        warningLabel.setText("");
        additionalDetailLabel.setText("");
        additionalImageLabel.setText("");
        additionalDetailTextArea.setVisible(false);
        uploadImgButton.setVisible(false);

        handleCategoryChoiceBox();
    }
    private void handleCategoryChoiceBox(){
        categoryChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<ReportCategory>() {
                    @Override
                    public void changed(ObservableValue<? extends ReportCategory>
                                                observable,
                                        ReportCategory oldValue, ReportCategory newValue) {
                        showSelectedCategory(newValue);
                    }
                });
    }

    private void showSelectedCategory(ReportCategory reportCategory){

        if(reportCategory.getImageNeeded() == true){
            additionalImageLabel.setText(reportCategory.getAdditionalImageTitle());
            uploadImgButton.setVisible(true);
        }else{
            additionalImageLabel.setText("");
            uploadImgButton.setVisible(false);
        }
        additionalDetailLabel.setText(reportCategory.getAdditionalDetailTitle());
        additionalDetailTextArea.setVisible(true);
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
    public void handleSubmitButton(ActionEvent actionEvent) {
        report = new Report(topicTextField.getText(), user.getUsername(), categoryChoiceBox.getValue().getName(), detailTextArea.getText());
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
